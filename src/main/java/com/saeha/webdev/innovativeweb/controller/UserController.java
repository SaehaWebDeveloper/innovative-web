package com.saeha.webdev.innovativeweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.saeha.webdev.innovativeweb.model.user.UserInfo;
import com.saeha.webdev.innovativeweb.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value="/user")
public class UserController {
	@Autowired private UserService userService;
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public ModelAndView save(UserInfo userInfo){
		ModelAndView mv = new ModelAndView(new RedirectView("/user/list"));
		
		
		userService.save(userInfo);
		
		
		return mv;
	}
}
