package com.saeha.webdev.innovativeweb.web.config.root;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

/**
 * 다국어 설정
 * 
 * @author Pure
 *
 */
@Configuration
public class LocaleConfig {
	
	/**
	 * 다국어 메시지 파일 경로
	 */
	@Value("#{'${config.message.basenames:classpath:message/message}'.split(',')}")
	private String[] messageBasenames;
	
	/**
	 * 다국어 설정 방식
	 * 
	 * @return LocaleResolver
	 */
	@Bean
	public LocaleResolver localeResolver(){
		return new SessionLocaleResolver();
	}
	/**
	 * 다국어 메시지
	 * 
	 * @return MessageSource
	 */
	@Bean
	public MessageSource messageSource(){
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames(messageBasenames);
		messageSource.setUseCodeAsDefaultMessage(true);
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(-1);
		return messageSource;
	}
}
