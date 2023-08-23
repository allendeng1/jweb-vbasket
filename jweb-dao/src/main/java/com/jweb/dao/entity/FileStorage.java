package com.jweb.dao.entity;

import com.jweb.dao.base.BaseEntity;
import java.lang.Long;
import lombok.Data;
import lombok.ToString;
import io.swagger.annotations.ApiModel;
import java.lang.Boolean;
import javax.persistence.*;
import java.lang.String;
import lombok.EqualsAndHashCode;
import java.lang.Integer;
import io.swagger.annotations.ApiModelProperty;

/**
 *
 * @author 邓超
 *
 * 2023/08/22 18:02
*/
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("文件存储记录")
@Table(name = "file_storage")
public class FileStorage extends BaseEntity {

    private static final long serialVersionUID = 1L;

	/***存储站点*/
	@Column(name = "storage_site")
	@ApiModelProperty(value="存储站点", dataType="String")
	private String storageSite;

	/***拥有者*/
	@Column(name = "owner")
	@ApiModelProperty(value="拥有者", dataType="String")
	private String owner;

	/***业务类型*/
	@Column(name = "type")
	@ApiModelProperty(value="业务类型", dataType="Integer")
	private Integer type;

	/***存储路径*/
	@Column(name = "storage_path")
	@ApiModelProperty(value="存储路径", dataType="String")
	private String storagePath;

	/***文件类型*/
	@Column(name = "file_type")
	@ApiModelProperty(value="文件类型", dataType="String")
	private String fileType;

	/***文件名称*/
	@Column(name = "file_name")
	@ApiModelProperty(value="文件名称", dataType="String")
	private String fileName;

	/***文件大小*/
	@Column(name = "file_size")
	@ApiModelProperty(value="文件大小", dataType="Long")
	private Long fileSize;

	/****/
	@Column(name = "is_delete")
	@ApiModelProperty(value="", dataType="Boolean")
	private Boolean isDelete;

	/****/
	@Column(name = "ctdate")
	@ApiModelProperty(value="", dataType="Long")
	private Long ctdate;

	/****/
	@Column(name = "mddate")
	@ApiModelProperty(value="", dataType="Long")
	private Long mddate;

}