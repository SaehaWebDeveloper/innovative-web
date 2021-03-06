package com.saeha.webdev.innovativeweb.web.config.servlet;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.MediaType;
import org.springframework.mobile.device.DeviceHandlerMethodArgumentResolver;
import org.springframework.mobile.device.DeviceResolverHandlerInterceptor;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.PathExtensionContentNegotiationStrategy;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.BeanNameViewResolver;
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

/**
 * MVC 설정
 * 
 * @author Pure
 *
 */
@Configuration
@EnableWebMvc
@EnableSpringDataWebSupport
@ComponentScan(basePackages={"${config.spring.component.basePackages}"})
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	
	private static final Integer RESOURCE_CACHE_PERIOD_SECOND = 60 * 60;
	
	/**
	 * Tiles Definitions
	 */
	@Value("#{'${config.tiles.definitions:}'.split(',')}")
	private String[] tilesDefinitions;
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#addResourceHandlers(org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry)
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**")
			.addResourceLocations("/resources/")
			.setCachePeriod(RESOURCE_CACHE_PERIOD_SECOND);
		registry.addResourceHandler("/resources/librarys/**")
			.addResourceLocations("/webjars/", "/resources/librarys/")
			.setCachePeriod(RESOURCE_CACHE_PERIOD_SECOND);
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#addViewControllers(org.springframework.web.servlet.config.annotation.ViewControllerRegistry)
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		// <mvc:view-controller path="/login" view-name="redirect:/auth/login"> 설정과 동일
		registry.addRedirectViewController("/login", "/auth/login");
		registry.addRedirectViewController("/logout", "/auth/logout");
		registry.addRedirectViewController("/main", "/conference/list");
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry)
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry){
		// <mvc:interceptors> 설정과 동일 
		registry.addInterceptor(deviceResolverHandlerInterceptor());
		
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
					, "/error*"
					
					, "/rest/**"
					// Test
					, "/test/**"
					);
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#addArgumentResolvers(java.util.List)
	 */
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(deviceHandlerMethodArgumentResolver());
	}
	
	/**
	 * commonInterceptor
	 * @return
	 */
	@Bean
	public CommonInterceptor commonInterceptor(){
		return new CommonInterceptor();
	}
	/**
	 * defaultSettingInterceptor
	 * @return
	 */
	@Bean public DefaultSettingInterceptor defaultSettingInterceptor(){
		return new DefaultSettingInterceptor();
	}
	/**
	 * authInterceptor
	 * @return
	 */
	@Bean
	public AuthInterceptor authInterceptor(){
		return new AuthInterceptor();
	}
	
	@Bean
	public DeviceResolverHandlerInterceptor deviceResolverHandlerInterceptor(){
		return new DeviceResolverHandlerInterceptor();
	}
	
	@Bean
	public DeviceHandlerMethodArgumentResolver deviceHandlerMethodArgumentResolver(){
		return new DeviceHandlerMethodArgumentResolver();
	}
	
	/**
	 * jsonView
	 * @return
	 */
	@Bean
	public MappingJackson2JsonView jsonView(){
		return new MappingJackson2JsonView();
	}
	
	/**
	 * ContentNegotiationStrategy
	 * @return
	 */
	@Bean
	public ContentNegotiationStrategy contentNegotiationStrategy(){
		Map<String, MediaType> mediaTypeMap = new HashMap<>();
		mediaTypeMap.put("html", MediaType.TEXT_HTML);
		mediaTypeMap.put("json", MediaType.APPLICATION_JSON_UTF8);
		
		return new PathExtensionContentNegotiationStrategy(mediaTypeMap);
	}
	/**
	 * ContentNegotiationManager
	 * @return
	 */
	@Bean
	public ContentNegotiationManager contentNegotiationManager(){
		return new ContentNegotiationManager(contentNegotiationStrategy());
	}
	/**
	 * ContentNegotiating View Resolver
	 * @return
	 */
	@Bean
	public ContentNegotiatingViewResolver contentNegotiatingViewResolver(){
		ContentNegotiatingViewResolver contentNegotiatingViewResolver = new ContentNegotiatingViewResolver();
		contentNegotiatingViewResolver.setContentNegotiationManager(contentNegotiationManager());
		contentNegotiatingViewResolver.setDefaultViews(Arrays.asList(jsonView()));
		contentNegotiatingViewResolver.setOrder(1);
		return contentNegotiatingViewResolver;
	}
	
	/**
	 * Tiles 설정
	 * @return
	 */
	@Bean
	public TilesConfigurer tilesConfigurer(){
		TilesConfigurer tilesConfigurer = new TilesConfigurer();
		tilesConfigurer.setDefinitions(tilesDefinitions);
		return tilesConfigurer;
	}
	
	/**
	 * Tiles View Resolver
	 * @return
	 */
	@Bean
	public TilesViewResolver tilesViewResolver(){
		TilesViewResolver tilesViewResolver = new TilesViewResolver();
		tilesViewResolver.setViewClass(TilesView.class);
		tilesViewResolver.setOrder(2);
		return tilesViewResolver;
	}
	
	/**
	 * BeanName View Resolver - ExcelView
	 * @return
	 */
	@Bean
	public BeanNameViewResolver beanNameViewResolver(){
		BeanNameViewResolver beanNameViewResolver = new BeanNameViewResolver();
		beanNameViewResolver.setOrder(3);
		return beanNameViewResolver;
	}
	
	/**
	 * JSP View Resolver
	 * @return
	 */
	@Bean
	public ViewResolver viewResolver(){
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		viewResolver.setOrder(4);
		return viewResolver;
	}
	
	/**
	 * Default pageable 설정
	 * 
	 * @return
	 */
	@Bean
	public PageableHandlerMethodArgumentResolver pageableHandlerMethodArgumentResolver(){
		PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
		resolver.setOneIndexedParameters(true);
		resolver.setFallbackPageable(new PageRequest(0, 10));
		return resolver;
	}
}
