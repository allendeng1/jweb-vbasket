package com.jweb.dao.constant;

/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
public class DatabaseConstant {
	
	public static enum AdminUserStatus{
		
		/***可用*/
		AVAILABLE(0),
		/***禁用*/
		DISABLED(1);
		
		AdminUserStatus(int value){
			this.value = value;
		}
		private int value;
		public int getValue() {
			return value;
		}
	}
	public static enum JobType{
		
		/***固定*/
		FIXED(1),
		/***临时*/
		TEMPORARY(2);

		JobType(int value){
			this.value = value;
		}
		private int value;
		public int getValue() {
			return value;
		}
		
		public static JobType get(int value) {
			for (JobType type : JobType.values()) {
				if(type.getValue() == value){
					return type;
				}
			}
			return null;
		}
	}
	public static enum JobStatus{
		
		/***使用中*/
		USEING(0),
		/***已废弃*/
		DISCARD(1);

		JobStatus(int value){
			this.value = value;
		}
		private int value;
		public int getValue() {
			return value;
		}
	}
	public static enum JobRunStatus{
		
		/***未运行*/
		UN_RUN(0),
		/***运行中*/
		RUNNING(1),
		/***已执行*/
		EXCUTED(2);
		
		JobRunStatus(int value){
			this.value = value;
		}
		private int value;
		public int getValue() {
			return value;
		}
	}
	public static enum JobRunLogStatus{
		
		/***运行中*/
		RUNNING(0),
		/***运行结束*/
		FINISH(1);
		
		JobRunLogStatus(int value){
			this.value = value;
		}
		private int value;
		public int getValue() {
			return value;
		}
	}
	public static enum JobRunResult{
		
		/***成功*/
		SUCCESS(1),
		/***失败*/
		FAIL(2);
		
		JobRunResult(int value){
			this.value = value;
		}
		private int value;
		public int getValue() {
			return value;
		}
	}
	public static enum JobUpdateType{
		//修改类型：0-创建任务，1-修改执行时间，2-修改使用状态，3-修改运行状态，4-修改执行器，5-修改其他信息
		/***创建任务*/
		CREATE(0),
		/***修改执行时间*/
		UPDATE_EXECUTE_TIME(1),
		/***修改使用状态*/
		UPDATE_USE_STATUS(2),
		/***修改运行状态*/
		UPDATE_RUN_STATUS(3),
		/***修改执行器*/
		UPDATE_EXECUTE_HANDLER(4),
		/***修改其他信息*/
		UPDATE_OTHER_INFO(5);
		
		JobUpdateType(int value){
			this.value = value;
		}
		private int value;
		public int getValue() {
			return value;
		}
	}
	
	public static enum MenuOperaType{
		
		/***菜单*/
		MENU("MENU"),
		/***操作*/
		OPERA("OPERA");
		
		MenuOperaType(String value){
			this.value = value;
		}
		private String value;
		public String getValue() {
			return value;
		}
	}
	
	public static enum MqMsgChannel{
		
		/***active mq*/
		ACTIVEMQ("ACTIVEMQ"),
		/***rabbit mq*/
		RABBITMQ("RABBITMQ"),
		/***rocket mq*/
		ROCKETMQ("ROCKETMQ"),
		/***kafka*/
		KAFKA("KAFKA");
		
		MqMsgChannel(String value){
			this.value = value;
		}
		private String value;
		public String getValue() {
			return value;
		}
	}
	public static enum MqMsgStatus{
		
		/***未发布*/
		UN_PUBLISH(0),
		/***已发布*/
		PUBLISHED(1),
		/***消费成功*/
		CONSUMED_SUCCESS(2),
		/***消费失败*/
		CONSUMED_FAIL(3);
		
		MqMsgStatus(int value){
			this.value = value;
		}
		private int value;
		public int getValue() {
			return value;
		}
	}
	
	public static enum MqMsgType{
		
		/***创建*/
		CREATED("CREATED"),
		/***发布*/
		PUBLISHED("PUBLISHED"),
		/***消费*/
		CONSUMED("CONSUMED"),
		/***跳过*/
		SKIPED("SKIPED");
		
		MqMsgType(String value){
			this.value = value;
		}
		private String value;
		public String getValue() {
			return value;
		}
	}
	public static enum MqMsgExcuteMethod{
		
		/***系统自动*/
		AUTO("AUTO"),
		/***手动*/
		MANUAL("MANUAL");
		
		MqMsgExcuteMethod(String value){
			this.value = value;
		}
		private String value;
		public String getValue() {
			return value;
		}
	}
	
	public static enum MessageSentWay{
		
		/***实时发送*/
		REAL_TIME(1),
		/***定时发送*/
		TIMING(2);
		
		MessageSentWay(int value){
			this.value = value;
		}
		private int value;
		public int getValue() {
			return value;
		}
	}
	public static enum MessageSentStatus{
		
		/***待发送*/
		WAIT_SENT(0),
		/***发送中*/
		SENTTING(1),
		/***发送成功*/
		SENT_SUCCESS(2),
		/***发送失败*/
		SENT_FAIL(3);
		
		MessageSentStatus(int value){
			this.value = value;
		}
		private int value;
		public int getValue() {
			return value;
		}
	}
}
