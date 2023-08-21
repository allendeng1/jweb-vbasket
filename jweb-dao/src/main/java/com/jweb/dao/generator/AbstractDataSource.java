package com.jweb.dao.generator;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
public abstract class AbstractDataSource {
	
	public String driverClass;
	public String url;
	public String username;
	public String password; 
	
	public AbstractDataSource(String driverClass, String url, String username, String password){
		this.driverClass = driverClass;
		this.url = url;
		this.username = username;	
		this.password = password;	
	}

	abstract TableInfo getTableInfo(String tableSchema, String tableName, boolean createIndexDoc);

}
