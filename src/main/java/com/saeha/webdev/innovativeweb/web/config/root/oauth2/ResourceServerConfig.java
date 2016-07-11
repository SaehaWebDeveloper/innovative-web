package com.saeha.webdev.innovativeweb.web.config.root.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * API 서버
 * @author PureDesk
 *
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	@Autowired private TokenStore tokenStore;
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.requestMatchers().anyRequest()
			.and()
			.authorizeRequests().antMatchers("/rest/**").hasRole("CLIENT");
			
	}
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenStore(tokenStore);
		resources.resourceId("webdevResource");
	}
}
