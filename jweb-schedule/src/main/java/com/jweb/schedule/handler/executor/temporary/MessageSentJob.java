package com.jweb.schedule.handler.executor.temporary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jweb.common.exception.MyException;
import com.jweb.dao.entity.JobRule;
import com.jweb.message.MessageService;

import lombok.extern.slf4j.Slf4j;

/**
 * 消息发送
 * @author 邓超
 *
 * 2022/09/09 下午2:20:14
 */
@Slf4j
@Component
public class MessageSentJob extends TemporaryJob{
	
	@Autowired
	private MessageService messageService;

	@Override
	public TemporaryJobType getType() {
		return TemporaryJobType.MESSAGE_SENT;
	}

	@Override
	public void excute(JobRule task) throws MyException {
		String taskContent = task.getTaskContent();
		log.info("MessageSentJob excute：{}", taskContent);
		try {
			messageService.sentTimingMessage(Long.valueOf(taskContent));
		} catch (NumberFormatException e) {
			log.error("MessageSentJob excute error", e);
		} catch (MyException e) {
			log.error("MessageSentJob excute error", e);
		}
	}

}
