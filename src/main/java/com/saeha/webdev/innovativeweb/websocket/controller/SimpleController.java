package com.saeha.webdev.innovativeweb.websocket.controller;

import com.saeha.webdev.innovativeweb.websocket.annotation.WebSocketController;
import com.saeha.webdev.innovativeweb.websocket.annotation.WebSocketMessageMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebSocketController
@WebSocketMessageMapping("simple")
public class SimpleController {
	
	@WebSocketMessageMapping("send")
	public void send(){
		
	}
}
