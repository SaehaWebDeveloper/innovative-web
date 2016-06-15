package com.saeha.webdev.innovativeweb.common.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerController {

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ModelAndView handlerForException(Exception e, HttpServletRequest request) {
		log.error("Server Error.", e);
		
		ModelAndView mv = new ModelAndView("error/500");
		mv.addObject("exceptionMessage", e.getMessage());
		return mv;
	}
}
