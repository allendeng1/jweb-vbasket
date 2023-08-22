package com.jweb.adminweb.config;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jweb.adminweb.filter.AuthorizationFilter;
import com.jweb.adminweb.filter.ResourcesSecurityFilter;

/**
 * Filter注册
 * 
 * <p> 以这种方式注册Filter是为了控制Filter的执行顺序
 * 
 * @author allen
 * 
 */
@Configuration
public class FilterConfig {
	
	@Autowired
	private AuthorizationFilter authorizationFilter;
	@Autowired
	private ResourcesSecurityFilter resourcesSecurityFilter;

	@Bean
    public FilterRegistrationBean<Filter> registerAuthorizationFilter() {
		FilterRegistrationBean<Filter> registration = registerFilter(authorizationFilter, "authorizationfilter", "/*", 1);
        return registration;
    }
	@Bean
    public FilterRegistrationBean<Filter> registerResourcesSecurityFilter() {
        FilterRegistrationBean<Filter> registration = registerFilter(resourcesSecurityFilter, "resourcesSecurityFilter", "/*", 2);
        return registration;
    }
	
	private FilterRegistrationBean<Filter> registerFilter(Filter filter, String name, String urlPatterns, int order){
		FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<Filter>();
        registration.setFilter(filter);
        registration.addUrlPatterns(urlPatterns);
        registration.setName(name);
        registration.setOrder(order);
        return registration;
	}

}
