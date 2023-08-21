package com.jweb.dao.impl;

import org.springframework.stereotype.Service;
import com.jweb.dao.base.BaseDaoSupport;
import com.jweb.dao.SysConfigDao;
import com.jweb.dao.entity.SysConfig;

/**
 *
 * @author 邓超
 *
 * 2023/08/19 17:37
*/
@Service
public class SysConfigDaoImpl extends BaseDaoSupport<SysConfig> implements SysConfigDao {

    @Override
    public Class<SysConfig> getClazz() {
        return SysConfig.class;
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
