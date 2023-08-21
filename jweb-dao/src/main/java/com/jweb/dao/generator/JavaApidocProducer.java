package com.jweb.dao.generator;

import java.util.List;

import com.jweb.dao.generator.TableInfo.Cel;

public class JavaApidocProducer extends AbstractProducer{

	public JavaApidocProducer(TableInfo info, Context context) {
		super(info, context);
	}

	@Override
	StringBuilder coding() {
		String tableName = info.getTableName();
		String annotation = info.getAnnotation();
		List<Cel> cels = info.getCels();
		tableName = formatName(tableName);
		tableName = tableName.substring(0, 1).toUpperCase()+tableName.substring(1, tableName.length());
		String packageName = packageName();
		String apiPackageName = packageName.substring(0, packageName.lastIndexOf(".")+1)+"adminweb.generatecode";
		
		StringBuilder builder = new StringBuilder();
		builder.append("package ").append(apiPackageName).append(";").append(NEW_LINE).append(NEW_LINE);
		builder.append("import com.jweb.common.api.ApiResult;").append(NEW_LINE);
		builder.append("import io.swagger.annotations.Api;").append(NEW_LINE);
		builder.append("import io.swagger.annotations.ApiImplicitParam;").append(NEW_LINE);
		builder.append("import io.swagger.annotations.ApiImplicitParams;").append(NEW_LINE);
		builder.append("import io.swagger.annotations.ApiOperation;").append(NEW_LINE);
		builder.append("import org.springframework.web.bind.annotation.PostMapping;").append(NEW_LINE);
		builder.append("import org.springframework.web.bind.annotation.RequestMapping;").append(NEW_LINE);
		builder.append("import org.springframework.web.bind.annotation.RequestParam;").append(NEW_LINE);
		
		createAuthor(builder);
		
		builder.append("@RequestMapping(value=\"").append(tableName.toLowerCase()).append("\")").append(NEW_LINE);
		builder.append("@Api(tags=\"").append(annotation).append("管理接口\")").append(NEW_LINE);
		builder.append("public interface ").append(tableName).append("ApiDoc").append(" {").append(NEW_LINE).append(NEW_LINE);
		
		//创建API
		builder.append(BLANK).append(BLANK).append("@ApiOperation(value = \"添加").append(annotation).append("\",notes = \"添加").append(annotation).append("\")").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("@ApiImplicitParams({").append(NEW_LINE);
		for(int i=0;i<cels.size();i++) {
			Cel cel = cels.get(i);
			if(cel.getName().equals("id")) {
				continue;
			}
			String dataType = cel.getDataType();
			String celAnnotation = cel.getAnnotation();
			celAnnotation = celAnnotation == null ? "" : celAnnotation;
			dataType = dataType.substring(dataType.lastIndexOf(".")+1, dataType.length());
			
			builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK)
			.append("@ApiImplicitParam(name=\"").append(cel.getName()).append("\", dataType=\"").append(dataType)
			.append("\", paramType=\"form\", required=").append(cel.isNotNull()).append(", value=\"").append(celAnnotation).append("\")");
			if(i+1 < cels.size()) {
				builder.append(",");
			}
			builder.append(NEW_LINE);
		}
		builder.append(BLANK).append(BLANK).append("})").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("@PostMapping(value = \"/create\")").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("public ApiResult add").append(tableName).append("(").append(NEW_LINE);
		for(int i=0;i<cels.size();i++) {
			Cel cel = cels.get(i);
			if(cel.getName().equals("id")) {
				continue;
			}
			
			String dataType = cel.getDataType();
			dataType = dataType.substring(dataType.lastIndexOf(".")+1, dataType.length());
			
			builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK)
			.append("@RequestParam(value=\"").append(cel.getName()).append("\", required=").append(cel.isNotNull()).append(")").append(BLANK).append(dataType).append(BLANK).append(formatName(cel.getName()));
			if(i+1 == cels.size()) {
				builder.append(");");
			}else {
				builder.append(",");
			}
			builder.append(NEW_LINE);
		}
		builder.append(NEW_LINE);
		
		
		//编辑API
		builder.append(BLANK).append(BLANK).append("@ApiOperation(value = \"修改").append(annotation).append("\",notes = \"修改").append(annotation).append("\")").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("@ApiImplicitParams({").append(NEW_LINE);
		for(int i=0;i<cels.size();i++) {
			Cel cel = cels.get(i);
			boolean isRequired = cel.getName().equals("id");
			String dataType = cel.getDataType();
			String celAnnotation = cel.getAnnotation();
			celAnnotation = celAnnotation == null ? "" : celAnnotation;
			dataType = dataType.substring(dataType.lastIndexOf(".")+1, dataType.length());
			
			builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK)
			.append("@ApiImplicitParam(name=\"").append(cel.getName()).append("\", dataType=\"").append(dataType)
			.append("\", paramType=\"form\", required=").append(isRequired).append(", value=\"").append(celAnnotation).append("\")");
			if(i+1 < cels.size()) {
				builder.append(",");
			}
			builder.append(NEW_LINE);
		}
		builder.append(BLANK).append(BLANK).append("})").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("@PostMapping(value = \"/update\")").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("public ApiResult edit").append(tableName).append("(").append(NEW_LINE);
		for(int i=0;i<cels.size();i++) {
			Cel cel = cels.get(i);
			boolean isRequired = cel.getName().equals("id");
			String dataType = cel.getDataType();
			dataType = dataType.substring(dataType.lastIndexOf(".")+1, dataType.length());
			
			builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK)
			.append("@RequestParam(value=\"").append(cel.getName()).append("\", required=").append(isRequired).append(")").append(BLANK).append(dataType).append(BLANK).append(formatName(cel.getName()));
			if(i+1 == cels.size()) {
				builder.append(");");
			}else {
				builder.append(",");
			}
			builder.append(NEW_LINE);
		}
		builder.append(NEW_LINE);
		
		//根据条件查询API
		builder.append(BLANK).append(BLANK).append("@ApiOperation(value = \"根据条件查询").append(annotation).append("\",notes = \"根据条件查询").append(annotation).append("\")").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("@ApiImplicitParams({").append(NEW_LINE);
		for(int i=0;i<cels.size();i++) {
			Cel cel = cels.get(i);
			if(cel.getName().equals("id")) {
				continue;
			}
			String dataType = cel.getDataType();
			String celAnnotation = cel.getAnnotation();
			celAnnotation = celAnnotation == null ? "" : celAnnotation;
			dataType = dataType.substring(dataType.lastIndexOf(".")+1, dataType.length());
			
			builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK)
			.append("@ApiImplicitParam(name=\"").append(cel.getName()).append("\", dataType=\"").append(dataType)
			.append("\", paramType=\"form\", required=false").append(", value=\"").append(celAnnotation).append("\")").append(",").append(NEW_LINE);
		}
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK)
		.append("@ApiImplicitParam(name=\"page\", dataType=\"Integer\", paramType=\"form\", required=false, value=\"分页页码，默认1\")").append(",").append(NEW_LINE);
		
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK)
		.append("@ApiImplicitParam(name=\"limit\", dataType=\"Integer\", paramType=\"form\", required=false, value=\"分页每页条数，默认20\")").append(NEW_LINE);
		
		builder.append(BLANK).append(BLANK).append("})").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("@PostMapping(value = \"/query\")").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("public ApiResult get").append(tableName).append("List(").append(NEW_LINE);
		for(int i=0;i<cels.size();i++) {
			Cel cel = cels.get(i);
			if(cel.getName().equals("id")) {
				continue;
			}
			
			String dataType = cel.getDataType();
			dataType = dataType.substring(dataType.lastIndexOf(".")+1, dataType.length());
			
			builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK)
			.append("@RequestParam(value=\"").append(cel.getName()).append("\", required=false").append(")").append(BLANK).append(dataType).append(BLANK).append(formatName(cel.getName())).append(",").append(NEW_LINE);
		}
		
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK)
		.append("@RequestParam(value=\"page\", required=false, defaultValue = \"1\") Integer page,").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK)
		.append("@RequestParam(value=\"limit\", required=false, defaultValue = \"20\") Integer limit);").append(NEW_LINE);
		
		builder.append(NEW_LINE);
		
		//根据ID查询API
		builder.append(BLANK).append(BLANK).append("@ApiOperation(value = \"根据ID查询").append(annotation).append("\",notes = \"根据ID查询").append(annotation).append("\")").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("@ApiImplicitParams({").append(NEW_LINE);
		
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK)
		.append("@ApiImplicitParam(name=\"id\", dataType=\"Long\", paramType=\"form\", required=true").append(", value=\"ID\")").append(NEW_LINE);
		
		builder.append(BLANK).append(BLANK).append("})").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("@PostMapping(value = \"/queryById\")").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("public ApiResult get").append(tableName).append("(");
		
		builder.append("@RequestParam(value=\"id\", required=true").append(")").append(BLANK).append("Long").append(BLANK).append("id").append(");").append(NEW_LINE).append(NEW_LINE);
		
		builder.append("}").append(NEW_LINE);
		return builder;
	}

	@Override
	String producer() {
		return "apidoc";
	}

}
