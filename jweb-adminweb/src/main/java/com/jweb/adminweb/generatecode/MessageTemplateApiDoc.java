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
@RequestMapping(value="messagetemplate")
@Api(tags="消息模板管理接口")
public interface MessageTemplateApiDoc {

  @ApiOperation(value = "添加消息模板",notes = "添加消息模板")
  @ApiImplicitParams({
    @ApiImplicitParam(name="app_id", dataType="Long", paramType="form", required=true, value="app Id"),
    @ApiImplicitParam(name="code", dataType="String", paramType="form", required=true, value="模板编号"),
    @ApiImplicitParam(name="sent_channel", dataType="String", paramType="form", required=true, value="发送渠道"),
    @ApiImplicitParam(name="template_code", dataType="String", paramType="form", required=false, value="第三方平台模板编号"),
    @ApiImplicitParam(name="content", dataType="String", paramType="form", required=true, value="消息内容"),
    @ApiImplicitParam(name="is_delete", dataType="Boolean", paramType="form", required=true, value=""),
    @ApiImplicitParam(name="ctdate", dataType="Long", paramType="form", required=true, value=""),
    @ApiImplicitParam(name="mddate", dataType="Long", paramType="form", required=true, value="")
  })
  @PostMapping(value = "/create")
  public ApiResult addMessageTemplate(
    @RequestParam(value="app_id", required=true) Long appId,
    @RequestParam(value="code", required=true) String code,
    @RequestParam(value="sent_channel", required=true) String sentChannel,
    @RequestParam(value="template_code", required=false) String templateCode,
    @RequestParam(value="content", required=true) String content,
    @RequestParam(value="is_delete", required=true) Boolean isDelete,
    @RequestParam(value="ctdate", required=true) Long ctdate,
    @RequestParam(value="mddate", required=true) Long mddate);

  @ApiOperation(value = "修改消息模板",notes = "修改消息模板")
  @ApiImplicitParams({
    @ApiImplicitParam(name="id", dataType="Long", paramType="form", required=true, value=""),
    @ApiImplicitParam(name="app_id", dataType="Long", paramType="form", required=false, value="app Id"),
    @ApiImplicitParam(name="code", dataType="String", paramType="form", required=false, value="模板编号"),
    @ApiImplicitParam(name="sent_channel", dataType="String", paramType="form", required=false, value="发送渠道"),
    @ApiImplicitParam(name="template_code", dataType="String", paramType="form", required=false, value="第三方平台模板编号"),
    @ApiImplicitParam(name="content", dataType="String", paramType="form", required=false, value="消息内容"),
    @ApiImplicitParam(name="is_delete", dataType="Boolean", paramType="form", required=false, value=""),
    @ApiImplicitParam(name="ctdate", dataType="Long", paramType="form", required=false, value=""),
    @ApiImplicitParam(name="mddate", dataType="Long", paramType="form", required=false, value="")
  })
  @PostMapping(value = "/update")
  public ApiResult editMessageTemplate(
    @RequestParam(value="id", required=true) Long id,
    @RequestParam(value="app_id", required=false) Long appId,
    @RequestParam(value="code", required=false) String code,
    @RequestParam(value="sent_channel", required=false) String sentChannel,
    @RequestParam(value="template_code", required=false) String templateCode,
    @RequestParam(value="content", required=false) String content,
    @RequestParam(value="is_delete", required=false) Boolean isDelete,
    @RequestParam(value="ctdate", required=false) Long ctdate,
    @RequestParam(value="mddate", required=false) Long mddate);

  @ApiOperation(value = "根据条件查询消息模板",notes = "根据条件查询消息模板")
  @ApiImplicitParams({
    @ApiImplicitParam(name="app_id", dataType="Long", paramType="form", required=false, value="app Id"),
    @ApiImplicitParam(name="code", dataType="String", paramType="form", required=false, value="模板编号"),
    @ApiImplicitParam(name="sent_channel", dataType="String", paramType="form", required=false, value="发送渠道"),
    @ApiImplicitParam(name="template_code", dataType="String", paramType="form", required=false, value="第三方平台模板编号"),
    @ApiImplicitParam(name="content", dataType="String", paramType="form", required=false, value="消息内容"),
    @ApiImplicitParam(name="is_delete", dataType="Boolean", paramType="form", required=false, value=""),
    @ApiImplicitParam(name="ctdate", dataType="Long", paramType="form", required=false, value=""),
    @ApiImplicitParam(name="mddate", dataType="Long", paramType="form", required=false, value=""),
    @ApiImplicitParam(name="page", dataType="Integer", paramType="form", required=false, value="分页页码，默认1"),
    @ApiImplicitParam(name="limit", dataType="Integer", paramType="form", required=false, value="分页每页条数，默认20")
  })
  @PostMapping(value = "/query")
  public ApiResult getMessageTemplateList(
    @RequestParam(value="app_id", required=false) Long appId,
    @RequestParam(value="code", required=false) String code,
    @RequestParam(value="sent_channel", required=false) String sentChannel,
    @RequestParam(value="template_code", required=false) String templateCode,
    @RequestParam(value="content", required=false) String content,
    @RequestParam(value="is_delete", required=false) Boolean isDelete,
    @RequestParam(value="ctdate", required=false) Long ctdate,
    @RequestParam(value="mddate", required=false) Long mddate,
    @RequestParam(value="page", required=false, defaultValue = "1") Integer page,
    @RequestParam(value="limit", required=false, defaultValue = "20") Integer limit);

  @ApiOperation(value = "根据ID查询消息模板",notes = "根据ID查询消息模板")
  @ApiImplicitParams({
    @ApiImplicitParam(name="id", dataType="Long", paramType="form", required=true, value="ID")
  })
  @PostMapping(value = "/queryById")
  public ApiResult getMessageTemplate(@RequestParam(value="id", required=true) Long id);

}
