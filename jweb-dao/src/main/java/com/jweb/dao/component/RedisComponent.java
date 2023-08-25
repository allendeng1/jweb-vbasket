package com.jweb.dao.component;


import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.redisson.api.RBucket;
import org.redisson.api.RList;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
@Service
public class RedisComponent {
	
	@Autowired
	private RedissonClient redissonClient;
	
	public String getString(String key)throws RuntimeException{
		RBucket<String> bucket = redissonClient.getBucket(key);
		return bucket.get();
	}
	/**
	 * 
	 * @param key
	 * @param value
	 * @param expireSeconds 有效时间，单位：秒，null表示维持之前的设置
	 * @throws RuntimeException
	 */
	public void setString(String key, String value, Long expireSeconds)throws RuntimeException{
		RBucket<String> bucket = redissonClient.getBucket(key);
		if(expireSeconds != null){
			bucket.set(value, expireSeconds, TimeUnit.SECONDS);
		}else {
			long ttl = bucket.remainTimeToLive();
			if(ttl > 0) {
				bucket.set(value, ttl/1000, TimeUnit.SECONDS);
			}else {
				bucket.set(value);
			}
		}
	}
	
	public void deleteKey(String key)throws RuntimeException{
		redissonClient.getBucket(key).delete();
	}
	public void deleteKeyByPattern(String pattern)throws RuntimeException{
		redissonClient.getKeys().deleteByPattern(pattern);
	}
	public boolean isExistKey(String key) throws RuntimeException{
		RBucket<Object> bucket = redissonClient.getBucket(key);
		return bucket.isExists();
	}
	public void setList(String name, Object obj) throws RuntimeException{
		RList<Object> rlist = redissonClient.getList(name);
		rlist.add(obj);
	}
	
	public boolean isListContains(String name, Object obj)throws RuntimeException{
		RList<Object> rlist = redissonClient.getList(name);
    	if(rlist == null) {
    		return false;
    	}
    	
    	return rlist.contains(obj);
    }
	public void setMap(String name, Map<String, Object> map, Long expireSeconds)throws RuntimeException{
		RMap<String, Object> rmap = redissonClient.getMap(name);
		rmap.putAll(map);
		if(expireSeconds != null){
			rmap.expire(expireSeconds, TimeUnit.SECONDS);
		}
		
	}
	public void setMap(String name, String field, Object value)throws RuntimeException{
		RMap<String, Object> map = redissonClient.getMap(name);
		map.put(field, value);
	}
	public RMap<String, Object> getMap(String name)throws RuntimeException{
		return redissonClient.getMap(name);
	}
	public Object getMapValue(String name, String field)throws RuntimeException{
		RMap<String, Object> map = getMap(name);
    	if(map == null) {
    		return null;
    	}
    	return map.get(field);
	}
	public void removeMap(String name, String field)throws RuntimeException{
		RMap<String, Object> map = redissonClient.getMap(name);
		map.remove(field);
	}
	
    public boolean isMapContainKey(String name, String key)throws RuntimeException{
    	RMap<String, Object> map = getMap(name);
    	if(map == null) {
    		return false;
    	}
    	return map.containsKey(key);
    }
    
    /**
     * 获取key的剩余有效时间
     * @param key
     * @return 单位：秒，-1表示key未设置过期时间
     */
    public long ttl(String key) {
        RBucket<Object> bucket = redissonClient.getBucket(key);
        if (bucket.isExists()) {
            long ttl = bucket.remainTimeToLive();
            if (ttl == -2) {
                return 0;
            }else if(ttl == -1){
            	 return -1L;
            }else {
            	return ttl / 1000;
            }
        }
        return 0;
    }
	/**
	 * 非公平锁
	 * @param key
	 * @return
	 */
    public RLock getLock(String key) {
    	return redissonClient.getLock(key);
    }
    /**
	 * 公平锁
	 * @param key
	 * @return
	 */
    public RLock getFairLock(String key) {
    	return redissonClient.getFairLock(key);
    }
    /**
     * 自增
     * @param key
     * @param stepSize 步长
     * @return
     */
    public long increment(String key, long stepSize){
    	return redissonClient.getAtomicLong(key).addAndGet(stepSize);
    }
}