package com.jweb.watchdog.aspect.resubmit;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.jweb.common.encrypt.MD5;
import com.jweb.common.exception.CommonException;
import com.jweb.common.util.DataUtil;
import com.jweb.common.util.IpUtil;
import com.jweb.dao.component.RedisComponent;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Order(-1)
@Slf4j
public class NoResubmitAspect extends DataUtil{
	
	@Autowired
	private RedisComponent redisComponent;
	
	@Pointcut("@annotation(com.jweb.watchdog.aspect.resubmit.NoResubmit)")
    public void annotationPoinCut(){}

	@Around(value = "annotationPoinCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		
		//用户标识
		String userKey = "";
		userKey = request.getHeader("XA-AUTH");//用户token
		if(isNullOrTrimEmpty(userKey)) {
			userKey = IpUtil.getIpAddress(request);//用户客户端IP
		}

    	String param = getFieldsName(joinPoint);
    	String className = joinPoint.getTarget().getClass().getName();
    	MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    	String methodName = signature.getName();
    	
    	NoResubmit noResubmit = signature.getMethod().getAnnotation(NoResubmit.class);
    	
    	String originalKey = spliceString("-", true, userKey,className,methodName,param);
    	log.info("重复提交检查原始key:{}", originalKey);
    	String encryptKey = MD5.encrypt(originalKey);
    	log.info("重复提交检查加密key:{}", encryptKey);
    	RLock lock = redisComponent.getLock(encryptKey);
    	boolean isOk = lock.tryLock(0, noResubmit.timeInterval(), TimeUnit.SECONDS);
    	if(!isOk) {
    		log.warn("重复提交：{} {} {} {}", userKey, className, methodName, param);
    		CommonException.noResubmit();
    	}
    	
		Object result = joinPoint.proceed();
		if(noResubmit.submittable()) {
			//一次请求执行完后强制解锁
			lock.forceUnlock();
		}
    	return result;
    }
	
	/**
     * 获取参数列表
     *
     * @param joinPoint
     * @return
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     */
    private String getFieldsName(ProceedingJoinPoint joinPoint) {
        // 参数值
        Object[] args = joinPoint.getArgs();
        ParameterNameDiscoverer pnd = new DefaultParameterNameDiscoverer();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String[] parameterNames = pnd.getParameterNames(method);
        if(isNullOrEmpty(parameterNames)) {
        	return null;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < parameterNames.length; i++) {
            builder.append(parameterNames[i]).append("=").append(args[i]).append("&");
        }
        return builder.toString();
    }
}
