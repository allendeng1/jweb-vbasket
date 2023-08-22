package com.jweb.dao.impl;

import org.springframework.stereotype.Service;
import com.jweb.dao.base.BaseDaoSupport;
import com.jweb.dao.ApiLogDao;
import com.jweb.dao.entity.ApiLog;

/**
 *
 * @author 邓超
 *
 * 2023/08/22 11:27
*/
@Service
public class ApiLogDaoImpl extends BaseDaoSupport<ApiLog> implements ApiLogDao {

    @Override
    public Class<ApiLog> getClazz() {
        return ApiLog.class;
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
