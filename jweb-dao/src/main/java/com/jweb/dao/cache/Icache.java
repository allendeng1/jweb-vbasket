package com.jweb.dao.cache;

import com.jweb.dao.base.BaseEntity;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
/**
 * 缓存接口
 * @author allen
 *
 * @param <T>
 */
public interface Icache<T extends BaseEntity> {
	
	/**
	 * 获取缓存
	 * @param cacheName 缓存名称
	 * @param cacheKey  缓存唯一键
	 * @param clazz     缓存对象class
	 * @return
	 */
	T get(String cacheName, String cacheKey, Class<T> clazz);
	
	/**
	 * 保存缓存
	 * @param cacheName 缓存名称
	 * @param cacheKey  缓存唯一键
	 * @param cacheData 缓存数据
	 * @param cacheTime 缓存时间(单位：秒)
	 * @return
	 */
	boolean set(String cacheName, String cacheKey, Object cacheData, Long cacheTime);
	
	/**
	 * 删除缓存
	 * @param cacheName 缓存名称
	 * @param cacheKey  缓存唯一键
	 * @return
	 */
	boolean delete(String cacheName, String cacheKey);
	
	/**
	 * 清空缓存
	 * @param cacheName 缓存名称
	 * @return
	 */
	boolean clean(String cacheName);
	
	/**
	 * 清空所有缓存
	 * @return
	 */
	boolean  cleanAll();
	
	/**
	 * 更新缓存命中率
	 * @param cacheName 缓存名称
	 * @param queryNum  查询次数
	 * @param hitNum    命中次数
	 */
	void updateCacheHitRate(String cacheName, int queryNum, int hitNum);

}
