package com.jweb.dao.generator;

/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
public class JavaMapperProducer extends AbstractProducer{

	public JavaMapperProducer(TableInfo info, Context context) {
		super(info, context);
	}

	@Override
	StringBuilder coding() {
		String tableName = info.getTableName();
		tableName = formatName(tableName);
		tableName = tableName.substring(0, 1).toUpperCase()+tableName.substring(1, tableName.length());
		String packageName = packageName();
		
		StringBuilder builder = new StringBuilder();
		builder.append("package ").append(packageName).append(".mapper;").append(NEW_LINE).append(NEW_LINE);
		builder.append("import javax.annotation.Resource;").append(NEW_LINE);
		builder.append("import ").append(packageName).append(".base.MyBaseMapper;").append(NEW_LINE);
		builder.append("import ").append(packageName).append(".entity.").append(tableName).append(";").append(NEW_LINE).append(NEW_LINE);
		createAuthor(builder);
		builder.append("@Resource").append(NEW_LINE);
		builder.append("public interface ").append(tableName).append("Mapper extends MyBaseMapper<").append(tableName).append("> {").append(NEW_LINE).append(NEW_LINE);
		
		builder.append("}").append(NEW_LINE);
		return builder;
	}

	@Override
	String producer() {
		return "mapper";
	}

}
