package com.saeha.webdev.innovativeweb.web.config.servlet;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
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
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {
	
	/**
	 * Text, Binary Buffer Size
	 */
	private final static int MAX_BUFFER_SIZE = 65535;
	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry){
		registry.addEndpoint("/ws/default")
			.addInterceptors(new HttpSessionHandshakeInterceptor());
	}
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableSimpleBroker("/topic");
		registry.setApplicationDestinationPrefixes("/app");
		registry.setUserDestinationPrefix("/user");
	}
	
	@Bean
	public ServletServerContainerFactoryBean servletServerContainer(){
		ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
		container.setMaxTextMessageBufferSize(MAX_BUFFER_SIZE);
		container.setMaxBinaryMessageBufferSize(MAX_BUFFER_SIZE);
		return container;
	}
}
