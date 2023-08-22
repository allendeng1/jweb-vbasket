package com.jweb.dao.impl;

import org.springframework.stereotype.Service;
import com.jweb.dao.base.BaseDaoSupport;
import com.jweb.dao.JobUpdateLogDao;
import com.jweb.dao.entity.JobUpdateLog;

/**
 *
 * @author 邓超
 *
 * 2023/08/22 14:05
*/
@Service
public class JobUpdateLogDaoImpl extends BaseDaoSupport<JobUpdateLog> implements JobUpdateLogDao {

    @Override
    public Class<JobUpdateLog> getClazz() {
        return JobUpdateLog.class;
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
