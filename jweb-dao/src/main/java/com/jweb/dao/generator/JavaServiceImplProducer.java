package com.jweb.dao.generator;


/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
public class JavaServiceImplProducer extends AbstractProducer{

	public JavaServiceImplProducer(TableInfo info, Context context) {
		super(info, context);
	}

	@Override
	StringBuilder coding() {
		String tableName = info.getTableName();
		tableName = formatName(tableName);
		String upperTableName = tableName.substring(0, 1).toUpperCase()+tableName.substring(1, tableName.length());
		String lowerTableName = tableName.substring(0, 1).toLowerCase()+tableName.substring(1, tableName.length());
		String packageName = packageName();
		
		String servicePackageName = packageName.substring(0, packageName.lastIndexOf(".")+1)+"service";
		
		StringBuilder builder = new StringBuilder();
		builder.append("package ").append(servicePackageName).append(".impl;").append(NEW_LINE).append(NEW_LINE);
		builder.append("import com.jweb.common.exception.MyException;").append(NEW_LINE);
		builder.append("import com.jweb.dao.base.PageResult;").append(NEW_LINE);
		builder.append("import com.jweb.dao.").append(upperTableName).append("Dao;").append(NEW_LINE);
		builder.append("import ").append(servicePackageName).append(".").append(upperTableName).append("Service;").append(NEW_LINE);
		builder.append("import com.jweb.common.util.DataUtil;").append(NEW_LINE);
		builder.append("import java.util.List;").append(NEW_LINE);
		builder.append("import org.springframework.stereotype.Service;").append(NEW_LINE);
		builder.append("import org.springframework.beans.factory.annotation.Autowired;").append(NEW_LINE);
		builder.append("import lombok.extern.slf4j.Slf4j;").append(NEW_LINE);
		builder.append("import ").append(packageName).append(".entity.").append(upperTableName).append(";").append(NEW_LINE).append(NEW_LINE);
		createAuthor(builder);
		
		builder.append("@Slf4j").append(NEW_LINE);
		builder.append("@Service").append(NEW_LINE);
		builder.append("public class ").append(upperTableName).append("ServiceImpl").append(BLANK).append("extends DataUtil implements ").append(upperTableName).append("Service").append(" {").append(NEW_LINE).append(NEW_LINE);
		
		builder.append(BLANK).append(BLANK).append("@Autowired").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("private").append(BLANK).append(upperTableName).append("Dao").append(BLANK).append(lowerTableName).append("Dao;").append(NEW_LINE).append(NEW_LINE);
		
		builder.append(BLANK).append(BLANK).append("@Override").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("public").append(BLANK).append(upperTableName).append(BLANK)
		.append("saveEntity(").append(upperTableName).append(BLANK).append("entity)").append(BLANK).append("throws MyException {").append(NEW_LINE)
		.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(lowerTableName).append("Dao.insert(entity);").append(NEW_LINE)
		.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("return entity;").append(NEW_LINE)
		.append(BLANK).append(BLANK).append("}").append(NEW_LINE).append(NEW_LINE);
		
		
		builder.append(BLANK).append(BLANK).append("@Override").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("public").append(BLANK).append(upperTableName).append(BLANK)
		.append("getById(long id)").append(BLANK).append("throws MyException {").append(NEW_LINE)
		.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("return ").append(lowerTableName).append("Dao.selectById(id);").append(NEW_LINE)
		.append(BLANK).append(BLANK).append("}").append(NEW_LINE).append(NEW_LINE);
		
		builder.append(BLANK).append(BLANK).append("@Override").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("public").append(BLANK).append(upperTableName).append(BLANK)
		.append("getOneByCondition(").append(upperTableName).append(BLANK).append("entity)").append(BLANK).append("throws MyException {").append(NEW_LINE)
		.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("return ").append(lowerTableName).append("Dao.selectOneByExample(entity);").append(NEW_LINE)
		.append(BLANK).append(BLANK).append("}").append(NEW_LINE).append(NEW_LINE);
		
		builder.append(BLANK).append(BLANK).append("@Override").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("public").append(BLANK).append("List<").append(upperTableName).append(">").append(BLANK)
		.append("getListByCondition(").append(upperTableName).append(BLANK).append("entity)").append(BLANK).append("throws MyException {").append(NEW_LINE)
		.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("return ").append(lowerTableName).append("Dao.selectByExample(entity);").append(NEW_LINE)
		.append(BLANK).append(BLANK).append("}").append(NEW_LINE).append(NEW_LINE);
		
		builder.append(BLANK).append(BLANK).append("@Override").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("public").append(BLANK).append("PageResult<").append(upperTableName).append(">").append(BLANK)
		.append("getPageListByCondition(").append(upperTableName).append(BLANK).append("entity)").append(BLANK).append("throws MyException {").append(NEW_LINE)
		.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("return ").append(lowerTableName).append("Dao.selectPageResultByExample(entity);").append(NEW_LINE)
		.append(BLANK).append(BLANK).append("}").append(NEW_LINE).append(NEW_LINE);
		
		builder.append(BLANK).append(BLANK).append("@Override").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("public void").append(BLANK)
		.append("deleteById(long id)").append(BLANK).append("throws MyException {").append(NEW_LINE)
		.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(lowerTableName).append("Dao.deleteById(id);").append(NEW_LINE)
		.append(BLANK).append(BLANK).append("}").append(NEW_LINE).append(NEW_LINE);
		
		builder.append("}").append(NEW_LINE);
		return builder;
	}

	@Override
	String producer() {
		return "serviceimpl";
	}

}
