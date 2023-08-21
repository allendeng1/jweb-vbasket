package com.jweb.dao.generator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.RowSetMetaData;

import com.jweb.dao.generator.TableInfo.Cel;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
public class MysqlDatasource extends AbstractDataSource {

	public MysqlDatasource(String driverClass, String url, String username, String password) {
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
			Map<String, String> comMap = new HashMap<String, String>();
			pst = conn.prepareStatement("show full columns from " + tableSchema + tableName);
			rs = pst.executeQuery();
			while (rs.next()) {
				comMap.put(rs.getString("Field"), rs.getString("Comment"));
			}
			List<Cel> cels = new ArrayList<Cel>();
			ResultSetMetaData rsd = pst.executeQuery("select * from " + tableSchema + tableName + " where 1=2")
					.getMetaData();
			for (int i = 1; i <= rsd.getColumnCount(); i++) {
				String name = rsd.getColumnName(i);
				String dbType = rsd.getColumnTypeName(i);
				String javaType = rsd.getColumnClassName(i);
				int nullAble = rsd.isNullable(i);
				String annotation = comMap.get(name);
				dbType = convertDataType(dbType);
				System.out.printf("%1$10s\t %2$10s\t %3$10s\t %4$s\t", name, dbType, javaType, annotation);
				System.out.println();
				Cel cel = new Cel();
				cel.setName(name);
				cel.setDbType(dbType);
				cel.setDataType(javaType);
				cel.setAnnotation(annotation);
				cel.setNotNull(nullAble == RowSetMetaData.columnNoNulls);
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
		if(dataType.equalsIgnoreCase("INT")){
			return "INTEGER";
		}
		if(dataType.equalsIgnoreCase("TEXT")){
			return "VARCHAR";
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
			rs = st.executeQuery("SHOW CREATE TABLE " + tableSchema + tableName);
			if (rs != null && rs.next()) {
				String createDDL = rs.getString(2);
				String comment = parse(createDDL);
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

	public static String parse(String all) {
		String comment = null;
		int index = all.indexOf("COMMENT='");
		if (index < 0) {
			return "";
		}
		comment = all.substring(index + 9);
		comment = comment.substring(0, comment.length() - 1);
		return comment;
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
