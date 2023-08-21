package com.jweb.common.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.jweb.common.annotation.param.ParamRule;
import com.jweb.common.annotation.param.ParameterCheck;
import com.jweb.common.util.HttpUtil.HttpResult;

/**
 * 调用远程服务
 * @author 邓超
 *
 * 2022/09/27 上午10:07:20
 */
@Configuration
@PropertySource(value = {"classpath:common-${spring.profiles.active}.properties"})
@ConfigurationProperties(prefix = "rpc")
public class RpcUtil extends DataUtil{
	
	private String jobdomain;
	
	private String mqdomain;
	
	/**
	 * 创建定时任务
	 * @param taskType 任务类型，详见：{@link com.dengchao.job.handler.executor.temporary.TemporaryJobType.java}
	 * @param timingTime 定时时间，格式：yyyy-MM-dd HH:mm:ss
	 * @param content 任务内容
	 * @return
	 */
	@ParameterCheck({
		@ParamRule(name="taskType,timingTime,content", rule=ParamRule.Rule.NOT_NULL_AND_EMPTY)
	})
	public HttpResult callCreateTemporaryTask(String taskType, String timingTime, String content) {
		HttpResult httpResult = HttpUtil.builder().url(spliceUrl(jobdomain, "/job/addTemporaryTask"))
				.addParam("type", taskType)
				.addParam("timingTime", timingTime)
				.addParam("content", content)
				.post(false).sync();
		return httpResult;
	}
	/**
	 * 发送 MQ VIP队列 消息
	 * @param queryType 消息类型 详见：{@link com.dengchao.mq.message.MessageType.java}
	 * @param content 消息内容
	 * @return
	 */
	public HttpResult callSentMqVipQueryMsg(String queryType, String content) {
		return sentMqQueryMsg(queryType, "VIP", content);
	}
	/**
	 * 发送 MQ INDEX_DOC队列 消息
	 * @param queryType 消息类型 详见：{@link com.dengchao.mq.message.MessageType.java}
	 * @param content 消息内容
	 * @return
	 */
	public HttpResult callSentMqIndexDocQueryMsg(String queryType, String content) {
		return sentMqQueryMsg(queryType, "INDEX_DOC", content);
	}
	/**
	 * 发送 MQ 顺序队列 消息
	 * @param queryType 消息类型 详见：{@link com.dengchao.mq.message.MessageType.java}
	 * @param content 消息内容
	 * @return
	 */
	public HttpResult callSentMqOrderQueryMsg(String queryType, String content) {
		return sentMqQueryMsg(queryType, "ORDER", content);
	}
	/**
	 * 发送 MQ 普通队列 消息
	 * @param queryType 消息类型 详见：{@link com.dengchao.mq.message.MessageType.java}
	 * @param content 消息内容
	 * @return
	 */
	public HttpResult callSentMqGeneralQueryMsg(String queryType, String content) {
		return sentMqQueryMsg(queryType, "GENERAL", content);
	}
	
	@ParameterCheck({
		@ParamRule(name="queryType,label,content", rule=ParamRule.Rule.NOT_NULL_AND_EMPTY)
	})
	private HttpResult sentMqQueryMsg(String queryType, String label, String content) {
		HttpResult httpResult = HttpUtil.builder().url(spliceUrl(mqdomain, "/mq/publish/queue/message"))
				.addParam("bizType", queryType)
				.addParam("label", label)
				.addParam("content", content)
				.post(false).sync();
		return httpResult;
	}
	/**
	 * 发送 MQ topic消息
	 * @param topic 主题类型 详见：{@link com.dengchao.mq.message.MessageTopic.java}
	 * @param content 消息内容
	 * @return
	 */
	@ParameterCheck({
		@ParamRule(name="topic,content", rule=ParamRule.Rule.NOT_NULL_AND_EMPTY)
	})
	public HttpResult callSentMqTopicMsg(String topic, String content) {
		HttpResult httpResult = HttpUtil.builder().url(spliceUrl(mqdomain, "/publish/topic/message"))
				.addParam("topic", topic)
				.addParam("content", content)
				.post(false).sync();
		return httpResult;
	}

	public void setJobdomain(String jobdomain) {
		this.jobdomain = jobdomain;
	}

	public void setMqdomain(String mqdomain) {
		this.mqdomain = mqdomain;
	}
	
}
