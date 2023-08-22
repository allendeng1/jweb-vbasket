package com.jweb.dao.generator;

import java.util.List;

import com.jweb.dao.generator.Context.Table;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
public class MyGenerator {

	public static void generator(String configFileName) throws Exception{
		
		Context context = ConfigurationParser.parser(configFileName);
		AbstractDataSource dataSource = DatasourceFactory.getDataSource(context.getDataSource());
		
		List<Table> tables = context.getTables();
		for(Table table : tables){
			TableInfo tableInfo = dataSource.getTableInfo(table.getSchema(), table.getTableName(), table.isCreateIndexDoc());
			
			new JavaEntityProducer(tableInfo, context).run();
			
			new JavaDaoProducer(tableInfo, context).run();
			
			new JavaDaoImplProducer(tableInfo, context).run();
			
			new JavaServiceProducer(tableInfo, context).run();
			
			new JavaServiceImplProducer(tableInfo, context).run();
			
			new JavaApivoProducer(tableInfo, context).run();
			
			new JavaApidocProducer(tableInfo, context).run();
			
			new JavaApicontrollerProducer(tableInfo, context).run();
			
			//new JavaMapperProducer(tableInfo, context).run();
			
			new JavaMapperXmlProducer(tableInfo, context).run();
			
			new JavaQueryXmlProducer(tableInfo, context).run();
			
		}
		
	} 
	
	public static void main(String[] args) throws Exception {
		try {
			System.out.println("====>逆向工程代码生成器启动...");
			generator("generatorConfig.xml");
			System.out.println("====>逆向工程代码生成器结束...");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
}
