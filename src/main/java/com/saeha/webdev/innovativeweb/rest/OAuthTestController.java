package com.saeha.webdev.innovativeweb.rest;

import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value="/rest")
public class OAuthTestController {
	@RequestMapping(value="/user")
	public @ResponseBody ModelAndView userList(){
		ModelAndView mv = new ModelAndView();
		mv.addObject("user", "webdev");
		mv.addObject("date", new Date());
		return mv;
	}
}
