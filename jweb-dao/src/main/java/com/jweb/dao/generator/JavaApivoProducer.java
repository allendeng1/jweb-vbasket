package com.jweb.dao.generator;


public class JavaApivoProducer extends AbstractProducer{

	public JavaApivoProducer(TableInfo info, Context context) {
		super(info, context);
	}

	@Override
	StringBuilder coding() {
		String tableName = info.getTableName();
		tableName = formatName(tableName);
		tableName = tableName.substring(0, 1).toUpperCase()+tableName.substring(1, tableName.length());
		String packageName = packageName();
		String apiPackageName = packageName.substring(0, packageName.lastIndexOf(".")+1)+"adminweb.generatecode.vo";
		
		StringBuilder builder = new StringBuilder();
		builder.append("package ").append(apiPackageName).append(";").append(NEW_LINE).append(NEW_LINE);
		builder.append("import com.jweb.common.api.ApiResult;").append(NEW_LINE);
		builder.append("import com.jweb.dao.base.PageResult;").append(NEW_LINE);
		builder.append("import ").append(packageName).append(".entity.").append(tableName).append(";").append(NEW_LINE).append(NEW_LINE);
		builder.append("import lombok.Data;").append(NEW_LINE);
		builder.append("import lombok.EqualsAndHashCode;").append(NEW_LINE);
		builder.append("import lombok.ToString;").append(NEW_LINE);
		
		createAuthor(builder);
		builder.append("@Data").append(NEW_LINE);
		builder.append("@ToString(callSuper = true)").append(NEW_LINE);
		builder.append("@EqualsAndHashCode(callSuper = true)").append(NEW_LINE);
		builder.append("public class ").append(tableName).append("Result").append(" extends ApiResult").append(" {").append(NEW_LINE).append(NEW_LINE);
		
		builder.append(BLANK).append(BLANK).append("private static final long serialVersionUID = 1L;").append(NEW_LINE).append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("public PageResult<").append(tableName).append("> data;").append(NEW_LINE);
		
		builder.append("}").append(NEW_LINE);
		return builder;
	}

	@Override
	String producer() {
		return "apivo";
	}

}
