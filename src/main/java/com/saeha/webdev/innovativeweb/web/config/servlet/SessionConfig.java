package com.saeha.webdev.innovativeweb.web.config.servlet;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.hazelcast.config.annotation.web.http.EnableHazelcastHttpSession;

/**
 * Session Cluster (dependency: spring-session 1.2.0.RELEASE, hazelcast)
 * 
 * @author Pure
 *
 */
@Configuration
@EnableHazelcastHttpSession(maxInactiveIntervalInSeconds=1800)
public class SessionConfig {
}
