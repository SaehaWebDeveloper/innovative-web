package com.saeha.webdev.innovativeweb.web.config.root.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import lombok.extern.slf4j.Slf4j;

/**
 * OAuth2 인증 서버
 * 
 * @author Pure
 *
 */
@Slf4j
@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {
	
	/**
	 * ResourceServer Id
	 */
	@Value("${config.oauth.resourceServer:apiServer}")
	private String resourceId;
	
	@Autowired private AuthenticationManager authenticationManager;
	
	/* (non-Javadoc)
	 * @see org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter#configure(org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer)
	 */
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		// 인증서버 자체의 보안정보 설정
		security.allowFormAuthenticationForClients();
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter#configure(org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer)
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		// TODO JDBC
		// Client 정보 설정
		clients.inMemory()
			.withClient("webdev-client")
				.secret("webdev-client")
				.authorizedGrantTypes("client_credentials")
				.authorities("ROLE_REST_USER")
				.scopes("read", "write")
				.accessTokenValiditySeconds(60 * 60)
				.resourceIds(resourceId)
			.and()
			.withClient("webdev-client2")
				.secret("webdev-client2")
				.authorizedGrantTypes("client_credentials")
				.authorities("ROLE_REST_CONFERENCE")
				.scopes("read", "write")
				.accessTokenValiditySeconds(60 * 60)
				.resourceIds(resourceId);
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter#configure(org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer)
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		// 인증 서버가 작동하기 위한 Endpoint 정보 설정
		endpoints
			.tokenStore(tokenStore())
			.accessTokenConverter(jwtAccessTokenConverter())
			.approvalStoreDisabled()
			.authenticationManager(authenticationManager);
	}
	
	/**
	 * Token Store
	 * 
	 * @return TokenStore
	 */
	@Bean
	public TokenStore tokenStore(){
		return new JwtTokenStore(jwtAccessTokenConverter());
	}
	
	/**
	 * AccessTokenConverter
	 * 
	 * @return JwtAccessTokenConverter
	 */
	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter(){
		return new JwtAccessTokenConverter();
	}
}
