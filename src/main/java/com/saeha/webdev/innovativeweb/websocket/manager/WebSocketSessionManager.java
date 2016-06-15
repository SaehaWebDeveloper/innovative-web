package com.saeha.webdev.innovativeweb.websocket.manager;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
@ManagedResource(objectName="com.pure.test:name=websocket,type=session")
public class WebSocketSessionManager {
	
	private Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();
	
	public void add(WebSocketSession session){
		sessions.add(session);
	}
	
	public void remove(WebSocketSession session){
		sessions.remove(session);
	}
	
	@ManagedAttribute(description="Session Count")
	public int getSessionCount(){
		return sessions.size();
	}
}
