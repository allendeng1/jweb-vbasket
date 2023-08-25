package com.jweb.dao.cache;

import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.jweb.common.util.DateTimeUtil;
import com.jweb.dao.base.BaseEntity;
import com.jweb.dao.component.RedisComponent;

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
@Component
@Slf4j
public class IcacheHandler<T extends BaseEntity> implements Icache<T> {
	
	@Autowired
	private RedisComponent redisComponent;
	
	private static final String CACHE_MARK = "datacache-";
	
	private static final String CACHE_DELETE_MARK = "cache-deltimer"; 
	
	@Override
	@CacheReport
	public T get(String cacheName, String cacheKey, Class<T> clazz) {
		if(cacheName == null || "".equals(cacheName.trim())){
			log.warn("获取缓存数据时cacheName为null");
			return null;
		}
		if(cacheKey == null || "".equals(cacheKey.trim())){
			log.warn("获取缓存数据时cacheKey为null");
			return null;
		}
		String cacheValue = redisComponent.getString(CACHE_MARK+cacheName+"-"+cacheKey);
		if(cacheValue == null || "".equals(cacheValue.trim())){
			return null;
		}
		T t = null;
		try {
			if(cacheValue.equalsIgnoreCase("null")) {
				return clazz.newInstance();
			}
			t = JSONObject.parseObject(cacheValue, clazz);
		} catch (Exception e) {
			log.error("获取缓存数据时出错："+cacheName+"-"+cacheKey, e);
		}
		
		return t;
	}

	@Override
	public boolean set(String cacheName, String cacheKey, Object cacheData, Long cacheTime) {
		if(cacheName == null || "".equals(cacheName.trim())){
			log.warn("设置缓存数据时cacheName为null，跳过");
			return false;
		}
		if(cacheKey == null || "".equals(cacheKey.trim())){
			log.warn("设置缓存数据时cacheKey为null，跳过");
			return false;
		}
		if("".equals(cacheData)){
			log.warn("设置缓存数据时cacheData为null，跳过");
			return false;
		}
		String nameKey = cacheName+"-"+cacheKey;
		try {
			
			String cacheValue = "null";
			if(cacheData != null) {
				Long delTime = (Long) redisComponent.getMapValue(CACHE_DELETE_MARK, nameKey);
				if(delTime != null) {
					//被删除的缓存数据，10秒内不写入缓存
					//解决：事务1修改数据未提交状态的同时事务2查询数据写入缓存，导致缓存数据与数据库数据不一致的问题
					if(DateTimeUtil.nowTime() - delTime < 10000) {
						return true;
					}
					redisComponent.removeMap(CACHE_DELETE_MARK, nameKey);
				}
			
				cacheValue = JSONObject.toJSONString(cacheData);
			}
			redisComponent.setString(CACHE_MARK+nameKey, cacheValue, cacheTime);
			return true;
		} catch (Exception e) {
			log.error("设置缓存数据时出错："+nameKey, e);
			return false;
		}
	}

	@Override
	public boolean delete(String cacheName, String cacheKey) {

		if(cacheName == null || "".equals(cacheName.trim())){
			log.warn("删除缓存数据时cacheName为null");
			return false;
		}
		if(cacheKey == null || "".equals(cacheKey.trim())){
			log.warn("删除缓存数据时cacheKey为null");
			return false;
		}
		String nameKey = cacheName+"-"+cacheKey;
		try {
			
			redisComponent.deleteKey(CACHE_MARK+nameKey);
			//记录缓存被删除的时间
			redisComponent.setMap(CACHE_DELETE_MARK, nameKey, DateTimeUtil.nowTime());
			return true;
		} catch (Exception e) {
			log.error("删除缓存数据时出错："+nameKey, e);
			return false;
		}
	}

	@Override
	public boolean clean(String cacheName) {
		
		if(cacheName == null || "".equals(cacheName.trim())){
			log.warn("清空缓存数据时cacheName为null");
			return false;
		}
		try {
			redisComponent.deleteKeyByPattern(CACHE_MARK+cacheName+"-*");
			return true;
		} catch (Exception e) {
			log.error("清空缓存数据时出错："+cacheName, e);
			return false;
		}
		
	}

	@Override
	public boolean cleanAll() {
		try {
			redisComponent.deleteKeyByPattern(CACHE_MARK+"*");
			return true;
		} catch (Exception e) {
			log.error("清空所有缓存数据时出错", e);
			return false;
		}
	}

	@Override
	public void updateCacheHitRate(String cacheName, int queryNum, int hitNum) {
		RLock lock = redisComponent.getLock("cachehit-"+cacheName);
		lock.lock();
		try {
			String value = (String) redisComponent.getMapValue("cachehitrate", cacheName);
			if(value == null){
				redisComponent.setMap("cachehitrate", cacheName, queryNum+"-"+hitNum);
			}else{
				String[] vs = value.split("-");
				redisComponent.setMap("cachehitrate", cacheName, (Integer.valueOf(vs[0])+queryNum)+"-"+(Integer.valueOf(vs[1])+hitNum));
			}
		} catch (NumberFormatException e) {
			log.error("更新缓存命中率时出错", e);
		} catch (RuntimeException e) {
			log.error("更新缓存命中率时出错", e);
		}finally {
			lock.unlock();
		}
	}

}
