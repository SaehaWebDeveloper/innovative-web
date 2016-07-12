package com.saeha.webdev.innovativeweb.common.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice(basePackages="${config.spring.component.basePackages}")
public class ExceptionHandlerController {

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ModelAndView handlerForException(Exception e, HttpServletRequest request) {
		log.error("Server Error.", e);
		
		ModelAndView mv = new ModelAndView("error/500");
		mv.addObject("uri", request.getRequestURI());
		mv.addObject("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
		mv.addObject("exceptionMessage", e.getMessage());
		return mv;
	}
	
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public @ResponseBody ModelAndView handlerForUnauthorized(Exception e, HttpServletRequest request){
		ModelAndView mv = new ModelAndView("error/401");
		mv.addObject("uri", request.getRequestURI());
		mv.addObject("statusCode", HttpStatus.UNAUTHORIZED.value());
		mv.addObject("exceptionMessage", e.getMessage());
		return mv;
	}
	
	@ExceptionHandler({NoHandlerFoundException.class, NoHandlerFoundException.class})
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public @ResponseBody ModelAndView handlerForNotFound(Exception e, HttpServletRequest request){
		ModelAndView mv = new ModelAndView("error/404");
		mv.addObject("uri", request.getRequestURI());
		mv.addObject("statusCode", HttpStatus.NOT_FOUND.value());
		mv.addObject("exceptionMessage", e.getMessage());
		return mv;
	}
}
