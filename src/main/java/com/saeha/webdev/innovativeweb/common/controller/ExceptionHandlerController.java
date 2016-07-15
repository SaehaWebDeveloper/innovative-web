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

/**
 * Controller Exception Handler
 * 
 * @author Pure
 *
 */
@Slf4j
@ControllerAdvice(basePackages="${config.spring.component.basePackages}")
public class ExceptionHandlerController {

	/**
	 * 500
	 * @param e
	 * @param request
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ModelAndView handlerForException(Exception e, HttpServletRequest request) {
		log.error("Server Error.", e);
		return makeResult(HttpStatus.INTERNAL_SERVER_ERROR, e, request);
	}
	
	/**
	 * 401
	 * @param e
	 * @param request
	 * @return
	 */
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public @ResponseBody ModelAndView handlerForUnauthorized(Exception e, HttpServletRequest request){
		return makeResult(HttpStatus.UNAUTHORIZED, e, request);
	}
	
	/**
	 * 404
	 * @param e
	 * @param request
	 * @return
	 */
	@ExceptionHandler({NoHandlerFoundException.class, NoHandlerFoundException.class})
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public @ResponseBody ModelAndView handlerForNotFound(Exception e, HttpServletRequest request){
		return makeResult(HttpStatus.NOT_FOUND, e, request);
	}
	
	/**
	 * @param httpStatus
	 * @param e
	 * @param request
	 * @return
	 */
	private ModelAndView makeResult(HttpStatus httpStatus, Exception e, HttpServletRequest request){
		ModelAndView mv = new ModelAndView("error/"+httpStatus.value());
		mv.addObject("uri", request.getRequestURI());
		mv.addObject("statusCode", httpStatus.value());
		mv.addObject("HttpStatus", httpStatus.getReasonPhrase());
		mv.addObject("exceptionMessage", e.getMessage());
		return mv;
	}
}
