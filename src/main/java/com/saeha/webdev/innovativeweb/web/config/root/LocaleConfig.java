package com.saeha.webdev.innovativeweb.web.config.root;

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
		messageSource.setBasenames("classpath:message/message", "classpath:message/validation");
		messageSource.setUseCodeAsDefaultMessage(true);
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(-1);
		return messageSource;
	}
}
