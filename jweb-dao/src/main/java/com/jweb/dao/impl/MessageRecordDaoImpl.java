package com.jweb.dao.impl;

import org.springframework.stereotype.Service;
import com.jweb.dao.base.BaseDaoSupport;
import com.jweb.dao.MessageRecordDao;
import com.jweb.dao.entity.MessageRecord;

/**
 *
 * @author 邓超
 *
 * 2023/08/19 17:37
*/
@Service
public class MessageRecordDaoImpl extends BaseDaoSupport<MessageRecord> implements MessageRecordDao {

    @Override
    public Class<MessageRecord> getClazz() {
        return MessageRecord.class;
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
