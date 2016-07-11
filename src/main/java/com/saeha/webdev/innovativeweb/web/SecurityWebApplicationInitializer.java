package com.saeha.webdev.innovativeweb.web;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;

/**
 * Spring Security Filter Chain Setting
 * 
 * @author Pure
 *
 */
public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {
	/* (non-Javadoc)
	 * @see org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer#getDispatcherWebApplicationContextSuffix()
	 */
	@Override
	protected String getDispatcherWebApplicationContextSuffix() {
		return AbstractDispatcherServletInitializer.DEFAULT_SERVLET_NAME;
	}
}
