package com.jweb.adminweb.generatecode;

import com.jweb.common.api.ApiResult;
import com.jweb.dao.base.PageResult;
import com.jweb.common.api.BaseController;
import com.jweb.common.exception.MyException;
import com.jweb.dao.entity.MessageTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.jweb.adminweb.generatecode.vo.MessageTemplateResult;
import com.jweb.adminweb.generatecode.MessageTemplateApiDoc;
import com.jweb.service.MessageTemplateService;
/**
 *
 * @author 邓超
 *
 * 2023/08/22 18:02
*/
@RestController
public class MessageTemplateController extends BaseController implements MessageTemplateApiDoc {

  @Autowired
  private MessageTemplateService messageTemplateService;

  @Override
  public ApiResult addMessageTemplate(
    String code,
    String sentChannel,
    String templateCode,
    String content,
    Boolean isDelete,
    Long ctdate,
    Long mddate) {
    ApiResult result = new ApiResult();
    try {
      MessageTemplate entity = new MessageTemplate();
      entity.setCode(code);
      entity.setSentChannel(sentChannel);
      entity.setTemplateCode(templateCode);
      entity.setContent(content);
      entity.setIsDelete(isDelete);
      entity.setCtdate(ctdate);
      entity.setMddate(mddate);
      messageTemplateService.saveEntity(entity);
    } catch (MyException e) {
      result.bizFail(e);
    }
    return result;
  }

  @Override
  public ApiResult editMessageTemplate(
    Long id,
    String code,
    String sentChannel,
    String templateCode,
    String content,
    Boolean isDelete,
    Long ctdate,
    Long mddate) {
    ApiResult result = new ApiResult();
    try {
      MessageTemplate entity = new MessageTemplate();
      entity.setId(id);
      entity.setCode(code);
      entity.setSentChannel(sentChannel);
      entity.setTemplateCode(templateCode);
      entity.setContent(content);
      entity.setIsDelete(isDelete);
      entity.setCtdate(ctdate);
      entity.setMddate(mddate);
      messageTemplateService.saveEntity(entity);
    } catch (MyException e) {
      result.bizFail(e);
    }
    return result;
  }

  @Override
  public MessageTemplateResult getMessageTemplateList(
    String code,
    String sentChannel,
    String templateCode,
    String content,
    Boolean isDelete,
    Long ctdate,
    Long mddate,
    Integer page,
    Integer limit) {
    MessageTemplateResult result = new MessageTemplateResult();
    try {
      MessageTemplate entity = new MessageTemplate();
      entity.setCode(code);
      entity.setSentChannel(sentChannel);
      entity.setTemplateCode(templateCode);
      entity.setContent(content);
      entity.setIsDelete(isDelete);
      entity.setCtdate(ctdate);
      entity.setMddate(mddate);
      entity.page(page, limit);
      PageResult<MessageTemplate> pageData = messageTemplateService.getPageListByCondition(entity);
      result.setData(pageData);
    } catch (MyException e) {
      result.bizFail(e);
    }
    return result;
  }

  @Override
  public ApiResult getMessageTemplate(Long id) {

    ApiResult result = new ApiResult();
    try {
      MessageTemplate data = messageTemplateService.getById(id);
      result.setData(data);
    } catch (MyException e) {
      result.bizFail(e);
    }
    return result;
  }
}
