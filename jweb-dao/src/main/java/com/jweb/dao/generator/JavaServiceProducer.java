package com.jweb.dao.generator;

public class JavaServiceProducer extends AbstractProducer{

	public JavaServiceProducer(TableInfo info, Context context) {
		super(info, context);
	}

	@Override
	StringBuilder coding() {
		String tableName = info.getTableName();
		tableName = formatName(tableName);
		tableName = tableName.substring(0, 1).toUpperCase()+tableName.substring(1, tableName.length());
		String packageName = packageName();
		String servicePackageName = packageName.substring(0, packageName.lastIndexOf(".")+1)+"service";
		
		StringBuilder builder = new StringBuilder();
		builder.append("package ").append(servicePackageName).append(";").append(NEW_LINE).append(NEW_LINE);
		builder.append("import com.jweb.common.exception.MyException;").append(NEW_LINE);
		builder.append("import java.util.List;").append(NEW_LINE);
		builder.append("import com.jweb.dao.base.PageResult;").append(NEW_LINE);
		builder.append("import ").append(packageName).append(".entity.").append(tableName).append(";").append(NEW_LINE).append(NEW_LINE);
		createAuthor(builder);
		builder.append("public interface ").append(tableName).append("Service").append(" {").append(NEW_LINE).append(NEW_LINE);
		
		builder.append(BLANK).append(BLANK).append("/**").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("* 创建或修改(id != null)").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("* @param entity").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("* @return").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("* @throws MyException").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("*/").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("public").append(BLANK).append(tableName).append(BLANK)
		.append("saveEntity(").append(tableName).append(BLANK).append("entity)").append(BLANK).append("throws MyException;").append(NEW_LINE);
		
		builder.append(BLANK).append(BLANK).append("/**").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("* 根据ID查询数据").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("* @param id").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("* @return").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("* @throws MyException").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("*/").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("public").append(BLANK).append(tableName).append(BLANK)
		.append("getById(long id)").append(BLANK).append("throws MyException;").append(NEW_LINE);
		
		builder.append(BLANK).append(BLANK).append("/**").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("* 根据条件查询一条数据").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("* @param entity").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("* @return").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("* @throws MyException").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("*/").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("public").append(BLANK).append(tableName).append(BLANK)
		.append("getOneByCondition(").append(tableName).append(BLANK).append("entity)").append(BLANK).append("throws MyException;").append(NEW_LINE);
		
		builder.append(BLANK).append(BLANK).append("/**").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("* 根据条件查询数据").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("* @param entity").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("* @return").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("* @throws MyException").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("*/").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("public").append(BLANK).append("List<").append(tableName).append(">").append(BLANK)
		.append("getListByCondition(").append(tableName).append(BLANK).append("entity)").append(BLANK).append("throws MyException;").append(NEW_LINE);
		
		builder.append(BLANK).append(BLANK).append("/**").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("* 根据条件查询page数据").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("* @param entity").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("* @return").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("* @throws MyException").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("*/").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("public").append(BLANK).append("PageResult<").append(tableName).append(">").append(BLANK)
		.append("getPageListByCondition(").append(tableName).append(BLANK).append("entity)").append(BLANK).append("throws MyException;").append(NEW_LINE);
		
		builder.append(BLANK).append(BLANK).append("/**").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("* 根据ID删除数据").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("* @param id").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("* @return").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("* @throws MyException").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("*/").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("public void").append(BLANK)
		.append("deleteById(long id)").append(BLANK).append("throws MyException;").append(NEW_LINE);
		
		builder.append("}").append(NEW_LINE);
		return builder;
	}

	@Override
	String producer() {
		return "service";
	}

}
