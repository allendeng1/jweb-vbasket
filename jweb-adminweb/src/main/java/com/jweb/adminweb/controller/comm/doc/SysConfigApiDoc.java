package com.jweb.adminweb.controller.comm.doc;

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
 * 2023/08/22 18:02
*/
@RequestMapping(value="sysconfig")
@Api(tags="系统配置管理接口")
public interface SysConfigApiDoc {

  @ApiOperation(value = "添加系统配置",notes = "添加系统配置")
  @ApiImplicitParams({
    @ApiImplicitParam(name="prop_key", dataType="String", paramType="form", required=true, value="配置KEY"),
    @ApiImplicitParam(name="prop_value", dataType="String", paramType="form", required=true, value="配置值"),
    @ApiImplicitParam(name="remark", dataType="String", paramType="form", required=true, value="配置描述说明")
  })
  @PostMapping(value = "/save")
  public ApiResult saveSysConfig(
    @RequestParam(value="prop_key", required=true) String propKey,
    @RequestParam(value="prop_value", required=true) String propValue,
    @RequestParam(value="remark", required=true) String remark);

  @ApiOperation(value = "根据条件查询系统配置",notes = "根据条件查询系统配置")
  @ApiImplicitParams({
    @ApiImplicitParam(name="status", dataType="Integer", paramType="form", required=false, value="状态：0-使用中，1-已废弃"),
    @ApiImplicitParam(name="prop_key", dataType="String", paramType="form", required=false, value="配置KEY"),
    @ApiImplicitParam(name="page", dataType="Integer", paramType="form", required=false, value="分页页码，默认1"),
    @ApiImplicitParam(name="limit", dataType="Integer", paramType="form", required=false, value="分页每页条数，默认20")
  })
  @PostMapping(value = "/query")
  public ApiResult getSysConfigList(
    @RequestParam(value="status", required=false) Integer status,
    @RequestParam(value="prop_key", required=false) String propKey,
    @RequestParam(value="page", required=false, defaultValue = "1") Integer page,
    @RequestParam(value="limit", required=false, defaultValue = "20") Integer limit);

  @ApiOperation(value = "根据ID查询系统配置",notes = "根据ID查询系统配置")
  @ApiImplicitParams({
    @ApiImplicitParam(name="id", dataType="Long", paramType="form", required=true, value="ID")
  })
  @PostMapping(value = "/queryById")
  public ApiResult getSysConfig(@RequestParam(value="id", required=true) Long id);

}
