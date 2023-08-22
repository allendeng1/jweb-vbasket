package com.jweb.adminweb.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.jweb.common.util.DataUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 资源授权认证 Filter
 * 
 * <p> 在com.loan.admin.api.config.FilterConfig中注册Filter
 * 
 * @author allen
 * 
 */
@Slf4j
@Component
public class ResourcesSecurityFilter extends DataUtil implements Filter{

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) arg0;
        HttpServletResponse response = (HttpServletResponse) arg1;
		
        String requestUri = request.getRequestURI();

		chain.doFilter(arg0, arg1);
	
	}

}
