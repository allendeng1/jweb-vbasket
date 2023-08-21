package com.jweb.dao.generator;

import java.util.List;

import com.jweb.dao.generator.TableInfo.Cel;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
public class JavaMapperXmlProducer extends AbstractProducer{

	public JavaMapperXmlProducer(TableInfo info, Context context) {
		super(info, context);
	}

	@Override
	StringBuilder coding() {
		String tableName = info.getTableName();
		tableName = formatName(tableName);
		String packageName = packageName();
		List<Cel> cels = info.getCels();
		String entityName = tableName.substring(0, 1).toUpperCase()+tableName.substring(1, tableName.length());
		StringBuilder builder = new StringBuilder();
		builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append(NEW_LINE);
		builder.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">").append(NEW_LINE);
		builder.append("<mapper namespace=\"").append(entityName).append("\">").append(NEW_LINE);
		builder.append("<!--代码生成器会覆盖此文件，自定义SQL请写到query文件中-->").append(NEW_LINE).append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("<resultMap id=\""+entityName+".ResultMap\" type=\"").append(packageName).append(".entity.").append(entityName).append("\">").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("<id column=\"id\" jdbcType=\"BIGINT\" property=\"id\" />").append(NEW_LINE);
		
		StringBuilder whereBuilder = new StringBuilder();
		StringBuilder clounmBuilder = new StringBuilder();
		StringBuilder clounmNoIdBuilder = new StringBuilder();
		StringBuilder insertValuesBuilder = new StringBuilder();
		StringBuilder updateSetBuilder = new StringBuilder();
		
		for(Cel cel : cels){
			String name = cel.getName();
			String fieldName = formatName(name);
			String dbType = cel.getDbType();
			String dataType = cel.getDataType();
			dataType = dataType.substring(dataType.lastIndexOf(".")+1, dataType.length());
			
			clounmBuilder.append(name).append(",");
			
			
			whereBuilder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("<if test=\"").append(fieldName).append(" != null\">").append(NEW_LINE);
			whereBuilder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("and ").append(name).append(" = #{").append(fieldName).append("}").append(NEW_LINE);
			whereBuilder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("</if>").append(NEW_LINE);
			
			if(name.equalsIgnoreCase("id")){
				continue;
			}
			
			insertValuesBuilder.append("#{").append(fieldName).append("},");
			
			updateSetBuilder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("<if test=\"").append(fieldName).append(" != null\">").append(NEW_LINE);
			updateSetBuilder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(name).append(" = #{").append(fieldName).append("},").append(NEW_LINE);
			updateSetBuilder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("</if>").append(NEW_LINE);
			
			clounmNoIdBuilder.append(name).append(",");
			builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("<result column=\"").append(name).append("\" jdbcType=\"").append(dbType).append("\" property=\"").append(fieldName).append("\" />").append(NEW_LINE);
			
		}
		builder.append(BLANK).append(BLANK).append("</resultMap>").append(NEW_LINE).append(NEW_LINE);
		
		String clounms = clounmBuilder.toString();
		String noidclounms = clounmNoIdBuilder.toString();
		
		builder.append(BLANK).append(BLANK).append("<sql id=\""+entityName+".clounms\">").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(clounms.substring(0, clounms.length()-1)).append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("</sql>").append(NEW_LINE).append(NEW_LINE);
		
		builder.append(BLANK).append(BLANK).append("<sql id=\""+entityName+".noidclounms\">").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(noidclounms.substring(0, noidclounms.length()-1)).append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("</sql>").append(NEW_LINE).append(NEW_LINE);
		
		builder.append(BLANK).append(BLANK).append("<sql id=\""+entityName+".where\">").append(NEW_LINE);
		builder.append(whereBuilder.toString());
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("<if test=\"customCondition != null and customCondition != '' \">").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("and ${customCondition}").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("</if>").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("</sql>").append(NEW_LINE).append(NEW_LINE);
		
		builder.append(BLANK).append(BLANK).append("<sql id=\""+entityName+".orderAndlimit\">").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("<if test=\"orderCondition != null and orderCondition != '' \">").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("order by ${orderCondition}").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("</if>").append(NEW_LINE);
		
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("<if test=\"limit != null and limit > 0 \">").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("<if test=\"offset != null and offset >= 0 \">").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("limit #{limit} offset #{offset}").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("</if>").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("<if test=\"offset == null or offset &lt; 0 \">").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("limit #{limit}").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("</if>").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("</if>").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("</sql>").append(NEW_LINE).append(NEW_LINE);
		
		String insertValues = insertValuesBuilder.toString();
		
		builder.append(BLANK).append(BLANK).append("<insert id=\""+entityName+".insert\" parameterType=\"").append(packageName).append(".entity.").append(entityName).append("\" useGeneratedKeys=\"true\" keyProperty=\"id\">").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("insert into ").append(info.getTableName()).append(" (<include refid=\""+entityName+".noidclounms\" />) values (").append(insertValues.substring(0, insertValues.length()-1)).append(")").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("</insert>").append(NEW_LINE).append(NEW_LINE);
		
		
		builder.append(BLANK).append(BLANK).append("<select id=\""+entityName+".selectIds\" resultType=\"Long\" parameterType=\"").append(packageName).append(".entity.").append(entityName).append("\">").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("select id from ").append(info.getTableName()).append(" where 1=1").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("<include refid=\""+entityName+".where\" />").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("<include refid=\""+entityName+".orderAndlimit\" />").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("</select>").append(NEW_LINE).append(NEW_LINE);
		
		builder.append(BLANK).append(BLANK).append("<select id=\""+entityName+".selectList\" resultMap=\""+entityName+".ResultMap\" parameterType=\"").append(packageName).append(".entity.").append(entityName).append("\">").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("select <include refid=\""+entityName+".clounms\" /> from ").append(info.getTableName()).append(" where 1=1").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("<include refid=\""+entityName+".where\" />").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("<include refid=\""+entityName+".orderAndlimit\" />").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("</select>").append(NEW_LINE).append(NEW_LINE);
		
		builder.append(BLANK).append(BLANK).append("<select id=\""+entityName+".selectById\" resultMap=\""+entityName+".ResultMap\" parameterType=\"Long\">").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("select <include refid=\""+entityName+".clounms\" /> from ").append(info.getTableName()).append(" where id=#{value}").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("</select>").append(NEW_LINE).append(NEW_LINE);
		
		builder.append(BLANK).append(BLANK).append("<select id=\""+entityName+".selectCount\" resultType=\"Integer\" parameterType=\"").append(packageName).append(".entity.").append(tableName.substring(0, 1).toUpperCase()+tableName.substring(1, tableName.length())).append("\">").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("select count(*) from ").append(info.getTableName()).append(" where 1=1").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("<include refid=\""+entityName+".where\" />").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("</select>").append(NEW_LINE).append(NEW_LINE);
		
		builder.append(BLANK).append(BLANK).append("<update id=\""+entityName+".updateById\" parameterType=\"").append(packageName).append(".entity.").append(entityName).append("\">").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("update ").append(info.getTableName()).append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("<set>").append(NEW_LINE);
		builder.append(updateSetBuilder.toString());
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("</set>").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("where id = #{id}").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("</update>").append(NEW_LINE).append(NEW_LINE);
		
		builder.append(BLANK).append(BLANK).append("<delete id=\""+entityName+".deleteById\" parameterType=\"Long\">").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append(BLANK).append(BLANK).append("delete from ").append(info.getTableName()).append(" where id = #{value}").append(NEW_LINE);
		builder.append(BLANK).append(BLANK).append("</delete>").append(NEW_LINE);
		
		builder.append("</mapper>").append(NEW_LINE);
		
		return builder;
	}

	@Override
	String producer() {
		return "mapperxml";
	}

}
