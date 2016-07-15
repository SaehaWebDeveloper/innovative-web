package com.saeha.webdev.innovativeweb.rest;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.saeha.webdev.innovativeweb.common.constants.SessionConstants;
import com.saeha.webdev.innovativeweb.model.user.UserInfo;
import com.saeha.webdev.innovativeweb.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/rest/user")
public class UserRestController {
	
	@Autowired private UserService userService;
	
	@Autowired private MessageSource messageSource;
	
	@RequestMapping(value="/sso/{userid}", produces={MediaType.TEXT_HTML_VALUE})
	public @ResponseBody ModelAndView ssoProcess(HttpSession session
			, @PathVariable String userid
			, @RequestParam(required=false, defaultValue="NORMAL") String mode) throws Exception {
		ModelAndView mv = new ModelAndView("message/message");
		
		// FIXME 로그인 실패 처리
		UserInfo userInfo = new UserInfo();
		userInfo.setUserRealId(userid);
		
		UserInfo userSession = userService.checkUser(userInfo);
		if(userSession == null){
			log.info("Not Found User. userInfo:{}", userInfo);
			mv.addObject("message", messageSource.getMessage("login.fail.message", null, LocaleContextHolder.getLocale()));
			return mv;
		}
		
		session.setAttribute(SessionConstants.SESSION_USER, userSession);
		mv.setViewName("redirect:/main");
		return mv;
	}
}
