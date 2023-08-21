package com.jweb.common.annotation.param;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ParamRule {

	/**
	 * 参数名称
	 * <p> name参数是实体类，校验某个属性值时，需要配置fieldName
	 * @return
	 */
	String name();
	
	/**
	 * 校验规则
	 * <p> ParamRule enum Rule中定义
	 * @return
	 */
	Rule rule();
	
	/**
	 * 实体类属性名称
	 * <p> name参数是实体类时，实体类的field name
	 * @return
	 */
	String fieldName() default "";
	
	/**
	 * 比较规则的value值
	 * @return
	 */
	String value() default "";
	
	/**
	 * 比较规则IS_BETWEEN或IS_IN的value值
	 * @return
	 */
	String[] values() default "";
	/**
	 * 规则IS_MATCH的正则表达式
	 * @return
	 */
	String pattern() default "";
	
	/**
	 * 字符串长度限制
	 * @return
	 */
	int maxLength() default -1;

	/**
	 * 校验规则
	 * @author 邓超
	 *
	 * 2022/09/02 下午6:03:09
	 */
	public static enum Rule{
		/**
		 * parameter不为null
		 */
		NOT_NULL,
		/**
		 * parameter不为null或""
		 */
		NOT_NULL_AND_EMPTY,
		/**
		 * 正则匹配
		 */
		MATCH,
		/**
		 * 正则匹配，允许null值
		 */
		MATCH_OR_NULL,
		/**
		 * parameter包含value
		 */
		CONTAIN,
		/**
		 * parameter包含value，允许null值
		 */
		CONTAIN_OR_NULL,
		/**
		 * parameter以value开头
		 */
		START_WITH,
		/**
		 * parameter以value开头，允许null值
		 */
		START_WITH_OR_NULL,
		/**
		 * parameter以value结尾
		 */
		END_WITH,
		/**
		 * parameter以value结尾，允许null值
		 */
		END_WITH_OR_NULL,
		/**
		 * parameter最大字符长度
		 */
		MAX_LENGTH,
		/**
		 * parameter最大字符长度，允许null值
		 */
		MAX_LENGTH_OR_NULL,
		/**
		 * 相等
		 * <p>parameter == value
		 */
		EQUAL,
		/**
		 * 相等，允许null值
		 * <p>parameter == value
		 */
		EQUAL_OR_NULL,
		/**
		 * 大于
		 * <p>parameter > value
		 */
		MORE_THAN,
		/**
		 * 大于，允许null值
		 * <p>parameter > value
		 */
		MORE_THAN_OR_NULL,
		/**
		 * 大于等于
		 * <p>parameter => value
		 */
		MORE_THAN_EQUAL,
		/**
		 * 大于等于，允许null值
		 * <p>parameter => value
		 */
		MORE_THAN_EQUAL_OR_NULL,
		
		/**
		 * 小于
		 * <p>parameter < value
		 */
		LESS_THAN,
		/**
		 * 小于，允许null值
		 * <p>parameter < value
		 */
		LESS_THAN_OR_NULL,
		/**
		 * 小于等于
		 * <p>parameter <= value
		 */
		LESS_THAN_EQUAL,
		/**
		 * 小于等于，允许null值
		 * <p>parameter <= value
		 */
		LESS_THAN_EQUAL_OR_NULL,
		/**
		 * 两个范围值之间
		 * <p>parameter > values[0] and parameter < values[1]
		 */
		BETWEEN,
		/**
		 * 两个范围值之间，允许null值
		 * <p>parameter > values[0] and parameter < values[1]
		 */
		BETWEEN_OR_NULL,
		/**
		 * 两个范围值之间
		 * <p>parameter => values[0] and parameter <= values[1]
		 */
		BETWEEN_EQUAL,
		/**
		 * 两个范围值之间，允许null值
		 * <p>parameter => values[0] and parameter <= values[1]
		 */
		BETWEEN_EQUAL_OR_NULL,
		/**
		 * 多个值之中
		 * <p>parameter == values[0] or parameter == values[1] or parameter == values[2]...
		 */
		IN,
		/**
		 * 多个值之中，允许null值
		 * <p>parameter == values[0] or parameter == values[1] or parameter == values[2]...
		 */
		IN_OR_NULL;
	}
	
}
