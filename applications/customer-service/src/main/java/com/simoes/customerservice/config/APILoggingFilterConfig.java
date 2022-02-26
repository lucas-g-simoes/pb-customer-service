package com.simoes.customerservice.config;

import com.simoes.customerservice.api.v1.APILoggingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class APILoggingFilterConfig {

	public FilterRegistrationBean<APILoggingFilter> loggingFilter() {
		FilterRegistrationBean<APILoggingFilter> registrationBean = new FilterRegistrationBean<>();

		registrationBean.setFilter(new APILoggingFilter());
		registrationBean.addUrlPatterns("/v1/*");

		return registrationBean;

	}

}
