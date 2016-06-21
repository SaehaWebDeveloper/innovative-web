package com.saeha.webdev.innovativeweb.web;

import javax.servlet.Filter;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.saeha.webdev.innovativeweb.web.util.ConfigClassLoader;
import com.saeha.webdev.innovativeweb.web.util.ConfigClassLoader.ConfigType;
import com.saeha.webdev.innovativeweb.web.util.filter.XSSFilter;

/**
 * web.xml 설정
 * 
 * @author Pure
 *
 */
public class ApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer#getRootConfigClasses()
	 */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		// root context config
		return ConfigClassLoader.getConfigClass(ConfigType.ROOT, this.getClass().getPackage().getName());
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer#getServletConfigClasses()
	 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		// servlet context config
		return ConfigClassLoader.getConfigClass(ConfigType.SERVLET, this.getClass().getPackage().getName());
	}
	
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.support.AbstractDispatcherServletInitializer#getServletMappings()
	 */
	@Override
	protected String[] getServletMappings() {
		return new String[]{"/"};
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.support.AbstractDispatcherServletInitializer#getServletFilters()
	 */
	@Override
	protected Filter[] getServletFilters() {
		// CharacterEncodingFilter
		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
		encodingFilter.setEncoding("UTF-8");
		encodingFilter.setForceEncoding(true);
		
		// XSS Filter
		XSSFilter xssFilter = new XSSFilter();
		
		return new Filter[]{encodingFilter, xssFilter};
	}

}
