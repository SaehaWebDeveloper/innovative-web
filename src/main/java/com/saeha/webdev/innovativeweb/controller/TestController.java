package com.saeha.webdev.innovativeweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/test")
public class TestController {
	
	@RequestMapping(value="/home")
	public String homePage() throws Exception {
		
		if(true)
			throw new Exception("Asfasfasdfasdfasdf");
		
		return "test/home";
	}
}
