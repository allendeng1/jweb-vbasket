package com.jweb.dao.impl;

import org.springframework.stereotype.Service;
import com.jweb.dao.base.BaseDaoSupport;
import com.jweb.dao.SysDictionaryDao;
import com.jweb.dao.entity.SysDictionary;

/**
 *
 * @author 邓超
 *
 * 2023/08/22 11:27
*/
@Service
public class SysDictionaryDaoImpl extends BaseDaoSupport<SysDictionary> implements SysDictionaryDao {

    @Override
    public Class<SysDictionary> getClazz() {
        return SysDictionary.class;
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
