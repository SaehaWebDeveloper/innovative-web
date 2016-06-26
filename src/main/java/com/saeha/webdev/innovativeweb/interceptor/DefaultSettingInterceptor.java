package com.saeha.webdev.innovativeweb.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.saeha.webdev.innovativeweb.common.constants.SessionConstants;
import com.saeha.webdev.innovativeweb.model.skin.SkinInfo;
import com.saeha.webdev.innovativeweb.service.MessageService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultSettingInterceptor extends HandlerInterceptorAdapter {
	@Autowired private MessageService messageService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		log.debug("{} {} {}", request.getRemoteAddr(), request.getServerName(), request.getRequestURI());
		
		// 언어설정
		messageService.settingLocale();
		request.setAttribute("language", LocaleContextHolder.getLocale().getLanguage());
		
		// 접속 회사 정보 확인
		
		// 회사 정보가 있는지 확인
		
		// 접속한 회사와 세션의 회사가 같은지 확인
		
		// 회사 정보 로딩 및 세션에 저장
		// 회사 기본 정보, 세팅 정보, 기타 정보등
		
		HttpSession session = request.getSession();
		session.setAttribute(SessionConstants.SESSION_SKIN_INFO, new SkinInfo());
		
		return true;
	}

}
