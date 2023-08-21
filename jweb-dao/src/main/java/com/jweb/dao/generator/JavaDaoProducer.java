package com.jweb.dao.generator;

public class JavaDaoProducer extends AbstractProducer{

	public JavaDaoProducer(TableInfo info, Context context) {
		super(info, context);
	}

	@Override
	StringBuilder coding() {
		String tableName = info.getTableName();
		tableName = formatName(tableName);
		tableName = tableName.substring(0, 1).toUpperCase()+tableName.substring(1, tableName.length());
		String packageName = packageName();
		
		StringBuilder builder = new StringBuilder();
		builder.append("package ").append(packageName).append(";").append(NEW_LINE).append(NEW_LINE);
		builder.append("import ").append(packageName).append(".base.BaseDao;").append(NEW_LINE);
		builder.append("import ").append(packageName).append(".entity.").append(tableName).append(";").append(NEW_LINE).append(NEW_LINE);
		createAuthor(builder);
		builder.append("public interface ").append(tableName).append("Dao extends BaseDao<").append(tableName).append("> {").append(NEW_LINE).append(NEW_LINE);
		
		builder.append("}").append(NEW_LINE);
		return builder;
	}

	@Override
	String producer() {
		return "dao";
	}

}
