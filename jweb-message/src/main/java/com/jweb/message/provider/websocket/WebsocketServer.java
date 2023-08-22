package com.jweb.message.provider.websocket;

import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import com.jweb.dao.constant.DatabaseConstant;
import com.jweb.message.base.MessageResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@ServerEndpoint(value = "/socket/{userId}")
@ConditionalOnExpression("${websocket.enable:false}")
public class WebsocketServer {
	
	private static ConcurrentHashMap<String, ConcurrentHashMap<String, Session>> clients = new ConcurrentHashMap<>();
	
	/**
	 * 群发消息
	 * @param content
	 * @return
	 */
	public static MessageResult sentMessage(String content) {
		MessageResult result = new MessageResult();
		if(clients.isEmpty()) {
			log.info("Websocket 群发消息[{}]失败，原因：不在线", content);
			result.setStatus(DatabaseConstant.MessageSentStatus.SENT_FAIL);
			result.setResultData("不在线");
			return result;
		}
		Iterator<String> it = clients.keySet().iterator();
		while(it.hasNext()) {
			sentMessage(it.next(), content);
		}
		result.setStatus(DatabaseConstant.MessageSentStatus.SENT_SUCCESS);
		result.setResultData("发送成功");
		return result;
	}
	
	/**
	 * 发送给某个用户
	 * @param userId
	 * @param content
	 * @return
	 */
	public static MessageResult sentMessage(String userId, String content) {
		MessageResult result = new MessageResult();
		if(!clients.containsKey(userId)) {
			log.info("Websocket 给客户端[{}]发送消息[{}]失败，原因：不在线", userId, content);
			result.setStatus(DatabaseConstant.MessageSentStatus.SENT_FAIL);
			result.setResultData("不在线");
			return result;
		}
		ConcurrentHashMap<String, Session> map = clients.get(userId);
		Iterator<String> it = map.keySet().iterator();
		boolean isOk = false;
		while(it.hasNext()) {
			try {
				map.get(it.next()).getBasicRemote().sendText(content);
				isOk = true;
			} catch (IOException e) {
				log.error("Websocket 给客户端[{}]发送消息[{}]出错", userId, content, e);
			}
		}
		if(isOk) {
			result.setStatus(DatabaseConstant.MessageSentStatus.SENT_SUCCESS);
			result.setResultData("发送成功");
		}else {
			result.setStatus(DatabaseConstant.MessageSentStatus.SENT_FAIL);
			result.setResultData("发送失败");
		}
	    return result;
	}
	
	@OnOpen
	public void onOpen(@PathParam("userId") String userId, Session session) {
		ConcurrentHashMap<String, Session> map = clients.get(userId);
		if(map == null) {
			map = new ConcurrentHashMap<>();
		}
		map.put(session.getId(), session);
		clients.put(userId, map);
		log.info("Websocket 客户端[{}]连接成功",userId);
	}
	@OnClose
	public void onClose(@PathParam("userId") String userId, Session session) {
		String sessionId = session.getId();
		ConcurrentHashMap<String, Session> map = clients.get(userId);
		if(map == null || map.isEmpty() || !map.containsKey(sessionId)) {
			return;
		}
		map.remove(sessionId);
		clients.put(userId, map);
		log.info("Websocket 客户端[{}]断开连接",userId);
	}
	
	@OnMessage
    public void onMessage(@PathParam("userId") String userId, String message, Session session) {
        log.info("Websocket 客户端[{}]发来消息:{}", userId, message);
    }

    @OnError
    public void onError(@PathParam("userId") String userId, Session session, Throwable error) {
    	String sessionId = session.getId();
		ConcurrentHashMap<String, Session> map = clients.get(userId);
		if(map == null || map.isEmpty() || !map.containsKey(sessionId)) {
			return;
		}
		map.remove(sessionId);
		clients.put(userId, map);
        log.error("Websocket 客户端[{}]发生错误", userId, error);
    }
}
