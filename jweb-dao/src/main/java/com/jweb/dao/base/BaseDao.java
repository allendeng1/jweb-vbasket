package com.jweb.dao.base;

import java.util.List;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
public interface BaseDao<T extends BaseEntity>{
	

	public T selectById(long id);
	
	public int insert(T t) ;

	public int updateById(T t) ;
	
	public int deleteById(long id);

	public List<T> selectByExample(BaseEntity query);

	public T selectOneByExample(BaseEntity query);

	public int selectCountByExample(BaseEntity query) ;
	
	public PageResult<T> selectPageResultByExample(BaseEntity query);

}
