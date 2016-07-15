package com.saeha.webdev.innovativeweb.rest;

import java.util.Date;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value="/rest")
public class OAuthTestController {
	@RequestMapping(value="/user/{userid}")
	public @ResponseBody ModelAndView userList(@PathVariable String userid){
		ModelAndView mv = new ModelAndView();
		mv.addObject("userid", userid);
		mv.addObject("date", new Date());
		return mv;
	}
	
	@RequestMapping(value="/conference/{groupcode}")
	public @ResponseBody ModelAndView conferenceList(@PathVariable String groupcode){
		ModelAndView mv = new ModelAndView();
		mv.addObject("groupcode", groupcode);
		mv.addObject("date", new Date());
		return mv;
	}
}
