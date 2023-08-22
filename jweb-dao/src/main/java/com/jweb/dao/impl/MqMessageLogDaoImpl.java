package com.jweb.dao.impl;

import org.springframework.stereotype.Service;
import com.jweb.dao.base.BaseDaoSupport;
import com.jweb.dao.MqMessageLogDao;
import com.jweb.dao.entity.MqMessageLog;

/**
 *
 * @author 邓超
 *
 * 2023/08/22 15:26
*/
@Service
public class MqMessageLogDaoImpl extends BaseDaoSupport<MqMessageLog> implements MqMessageLogDao {

    @Override
    public Class<MqMessageLog> getClazz() {
        return MqMessageLog.class;
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
