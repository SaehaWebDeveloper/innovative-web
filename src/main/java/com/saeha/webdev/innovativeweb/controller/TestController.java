package com.saeha.webdev.innovativeweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/test")
public class TestController {
	
	@RequestMapping(value="/home")
	public String homePage(){
		return "test/home";
	}
}
