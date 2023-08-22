package com.jweb.mq.message;

import java.util.List;
import java.util.Random;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
@Component
public class MessageQueueManager {

	@Value("${config.queue.balance.model:POLLING}")
	private BalanceModel balance;
	
	private List<MessageQueue> queues = null;
	private int queueSize = 0;
	private int pollingIndex = 0;

	public MessageQueue assignQueue(){
		switch (balance) {
		case RANDOM:
			return randomQueue();
		case POLLING:
			return pollingQueue();
		default:
			break;
		}
		return MessageQueue.QUEUE_1;
	}
	
	private MessageQueue randomQueue(){
		MessageQueue queue = queues.get(new Random().nextInt(queueSize));
		return queue;
	}
	private synchronized MessageQueue pollingQueue(){
		MessageQueue queue = queues.get(pollingIndex);
		if(pollingIndex + 1 == queueSize){
			pollingIndex = 0;
		}else{
			pollingIndex++;
		}
		return queue;
	}
	
	@PostConstruct
	private void init(){
		queues = MessageQueue.generalQueueList();
		queueSize = queues.size();
	}

	enum BalanceModel{
		RANDOM, POLLING;
	}
}
