package com.jweb.dao.impl;

import org.springframework.stereotype.Service;
import com.jweb.dao.base.BaseDaoSupport;
import com.jweb.dao.FileStorageDao;
import com.jweb.dao.entity.FileStorage;

/**
 *
 * @author 邓超
 *
 * 2023/08/19 17:37
*/
@Service
public class FileStorageDaoImpl extends BaseDaoSupport<FileStorage> implements FileStorageDao {

    @Override
    public Class<FileStorage> getClazz() {
        return FileStorage.class;
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
