package com.jweb.dao.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.jweb.common.util.RpcUtil;
import com.jweb.dao.cache.Icache;

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
@Slf4j
public abstract class BaseDaoSupport<T extends BaseEntity> implements BaseDao<T>{
	
	
	@Autowired
	private Icache<T> icache;
	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	@Autowired
	private RpcUtil rpcUtil;
	
	/**
	 * 实体对象 class
	 * @return
	 */
	public abstract Class<T> getClazz();
	
	/**
	 * 是否缓存数据，默认false
	 * @return
	 */
	public abstract boolean isCacheable();
	
	/**
	 * 是否创建索引文档，默认false
	 * @return
	 */
	public abstract boolean isCreateIndexDoc();
	/**
	 * 缓存存储时长，单位：秒
	 * <p>
	 *    1、设置null，永久存储,直到更新数据清除缓存或系统清除缓存或人工清除缓存
	 * @return
	 */
	public abstract Long cacheStorageTimeSecond();
	
	@SuppressWarnings("unchecked")
	@Override
	public T selectById(long id) {
		if(isCacheable()){
			T o = getDataFromCache(id);
			if(o != null){
				return o;
			}
		}
		SqlSession session = sqlSessionFactory.openSession();
		try {
			Object t =  session.selectOne(getClazz().getSimpleName()+".selectById", id);
			if(isCacheable()){
				saveDataCache(id, t);
			}
			return (T) t;
		} catch (Exception e) {
			log.error("", e);
			throw new RuntimeException(e);
		}finally {
			session.close();
		}
	}

	@Override
	public int insert(T t) {
		if(t.getId() != null && t.getId() > 0){
			int rows = updateById(t);
			return rows;
		}
		SqlSession session = sqlSessionFactory.openSession();
		try {
			int rows = session.insert(getClazz().getSimpleName()+".insert", t);
			session.commit();
			sentSyncDataNotification(t.getId(), "C");
			return rows;
		} catch (Exception e) {
			log.error("", e);
			throw new RuntimeException(e);
		}finally {
			session.close();
		}
	}

	@Override
	public int updateById(T t) {
		if(t.getId() == null){
			throw new RuntimeException("Dao updateById id is null");
		}
		SqlSession session = sqlSessionFactory.openSession();
		try {
			int rows = session.update(getClazz().getSimpleName()+".updateById", t);
			session.commit();
			if(isCacheable()){
				icache.delete(getClazz().getSimpleName().toLowerCase(), t.getId()+"");
			}
			sentSyncDataNotification(t.getId(), "U");
			return rows;
		} catch (Exception e) {
			log.error("", e);
			throw new RuntimeException(e);
		}finally {
			session.close();
		}
	}

	@Override
	public int deleteById(long id) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			int rows = session.delete(getClazz().getSimpleName()+".deleteById", id);
			session.commit();
			if(isCacheable()){
				icache.delete(getClazz().getSimpleName().toLowerCase(), id+"");
			}
			sentSyncDataNotification(id, "D");
			return rows;
		} catch (Exception e) {
			log.error("", e);
			throw new RuntimeException(e);
		}finally {
			session.close();
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> selectByExample(BaseEntity query) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			if(isCacheable()){
				List<Long> ids = session.selectList(getClazz().getSimpleName()+".selectIds", query);
				if(ids == null || ids.isEmpty()){
					return null;
				}
				List<T> values = new ArrayList<T>();
				for(Long id : ids){
					T o = getDataFromCache(id);
					if(o == null){
						o = (T) session.selectOne(getClazz().getSimpleName()+".selectById", id);
						if(o != null){
							if(isCacheable()){
								saveDataCache(id, o);
							}
						}
					}
					if(o != null){
						values.add(o);
					}
				}
				return values;
			}

			return session.selectList(getClazz().getSimpleName()+".selectList", query);
		} catch (Exception e) {
			log.error("", e);
			throw new RuntimeException(e);
		}finally {
			session.close();
		}
	}

	@Override
	public T selectOneByExample(BaseEntity query) {
		if(isCacheable()){
			if(query.getId() != null){
				T o = getDataFromCache(query.getId());
				if(o != null){
					return o;
				}
			}
		}
		List<T> list = selectByExample(query);
		if(list == null || list.size() == 0){
			return null;
		}
		return list.get(0);
	}
	
	@Override
	public int selectCountByExample(BaseEntity query) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			return session.selectOne(getClazz().getSimpleName()+".selectCount", query);
		} catch (Exception e) {
			log.error("", e);
			throw new RuntimeException(e);
		}finally {
			session.close();
		}
	}
	
	@Override
	public PageResult<T> selectPageResultByExample(BaseEntity query) {
		PageResult<T> result = new PageResult<T>();
		result.setCurLimit(query.getLimit());
		result.setCurPage((query.getOffset()+query.getLimit())/query.getLimit());
		
		int totalCount = selectCountByExample(query);
		if(totalCount == 0){
			return result;
		}
		List<T> entitys = selectByExample(query);
		result.setTotalCount(totalCount);
		int totalPage = totalCount / query.getLimit();
		if(totalCount % query.getLimit() > 0) {
			totalPage += 1;
		}
		result.setTotalPage(totalPage);
		result.setEntitys(entitys);
		return result;
	}

	protected Object findOne(String sqlId, Map<String, Object> param){
		SqlSession session = sqlSessionFactory.openSession();
		try {
			return session.selectOne(sqlId, param);
		} catch (Exception e) {
			log.error("", e);
			throw new RuntimeException(e);
		}finally {
			session.close();
		}
	}
	
	protected List<?> findList(String sqlId, Map<String, Object> param){
		SqlSession session = sqlSessionFactory.openSession();
		try {
			return session.selectList(sqlId, param);
		} catch (Exception e) {
			log.error("", e);
			throw new RuntimeException(e);
		}finally {
			session.close();
		}
	}
	protected int findCount(String sqlId, Map<String, Object> param){
		SqlSession session = sqlSessionFactory.openSession();
		try {
			return session.selectOne(sqlId, param);
		} catch (Exception e) {
			log.error("", e);
			throw new RuntimeException(e);
		}finally {
			session.close();
		}
	}
	
	private T getDataFromCache(long id){
		return icache.get(getClazz().getSimpleName().toLowerCase(), id+"", getClazz());
	}
	
	private void saveDataCache(long id, Object o){
		icache.set(getClazz().getSimpleName().toLowerCase(), id+"", o, cacheStorageTimeSecond());
	}
	
	/**
	 * 发送数据同步通知
	 * @param id
	 * @param change C-新增，U-修改，D-删除
	 */
	private void sentSyncDataNotification(long id, String change) {
		if(!isCreateIndexDoc()) {
			return;
		}
		
		JSONObject json = new JSONObject();
		json.put("id", id);
		json.put("change", change);
		json.put("entityName", getClazz().getSimpleName());
		rpcUtil.callSentMqIndexDocQueryMsg("INDEX_DOC_SYNC", json.toJSONString());
	}
}
