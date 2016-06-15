package com.saeha.webdev.innovativeweb.websocket.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.saeha.webdev.innovativeweb.websocket.manager.WebSocketSessionManager;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultTextWebSocketHandler extends TextWebSocketHandler{
	@Autowired private WebSocketSessionManager webSocketSessionManager;
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		log.debug("afterConnectionEstablished : {}", session);
		webSocketSessionManager.add(session);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		log.debug("afterConnectionClosed : {}, {}", session, status);
		webSocketSessionManager.remove(session);
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		log.debug("handleTextMessage : {}, {}", session, message);
		//TODO async 처리
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		log.error("handleTransportError : " + session, exception);
	}
}
