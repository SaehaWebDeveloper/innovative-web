package com.saeha.webdev.innovativeweb.web;

import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;

/**
 * Session Context Setting
 * @author Pure
 *
 */
public class SessionApplicationInitialzer extends AbstractHttpSessionApplicationInitializer{
	/* (non-Javadoc)
	 * @see org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer#getDispatcherWebApplicationContextSuffix()
	 */
	@Override
	protected String getDispatcherWebApplicationContextSuffix() {
		return AbstractDispatcherServletInitializer.DEFAULT_SERVLET_NAME;
	}
}
