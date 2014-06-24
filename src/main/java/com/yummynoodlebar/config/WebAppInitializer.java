package com.yummynoodlebar.config;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.yummynoodlebar.config.persistence.PersistenceConfig;
import com.yummynoodlebar.config.security.SecurityConfig;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	// TODO config in application.properties
	private static final String SPRING_PROFILES_ACTIVE 	= "production";
	private static final String DEFAULT_HTML_ESCAPE 	= "true";
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);
		servletContext.setInitParameter("defaultHtmlEscape", DEFAULT_HTML_ESCAPE);
	}

	@Override
	protected WebApplicationContext createRootApplicationContext() {
		AnnotationConfigWebApplicationContext rootAppContext = (AnnotationConfigWebApplicationContext) super.createRootApplicationContext();
		if (rootAppContext != null) {
			ConfigurableEnvironment configurableEnvironment = rootAppContext.getEnvironment();
			configurableEnvironment.setActiveProfiles(SPRING_PROFILES_ACTIVE);
		}

		return rootAppContext;
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");

		Filter springSecurityFilterChain = new DelegatingFilterProxy("springSecurityFilterChain");
		return new Filter[] { characterEncodingFilter, springSecurityFilterChain };
	}

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { PersistenceConfig.class, CoreConfig.class, SecurityConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { WebConfig.class };
	}
}
