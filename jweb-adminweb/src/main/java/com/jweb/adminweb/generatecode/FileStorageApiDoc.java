package com.jweb.adminweb.generatecode;

import com.jweb.common.api.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
/**
 *
 * @author 邓超
 *
 * 2023/08/22 14:43
*/
@RequestMapping(value="filestorage")
@Api(tags="文件存储记录管理接口")
public interface FileStorageApiDoc {

  @ApiOperation(value = "添加文件存储记录",notes = "添加文件存储记录")
  @ApiImplicitParams({
    @ApiImplicitParam(name="storage_site", dataType="String", paramType="form", required=true, value="存储站点"),
    @ApiImplicitParam(name="owner", dataType="String", paramType="form", required=true, value="拥有者"),
    @ApiImplicitParam(name="type", dataType="Integer", paramType="form", required=true, value="业务类型"),
    @ApiImplicitParam(name="storage_path", dataType="String", paramType="form", required=true, value="存储路径"),
    @ApiImplicitParam(name="file_type", dataType="String", paramType="form", required=true, value="文件类型"),
    @ApiImplicitParam(name="file_name", dataType="String", paramType="form", required=true, value="文件名称"),
    @ApiImplicitParam(name="file_size", dataType="Long", paramType="form", required=true, value="文件大小"),
    @ApiImplicitParam(name="is_delete", dataType="Boolean", paramType="form", required=true, value=""),
    @ApiImplicitParam(name="ctdate", dataType="Long", paramType="form", required=true, value=""),
    @ApiImplicitParam(name="mddate", dataType="Long", paramType="form", required=true, value="")
  })
  @PostMapping(value = "/create")
  public ApiResult addFileStorage(
    @RequestParam(value="storage_site", required=true) String storageSite,
    @RequestParam(value="owner", required=true) String owner,
    @RequestParam(value="type", required=true) Integer type,
    @RequestParam(value="storage_path", required=true) String storagePath,
    @RequestParam(value="file_type", required=true) String fileType,
    @RequestParam(value="file_name", required=true) String fileName,
    @RequestParam(value="file_size", required=true) Long fileSize,
    @RequestParam(value="is_delete", required=true) Boolean isDelete,
    @RequestParam(value="ctdate", required=true) Long ctdate,
    @RequestParam(value="mddate", required=true) Long mddate);

  @ApiOperation(value = "修改文件存储记录",notes = "修改文件存储记录")
  @ApiImplicitParams({
    @ApiImplicitParam(name="id", dataType="Long", paramType="form", required=true, value=""),
    @ApiImplicitParam(name="storage_site", dataType="String", paramType="form", required=false, value="存储站点"),
    @ApiImplicitParam(name="owner", dataType="String", paramType="form", required=false, value="拥有者"),
    @ApiImplicitParam(name="type", dataType="Integer", paramType="form", required=false, value="业务类型"),
    @ApiImplicitParam(name="storage_path", dataType="String", paramType="form", required=false, value="存储路径"),
    @ApiImplicitParam(name="file_type", dataType="String", paramType="form", required=false, value="文件类型"),
    @ApiImplicitParam(name="file_name", dataType="String", paramType="form", required=false, value="文件名称"),
    @ApiImplicitParam(name="file_size", dataType="Long", paramType="form", required=false, value="文件大小"),
    @ApiImplicitParam(name="is_delete", dataType="Boolean", paramType="form", required=false, value=""),
    @ApiImplicitParam(name="ctdate", dataType="Long", paramType="form", required=false, value=""),
    @ApiImplicitParam(name="mddate", dataType="Long", paramType="form", required=false, value="")
  })
  @PostMapping(value = "/update")
  public ApiResult editFileStorage(
    @RequestParam(value="id", required=true) Long id,
    @RequestParam(value="storage_site", required=false) String storageSite,
    @RequestParam(value="owner", required=false) String owner,
    @RequestParam(value="type", required=false) Integer type,
    @RequestParam(value="storage_path", required=false) String storagePath,
    @RequestParam(value="file_type", required=false) String fileType,
    @RequestParam(value="file_name", required=false) String fileName,
    @RequestParam(value="file_size", required=false) Long fileSize,
    @RequestParam(value="is_delete", required=false) Boolean isDelete,
    @RequestParam(value="ctdate", required=false) Long ctdate,
    @RequestParam(value="mddate", required=false) Long mddate);

  @ApiOperation(value = "根据条件查询文件存储记录",notes = "根据条件查询文件存储记录")
  @ApiImplicitParams({
    @ApiImplicitParam(name="storage_site", dataType="String", paramType="form", required=false, value="存储站点"),
    @ApiImplicitParam(name="owner", dataType="String", paramType="form", required=false, value="拥有者"),
    @ApiImplicitParam(name="type", dataType="Integer", paramType="form", required=false, value="业务类型"),
    @ApiImplicitParam(name="storage_path", dataType="String", paramType="form", required=false, value="存储路径"),
    @ApiImplicitParam(name="file_type", dataType="String", paramType="form", required=false, value="文件类型"),
    @ApiImplicitParam(name="file_name", dataType="String", paramType="form", required=false, value="文件名称"),
    @ApiImplicitParam(name="file_size", dataType="Long", paramType="form", required=false, value="文件大小"),
    @ApiImplicitParam(name="is_delete", dataType="Boolean", paramType="form", required=false, value=""),
    @ApiImplicitParam(name="ctdate", dataType="Long", paramType="form", required=false, value=""),
    @ApiImplicitParam(name="mddate", dataType="Long", paramType="form", required=false, value=""),
    @ApiImplicitParam(name="page", dataType="Integer", paramType="form", required=false, value="分页页码，默认1"),
    @ApiImplicitParam(name="limit", dataType="Integer", paramType="form", required=false, value="分页每页条数，默认20")
  })
  @PostMapping(value = "/query")
  public ApiResult getFileStorageList(
    @RequestParam(value="storage_site", required=false) String storageSite,
    @RequestParam(value="owner", required=false) String owner,
    @RequestParam(value="type", required=false) Integer type,
    @RequestParam(value="storage_path", required=false) String storagePath,
    @RequestParam(value="file_type", required=false) String fileType,
    @RequestParam(value="file_name", required=false) String fileName,
    @RequestParam(value="file_size", required=false) Long fileSize,
    @RequestParam(value="is_delete", required=false) Boolean isDelete,
    @RequestParam(value="ctdate", required=false) Long ctdate,
    @RequestParam(value="mddate", required=false) Long mddate,
    @RequestParam(value="page", required=false, defaultValue = "1") Integer page,
    @RequestParam(value="limit", required=false, defaultValue = "20") Integer limit);

  @ApiOperation(value = "根据ID查询文件存储记录",notes = "根据ID查询文件存储记录")
  @ApiImplicitParams({
    @ApiImplicitParam(name="id", dataType="Long", paramType="form", required=true, value="ID")
  })
  @PostMapping(value = "/queryById")
  public ApiResult getFileStorage(@RequestParam(value="id", required=true) Long id);

}
