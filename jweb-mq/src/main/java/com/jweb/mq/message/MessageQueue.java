package com.jweb.mq.message;

import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
public enum MessageQueue {

	VIP_QUEUE(QueueType.QUEUE, QueueLabel.VIP, QueueName.VIP_QUEUE),
	ORDER_QUEUE(QueueType.QUEUE, QueueLabel.ORDER, QueueName.ORDER_QUEUE),
	INDEX_DOC_QUEUE(QueueType.QUEUE, QueueLabel.INDEX_DOC, QueueName.INDEX_DOC_QUEUE),
	QUEUE_1(QueueType.QUEUE, QueueLabel.GENERAL, QueueName.QUEUE_1),
	QUEUE_2(QueueType.QUEUE, QueueLabel.GENERAL, QueueName.QUEUE_2),
	QUEUE_3(QueueType.QUEUE, QueueLabel.GENERAL, QueueName.QUEUE_3),
	QUEUE_4(QueueType.QUEUE, QueueLabel.GENERAL, QueueName.QUEUE_4),
	QUEUE_5(QueueType.QUEUE, QueueLabel.GENERAL, QueueName.QUEUE_5),
	QUEUE_6(QueueType.QUEUE, QueueLabel.GENERAL, QueueName.QUEUE_6),
	QUEUE_7(QueueType.QUEUE, QueueLabel.GENERAL, QueueName.QUEUE_7),
	QUEUE_8(QueueType.QUEUE, QueueLabel.GENERAL, QueueName.QUEUE_8),
	QUEUE_9(QueueType.QUEUE, QueueLabel.GENERAL, QueueName.QUEUE_9),
	QUEUE_10(QueueType.QUEUE, QueueLabel.GENERAL, QueueName.QUEUE_10);
	
	private String type;
	private String label;
	private String name;
	MessageQueue(String type, String label, String name){
		this.type = type;
		this.label = label;
		this.name = name;
	}
	
	public static List<MessageQueue> generalQueueList(){
		List<MessageQueue> queues = new ArrayList<MessageQueue>();
		for (MessageQueue queue : MessageQueue.values()) {
			if(!queue.getLabel().equalsIgnoreCase(QueueLabel.GENERAL)){
				continue;
			}
            queues.add(queue);
        }
		return queues;
	}
	
	public boolean isVipQueue(){
		return this.getLabel().equalsIgnoreCase(QueueLabel.VIP);
	}
	public boolean isOrderQueue(){
		return this.getLabel().equalsIgnoreCase(QueueLabel.ORDER);
	}
	public boolean isIndexDocQueue(){
		return this.getLabel().equalsIgnoreCase(QueueLabel.INDEX_DOC);
	}
	public String getType() {
		return type;
	}
	public String getName() {
		return name;
	}

	public String getLabel() {
		return label;
	}
	
	
}
