package com.jweb.dao.generator;

import com.jweb.dao.generator.Context.DataSource;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
public class DatasourceFactory {
	
	public static AbstractDataSource getDataSource(DataSource dataSource){
		String type = dataSource.getType();
		if(type.equalsIgnoreCase("mysql")){
			return new MysqlDatasource(dataSource.getDriverClass(), dataSource.getUrl(), dataSource.getUsername(), dataSource.getPassword());
		}else
		if(type.equalsIgnoreCase("pgsql")){
			return new PgsqlDatasource(dataSource.getDriverClass(), dataSource.getUrl(), dataSource.getUsername(), dataSource.getPassword());
		}
		throw new RuntimeException("没有生成合适的数据源");
	}

}
