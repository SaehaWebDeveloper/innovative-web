package com.saeha.webdev.innovativeweb.web.config.servlet;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.session.ExpiringSession;
import org.springframework.session.web.socket.config.annotation.AbstractSessionWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

/**
 * WebSocket + STOMP
 * 
 * @author Pure
 *
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractSessionWebSocketMessageBrokerConfigurer<ExpiringSession> {
	
	/**
	 * Text, Binary Buffer Size, short max value
	 */
	private final static int MAXIMUM_BUFFER_SIZE = 65535;
	
	/* (non-Javadoc)
	 * @see org.springframework.session.web.socket.config.annotation.AbstractSessionWebSocketMessageBrokerConfigurer#configureStompEndpoints(org.springframework.web.socket.config.annotation.StompEndpointRegistry)
	 */
	@Override
	public void configureStompEndpoints(StompEndpointRegistry registry){
		registry
			.addEndpoint("/ws/default") // 접속 URL
			.addInterceptors(new HttpSessionHandshakeInterceptor()); // HTTP Session을 같이 사용
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer#configureMessageBroker(org.springframework.messaging.simp.config.MessageBrokerRegistry)
	 */
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableSimpleBroker("/topic");
		registry.setApplicationDestinationPrefixes("/app");
		registry.setUserDestinationPrefix("/user");
	}
	
	/**
	 * WebSocket Server 설정
	 * @return
	 */
	@Bean
	public ServletServerContainerFactoryBean servletServerContainer(){
		ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
		// 메시지 버퍼크기 지정
		container.setMaxTextMessageBufferSize(MAXIMUM_BUFFER_SIZE);
		container.setMaxBinaryMessageBufferSize(MAXIMUM_BUFFER_SIZE);
		return container;
	}
}
