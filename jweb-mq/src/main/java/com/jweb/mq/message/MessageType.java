package com.jweb.mq.message;

import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
public enum MessageType {
	INDEX_DOC_SYNC(TypeName.INDEX_DOC_SYNC, ""),
	SENT_SMS(TypeName.SENTSMS, ""),
	TOPIC_1(TypeName.TOPIC_1, TopicName.TOPIC_DEFAULT),
	TOPIC_2(TypeName.TOPIC_2, TopicName.TOPIC_DEFAULT);
	
	private String name;
	private String topicName;

	MessageType(String name, String topicName){
		this.name = name;
		this.topicName = topicName;
	}

	public String getName() {
		return name;
	}

	public String getTopicName() {
		return topicName;
	}
	
	public static List<MessageType> getMessageType(String topicName){
		List<MessageType> list = new ArrayList<MessageType>();
		for (MessageType type : MessageType.values()) {
			if(type.getTopicName().equals(topicName)){
				list.add(type);
			}
		}
		return list;
	}

}
