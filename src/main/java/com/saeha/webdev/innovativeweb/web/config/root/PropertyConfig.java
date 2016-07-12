package com.saeha.webdev.innovativeweb.web.config.root;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Property 설정
 * 
 * @author Pure
 *
 */
@Configuration
@PropertySources({
	@PropertySource(name="config", value="classpath:/properties/config.properties")
	,@PropertySource(name="dbConfig", value="classpath:/properties/db.properties")
	,@PropertySource(name="appConfig", value="classpath:/properties/application.properties")
})
public class PropertyConfig {
	/**
	 * Properties
	 * 
	 * @return
	 */
	@Bean
	public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(){
		PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
		return configurer;
	}
}
