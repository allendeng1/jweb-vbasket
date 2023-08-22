package com.jweb.dao.impl;

import org.springframework.stereotype.Service;
import com.jweb.dao.base.BaseDaoSupport;
import com.jweb.dao.JobRuleDao;
import com.jweb.dao.entity.JobRule;

/**
 *
 * @author 邓超
 *
 * 2023/08/22 14:04
*/
@Service
public class JobRuleDaoImpl extends BaseDaoSupport<JobRule> implements JobRuleDao {

    @Override
    public Class<JobRule> getClazz() {
        return JobRule.class;
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
