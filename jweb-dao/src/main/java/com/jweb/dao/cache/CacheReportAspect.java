package com.jweb.dao.cache;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.stereotype.Component;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
@Aspect
@Component
public class CacheReportAspect {
	
	@SuppressWarnings("rawtypes")
	@Autowired
	private Icache icache;

	@Pointcut("@annotation(com.jweb.dao.cache.CacheReport)")
    public void annotationPoinCut(){}

	@AfterReturning(value = "annotationPoinCut()", returning="returnValue")
    public void after(JoinPoint joinPoint, Object returnValue){
    	Map<String, Object> param = getFieldsName(joinPoint);
    	String cacheName = (String) param.get("cacheName");
    	int hitNum = returnValue == null ? 0 : 1;
    	icache.updateCacheHitRate(cacheName, 1, hitNum);
    }
    
    /**
     * 获取参数列表
     *
     * @param joinPoint
     * @return
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     */
    private Map<String, Object> getFieldsName(JoinPoint joinPoint) {
        // 参数值
        Object[] args = joinPoint.getArgs();
        ParameterNameDiscoverer pnd = new DefaultParameterNameDiscoverer();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String[] parameterNames = pnd.getParameterNames(method);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        for (int i = 0; i < parameterNames.length; i++) {
            paramMap.put(parameterNames[i], args[i]);
        }
        return paramMap;
    }
}
