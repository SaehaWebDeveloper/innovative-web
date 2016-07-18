package com.saeha.webdev.innovativeweb.mbean;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@ManagedResource(objectName="${config.mbean.prefix}:type=withoutxml,name=test")
public class PureTestMbean {
	
	private int count;
	
	@ManagedOperation(description="전송")
	public void send(){
		count++;
		log.debug("count:{}", count);
	}
	
	@ManagedAttribute(description="건수")
	public int getCount(){
		log.debug("count:{}", count);
		return count;
	}
}
