package com.jweb.dao.generator;

import java.util.List;

import com.jweb.dao.generator.TableInfo.Cel;

public class JavaApicontrollerProducer extends AbstractProducer{

	public JavaApicontrollerProducer(TableInfo info, Context context) {
		super(info, context);
	}

	@Override
	StringBuilder coding() {
		String tableName = info.getTableName();
		List<Cel> cels = info.getCels();
		tableName = formatName(tableName);
		String upperTableName = tableName.substring(0, 1).toUpperCase()+tableName.substring(1, tableName.length());
		String lowerTableName = tableName.substring(0, 1).toLowerCase()+tableName.substring(1, tableName.length());
		String packageName = packageName();
		String apiPackageName = packageName.substring(0, packageName.lastIndexOf(".")+1)+"adminweb.generatecode";
		String servicePackageName = packageName.substring(0, packageName.lastIndexOf(".")+1)+"service";
		
		StringBuilder builder = new StringBuilder();
		builder.append("package ").append(apiPackageName).append(";").append(NEW_LINE).append(NEW_LINE);
		builder.append("import com.jweb.common.api.ApiResult;").append(NEW_LINE);
		builder.append("import com.jweb.dao.base.PageResult;").append(NEW_LINE);
		builder.append("import com.jweb.common.api.BaseController;").append(NEW_LINE);
		builder.append("import com.jweb.common.exception.MyException;").append(NEW_LINE);
		builder.append("import ").append(packageName).append(".entity.").append(upperTableName).append(";").append(NEW_LINE);
		builder.append("import org.springframework.beans.factory.annotation.Autowired;").append(NEW_LINE);
		builder.append("import org.springframework.web.bind.annotation.RestController;").append(NEW_LINE);
		
		builder.append("import ").append(apiPackageName).append(".vo.").append(upperTableName).append("Result;").append(NEW_LINE);
		builder.append("import ").append(apiPackageName).append(".").append(upperTableName).append("ApiDoc;").append(NEW_LINE);
		builder.append("import ").append(servicePackageName).append(".").append(upperTableName).append("Service;").append(NEW_LINE);
		
		createAuthor(builder);
		
		builder.append("@RestController").append(NEW_LINE);
		builder.append("public class ").append(upperTableName).append("Controller").append(" extends BaseController implements ").append(upperTableName).append("ApiDoc").append(" {").append(NEW_LINE).append(NEW_LINE);
		
		String member = lowerTableName+"Service";
		builder.append(BLANK).append(BLANK).append("@Autowired").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("private").append(BLANK).append(upperTableName).append("Service").append(BLANK).append(member).append(";").append(NEW_LINE).append(NEW_LINE);
		
		//创建API
		builder.append(BLANK).append(BLANK).append("@Override").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("public ApiResult add").append(upperTableName).append("(").append(NEW_LINE);
		for(int i=0;i<cels.size();i++) {
			Cel cel = cels.get(i);
			if(cel.getName().equals("id")) {
				continue;
			}
			
			String dataType = cel.getDataType();
			dataType = dataType.substring(dataType.lastIndexOf(".")+1, dataType.length());
			
			builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(dataType).append(BLANK).append(formatName(cel.getName()));
			if(i+1 == cels.size()) {
				builder.append(") {");
			}else {
				builder.append(",");
			}
			builder.append(NEW_LINE);
		}

		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("ApiResult result = new ApiResult();").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("try {").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(upperTableName).append(" entity = new ").append(upperTableName).append("();").append(NEW_LINE);
		
		for(int i=0;i<cels.size();i++) {
			Cel cel = cels.get(i);
			if(cel.getName().equals("id")) {
				continue;
			}
			
			String dataType = cel.getDataType();
			dataType = dataType.substring(dataType.lastIndexOf(".")+1, dataType.length());
			String name = formatName(cel.getName());
			String upperName = name.substring(0, 1).toUpperCase()+name.substring(1, name.length());
			builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("entity.set").append(upperName).append("(").append(name).append(");").append(NEW_LINE);
		}
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(member).append(".").append("saveEntity(").append("entity").append(");").append(NEW_LINE);
		
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("} catch (MyException e) {").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("result.bizFail(e);").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("}").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("return result;").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("}").append(NEW_LINE);
		
		builder.append(NEW_LINE);
		
		
		//编辑API
		builder.append(BLANK).append(BLANK).append("@Override").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("public ApiResult edit").append(upperTableName).append("(").append(NEW_LINE);
		for(int i=0;i<cels.size();i++) {
			Cel cel = cels.get(i);
			
			String dataType = cel.getDataType();
			dataType = dataType.substring(dataType.lastIndexOf(".")+1, dataType.length());
			
			builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(dataType).append(BLANK).append(formatName(cel.getName()));
			if(i+1 == cels.size()) {
				builder.append(") {");
			}else {
				builder.append(",");
			}
			builder.append(NEW_LINE);
		}

		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("ApiResult result = new ApiResult();").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("try {").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(upperTableName).append(" entity = new ").append(upperTableName).append("();").append(NEW_LINE);
		
		for(int i=0;i<cels.size();i++) {
			Cel cel = cels.get(i);
			
			String dataType = cel.getDataType();
			dataType = dataType.substring(dataType.lastIndexOf(".")+1, dataType.length());
			String name = formatName(cel.getName());
			String upperName = name.substring(0, 1).toUpperCase()+name.substring(1, name.length());
			builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("entity.set").append(upperName).append("(").append(name).append(");").append(NEW_LINE);
		}
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(member).append(".").append("saveEntity(").append("entity").append(");").append(NEW_LINE);
		
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("} catch (MyException e) {").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("result.bizFail(e);").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("}").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("return result;").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("}").append(NEW_LINE);
		
		builder.append(NEW_LINE);
		
		//根据条件查询API
		builder.append(BLANK).append(BLANK).append("@Override").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("public ").append(upperTableName).append("Result get").append(upperTableName).append("List(").append(NEW_LINE);
		for(int i=0;i<cels.size();i++) {
			Cel cel = cels.get(i);
			if(cel.getName().equals("id")) {
				continue;
			}
			
			String dataType = cel.getDataType();
			dataType = dataType.substring(dataType.lastIndexOf(".")+1, dataType.length());
			
			builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(dataType).append(BLANK).append(formatName(cel.getName())).append(",").append(NEW_LINE);
		}
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("Integer page,").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("Integer limit) {").append(NEW_LINE);

		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(upperTableName).append("Result result = new ").append(upperTableName).append("Result();").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("try {").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(upperTableName).append(" entity = new ").append(upperTableName).append("();").append(NEW_LINE);
		
		for(int i=0;i<cels.size();i++) {
			Cel cel = cels.get(i);
			if(cel.getName().equals("id")) {
				continue;
			}
			
			String dataType = cel.getDataType();
			dataType = dataType.substring(dataType.lastIndexOf(".")+1, dataType.length());
			String name = formatName(cel.getName());
			String upperName = name.substring(0, 1).toUpperCase()+name.substring(1, name.length());
			builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("entity.set").append(upperName).append("(").append(name).append(");").append(NEW_LINE);
		}
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("entity.page(page, limit);").append(NEW_LINE);
		
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("PageResult<").append(upperTableName).append("> pageData = ").append(member).append(".").append("getPageListByCondition(").append("entity").append(");").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("result.setData(pageData);").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("} catch (MyException e) {").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("result.bizFail(e);").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("}").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("return result;").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("}").append(NEW_LINE);
		
		builder.append(NEW_LINE);
		
		//根据ID查询API
		builder.append(BLANK).append(BLANK).append("@Override").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("public ApiResult get").append(upperTableName).append("(Long id) {").append(NEW_LINE).append(NEW_LINE);
		
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("ApiResult result = new ApiResult();").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("try {").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(upperTableName).append(" data = ").append(member).append(".").append("getById(id);").append(NEW_LINE);
		
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("result.setData(data);").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("} catch (MyException e) {").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("result.bizFail(e);").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("}").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("return result;").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("}").append(NEW_LINE);
		
		builder.append("}").append(NEW_LINE);
		return builder;
	}

	@Override
	String producer() {
		return "apicontroller";
	}

}
