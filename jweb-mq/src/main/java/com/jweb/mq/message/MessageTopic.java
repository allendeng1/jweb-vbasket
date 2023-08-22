package com.jweb.mq.message;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
public enum MessageTopic {

	TOPIC_DEFAULT(TopicName.TOPIC_DEFAULT);
	
	private String name;
	
	MessageTopic(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
}
