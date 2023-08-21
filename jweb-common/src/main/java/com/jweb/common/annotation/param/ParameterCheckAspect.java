package com.jweb.common.annotation.param;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.jweb.common.annotation.param.ParamRule.Rule;
import com.jweb.common.exception.CommonException;
import com.jweb.common.exception.MyException;
import com.jweb.common.util.DataUtil;

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
@Aspect
@Component
@Slf4j
public class ParameterCheckAspect extends DataUtil{

	@Pointcut("@annotation(com.jweb.common.annotation.param.ParameterCheck)")
    public void annotationPoinCut(){}

	@Before(value = "annotationPoinCut()")
    public void before(JoinPoint joinPoint) throws MyException{
    	Map<String, Object> param = getFieldsName(joinPoint);
    	String className = joinPoint.getTarget().getClass().getName();
    	MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    	String logPrefix = className+"."+signature.getName()+"参数校验失败：";
    	ParameterCheck check = signature.getMethod().getAnnotation(ParameterCheck.class);
    	List<CheckRule> rules = splitRules(check.value());
    	for(CheckRule rule : rules){
			checkParameter(logPrefix, rule, param);
    	}
    }
	
	private List<CheckRule> splitRules(ParamRule[] rules) {
		List<CheckRule> checkRules = new ArrayList<CheckRule>();
		for(ParamRule rule : rules){
			String pname = rule.name();
			if(pname.contains(",")) {//多个参数共用一个规则
				String[] names = pname.split(",");
	
				for(String name : names) {
					if(isEmpty(name)) {
						continue;
					}
			        checkRules.add(new CheckRule(name, rule));
				}

			}else {
				checkRules.add(new CheckRule(pname, rule));
			}
    	}
		return checkRules;
	}
	
	private void checkParameter(String logPrefix, CheckRule rule, Map<String, Object> param)throws MyException{
		if(param == null || param.isEmpty()){
			log.info(logPrefix+"没有获取到方法的参数");
			CommonException.parameterInvalid();
		}
		checkRuleConfig(logPrefix, rule);
		String pname = rule.name();
		if(!param.containsKey(pname)){
			return;
		}
		Object pvalue = getParameterValue(logPrefix, rule, param);
		ParamRule.Rule r = rule.rule();
		try {
			switch (r) {
			case NOT_NULL:
				notNull(pvalue, rule);
				break;
			case NOT_NULL_AND_EMPTY:
				notNullAndEmpty(pvalue, rule);
				break;
			case MATCH:
				match(pvalue, rule, false);
				break;
			case MATCH_OR_NULL:
				match(pvalue, rule, true);
				break;
			case CONTAIN:
				checkString(pvalue, rule, false);
				break;
			case CONTAIN_OR_NULL:
				checkString(pvalue, rule, true);
				break;
			case START_WITH:
				checkString(pvalue, rule, false);
				break;
			case START_WITH_OR_NULL:
				checkString(pvalue, rule, true);
				break;
			case END_WITH:
				checkString(pvalue, rule, false);
				break;
			case END_WITH_OR_NULL:
				checkString(pvalue, rule, true);
				break;
			case MAX_LENGTH:
				checkString(pvalue, rule, false);
				break;
			case MAX_LENGTH_OR_NULL:
				checkString(pvalue, rule, true);
				break;
			case EQUAL:
				checkNumber(pvalue, rule, false);
				break;
			case EQUAL_OR_NULL:
				checkNumber(pvalue, rule, true);
				break;
			case MORE_THAN:
				checkNumber(pvalue, rule, false);
				break;
			case MORE_THAN_OR_NULL:
				checkNumber(pvalue, rule, true);
				break;
			case MORE_THAN_EQUAL:
				checkNumber(pvalue, rule, false);
				break;
			case MORE_THAN_EQUAL_OR_NULL:
				checkNumber(pvalue, rule, true);
				break;
			case LESS_THAN:
				checkNumber(pvalue, rule, false);
				break;
			case LESS_THAN_OR_NULL:
				checkNumber(pvalue, rule, true);
				break;
			case LESS_THAN_EQUAL:
				checkNumber(pvalue, rule, false);
				break;
			case LESS_THAN_EQUAL_OR_NULL:
				checkNumber(pvalue, rule, true);
				break;
			case BETWEEN:
				checkNumber(pvalue, rule, false);
				break;
			case BETWEEN_OR_NULL:
				checkNumber(pvalue, rule, true);
				break;
			case BETWEEN_EQUAL:
				checkNumber(pvalue, rule, false);
				break;
			case BETWEEN_EQUAL_OR_NULL:
				checkNumber(pvalue, rule, true);
				break;
			case IN:
				in(pvalue, rule, false);
				break;
			case IN_OR_NULL:
				in(pvalue, rule, true);
				break;
			default:
				break;
			}
		} catch (MyException e) {
			printLog(pvalue, rule, logPrefix);
			CommonException.parameterInvalid();
		}
		
	}
	
	private void in(Object pvalue, CheckRule rule, boolean isAllowNull)throws MyException{
		if(isNull(pvalue)){
			if(isAllowNull){
				return;
			}
			CommonException.parameterInvalid();
		}
		String[] values = getCheckValues(rule);
		boolean isOk = false;
		String pv = pvalue.toString();
		for(String value : values){
			if(pv.equals(value)){
				isOk = true;
			}
		}
		if(!isOk){
			CommonException.parameterInvalid();
		}
	}
	
	private void notNull(Object pvalue, CheckRule rule)throws MyException{
		if(isNull(pvalue)){
			CommonException.parameterInvalid();
		}
		if(rule.maxLength() > -1){
			if(pvalue instanceof String){
				if(pvalue.toString().length() > rule.maxLength()){
					CommonException.parameterInvalid();
				}
			}
		}
	}
	private void notNullAndEmpty(Object pvalue, CheckRule rule)throws MyException{
		if(isNull(pvalue)){
			CommonException.parameterInvalid();
		}
		if(isEmpty((String) pvalue)){
			CommonException.parameterLost();
		}
		if(rule.maxLength() > -1){
			if(pvalue instanceof String){
				if(pvalue.toString().length() > rule.maxLength()){
					CommonException.parameterInvalid();
				}
			}
		}
	}
	private void match(Object pvalue, CheckRule rule, boolean isAllowNull)throws MyException{
		if(isNull(pvalue)){
			if(isAllowNull){
				return;
			}
			CommonException.parameterInvalid();
		}
		if(!(pvalue instanceof String)){
			CommonException.parameterInvalid();
		}
		String pv = (String) pvalue;
		if(isEmpty(pv)){
			CommonException.parameterLost();
		}
		if(rule.maxLength() > -1){
			if(pv.length() > rule.maxLength()){
				CommonException.parameterInvalid();
			}
		}
		String regx = rule.pattern();
		if(isNullOrTrimEmpty(regx)){
			CommonException.parameterInvalid();
		}
		
		Pattern pattern = Pattern.compile(regx);
		if(!pattern.matcher(pv).matches()){
			CommonException.parameterInvalid();
		}
	}
	
	private void checkNumber(Object pvalue, CheckRule rule, boolean isAllowNull)throws MyException{
		if(isNull(pvalue)){
			if(isAllowNull){
				return;
			}
			CommonException.parameterInvalid();
		}
		
		boolean isOk = true;
		ParamRule.Rule r = rule.rule();
		if(r.equals(ParamRule.Rule.EQUAL)){
			String value = getCheckValue(rule);
			isOk = pvalue.equals(value);
		}else if(r.equals(ParamRule.Rule.MORE_THAN)){
			String value = getCheckValue(rule);
			if(pvalue instanceof Integer){
				try {
					int pv = (int) pvalue;
					int v = Integer.parseInt(value);
					isOk = pv > v;
				} catch (Exception e) {
					log.error("", e);
					CommonException.parameterInvalid();
				}
			}else if(pvalue instanceof Double){
				try {
					double pv = (double) pvalue;
					double v = Double.parseDouble(value);
					isOk = pv > v;
				} catch (Exception e) {
					log.error("", e);
					CommonException.parameterInvalid();
				}
			}else if(pvalue instanceof BigDecimal){
				try {
					double pv = ((BigDecimal) pvalue).doubleValue();
					double v = Double.parseDouble(value);
					isOk = pv > v;
				} catch (Exception e) {
					log.error("", e);
					CommonException.parameterInvalid();
				}
			}else if(pvalue instanceof Float){
				try {
					float pv = (float) pvalue;
					float v = Float.parseFloat(value);
					isOk = pv > v;
				} catch (Exception e) {
					log.error("", e);
					CommonException.parameterInvalid();
				}
			}else if(pvalue instanceof Long){
				try {
					long pv = (long) pvalue;
					long v = Long.parseLong(value);
					isOk = pv > v;
				} catch (Exception e) {
					log.error("", e);
					CommonException.parameterInvalid();
				}
			}
		}else if(r.equals(ParamRule.Rule.LESS_THAN)){
			String value = getCheckValue(rule);
			if(pvalue instanceof Integer){
				try {
					int pv = (int) pvalue;
					int v = Integer.parseInt(value);
					isOk = pv < v;
				} catch (Exception e) {
					log.error("", e);
					CommonException.parameterInvalid();
				}
			}else if(pvalue instanceof Double){
				try {
					double pv = (double) pvalue;
					double v = Double.parseDouble(value);
					isOk = pv < v;
				} catch (Exception e) {
					log.error("", e);
					CommonException.parameterInvalid();
				}
			}else if(pvalue instanceof BigDecimal){
				try {
					double pv = ((BigDecimal) pvalue).doubleValue();
					double v = Double.parseDouble(value);
					isOk = pv < v;
				} catch (Exception e) {
					log.error("", e);
					CommonException.parameterInvalid();
				}
			}else if(pvalue instanceof Float){
				try {
					float pv = (float) pvalue;
					float v = Float.parseFloat(value);
					isOk = pv < v;
				} catch (Exception e) {
					log.error("", e);
					CommonException.parameterInvalid();
				}
			}else if(pvalue instanceof Long){
				try {
					long pv = (long) pvalue;
					long v = Long.parseLong(value);
					isOk = pv < v;
				} catch (Exception e) {
					log.error("", e);
					CommonException.parameterInvalid();
				}
			}
		}else if(r.equals(ParamRule.Rule.MORE_THAN_EQUAL)){
			String value = getCheckValue(rule);
			if(pvalue instanceof Integer){
				try {
					int pv = (int) pvalue;
					int v = Integer.parseInt(value);
					isOk = pv >= v;
				} catch (Exception e) {
					log.error("", e);
					CommonException.parameterInvalid();
				}
			}else if(pvalue instanceof Double){
				try {
					double pv = (double) pvalue;
					double v = Double.parseDouble(value);
					isOk = pv >= v;
				} catch (Exception e) {
					log.error("", e);
					CommonException.parameterInvalid();
				}
			}else if(pvalue instanceof BigDecimal){
				try {
					double pv = ((BigDecimal) pvalue).doubleValue();
					double v = Double.parseDouble(value);
					isOk = pv >= v;
				} catch (Exception e) {
					log.error("", e);
					CommonException.parameterInvalid();
				}
			}else if(pvalue instanceof Float){
				try {
					float pv = (float) pvalue;
					float v = Float.parseFloat(value);
					isOk = pv >= v;
				} catch (Exception e) {
					log.error("", e);
					CommonException.parameterInvalid();
				}
			}else if(pvalue instanceof Long){
				try {
					long pv = (long) pvalue;
					long v = Long.parseLong(value);
					isOk = pv >= v;
				} catch (Exception e) {
					log.error("", e);
					CommonException.parameterInvalid();
				}
			}
		}else if(r.equals(ParamRule.Rule.LESS_THAN_EQUAL)){
			String value = getCheckValue(rule);
			if(pvalue instanceof Integer){
				try {
					int pv = (int) pvalue;
					int v = Integer.parseInt(value);
					isOk = pv <= v;
				} catch (Exception e) {
					log.error("", e);
					CommonException.parameterInvalid();
				}
			}else if(pvalue instanceof Double){
				try {
					double pv = (double) pvalue;
					double v = Double.parseDouble(value);
					isOk = pv <= v;
				} catch (Exception e) {
					log.error("", e);
					CommonException.parameterInvalid();
				}
			}else if(pvalue instanceof BigDecimal){
				try {
					double pv = ((BigDecimal) pvalue).doubleValue();
					double v = Double.parseDouble(value);
					isOk = pv <= v;
				} catch (Exception e) {
					log.error("", e);
					CommonException.parameterInvalid();
				}
			}else if(pvalue instanceof Float){
				try {
					float pv = (float) pvalue;
					float v = Float.parseFloat(value);
					isOk = pv <= v;
				} catch (Exception e) {
					log.error("", e);
					CommonException.parameterInvalid();
				}
			}else if(pvalue instanceof Long){
				try {
					long pv = (long) pvalue;
					long v = Long.parseLong(value);
					isOk = pv <= v;
				} catch (Exception e) {
					log.error("", e);
					CommonException.parameterInvalid();
				}
			}
		}else if(r.equals(ParamRule.Rule.BETWEEN)){
			String[] values = getCheckValues(rule);
			if(pvalue instanceof Integer){
				try {
					int pv = (int) pvalue;
					int minv = Integer.parseInt(values[0]);
					int maxv = Integer.parseInt(values[1]);
					isOk = pv > minv && pv < maxv;
				} catch (Exception e) {
					log.error("", e);
					CommonException.parameterInvalid();
				}
			}else if(pvalue instanceof Double){
				try {
					double pv = (double) pvalue;
					double minv = Double.parseDouble(values[0]);
					double maxv = Double.parseDouble(values[1]);
					isOk = pv > minv && pv < maxv;
				} catch (Exception e) {
					log.error("", e);
					CommonException.parameterInvalid();
				}
			}else if(pvalue instanceof BigDecimal){
				try {
					double pv = ((BigDecimal) pvalue).doubleValue();
					double minv = Double.parseDouble(values[0]);
					double maxv = Double.parseDouble(values[1]);
					isOk = pv > minv && pv < maxv;
				} catch (Exception e) {
					log.error("", e);
					CommonException.parameterInvalid();
				}
			}else if(pvalue instanceof Float){
				try {
					float pv = (float) pvalue;
					float minv = Float.parseFloat(values[0]);
					float maxv = Float.parseFloat(values[1]);
					isOk = pv > minv && pv < maxv;
				} catch (Exception e) {
					log.error("", e);
					CommonException.parameterInvalid();
				}
			}else if(pvalue instanceof Long){
				try {
					long pv = (long) pvalue;
					long minv = Long.parseLong(values[0]);
					long maxv = Long.parseLong(values[1]);
					isOk = pv > minv && pv < maxv;
				} catch (Exception e) {
					log.error("", e);
					CommonException.parameterInvalid();
				}
			}
		}else if(r.equals(ParamRule.Rule.BETWEEN_EQUAL)){
			String[] values = getCheckValues(rule);
			if(pvalue instanceof Integer){
				try {
					int pv = (int) pvalue;
					int minv = Integer.parseInt(values[0]);
					int maxv = Integer.parseInt(values[1]);
					isOk = pv >= minv && pv <= maxv;
				} catch (Exception e) {
					log.error("", e);
					CommonException.parameterInvalid();
				}
			}else if(pvalue instanceof Double){
				try {
					double pv = (double) pvalue;
					double minv = Double.parseDouble(values[0]);
					double maxv = Double.parseDouble(values[1]);
					isOk = pv >= minv && pv <= maxv;
				} catch (Exception e) {
					log.error("", e);
					CommonException.parameterInvalid();
				}
			}else if(pvalue instanceof BigDecimal){
				try {
					double pv = ((BigDecimal) pvalue).doubleValue();
					double minv = Double.parseDouble(values[0]);
					double maxv = Double.parseDouble(values[1]);
					isOk = pv >= minv && pv <= maxv;
				} catch (Exception e) {
					log.error("", e);
					CommonException.parameterInvalid();
				}
			}else if(pvalue instanceof Float){
				try {
					float pv = (float) pvalue;
					float minv = Float.parseFloat(values[0]);
					float maxv = Float.parseFloat(values[1]);
					isOk = pv >= minv && pv <= maxv;
				} catch (Exception e) {
					log.error("", e);
					CommonException.parameterInvalid();
				}
			}else if(pvalue instanceof Long){
				try {
					long pv = (long) pvalue;
					long minv = Long.parseLong(values[0]);
					long maxv = Long.parseLong(values[1]);
					isOk = pv >= minv && pv <= maxv;
				} catch (Exception e) {
					log.error("", e);
					CommonException.parameterInvalid();
				}
			}
		}
		if(!isOk){
			CommonException.parameterInvalid();
		}
	}
	
	
	private void checkString(Object pvalue, CheckRule rule, boolean isAllowNull)throws MyException{
		
		if(isNull(pvalue)){
			if(isAllowNull){
				return;
			}
			CommonException.parameterInvalid();
		}
		if(!(pvalue instanceof String)){
			CommonException.parameterInvalid();
		}
		String pv = (String) pvalue;
		if(isEmpty(pv)){
			CommonException.parameterInvalid();
		}
		String value = null;
		ParamRule.Rule r = rule.rule();
		if(r.equals(ParamRule.Rule.MAX_LENGTH) || r.equals(ParamRule.Rule.MAX_LENGTH_OR_NULL)){
			if(rule.maxLength() <= -1){
				CommonException.parameterInvalid();
			}
			value = rule.maxLength()+"";
		}else{
			value = getCheckValue(rule);
		}
		if(rule.maxLength() > -1){
			if(pv.length() > rule.maxLength()){
				CommonException.parameterInvalid();
			}
		}
		
		boolean isOk = true;
		switch (r) {
		case CONTAIN:
			isOk = isContain(pv, value);
			break;
		case CONTAIN_OR_NULL:
			isOk = isContain(pv, value);
			break;
		case START_WITH:
			isOk = pv.startsWith(value);
			break;
		case START_WITH_OR_NULL:
			isOk = pv.startsWith(value);
			break;
		case END_WITH:
			isOk = pv.endsWith(value);
			break;
		case END_WITH_OR_NULL:
			isOk = pv.endsWith(value);
			break;
		default:
			break;
		}
		if(!isOk){
			CommonException.parameterInvalid();
		}
	}
	private String getCheckValue(CheckRule rule)throws MyException{
		String value = rule.value();
		if(isNullOrTrimEmpty(value)){
			CommonException.parameterInvalid();
		}
		return value;
	}
	private String[] getCheckValues(CheckRule rule)throws MyException{
		String[] values = rule.values();
		if(isNull(values)){
			CommonException.parameterInvalid();
		}
		if(values.length == 0){
			CommonException.parameterInvalid();
		}
		if(rule.rule().equals(ParamRule.Rule.BETWEEN) || rule.rule().equals(ParamRule.Rule.BETWEEN_EQUAL)){
			if(values.length != 2){
				CommonException.parameterInvalid();
			}
		}
		return values;
	}

	private Object getParameterValue(String logPrefix, CheckRule rule, Map<String, Object> param)throws MyException{
		
		Object obj = param.get(rule.name());

		if(obj == null || obj instanceof String || obj instanceof Double 
				|| obj instanceof Float || obj instanceof Long 
				|| obj instanceof Integer || obj instanceof BigDecimal){
			return obj;
		}

		String fieldName = rule.fieldName();
		if(isNullOrTrimEmpty(fieldName)){//没有配置field name
			if(rule.rule().equals(ParamRule.Rule.NOT_NULL)){//只是判断不为null的话，默认认为是判断参数实体类不为null
				return obj;
			}
			log.info(logPrefix+"参数["+rule.name()+"]未配置fieldName");
			CommonException.parameterInvalid();
		}
		return getReflectValue(logPrefix, rule.name(), obj, fieldName);
		
	}
	
	private Object getReflectValue(String logPrefix, String pname, Object obj, String fieldName)throws MyException{
		
		try {
			Class<?> clazz = obj.getClass();
			Field field = clazz.getDeclaredField(fieldName);
			return field.get(obj);
		} catch (NoSuchFieldException e) {
			log.error(logPrefix+"参数["+pname+"]配置的fieldName在参数对象中未找到", e);
			CommonException.parameterInvalid();
		} catch (SecurityException e) {
			log.error(logPrefix+"参数["+pname+"]配置的fieldName在参数对象中未找到", e);
			CommonException.parameterInvalid();
		} catch (IllegalArgumentException e) {
			log.error(logPrefix+"参数["+pname+"]配置的fieldName在参数对象中未找到", e);
			CommonException.parameterInvalid();
		} catch (IllegalAccessException e) {
			log.error(logPrefix+"参数["+pname+"]配置的fieldName在参数对象中未找到", e);
			CommonException.parameterInvalid();
		}
		return null;
	}
	
	private void checkRuleConfig(String logPrefix, CheckRule rule)throws MyException{
		String pname = rule.name();
		if(isNullOrTrimEmpty(pname)){
			log.info(logPrefix+"参数名称为null");
			CommonException.parameterInvalid();
		}
		if(isNull(rule.rule())){
			log.info(logPrefix+"参数["+pname+"]未配置rule");
			CommonException.parameterInvalid();
		}
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
        Map<String, Object> paramMap = new TreeMap<String, Object>();//TreeMap 忽略Key大小写
        for (int i = 0; i < parameterNames.length; i++) {
            paramMap.put(parameterNames[i], args[i]);
        }
        return paramMap;
    }
    private void printLog(Object pvalue, CheckRule rule, String logPrefix){
		JSONObject json = new JSONObject();
		json.put("paramValue", pvalue);
		json.put("rule", rule.rule);
		log.info(logPrefix+json.toJSONString());
	}
	
    public class CheckRule implements Serializable{
    	
    	/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private ParamRule rule;
    	private String name;
    	
    	CheckRule (String name, ParamRule rule){
    		this.name = name;
    		this.rule = rule;
    	}
    	
    	/**
    	 * 参数名称
    	 * @return
    	 */
    	public String name() {
    		return this.name;
    	}
    	
    	
    	/**
    	 * 参数类型
    	 * <p> ParamRule enum Type中定义
    	 * @return
    	 */
//    	public Type type() {
//    		return this.rule.type();
//    	}
    	
    	/**
    	 * 校验规则
    	 * <p> ParamRule enum Rule中定义
    	 * @return
    	 */
    	public Rule rule() {
    		return this.rule.rule();
    	}
    	
    	/**
    	 * 对象属性名称
    	 * <p> type是object时，参数对象的field name
    	 * @return
    	 */
    	public String fieldName() {
    		return this.rule.fieldName();
    	}
    	
    	/**
    	 * 比较规则的value值
    	 * @return
    	 */
    	public String value() {
    		return this.rule.value();
    	}
    	
    	/**
    	 * 比较规则IS_BETWEEN或IS_IN的value值
    	 * @return
    	 */
    	public String[] values() {
    		return this.rule.values();
    	} 
    	/**
    	 * 规则IS_MATCH的正则表达式
    	 * @return
    	 */
    	public String pattern() {
    		return this.rule.pattern();
    	}
    	
    	/**
    	 * 字符串长度限制
    	 * @return
    	 */
    	public int maxLength() {
    		return this.rule.maxLength();
    	} 
    }
}
