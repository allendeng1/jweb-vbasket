package com.jweb.watchdog.aspect.ccattack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.jweb.common.encrypt.MD5;
import com.jweb.common.exception.AuthException;
import com.jweb.common.exception.MyException;
import com.jweb.common.util.DataUtil;
import com.jweb.common.util.DateTimeUtil;
import com.jweb.dao.component.RedisComponent;
import com.jweb.dao.config.SystemConfig;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
@ConditionalOnExpression("${watchdog.ccattack.enable:true}")
public class CCAttackMonitor extends DataUtil{
	
	@Autowired
	private RedisComponent redisComponent;
	@Autowired
	private SystemConfig systemConfig;
	
	private static final String IP_BLACKLIST = "blacklist-ip";
	private ConcurrentHashMap<String, Integer> blacklist = new ConcurrentHashMap<String, Integer>();
	private ConcurrentHashMap<String, List<String>> ipRecord = new ConcurrentHashMap<String, List<String>>();
	private ConcurrentHashMap<String, Set<String>> tokenRecord = new ConcurrentHashMap<String, Set<String>>();
	private ConcurrentHashMap<String, Integer> ignoreMap = new ConcurrentHashMap<String, Integer>();
	
	private static final int SECOND_FREQUENCY_LIMIT = 10;
	private static final int MINUTE_FREQUENCY_LIMIT = 200;
	private static final int TOKEN_DIFFE_IP_LIMIT = 5;
	
	private ExecutorService executor = Executors.newFixedThreadPool(20);
	
	public void securityCheck(String ip, String token) throws MyException{
		
		if(ignoreMap.containsKey(ip)) {
			return;
		}
		
		executor.submit(new CCAttackScanTask(ip, token, DateTimeUtil.nowTime()));
		checkBlacklist(ip);
	}
	
	private void checkBlacklist(String ip) throws MyException{
		String timeMark = getTimeMark();
		if(blacklist.containsKey(timeMark+"-"+ip)) {
			log.warn("拦截异常访问服务IP：{}", ip);
			AuthException.accessDenied();
		}
		boolean isBlacklist = redisComponent.isMapContainKey(IP_BLACKLIST+"-"+timeMark, ip);
		if(isBlacklist) {
			blacklist.put(timeMark+"-"+ip, 1);
			log.warn("拦截异常访问服务IP：{}", ip);
			AuthException.accessDenied();
		}
	}
	
	private String getTimeMark() throws MyException {
		return DateTimeUtil.timeToString(DateTimeUtil.nowTime(), DateTimeUtil.Format.YYYYMMDD_4);
	}
	
	public class CCAttackScanTask implements Callable<Integer>{
		
		private String ip;
		private String token;
		private long dateTime;
		
		CCAttackScanTask(String ip, String token, long dateTime){
			this.ip = ip;
			this.token = token;
			this.dateTime = dateTime;
		}

		@Override
		public Integer call() throws Exception {
			
			if(isNotNull(token)) {
				token = MD5.encrypt(token);
				Set<String> set = tokenRecord.get(token);
				if(set == null) {
					set = new HashSet<String>();
				}
				set.add(ip);
				if(set.size() >= TOKEN_DIFFE_IP_LIMIT) {
					String timeMark = getTimeMark();
					blacklist.put(timeMark+"-"+ip, 1);
					redisComponent.setMap(IP_BLACKLIST+"-"+timeMark, ip, 1);
					tokenRecord.remove(token);
					return 1;
				}
				tokenRecord.put(token, set);
			}
			List<String> lists = ipRecord.get(ip);
			if(!isNullOrEmpty(lists)) {
				if(lists.size() > 50) {
					RLock lock = redisComponent.getLock("ipattck-"+ip);
					boolean isOk = lock.tryLock(0, 3, TimeUnit.SECONDS);
					if(isOk) {
						lists = ipRecord.get(ip);
						Map<String, Integer> scount = new HashMap<String, Integer>();
						Map<String, Integer> mcount = new HashMap<String, Integer>();
						for(String list : lists) {
							JSONObject json = toJsonObject(list);
							String dims = json.getString("dims");
							if(scount.containsKey(dims)) {
								scount.put(dims, scount.get(dims)+1);
							}else {
								scount.put(dims, 1);
							}
							String dimm = json.getString("dimm");
							if(mcount.containsKey(dimm)) {
								mcount.put(dimm, mcount.get(dimm)+1);
							}else {
								mcount.put(dimm, 1);
							}
						}
						
						String timeMark = getTimeMark();
						if(calculateProportion(scount, SECOND_FREQUENCY_LIMIT) >= 0.2 
								|| calculateProportion(mcount, MINUTE_FREQUENCY_LIMIT) >= 0.3) {
							blacklist.put(timeMark+"-"+ip, 1);
							redisComponent.setMap(IP_BLACKLIST+"-"+timeMark, ip, 1);
							ipRecord.remove(ip);
							return 1;
						}
					}
				}
			}else {
				lists = new ArrayList<>();
			}
			JSONObject json = new JSONObject();
			json.put("dims", DateTimeUtil.timeToString(dateTime, DateTimeUtil.Format.YYYYMMDDHHMMSS_4));
			json.put("dimm", DateTimeUtil.timeToString(dateTime, DateTimeUtil.Format.YYYYMMDDHHMM_4));
			lists.add(json.toJSONString());
			ipRecord.put(ip, lists);
		
			return 1;
		}
		
		private double calculateProportion(Map<String, Integer> map, int threshold) {
			Iterator<String> it = map.keySet().iterator();
			int count = 0;
			while(it.hasNext()) {
				String key = it.next();
				if(map.get(key) >= threshold) {
					count++;
				}
			}
			
			return (double)count/(double)map.size();
		}
	}
	
	public void updateIgnoreIp(String ignoreIp) {
		ignoreMap.clear();
		if(isNullOrTrimEmpty(ignoreIp)) {
			return;
		}
		String[] ips = ignoreIp.split(",");
		for(String ip : ips) {
			ignoreMap.put(ip, 1);
		}
	}
	
	@PostConstruct
	private void init() {
		try {
			String ignore = systemConfig.getString("attack.cc.ignore.ip");
			if(!isNullOrTrimEmpty(ignore)) {
				String[] ips = ignore.split(",");
				for(String ip : ips) {
					ignoreMap.put(ip, 1);
				}
			}
		} catch (MyException e) {
			throw new RuntimeException(e);
		}
	}
}
