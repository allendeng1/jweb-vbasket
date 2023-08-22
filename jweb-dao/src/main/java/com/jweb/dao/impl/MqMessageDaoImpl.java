package com.jweb.dao.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import com.jweb.dao.base.BaseDaoSupport;
import com.jweb.dao.dto.MqQueueCountDto;
import com.jweb.dao.MqMessageDao;
import com.jweb.dao.entity.MqMessage;

/**
 *
 * @author 邓超
 *
 * 2023/08/22 15:26
*/
@Service
public class MqMessageDaoImpl extends BaseDaoSupport<MqMessage> implements MqMessageDao {

    @Override
    public Class<MqMessage> getClazz() {
        return MqMessage.class;
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

	@SuppressWarnings("unchecked")
	@Override
	public List<MqQueueCountDto> getMqQueueCount() {
		return (List<MqQueueCountDto>) this.findList("MqMessage.getMqQueueCount", null);
	}

}
