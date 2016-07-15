package com.saeha.webdev.innovativeweb.web.config.servlet;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.hazelcast.config.annotation.web.http.EnableHazelcastHttpSession;

@Configuration
@EnableHazelcastHttpSession
public class SessionConfig {
}
