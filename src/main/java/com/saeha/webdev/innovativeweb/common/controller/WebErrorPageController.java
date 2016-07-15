package com.saeha.webdev.innovativeweb.common.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

/**
 * WAS에서 발생하는 오류 처리(web.xml)
 * @author Pure
 *
 */
@Slf4j
@Controller
public class WebErrorPageController {
	
	final private static String SUPPORTED_ERROR_PAGE = " 500 404 ";
	
	@RequestMapping(value="/error")
	public @ResponseBody Object handlerForWeb(HttpServletRequest request, HttpServletResponse response){
		String statusCodeStr = String.valueOf(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE));
		int statusCode = Integer.parseInt(statusCodeStr);
		String message = String.valueOf(request.getAttribute(RequestDispatcher.ERROR_MESSAGE));
		String requestUri = String.valueOf(request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI));
		String exceptionType = String.valueOf(request.getAttribute(RequestDispatcher.ERROR_EXCEPTION_TYPE));
		Throwable exception = (Throwable)request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
		String servletName = String.valueOf(request.getAttribute(RequestDispatcher.ERROR_SERVLET_NAME));
		
		log.debug("SERVLET ERROR. [statusCode:{}] [exceptionType:{}] [message:{}] [requestUri:{}] [exception:{}] [servletName:{}]"
				,statusCode, exceptionType, message, requestUri, exception, servletName);
		if(exception != null){
			log.warn("SERVLET ERROR. Exception:", exception);
		}
		
		// TODO status code에 따른 화면 처리
		ModelAndView mv = new ModelAndView(SUPPORTED_ERROR_PAGE.indexOf(statusCode) >= 0 ? "error/"+statusCode : "error/500");
		mv.addObject("uri", requestUri);
		mv.addObject("statusCode", statusCode);
		mv.addObject("HttpStatus", HttpStatus.valueOf(statusCode).getReasonPhrase());
		mv.addObject("exceptionMessage", message + "  " + (exception != null ? exception.getMessage() : ""));
		
		if(requestUri.endsWith(".json") || requestUri.startsWith("/oauth")){
			response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
			return mv.getModelMap();
		}
		return mv;
	}
}
