package com.saeha.webdev.innovativeweb.web.config.servlet;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import lombok.extern.slf4j.Slf4j;

/**
 * OAuth 설정
 * @author Pure
 *
 */
@Slf4j
@Configuration
public class OAuth2Config {
	/**
	 * ResourceServer Id
	 */
	@Value("${config.oauth.resourceServer:apiServer}")
	private String resourceId;
	
	/**
	 * Security
	 * @author Pure
	 *
	 */
	@Configuration
	@EnableWebSecurity
	protected class SecurityConfig extends WebSecurityConfigurerAdapter {
	}
	
	/**
	 * 인증서버
	 * @author Pure
	 *
	 */
	@Configuration
	@EnableAuthorizationServer
	protected class AuthServerConfig extends AuthorizationServerConfigurerAdapter {
		
		/**
		 * Project DataSource
		 */
		@Autowired private DataSource dataSource;
		
		/* (non-Javadoc)
		 * @see org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter#configure(org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer)
		 */
		@Override
		public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
			// 토큰 엔트 포인트의 보안 제약 조건 정의
			security.accessDeniedHandler(oAuth2AccessDeniedHandler());
			
			// https only
			//security.sslOnly();
			// "/oauth/token_key" 접근 권한
			//security.tokenKeyAccess("permitAll()");
			// "/oauth/check_token" 접근 권한
			//security.checkTokenAccess("permitAll()");
		}
		
		/* (non-Javadoc)
		 * @see org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter#configure(org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer)
		 */
		@Override
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
			// 클라이언트 정보 서비스를 정의 및 구성
			// JDBC
			// TODO Custom ClientDetailsService
			clients.jdbc(dataSource).clients(clientDetailsService());
		}
		
		/* (non-Javadoc)
		 * @see org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter#configure(org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer)
		 */
		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
			// 인증 토큰 엔드 포인트와 토큰 서비스를 정의
			endpoints
				.tokenStore(tokenStore())
				.accessTokenConverter(jwtAccessTokenConverter())
				;
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
		
		/**
		 * Client JDBC Service
		 * 
		 * @return ClientDetailsService
		 */
		@Bean
		public JdbcClientDetailsService clientDetailsService(){
			return new JdbcClientDetailsService(dataSource);
		}
		
		@Bean
		public OAuth2AccessDeniedHandler oAuth2AccessDeniedHandler(){
			OAuth2AccessDeniedHandler oAuth2AccessDeniedHandler = new OAuth2AccessDeniedHandler();
			return oAuth2AccessDeniedHandler;
		}
	}
	
	/**
	 * API 서버
	 * @author Pure
	 *
	 */
	@Configuration
	@EnableResourceServer
	protected class ResourceServerConfig extends ResourceServerConfigurerAdapter {
		/* (non-Javadoc)
		 * @see org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.HttpSecurity)
		 */
		@Override
		public void configure(HttpSecurity http) throws Exception {
			// TODO 권한 정의 필요
			http.requestMatchers().anyRequest().antMatchers("/**")
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
	
}
