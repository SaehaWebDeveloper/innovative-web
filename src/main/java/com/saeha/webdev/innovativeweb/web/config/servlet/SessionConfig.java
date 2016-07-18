package com.saeha.webdev.innovativeweb.web.config.servlet;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.hazelcast.config.annotation.web.http.EnableHazelcastHttpSession;

import com.hazelcast.config.SerializerConfig;
import com.hazelcast.core.HazelcastInstance;

/**
 * Session Cluster (dependency: spring-session 1.2.0.RELEASE, hazelcast)
 * 
 * @author Pure
 *
 */
@Configuration
@EnableHazelcastHttpSession(maxInactiveIntervalInSeconds=1800)
public class SessionConfig {

	/**
	 * Hazelcast Instance
	 */
	@Autowired(required = false)
	HazelcastInstance hazelcastInstance;

	@PostConstruct
	public void init() {
		if (hazelcastInstance != null) {
//			SerializerConfig serializerConfig = new SerializerConfig().setTypeClass(Object.class)
//					.setImplementation(new ObjectStreamSerializer());
//			hazelcastInstance.getConfig().getSerializationConfig().addSerializerConfig(serializerConfig);
		}
	}
}
