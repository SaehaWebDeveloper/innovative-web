package com.saeha.webdev.innovativeweb.web.config.root;

import java.io.IOException;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig;
import org.jasypt.spring3.properties.EncryptablePropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * Property 설정 (dependency. jasypt - properties 암호화)
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
	 * 암호화 환경 설정
	 * @return
	 */
	@Bean
	public EnvironmentStringPBEConfig environmentStringPBEConfig(){
		EnvironmentStringPBEConfig config = new EnvironmentStringPBEConfig();
		config.setAlgorithm("PBEWithMD5AndDES");
		config.setPassword("BRACE_PASS");
		return config;
	}
	
	/**
	 * 암호화 객체 생성
	 * @return
	 */
	@Bean
	public StandardPBEStringEncryptor standardPBEStringEncryptor(){
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setConfig(environmentStringPBEConfig());
		return encryptor;
	}
	
	/**
	 * 암호화된 Properties를 복호화 
	 * @return
	 * @throws IOException
	 */
	@Bean
	public EncryptablePropertyPlaceholderConfigurer propertyPlaceholderConfigurer() throws IOException {
		EncryptablePropertyPlaceholderConfigurer configurer = new EncryptablePropertyPlaceholderConfigurer(standardPBEStringEncryptor());
		configurer.setLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/properties/*.properties"));
		configurer.setNullValue("");
		return configurer;
	}
}
