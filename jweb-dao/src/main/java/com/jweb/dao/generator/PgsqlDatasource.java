package com.jweb.dao.generator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jweb.dao.generator.TableInfo.Cel;

/**
 * 
 * @author 邓超
 *
 * 2023/08/16 下午6:12:15
 */
public class PgsqlDatasource extends AbstractDataSource{

	public PgsqlDatasource(String driverClass, String url, String username, String password) {
		super(driverClass, url, username, password);
	}

	@Override
	TableInfo getTableInfo(String tableSchema, String tableName, boolean createIndexDoc) {
		TableInfo info = new TableInfo();
		info.setTableName(tableName);
		info.setCreateIndexDoc(createIndexDoc);
		setTableInfo(tableSchema, tableName, info);
		setTableCelInfo(tableSchema, tableName, info);
		return info;
	}

	private void setTableCelInfo(String tableSchema, String tableName, TableInfo info) {
		Connection conn = getConnection(tableSchema);
		PreparedStatement pst = null;
		ResultSet rs = null;
		tableSchema = tableSchema == null || "".equals(tableSchema) ? "" : tableSchema + ".";
		try {
			List<Cel> cels = new ArrayList<Cel>();
			pst = conn.prepareStatement(" SELECT "
					+ "a.attname AS column_name,"
					+ "t.typname AS data_type,"
					+ "CASE WHEN t.typlen = -1 THEN a.atttypmod - 4 ELSE t.typlen::integer END AS lengthvar,"
					+ "case a.attnotnull when 't' then true else false end AS notnull,"
					+ "b.description AS description "
					+ "FROM pg_class c,"
					+ "pg_attribute a "
					+ "LEFT OUTER JOIN pg_description b ON a.attrelid=b.objoid AND a.attnum = b.objsubid,"
					+ "pg_type t "
					+ "WHERE c.relname = '"+tableName+"' "
					+ "and a.attnum > 0 "
					+ "and a.attrelid = c.oid "
					+ "and a.atttypid = t.oid "
					+ "ORDER BY a.attnum");
			rs = pst.executeQuery();
			while (rs.next()) {
				String name = rs.getString("column_name");
				String dbType = rs.getString("data_type");
				String annotation = rs.getString("description");
				annotation = annotation == null ? "" : annotation;
				boolean isNotNull = rs.getBoolean("notnull");
				
				String javaType = convertDataType(dbType);
				System.out.printf("%1$10s\t %2$10s\t %3$10s\t %4$s\t", name, dbType, javaType, annotation);
				System.out.println();
				Cel cel = new Cel();
				cel.setName(name);
				cel.setDbType(dbType);
				cel.setDataType(javaType);
				cel.setAnnotation(annotation);
				cel.setNotNull(isNotNull);
				cels.add(cel);
			}
			info.setCels(cels);
		} catch (SQLException e) {
			throw new RuntimeException("", e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pst != null) {
				try {
					pst.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private String convertDataType(String dataType){
		if(dataType.equalsIgnoreCase("bigint")){
			return "java.lang.Long";
		}else if(dataType.equalsIgnoreCase("int8")){
			return "java.lang.Long";
		}else if(dataType.equalsIgnoreCase("text")){
			return "java.lang.String";
		}else if(dataType.equalsIgnoreCase("character varying")){
			return "java.lang.String";
		}else if(dataType.equalsIgnoreCase("varchar")){
			return "java.lang.String";
		}else if(dataType.equalsIgnoreCase("jsonb")){
			return "java.lang.String";
		}else if(dataType.equalsIgnoreCase("boolean")){
			return "java.lang.Boolean";
		}else if(dataType.equalsIgnoreCase("bool")){
			return "java.lang.Boolean";
		}else if(dataType.equalsIgnoreCase("smallint")){
			return "java.lang.Integer";
		}else if(dataType.equalsIgnoreCase("integer")){
			return "java.lang.Integer";
		}else if(dataType.equalsIgnoreCase("int2")){
			return "java.lang.Integer";
		}else if(dataType.equalsIgnoreCase("int4")){
			return "java.lang.Integer";
		}else if(dataType.equalsIgnoreCase("int6")){
			return "java.lang.Integer";
		}else if(dataType.equalsIgnoreCase("numeric")){
			return "java.math.BigDecimal";
		}else if(dataType.equalsIgnoreCase("timestamp with time zone")){
			return "java.util.Date";
		}else if(dataType.equalsIgnoreCase("timestamptz")){
			return "java.util.Date";
		}
		return dataType;
	}

	private void setTableInfo(String tableSchema, String tableName, TableInfo info) {
		Connection conn = getConnection(tableSchema);
		Statement st = null;
		ResultSet rs = null;
		tableSchema = tableSchema == null || "".equals(tableSchema) ? "" : tableSchema + ".";
		try {
			st = conn.createStatement();
			rs = st.executeQuery("select cast(obj_description(relfilenode,'pg_class') as varchar) as comment from pg_class where  relkind = 'r' and relname ='"+tableName+"'");
			if (rs != null && rs.next()) {
				String comment = rs.getString(1);
				info.setAnnotation(comment);
			}
		} catch (SQLException e) {
			throw new RuntimeException("", e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private Connection getConnection(String tableSchema) {
		try {
			Class.forName(driverClass);
			Connection conn = DriverManager.getConnection(url.replaceAll("dbname", tableSchema), username, password);
			return conn;
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("", e);
		} catch (SQLException e) {
			throw new RuntimeException("", e);
		}
	}
}
