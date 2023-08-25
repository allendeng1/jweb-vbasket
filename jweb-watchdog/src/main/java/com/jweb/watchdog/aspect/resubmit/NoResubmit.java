package com.jweb.watchdog.aspect.resubmit;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 防重复提交
 * 
 * @author 邓超
 *
 * 2023/08/24 上午10:48:23
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NoResubmit {

	/**
	 * 时间间隔，单位：秒
	 * <p> 默认3秒
	 * @return
	 */
	long timeInterval() default 3;
	/**
	 * 方法执行结束后是否可以提交
	 * <p> 默认true
	 * <p> 1、true，方法执行结束后立即可以再次提交
	 * <p> 2、false，等待时间间隔结束后才能再次提交
	 * @return
	 */
	boolean submittable() default true;

}
