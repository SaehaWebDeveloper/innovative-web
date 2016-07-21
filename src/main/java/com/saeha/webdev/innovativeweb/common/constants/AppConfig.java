package com.saeha.webdev.innovativeweb.common.constants;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Component
@ToString
@Slf4j
public class AppConfig implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1874660336075502009L;

	@PostConstruct
	public void init(){
		log.debug("####################### Loading config. ####################### {}", this.toString());
	}
	
	@Value("${project.version}")
	@Getter private String projectVersion;
}
