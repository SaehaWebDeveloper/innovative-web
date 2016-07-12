package com.saeha.webdev.innovativeweb.web.config.root.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * API 서버
 * 
 * @author Pure
 *
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	
	/**
	 * ResourceServer Id
	 */
	@Value("${config.oauth.resourceServer:apiServer}")
	private String resourceId;
	
	/* (non-Javadoc)
	 * @see org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.HttpSecurity)
	 */
	@Override
	public void configure(HttpSecurity http) throws Exception {
		// TODO 권한 정의 필요
		http.requestMatchers().anyRequest()
			.and()
			// URI별 권한
			.authorizeRequests().antMatchers("/rest/user/**").hasRole("REST_USER")
			.and()
			.authorizeRequests().antMatchers("/rest/conference/**").hasRole("REST_CONFERENCE");
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter#configure(org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer)
	 */
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.resourceId(resourceId);
	}
}
