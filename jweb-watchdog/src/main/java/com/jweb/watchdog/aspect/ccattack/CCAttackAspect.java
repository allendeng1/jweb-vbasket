package com.jweb.watchdog.aspect.ccattack;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.jweb.common.exception.MyException;
import com.jweb.common.util.IpUtil;


/**
 * 防CC攻击
 * @author 邓超
 *
 * 2023/08/24 下午6:46:35
 */
@Aspect
@Component
@ConditionalOnExpression("${watchdog.ccattack.enable:true}")
public class CCAttackAspect {
	
	@Autowired
	private CCAttackMonitor cCAttackMonitor;

	@Pointcut("execution(* com.jweb..*.*Controller.*(..))")
    public void poinCut(){}
	
	@Before(value = "poinCut()")
    public void before(JoinPoint joinPoint) throws MyException{
		
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		
		String token = request.getHeader("XA-AUTH");
		String ip = IpUtil.getIpAddress(request);
		cCAttackMonitor.securityCheck(ip, token);

	}

}
