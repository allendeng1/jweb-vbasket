package com.jweb.dao.generator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.jweb.dao.generator.TableInfo.Cel;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
public class JavaEntityProducer extends AbstractProducer{

	public JavaEntityProducer(TableInfo info, Context context) {
		super(info, context);
	}

	@Override
	StringBuilder coding() {
		String packageName = packageName();
		String tableName = info.getTableName();
		String tableAnnotation = info.getAnnotation();
		boolean isCreateIndexDoc = info.isCreateIndexDoc();
		List<Cel> cels = info.getCels();
		Set<String> imports = new HashSet<String>();
		imports.add(packageName+".base.BaseEntity");
		boolean isForceAnnotation = context.isForceAnnotation();
		boolean isLombok = context.isLombok();
		boolean isSwagger = context.isSwagger();
		if(isForceAnnotation){
			imports.add("javax.persistence.*");
		}
		if(isLombok){
			imports.add("lombok.Data");
			imports.add("lombok.EqualsAndHashCode");
			imports.add("lombok.ToString");
		}
		if(isSwagger){
			imports.add("io.swagger.annotations.ApiModel");
			imports.add("io.swagger.annotations.ApiModelProperty");
		}
		if(isCreateIndexDoc) {
			imports.add("org.springframework.data.elasticsearch.annotations.*");
		}
		
		StringBuilder fieldBuilder = new StringBuilder();
		StringBuilder setGetBuilder = new StringBuilder();
		for(Cel cel : cels){
			String name = cel.getName();
			if(name.equalsIgnoreCase("id")){
				continue;
			}
			String dataType = cel.getDataType();
			String annotation = cel.getAnnotation();
			annotation = annotation == null ? "" : annotation;
			imports.add(dataType);
			dataType = dataType.substring(dataType.lastIndexOf(".")+1, dataType.length());
			name = formatName(name);
			
			if(isForceAnnotation){
				fieldBuilder.append(TABLE).append("/***").append(annotation).append("*/").append(NEW_LINE);
			}
			fieldBuilder.append(TABLE).append("@Column(name = \"").append(cel.getName()).append("\")").append(NEW_LINE);
			if(isSwagger){
				fieldBuilder.append(TABLE).append("@ApiModelProperty(value=\"").append(annotation).append("\", dataType=\"").append(dataType).append("\")").append(NEW_LINE);
			}
			if(isCreateIndexDoc) {
				fieldBuilder.append(TABLE).append("@Field(type = ");
				if(dataType.equalsIgnoreCase("String")) {
					fieldBuilder.append("FieldType.Keyword");
				}else {
					fieldBuilder.append("FieldType.").append(dataType);
				}
				fieldBuilder.append(")").append(NEW_LINE);
			}
			fieldBuilder.append(TABLE).append("private").append(BLANK).append(dataType).append(BLANK).append(name).append(";").append(NEW_LINE).append(NEW_LINE);
		
			if(!isLombok){
				setGetBuilder.append(TABLE).append("public").append(BLANK).append(dataType).append(BLANK).append("get")
							.append(name.substring(0, 1).toUpperCase()+name.substring(1, name.length()))
							.append("()").append(BLANK).append("{").append(NEW_LINE)
							.append(TABLE).append(TABLE).append("return").append(BLANK).append(name).append(";").append(NEW_LINE)
							.append(TABLE).append("}").append(NEW_LINE);
				
				setGetBuilder.append(TABLE).append("public").append(BLANK).append("void").append(BLANK).append("set")
							.append(name.substring(0, 1).toUpperCase()+name.substring(1, name.length()))
							.append("(").append(dataType).append(BLANK).append(name).append(")").append(BLANK).append("{").append(NEW_LINE)
							.append(TABLE).append(TABLE).append("this.").append(name).append("=").append(name).append(";").append(NEW_LINE)
							.append(TABLE).append("}").append(NEW_LINE);
			}
		}
		StringBuilder importBuilder = new StringBuilder();
		for(String imp : imports){
			importBuilder.append("import").append(BLANK).append(imp).append(";").append(NEW_LINE);
		}
		
		StringBuilder builder = new StringBuilder();
		builder.append("package ").append(packageName).append(".entity;").append(NEW_LINE).append(NEW_LINE);
		builder.append(importBuilder.toString()).append(NEW_LINE);
		createAuthor(builder);
		
		if(isLombok){
			builder.append("@Data").append(NEW_LINE);
			builder.append("@ToString(callSuper = true)").append(NEW_LINE);
			builder.append("@EqualsAndHashCode(callSuper = true)").append(NEW_LINE);
		}
	
		if(isSwagger){
			builder.append("@ApiModel(\"").append(tableAnnotation).append("\")").append(NEW_LINE);
		}
		builder.append("@Table(name = \"").append(tableName).append("\")").append(NEW_LINE);
		
		tableName = formatName(tableName);
		if(isCreateIndexDoc) {
			builder.append("@Document(indexName = \"").append(tableName.toLowerCase()).append("\", type = \"_doc\", replicas = 5, shards = 5, createIndex = true)").append(NEW_LINE);
		}
		builder.append("public").append(BLANK).append("class").append(BLANK).append(tableName.substring(0, 1).toUpperCase()+tableName.substring(1, tableName.length()))
				.append(BLANK).append("extends").append(BLANK).append("BaseEntity").append(BLANK).append("{").append(NEW_LINE).append(NEW_LINE);
		
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("private static final long serialVersionUID = 1L;").append(NEW_LINE).append(NEW_LINE);
		
		builder.append(fieldBuilder.toString());
		builder.append(setGetBuilder.toString());
		builder.append("}");
		return builder;
	}

	@Override
	String producer() {
		return "entity";
	}

}
