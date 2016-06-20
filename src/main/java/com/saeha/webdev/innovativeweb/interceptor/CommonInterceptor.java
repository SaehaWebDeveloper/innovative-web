package com.saeha.webdev.innovativeweb.interceptor;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonInterceptor extends HandlerInterceptorAdapter{
	private final String ST = "_____ST";
	
	private boolean isDefaultHandler(Object handler) {
		return handler.getClass() == DefaultServletHttpRequestHandler.class;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if(isDefaultHandler(handler)){
			return true;
		}
		
		if(request.getAttribute(ST) == null){
			request.setAttribute(ST, System.currentTimeMillis());
			
			if(log.isDebugEnabled()){
				log.info("[START-{}({})]", request.getRequestURI(), request.getMethod());
			}
		}
		printRequestInfo(request);
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		if (!isDefaultHandler(handler)) {
			if(log.isDebugEnabled()){
				log.debug("[ING-{}({})]({}ms) modelAndView:{}", request.getRequestURI(), request.getMethod(), System.currentTimeMillis()-(Long)request.getAttribute(ST), modelAndView);
			}
		}
	}
	
	@Override
	public void afterCompletion(HttpServletRequest req, HttpServletResponse res, Object handler, Exception ex) throws Exception {
		if(ex != null){
			log.error("error", ex);
		}
		
		if(!isDefaultHandler(handler)){
			if(log.isDebugEnabled()){
				log.info("[END-{}({})]({}ms)", req.getRequestURI(), req.getMethod(), System.currentTimeMillis()-(Long)req.getAttribute(ST));
			}
		}
	}
	
	private void printRequestInfo(HttpServletRequest request) {
		Map<String, Object> requestInfoMap = new LinkedHashMap<>();
		
		requestInfoMap.put("HTTP REQUEST INFORMATION", "--");
		requestInfoMap.put("URI", request.getRequestURI());
		requestInfoMap.put("QueryString", request.getQueryString());
		
		Map<String, String[]> parameterMap = request.getParameterMap();
		Map<String, Object> resultParameterMap = new LinkedHashMap<>();
		for(Map.Entry<String, String[]> em : parameterMap.entrySet()){
			String key = em.getKey();
			String paramValue = request.getParameter(key);
			String[] paramValues = request.getParameterValues(em.getKey());
			
			//if(key.indexOf("data") > -1) continue; 
			
			if(paramValues != null && paramValues.length > 1) {
				resultParameterMap.put(key, Arrays.asList(paramValues));
			}
			else { 
				resultParameterMap.put(key, paramValue);
			}
		}
		requestInfoMap.put("Parameter", resultParameterMap);
		parameterMap = null;
		resultParameterMap = null;
		
		requestInfoMap.put("HEADER INFORMATION", "--");
		Enumeration<String> headerNames = request.getHeaderNames();
		while(headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			requestInfoMap.put(headerName, request.getHeader(headerName));
		}
		
		log.debug("######## HTTP Request Information: {}", requestInfoMap);
	}//:
}
