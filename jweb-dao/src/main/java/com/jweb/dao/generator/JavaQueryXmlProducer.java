package com.jweb.dao.generator;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
public class JavaQueryXmlProducer extends AbstractProducer{

	public JavaQueryXmlProducer(TableInfo info, Context context) {
		super(info, context);
	}

	@Override
	StringBuilder coding() {
		String tableName = info.getTableName();
		tableName = formatName(tableName);
		StringBuilder builder = new StringBuilder();
		builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append(NEW_LINE);
		builder.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">").append(NEW_LINE);
		builder.append("<mapper namespace=\"").append(tableName.substring(0, 1).toUpperCase()+tableName.substring(1, tableName.length())).append("\">").append(NEW_LINE);
		builder.append("<!--自定义SQL，代码生成器不会覆盖此文件-->").append(NEW_LINE).append(NEW_LINE);
		
		builder.append("</mapper>").append(NEW_LINE);
		return builder;
	}

	@Override
	String producer() {
		return "queryxml";
	}

}
