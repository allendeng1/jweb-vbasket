package com.jweb.service.apilog;

import java.util.Enumeration;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.RandomStringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONObject;
import com.jweb.common.util.DateTimeUtil;
import com.jweb.common.util.IpUtil;
import com.jweb.dao.ApiLogDao;
import com.jweb.dao.entity.ApiLog;

import lombok.extern.slf4j.Slf4j;

/**
 * API 访问日志
 * @author 邓超
 *
 * 2022/09/30 下午5:01:26
 */
@Aspect
@Component
@Slf4j
public class ApiRequestAspect {
	
	@Resource
	private ApiLogDao apiLogDao;
	
	/**
	 * 匹配com.jweb包下及其子包下以Controller结尾的类中的所有方法
	 */
	@Pointcut("execution(* com.jweb..*.*Controller.*(..))")
	public void poinCut(){}
	
	@Around("poinCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = DateTimeUtil.nowTime();
        
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		String reqId = RandomStringUtils.randomAlphanumeric(10);

		String url = request.getRequestURI();
		String method = request.getMethod();
		String ip = IpUtil.getIpAddress(request);
		log.info("["+reqId+"]请求："+ip+"  "+method+"  "+url);
		
		//参数
		Enumeration<String> paramter = request.getParameterNames();
		JSONObject json = new JSONObject();
		while (paramter.hasMoreElements()) {
			String str = (String) paramter.nextElement();
			json.put(str, request.getParameter(str));
		}
		String params = json.toJSONString();
		log.info("["+reqId+"]参数："+params);
				
		Signature signature = point.getSignature();
		String controllerName = signature.getDeclaringTypeName();
		String controllerMethod = signature.getName();
		
        Object result = point.proceed();
        
        long endTime = DateTimeUtil.nowTime();
        String resultData = JSONObject.toJSONString(result);
        log.info("["+reqId+"]结果："+resultData);
        log.info("["+reqId+"]耗时："+(endTime-beginTime)+"ms");
        
        try {
        	String[] urls = url.split("/", 3);
        	String serverContextPath = "/"+urls[1];
        	String reqUrl = "/"+urls[2];
        	
        	//同步索引接口请求，不创建日志，避免创建ApiLog索引死循环
        	if(reqUrl.equalsIgnoreCase("/publish/queue/message")) {
        		if(json.getString("bizType").equalsIgnoreCase("INDEX_DOC_SYNC")) {
        			 return result;
        		}
        	}
        	
        	ApiLog apiLog = new ApiLog();
        	apiLog.setBeginTime(beginTime);
        	apiLog.setControllerMethod(controllerMethod);
        	apiLog.setControllerName(controllerName);
        	apiLog.setCostTime(endTime-beginTime);
        	apiLog.setCtdate(DateTimeUtil.nowTime());
        	apiLog.setEndTime(endTime);
        	apiLog.setIpAddress(ip);
        	apiLog.setIsAttack(false);
        	apiLog.setMddate(DateTimeUtil.nowTime());
        	apiLog.setReqMethod(method);
        	apiLog.setReqParam(params);
        	apiLog.setReqUrl(reqUrl);
        	apiLog.setResultData(resultData);
        	apiLog.setServerContextPath(serverContextPath);
        	apiLog.setUserId(null);
        	apiLogDao.insert(apiLog);
        } catch (Exception e) {
        	log.error("保存API日志出错", e);
        }
        return result;
    }
}

