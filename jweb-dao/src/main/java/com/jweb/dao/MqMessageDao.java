package com.jweb.dao;

import java.util.List;

import com.jweb.dao.base.BaseDao;
import com.jweb.dao.dto.MqQueueCountDto;
import com.jweb.dao.entity.MqMessage;

/**
 *
 * @author 邓超
 *
 * 2023/08/22 15:26
*/
public interface MqMessageDao extends BaseDao<MqMessage> {

	List<MqQueueCountDto> getMqQueueCount();
}
