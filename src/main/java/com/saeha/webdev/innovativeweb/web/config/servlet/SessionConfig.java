package com.saeha.webdev.innovativeweb.web.config.servlet;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.hazelcast.config.annotation.web.http.EnableHazelcastHttpSession;

import com.hazelcast.config.SerializerConfig;
import com.hazelcast.core.HazelcastInstance;

@Configuration
@EnableHazelcastHttpSession(sessionMapName="sessions")
public class SessionConfig {
	
	/**
	 * Hazelcast Instance
	 */
	@Autowired(required=false) HazelcastInstance hazelcastInstance;
	
	@PostConstruct
	public void init(){
		if(hazelcastInstance != null){
			SerializerConfig serializerConfig = new SerializerConfig().setTypeClass(Object.class)
					.setImplementation(new ObjectStreamSerializer());
			hazelcastInstance.getConfig().getSerializationConfig().addSerializerConfig(serializerConfig);
		}
	}
}
