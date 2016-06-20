package com.saeha.webdev.innovativeweb.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;

import com.saeha.webdev.innovativeweb.model.user.UserInfo;

public class AuthInterceptor extends HandlerInterceptorAdapter{
	
	private boolean isDefaultHandler(Object handler) {
		return handler.getClass() == DefaultServletHttpRequestHandler.class;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if(isDefaultHandler(handler)){
			return true;
		}
		
		HttpSession session = request.getSession();
		UserInfo userSession = (UserInfo)session.getAttribute("SESSION_USER");
		
		if(userSession == null){
			throw new ModelAndViewDefiningException(new ModelAndView("redirect:/login"));
		}
		
		return true;
	}

}
