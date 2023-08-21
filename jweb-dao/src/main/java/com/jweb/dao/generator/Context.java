package com.jweb.dao.generator;

import java.util.List;

import lombok.Data;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
@Data
public class Context {
	private boolean isCaseSensitive;
	private boolean isForceAnnotation;
	private boolean isLombok;
	private boolean isSwagger;
	private DataSource dataSource;
	private List<Table> tables;
	
	@Data
	public static class DataSource{
		private String type;
		private String driverClass;
		private String url;
		private String username;
		private String password;
	}
	
	@Data
	public static class Table{
		private String schema;
		private String tableName;
		private boolean createIndexDoc;
	}

}
