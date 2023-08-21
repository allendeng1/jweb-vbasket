package com.jweb.dao.generator;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
public class JavaDaoImplProducer extends AbstractProducer{

	public JavaDaoImplProducer(TableInfo info, Context context) {
		super(info, context);
	}

	@Override
	StringBuilder coding() {
		String tableName = info.getTableName();
		boolean isCreateIndexDoc = info.isCreateIndexDoc();
		tableName = formatName(tableName);
		tableName = tableName.substring(0, 1).toUpperCase()+tableName.substring(1, tableName.length());
		String packageName = packageName();
		
		StringBuilder builder = new StringBuilder();
		builder.append("package ").append(packageName).append(".impl;").append(NEW_LINE).append(NEW_LINE);
		builder.append("import org.springframework.stereotype.Service;").append(NEW_LINE);
		builder.append("import ").append(packageName).append(".base.BaseDaoSupport;").append(NEW_LINE);
		builder.append("import ").append(packageName).append("."+tableName).append("Dao;").append(NEW_LINE);
		builder.append("import ").append(packageName).append(".entity.").append(tableName).append(";").append(NEW_LINE).append(NEW_LINE);
		createAuthor(builder);
		builder.append("@Service").append(NEW_LINE);
		builder.append("public class ").append(tableName).append("DaoImpl extends BaseDaoSupport<").append(tableName).append("> implements ").append(tableName).append("Dao {").append(NEW_LINE).append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("@Override").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("public Class<").append(tableName).append("> getClazz() {").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("return ").append(tableName).append(".class;").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("}").append(NEW_LINE).append(NEW_LINE);
		
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("@Override").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("public boolean isCacheable() {").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("return true;").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("}").append(NEW_LINE).append(NEW_LINE);
		
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("@Override").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("public Long cacheStorageTimeSecond() {").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("return null;").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("}").append(NEW_LINE).append(NEW_LINE);
		
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("@Override").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("public boolean isCreateIndexDoc() {").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("return ").append(isCreateIndexDoc).append(";").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("}").append(NEW_LINE).append(NEW_LINE);
		
		builder.append("}").append(NEW_LINE);
		return builder;
	}

	@Override
	String producer() {
		return "daoimpl";
	}

}
