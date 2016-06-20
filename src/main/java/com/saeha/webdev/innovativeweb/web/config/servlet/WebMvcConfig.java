package com.saeha.webdev.innovativeweb.web.config.servlet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.PagedResourcesAssemblerArgumentResolver;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.PathExtensionContentNegotiationStrategy;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

import com.saeha.webdev.innovativeweb.interceptor.AuthInterceptor;
import com.saeha.webdev.innovativeweb.interceptor.CommonInterceptor;
import com.saeha.webdev.innovativeweb.interceptor.DefaultSettingInterceptor;

@Configuration
@EnableWebMvc
@EnableSpringDataWebSupport
@ComponentScan(basePackages={"com.saeha.webdev.innovativeweb"})
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		// <mvc:view-controller path="/login" view-name="redirect:/auth/login"> 설정과 동일
		registry.addRedirectViewController("/login", "/auth/login");
		registry.addRedirectViewController("/logout", "/auth/logout");
		registry.addRedirectViewController("/main", "/conference/list");
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry){
		// <mvc:interceptors> 설정과 동일 
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("language");
		registry.addInterceptor(localeChangeInterceptor);
		
		registry.addInterceptor(commonInterceptor());
		registry.addInterceptor(defaultSettingInterceptor());
		registry.addInterceptor(authInterceptor())
			.addPathPatterns("/**")
			.excludePathPatterns(
					"/login"
					, "/auth/*"
					, "/message/*"
					, "/error/*"
					//Test
					, "/user/*"
					);
	}
	
	@Bean
	public CommonInterceptor commonInterceptor(){
		return new CommonInterceptor();
	}
	@Bean public DefaultSettingInterceptor defaultSettingInterceptor(){
		return new DefaultSettingInterceptor();
	}
	@Bean
	public AuthInterceptor authInterceptor(){
		return new AuthInterceptor();
	}
	
	@Bean
	public MappingJackson2JsonView jsonView(){
		return new MappingJackson2JsonView();
	}
	
	@Bean
	public ContentNegotiationStrategy contentNegotiationStrategy(){
		Map<String, MediaType> mediaTypeMap = new HashMap<>();
		mediaTypeMap.put("html", MediaType.TEXT_HTML);
		mediaTypeMap.put("json", MediaType.APPLICATION_JSON);
		
		return new PathExtensionContentNegotiationStrategy(mediaTypeMap);
	}
	@Bean
	public ContentNegotiationManager contentNegotiationManager(){
		return new ContentNegotiationManager(contentNegotiationStrategy());
	}
	@Bean
	public ContentNegotiatingViewResolver contentNegotiatingViewResolver(){
		List<View> defaultViews = new ArrayList<>();
		defaultViews.add(jsonView());
		
		ContentNegotiatingViewResolver contentNegotiatingViewResolver = new ContentNegotiatingViewResolver();
		contentNegotiatingViewResolver.setContentNegotiationManager(contentNegotiationManager());
		contentNegotiatingViewResolver.setDefaultViews(defaultViews);
		contentNegotiatingViewResolver.setOrder(2);
		return contentNegotiatingViewResolver;
	}
	
	@Bean
	public TilesConfigurer tilesConfigurer(){
		TilesConfigurer tilesConfigurer = new TilesConfigurer();
		tilesConfigurer.setDefinitions("classpath:/config/tiles/user-definition.xml");
		return tilesConfigurer;
	}
	
	@Bean
	public TilesViewResolver tilesViewResolver(){
		TilesViewResolver tilesViewResolver = new TilesViewResolver();
		tilesViewResolver.setViewClass(TilesView.class);
		tilesViewResolver.setOrder(3);
		return tilesViewResolver;
	}
	
	@Bean
	public ViewResolver viewResolver(){
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		viewResolver.setOrder(4);
		return viewResolver;
	}
	
	@Bean
	public PageableHandlerMethodArgumentResolver pageableHandlerMethodArgumentResolver(){
		PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
		resolver.setOneIndexedParameters(true);
		resolver.setFallbackPageable(new PageRequest(0, 10));
		return resolver;
	}
}
