package com.saeha.webdev.innovativeweb.web.config.root.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import lombok.extern.slf4j.Slf4j;

/**
 * OAuth2 인증 서버
 * @author PureDesk
 *
 */
@Slf4j
@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		// 인증서버 자체의 보안정보 설정
	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		// Client 정보 설정
		clients.inMemory()
			.withClient("webdev-client")
				.secret("webdev-client")
				.authorizedGrantTypes("password", "client_credentials", "refresh_token")
				.authorities("ROLE_CLIENT")
				.scopes("read", "write")
				.accessTokenValiditySeconds(60 * 10)
				.resourceIds("webdevResource");
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		// 인증 서버가 작동하기 위한 Endpoint 정보 설정
//		endpoints.tokenStore(tokenStore());
//		endpoints.accessTokenConverter(jwtAccessTokenConverter());
		endpoints.authenticationManager(authenticationManager);
	}
	
//	@Bean
//	public TokenStore tokenStore(){
		//return new JwtTokenStore(jwtAccessTokenConverter());
//	}
	
//	@Bean
//	public JwtAccessTokenConverter jwtAccessTokenConverter(){
//		return new JwtAccessTokenConverter();
//	}
}
