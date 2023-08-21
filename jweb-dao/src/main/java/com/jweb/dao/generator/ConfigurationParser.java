package com.jweb.dao.generator;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.jweb.dao.generator.Context.DataSource;
import com.jweb.dao.generator.Context.Table;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
public class ConfigurationParser {
	
	public static Context parser(String configFileName){
		Document doc = loadConfigFile(configFileName);
		Context context = new Context();
		Element root = doc.getRootElement();
		parseProperty(root, context);
		parseDatasource(root, context);
		parseTable(root, context);
		return context;
	}
	
	private static void parseTable(Element root, Context context){
		List<Element> tables = root.elements("table");
		if(tables == null || tables.size() == 0){
			throw new RuntimeException("未配置table");
		}
		List<Table> ts = new ArrayList<Context.Table>();
		for(Element table : tables){
			Table t = new Context.Table();
			Attribute schemaAttr = table.attribute("schema");
			if(schemaAttr == null){
				throw new RuntimeException("未配置table schema");
			}
			t.setSchema(schemaAttr.getText());
			Attribute tableNameAttr = table.attribute("tableName");
			if(tableNameAttr == null){
				throw new RuntimeException("未配置table n");
			}
			t.setTableName(tableNameAttr.getText());
			Attribute createIndexDocAttr = table.attribute("createIndexDoc");
			if(createIndexDocAttr != null) {
				t.setCreateIndexDoc(Boolean.valueOf(createIndexDocAttr.getText()));
			}
			System.out.println("====>table："+schemaAttr.getText()+"	"+tableNameAttr.getText());
			ts.add(t);
		}
		context.setTables(ts);
	}
	
	private static void parseDatasource(Element root, Context context){
		DataSource ds = new Context.DataSource();
		Element ele = root.element("datasource");
		if(ele == null){
			throw new RuntimeException("未配置数据源");
		}
		Attribute typeAttr = ele.attribute("type");
		if(typeAttr == null){
			throw new RuntimeException("未配置数据源类型");
		}
		String type = typeAttr.getText();
		if(!(type.equalsIgnoreCase("mysql") || type.equalsIgnoreCase("pgsql"))){
			throw new RuntimeException("不支持的数据源类型");
		}
		System.out.println("====>datasource：type"+"	"+type);
		ds.setType(type);
		
		Attribute driverAttr = ele.attribute("driverClass");
		if(driverAttr == null){
			throw new RuntimeException("未配置数据源驱动");
		}
		String driverClass = driverAttr.getText();
		System.out.println("====>datasource：driverClass"+"	"+driverClass);
		ds.setDriverClass(driverClass);
		
		Attribute connAttr = ele.attribute("connectionURL");
		if(connAttr == null){
			throw new RuntimeException("未配置数据源连接URL");
		}
		String connUrl = connAttr.getText();
		System.out.println("====>datasource：connectionURL"+"	"+connUrl);
		ds.setUrl(connUrl);
		
		Attribute usernameAttr = ele.attribute("username");
		if(usernameAttr != null){
			System.out.println("====>datasource：username"+"	"+usernameAttr.getText());
			ds.setUsername(usernameAttr.getText());
		}
		Attribute passwordAttr = ele.attribute("password");
		if(passwordAttr != null){
			System.out.println("====>datasource：password"+"	"+passwordAttr.getText());
			ds.setPassword(passwordAttr.getText());
		}
		context.setDataSource(ds);
	}
	
	private static void parseProperty(Element root, Context context){
		List<Element> pros = root.elements("property");
		if(pros == null || pros.size() == 0){
			return;
		}
		for(Element pro : pros){
			Attribute nameAttr = pro.attribute("name");
			if(nameAttr == null){
				continue;
			}
			String name = nameAttr.getText();
			Attribute valueAttr = pro.attribute("value");
			if(valueAttr == null){
				System.out.println("====>property："+name);
				continue;
			}
			String value = valueAttr.getText();
			System.out.println("====>property："+name+"	"+value);
			if(name.equalsIgnoreCase("caseSensitive")){
				context.setCaseSensitive(Boolean.valueOf(value));
			}else if(name.equalsIgnoreCase("forceAnnotation")){
				context.setForceAnnotation(Boolean.valueOf(value));
			}else if(name.equalsIgnoreCase("lombok")){
				context.setLombok(Boolean.valueOf(value));
			}else if(name.equalsIgnoreCase("swagger")){
				context.setSwagger(Boolean.valueOf(value));
			}
			
		}
	}
	
	private static Document loadConfigFile(String configFileName){
		SAXReader reader = new SAXReader();
		try {
			ClassLoader loader = ConfigurationParser.class.getClassLoader();
			URL url = loader.getResource(configFileName);
			System.out.println("====>Loading file "+url.getPath());
			InputStream stream = url.openStream();
			Document doc = reader.read(stream);
			return doc;
		} catch (Exception e) {
			throw new RuntimeException("加载配置文件出错", e);
		}
	}
}
