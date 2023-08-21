package com.jweb.dao.impl;

import org.springframework.stereotype.Service;
import com.jweb.dao.base.BaseDaoSupport;
import com.jweb.dao.MessageTemplateDao;
import com.jweb.dao.entity.MessageTemplate;

/**
 *
 * @author 邓超
 *
 * 2023/08/19 17:37
*/
@Service
public class MessageTemplateDaoImpl extends BaseDaoSupport<MessageTemplate> implements MessageTemplateDao {

    @Override
    public Class<MessageTemplate> getClazz() {
        return MessageTemplate.class;
    }

    @Override
    public boolean isCacheable() {
        return true;
    }

    @Override
    public Long cacheStorageTimeSecond() {
        return null;
    }

    @Override
    public boolean isCreateIndexDoc() {
        return false;
    }

}
