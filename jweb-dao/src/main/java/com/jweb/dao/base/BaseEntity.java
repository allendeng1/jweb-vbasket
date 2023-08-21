package com.jweb.dao.base;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import org.springframework.data.elasticsearch.annotations.*;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
@Data
public class BaseEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2741655525855851862L;
	
	@Id
	@Column(name = "id")
	@KeySql(useGeneratedKeys = true)//insert数据时在entity中返回ID值
	@ApiModelProperty(value="ID", dataType = "Long")
	@Field(type = FieldType.Long)
	public Long id;
	
	@Transient//非数据库字段
	public String customCondition;
	@Transient//非数据库字段
	public String orderCondition;
	@Transient//非数据库字段
	public Integer offset;
	@Transient//非数据库字段
	public Integer limit;
	
	public void and(String clounmName, Object value) {
		if(value == null){
			return;
		}
		if(this.customCondition == null){
			this.customCondition = clounmName+" = "+value;
		}else{
			this.customCondition += " and "+clounmName+" = "+value;
		}
	}
	public void andGE(String clounmName, Object value) {
		if(value == null){
			return;
		}
		if(this.customCondition == null){
			this.customCondition = clounmName+" >= "+value;
		}else{
			this.customCondition += " and "+clounmName+" >= "+value;
		}
	}
	public void andGT(String clounmName, Object value) {
		if(value == null){
			return;
		}
		if(this.customCondition == null){
			this.customCondition = clounmName+" > "+value;
		}else{
			this.customCondition += " and "+clounmName+" > "+value;
		}
	}
	public void andLE(String clounmName, Object value) {
		if(value == null){
			return;
		}
		if(this.customCondition == null){
			this.customCondition = clounmName+" <= "+value;
		}else{
			this.customCondition += " and "+clounmName+" <= "+value;
		}
	}
	public void andLT(String clounmName, Object value) {
		if(value == null){
			return;
		}
		if(this.customCondition == null){
			this.customCondition = clounmName+" < "+value;
		}else{
			this.customCondition += " and "+clounmName+" < "+value;
		}
	}
	public void andLike(String clounmName, Object value) {
		if(value == null){
			return;
		}
		if(this.customCondition == null){
			this.customCondition = clounmName+" like '%"+value+"%'";
		}else{
			this.customCondition += " and "+clounmName+" like '%"+value+"%'";
		}
	}
	
	public void sort(String clounmName, Sort sort) {
		if(clounmName == null || "".equals(clounmName.trim())){
			return;
		}
		this.orderCondition = clounmName+" "+sort.name();
	}
	public void sort(LinkedHashMap<String, Sort> sorts) {
		if(sorts == null || sorts.isEmpty()){
			return;
		}
		this.orderCondition = "";
		Iterator<String> it = sorts.keySet().iterator();
		while(it.hasNext()){
			String clounmName = it.next();
			this.orderCondition += clounmName+" "+sorts.get(clounmName).name()+",";
		}
		this.orderCondition = this.orderCondition.substring(0, this.orderCondition.length()-1);
	}
	public void offset(Integer offset, Integer limit){
		if(limit == null){
			return;
		}
		limit = limit < 1 ? 15 : limit;
		this.limit = limit;
		if(offset != null){
			offset = offset < 0 ? 0 : offset;
			this.offset = offset;
		}
	}
	public void page(Integer page, Integer pageSize){
		if(pageSize == null){
			return;
		}
		pageSize = pageSize < 1 ? 15 : pageSize;
		this.limit = pageSize;
		if(page != null){
			page = page < 1 ? 1 : page;
			this.offset = page * pageSize - pageSize;
		}
	}
	
	public enum Sort{
		DESC, ASC;
	}
}
