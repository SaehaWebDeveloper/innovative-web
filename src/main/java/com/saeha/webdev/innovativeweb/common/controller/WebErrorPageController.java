package com.saeha.webdev.innovativeweb.common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class WebErrorPageController {
	@RequestMapping(value="/error")
	public @ResponseBody Object handlerForWeb(HttpServletRequest request, HttpServletResponse response){
		
		String statusCode = String.valueOf(request.getAttribute("javax.servlet.error.status_code"));
		String exceptionType = String.valueOf(request.getAttribute("javax.servlet.error.exception_type"));
		String message = String.valueOf(request.getAttribute("javax.servlet.error.message"));
		String requestUri = String.valueOf(request.getAttribute("javax.servlet.error.request_uri"));
		Throwable exception = (Throwable)request.getAttribute("javax.servlet.error.exception");
		String servletName = String.valueOf(request.getAttribute("javax.servlet.error.servlet_name"));
		
		log.debug("{} {} {} {} {} {}"
				,statusCode, exceptionType, message, requestUri, exception, servletName);
		
		ModelAndView mv = new ModelAndView("error/404");
		mv.addObject("uri", requestUri);
		mv.addObject("statusCode", statusCode);
		mv.addObject("exceptionMessage", exception != null ? exception.getMessage() : null);
		
		if(requestUri.endsWith(".json")){
			response.setContentType("application/json; charset=UTF-8");
			return mv.getModelMap();
		}
		return mv;
	}
}
