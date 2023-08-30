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
public class TableInfo {
	private String tableName;
	private String annotation;
	private boolean createIndexDoc;
	private List<Cel> cels;
	
	@Data
	public static class Cel{
		private String name;
		private String dataType;
		private String dbType;
		private String annotation;
		private boolean isNotNull;
	}
	

}
