package com.jobportal.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class JobPortalInterceptorConfig implements WebMvcConfigurer {

	@Autowired
	private AuthenticationLogger authenticationLogger;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		registry.addInterceptor(authenticationLogger);
	}

}
