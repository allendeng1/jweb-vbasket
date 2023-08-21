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
 * 2023/08/21 17:20
*/
@RequestMapping(value="sysdictionary")
@Api(tags="系统字典管理接口")
public interface SysDictionaryApiDoc {

  @ApiOperation(value = "添加系统字典",notes = "添加系统字典")
  @ApiImplicitParams({
    @ApiImplicitParam(name="type", dataType="String", paramType="form", required=true, value="类型"),
    @ApiImplicitParam(name="region", dataType="String", paramType="form", required=true, value="地区"),
    @ApiImplicitParam(name="name", dataType="String", paramType="form", required=true, value="名称"),
    @ApiImplicitParam(name="status", dataType="Integer", paramType="form", required=true, value="状态：1-正常，2-停用"),
    @ApiImplicitParam(name="sort_col", dataType="Integer", paramType="form", required=true, value="排序列"),
    @ApiImplicitParam(name="ctdate", dataType="Long", paramType="form", required=true, value="创建时间"),
    @ApiImplicitParam(name="mddate", dataType="Long", paramType="form", required=true, value="更新时间")
  })
  @PostMapping(value = "/create")
  public ApiResult addSysDictionary(
    @RequestParam(value="type", required=true) String type,
    @RequestParam(value="region", required=true) String region,
    @RequestParam(value="name", required=true) String name,
    @RequestParam(value="status", required=true) Integer status,
    @RequestParam(value="sort_col", required=true) Integer sortCol,
    @RequestParam(value="ctdate", required=true) Long ctdate,
    @RequestParam(value="mddate", required=true) Long mddate);

  @ApiOperation(value = "修改系统字典",notes = "修改系统字典")
  @ApiImplicitParams({
    @ApiImplicitParam(name="id", dataType="Long", paramType="form", required=true, value=""),
    @ApiImplicitParam(name="type", dataType="String", paramType="form", required=false, value="类型"),
    @ApiImplicitParam(name="region", dataType="String", paramType="form", required=false, value="地区"),
    @ApiImplicitParam(name="name", dataType="String", paramType="form", required=false, value="名称"),
    @ApiImplicitParam(name="status", dataType="Integer", paramType="form", required=false, value="状态：1-正常，2-停用"),
    @ApiImplicitParam(name="sort_col", dataType="Integer", paramType="form", required=false, value="排序列"),
    @ApiImplicitParam(name="ctdate", dataType="Long", paramType="form", required=false, value="创建时间"),
    @ApiImplicitParam(name="mddate", dataType="Long", paramType="form", required=false, value="更新时间")
  })
  @PostMapping(value = "/update")
  public ApiResult editSysDictionary(
    @RequestParam(value="id", required=true) Long id,
    @RequestParam(value="type", required=false) String type,
    @RequestParam(value="region", required=false) String region,
    @RequestParam(value="name", required=false) String name,
    @RequestParam(value="status", required=false) Integer status,
    @RequestParam(value="sort_col", required=false) Integer sortCol,
    @RequestParam(value="ctdate", required=false) Long ctdate,
    @RequestParam(value="mddate", required=false) Long mddate);

  @ApiOperation(value = "根据条件查询系统字典",notes = "根据条件查询系统字典")
  @ApiImplicitParams({
    @ApiImplicitParam(name="type", dataType="String", paramType="form", required=false, value="类型"),
    @ApiImplicitParam(name="region", dataType="String", paramType="form", required=false, value="地区"),
    @ApiImplicitParam(name="name", dataType="String", paramType="form", required=false, value="名称"),
    @ApiImplicitParam(name="status", dataType="Integer", paramType="form", required=false, value="状态：1-正常，2-停用"),
    @ApiImplicitParam(name="sort_col", dataType="Integer", paramType="form", required=false, value="排序列"),
    @ApiImplicitParam(name="ctdate", dataType="Long", paramType="form", required=false, value="创建时间"),
    @ApiImplicitParam(name="mddate", dataType="Long", paramType="form", required=false, value="更新时间"),
    @ApiImplicitParam(name="page", dataType="Integer", paramType="form", required=false, value="分页页码，默认1"),
    @ApiImplicitParam(name="limit", dataType="Integer", paramType="form", required=false, value="分页每页条数，默认20")
  })
  @PostMapping(value = "/query")
  public ApiResult getSysDictionaryList(
    @RequestParam(value="type", required=false) String type,
    @RequestParam(value="region", required=false) String region,
    @RequestParam(value="name", required=false) String name,
    @RequestParam(value="status", required=false) Integer status,
    @RequestParam(value="sort_col", required=false) Integer sortCol,
    @RequestParam(value="ctdate", required=false) Long ctdate,
    @RequestParam(value="mddate", required=false) Long mddate,
    @RequestParam(value="page", required=false, defaultValue = "1") Integer page,
    @RequestParam(value="limit", required=false, defaultValue = "20") Integer limit);

  @ApiOperation(value = "根据ID查询系统字典",notes = "根据ID查询系统字典")
  @ApiImplicitParams({
    @ApiImplicitParam(name="id", dataType="Long", paramType="form", required=true, value="ID")
  })
  @PostMapping(value = "/queryById")
  public ApiResult getSysDictionary(@RequestParam(value="id", required=true) Long id);

}
