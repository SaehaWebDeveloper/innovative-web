package com.saeha.webdev.innovativeweb.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.saeha.webdev.innovativeweb.model.user.UserInfo;
import com.saeha.webdev.innovativeweb.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value="/auth")
public class AuthController {
	@Autowired private UserService userService;
	@Autowired private MessageSource messageSource;
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public @ResponseBody ModelAndView loginPage(HttpSession session){
		ModelAndView mv = new ModelAndView("auth/login");
		if(session.getAttribute("SESSION_USER") != null){
			mv.setViewName("redirect:/main");
		}
		return mv;
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public @ResponseBody ModelAndView loginProcess(HttpSession session
			, UserInfo userInfo) throws Exception{
		// FIXME 로그인 실패 처리
		ModelAndView mv = new ModelAndView();
		mv.addObject("userLogin", userInfo);
		
		UserInfo userSession = userService.checkUser(userInfo);
		if(userSession == null){
			mv.addObject("checkResult", messageSource.getMessage("login.fail.message", null, LocaleContextHolder.getLocale()));
			mv.setViewName("auth/login");
			return mv;
		}
		
		session.setAttribute("SESSION_USER", userSession);
		mv.setViewName("redirect:/main");
		return mv;
	}
	
	@RequestMapping(value="logout")
	public String logoutProcess(HttpSession session){
		session.removeAttribute("SESSION_USER");
		return "redirect:/";
	}
}
