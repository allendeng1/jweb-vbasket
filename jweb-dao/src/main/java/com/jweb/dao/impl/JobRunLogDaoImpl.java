package com.jweb.dao.impl;

import org.springframework.stereotype.Service;
import com.jweb.dao.base.BaseDaoSupport;
import com.jweb.dao.JobRunLogDao;
import com.jweb.dao.entity.JobRunLog;

/**
 *
 * @author 邓超
 *
 * 2023/08/22 14:04
*/
@Service
public class JobRunLogDaoImpl extends BaseDaoSupport<JobRunLog> implements JobRunLogDao {

    @Override
    public Class<JobRunLog> getClazz() {
        return JobRunLog.class;
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
