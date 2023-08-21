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
public class IndexDocEntityProducer extends AbstractProducer{

	public IndexDocEntityProducer(TableInfo info, Context context) {
		super(info, context);
	}

	@Override
	StringBuilder coding() {
		
		String tableName = info.getTableName();
		List<Cel> cels = info.getCels();
		Set<String> imports = new HashSet<String>();
		imports.add("java.io.Serializable");
		imports.add("com.dengchao.searchengine.base.BaseDoc");
		imports.add("org.springframework.data.elasticsearch.annotations.*");
		boolean isForceAnnotation = context.isForceAnnotation();
		boolean isLombok = context.isLombok();
		if(isForceAnnotation){
			imports.add("javax.persistence.*");
		}

		if(isLombok){
			imports.add("lombok.Data");
			imports.add("lombok.EqualsAndHashCode");
			imports.add("lombok.ToString");
		}
		
		StringBuilder fieldBuilder = new StringBuilder();
		StringBuilder setGetBuilder = new StringBuilder();
		for(Cel cel : cels){
			String name = cel.getName();
			String dataType = cel.getDataType();
			String annotation = cel.getAnnotation();
			annotation = annotation == null ? "" : annotation;
			imports.add(dataType);
			dataType = dataType.substring(dataType.lastIndexOf(".")+1, dataType.length());
			name = formatName(name);
			
			if(isForceAnnotation){
				fieldBuilder.append(TABLE).append("/***").append(annotation).append("*/").append(NEW_LINE);
			}
			if(name.equalsIgnoreCase("id")) {
				fieldBuilder.append(TABLE).append("@Id").append(NEW_LINE);
			}
			//fieldBuilder.append(TABLE).append("@Column(name = \"").append(cel.getName()).append("\")").append(NEW_LINE);
			fieldBuilder.append(TABLE).append("@Field(type = ");
			if(dataType.equalsIgnoreCase("String")) {
				fieldBuilder.append("FieldType.Keyword");
			}else {
				fieldBuilder.append("FieldType.").append(dataType);
			}
			fieldBuilder.append(")").append(NEW_LINE);
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
		builder.append("package com.dengchao.searchengine.doc;").append(NEW_LINE).append(NEW_LINE);
		builder.append(importBuilder.toString()).append(NEW_LINE);
		createAuthor(builder);
		
		if(isLombok){
			builder.append("@Data").append(NEW_LINE);
			builder.append("@ToString(callSuper = true)").append(NEW_LINE);
			builder.append("@EqualsAndHashCode(callSuper = true)").append(NEW_LINE);
		}
		
		tableName = formatName(tableName);
		
		builder.append("@Document(indexName = \"").append(tableName.toLowerCase()).append("\", type = \"_doc\", replicas = 5, shards = 5, createIndex = true)").append(NEW_LINE);
		
		builder.append("public").append(BLANK).append("class").append(BLANK).append(tableName.substring(0, 1).toUpperCase()+tableName.substring(1, tableName.length())).append("Doc")
				.append(BLANK).append("extends").append(BLANK).append("BaseDoc").append(BLANK).append("implements Serializable").append(BLANK).append("{").append(NEW_LINE).append(NEW_LINE);
		
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("private static final long serialVersionUID = 1L;").append(NEW_LINE).append(NEW_LINE);
		
//		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("@Override").append(NEW_LINE);
//		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("public String indexName() {").append(NEW_LINE);
//		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("return \"").append(tableName).append("\";").append(NEW_LINE);
//		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("}").append(NEW_LINE);
//		
		builder.append(fieldBuilder.toString());
		builder.append(setGetBuilder.toString());
		builder.append("}");
		return builder;
	}

	@Override
	String producer() {
		return "indexdoc";
	}

}
