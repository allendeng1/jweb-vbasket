package com.jweb.filestorage.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.jweb.common.api.ApiResult;
import com.jweb.common.api.BaseController;
import com.jweb.common.exception.MyException;
import com.jweb.common.util.DataUtil;
import com.jweb.common.util.RegularMatchUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 登录授权认证 Filter
 * 
 * <p> 在com.jweb.filestorage.config.FilterConfig中注册Filter
 * 
 * @author 邓超
 * 
 */
@Slf4j
@Component
public class AuthorizationFilter extends DataUtil implements Filter{
	
	
	private static final String AUTH_HEAD = "XA-AUTH";
	
	private List<String> matchUrls = new ArrayList<String>();
	private List<String> containUrls = new ArrayList<String>();
	private List<String> startWithUrls = new ArrayList<String>();


	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) arg0;
        HttpServletResponse response = (HttpServletResponse) arg1;
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE,HEAD,PUT,PATCH");
        response.setHeader("Access-Control-Max-Age", "36000");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept,Authorization,authorization");
        response.setHeader("Access-Control-Allow-Credentials","true");
        
        String requestUri = request.getRequestURI();
        
        boolean isAuthIgnore = isAuthIgnore(requestUri);
        if(isAuthIgnore){
        	chain.doFilter(arg0, arg1);
        	return;
        }
        String token = request.getHeader(AUTH_HEAD);
        try {
        	BaseController.checkAuthToken(token);
        	chain.doFilter(arg0, arg1);
		} catch (MyException e) {
			log.error(e.getMessage(), e);
			ApiResult result = new ApiResult();
			result.bizFail(e);
			response.getOutputStream().write(JSONObject.toJSONString(result).getBytes());
		}
        
	}
	
	private boolean isAuthIgnore(String url){
		if(isMatchAuthIgnoreUrl(url)){
			return true;
		}
		if(isContainAuthIgnoreUrl(url)){
			return true;
		}
		if(isStartWithAuthIgnoreUrl(url)){
			return true;
		}
		return false;
	}
	
	private boolean isMatchAuthIgnoreUrl(String url){
		return matchUrls.contains(url);
	}
	private boolean isContainAuthIgnoreUrl(String url){
		for(String curl : containUrls){
			if(isContain(url, curl)){
				return true;
			}
		}
		return false;
	}
	private boolean isStartWithAuthIgnoreUrl(String url){
		for(String surl : startWithUrls){
			if(url.startsWith(surl)){
				return true;
			}
		}
		return false;
	}
	
	@Value("${auth.ignore.urls:}")
	public void setAuthIgnoreMap(List<String> lists)throws RuntimeException{
		for(String list : lists){
			if(isNullOrTrimEmpty(list)){
				continue;
			}
			if(!isContain(list,"-")){
				throw new RuntimeException("配置[auth.ignore.urls]信息有误");
			}
			
			try {
				String str = list.substring(0, 1);
				String url = list.substring(2, list.length());
				if(isNullOrTrimEmpty(url)){
					continue;
				}
				if(!isMatch(str, RegularMatchUtil.Patter.NUMBER)){
					throw new RuntimeException("配置[auth.ignore.urls]信息有误");
				}
				int rule = parseint(str);
				if(rule < 1 || rule > 3){
					throw new RuntimeException("配置[auth.ignore.urls]信息有误");
				}
				switch (rule) {
				case 1:
					matchUrls.add(url);
					break;
				case 2:
					containUrls.add(url);
					break;
				case 3:
					startWithUrls.add(url);
					break;

				default:
					break;
				}
			} catch (MyException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
	}
}
