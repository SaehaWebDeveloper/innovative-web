package com.saeha.webdev.innovativeweb.websocket.controller;

import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class StompController {
	@MessageMapping("/send")
	public void send(Message<Object> message, String data){
		log.debug("message:{}, data:{}", message, data);
	}
}
