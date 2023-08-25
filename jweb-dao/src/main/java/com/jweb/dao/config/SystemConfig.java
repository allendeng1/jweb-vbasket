package com.jweb.dao.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jweb.common.annotation.param.ParamRule;
import com.jweb.common.annotation.param.ParameterCheck;
import com.jweb.common.exception.MyException;
import com.jweb.common.util.DataUtil;
import com.jweb.common.util.DateTimeUtil;
import com.jweb.dao.SysConfigDao;
import com.jweb.dao.component.RedisComponent;
import com.jweb.dao.entity.SysConfig;

import lombok.extern.slf4j.Slf4j;

/**
 * 系统参数
 * @author 邓超
 *
 * 2022/09/14 下午4:21:51
 */
@Component
@Slf4j
public class SystemConfig extends DataUtil{
	@Autowired
	private SysConfigDao sysConfigDao;
	@Autowired
	private RedisComponent redisComponent;
	
	private static final String KEY = "sysconfig";
	
	/**
	 * 获取String类型系统参数
	 * @param propKey
	 * @return 默认null
	 * @throws MyException
	 */
	public String getString(String propKey) throws MyException{
		return getSysConfig(propKey);
	}
	/**
	 * 获取Integer类型系统参数
	 * @param propKey
	 * @return 默认null
	 * @throws MyException
	 */
	public Integer getInteger(String propKey) throws MyException{
		String value = getSysConfig(propKey);
		if(isNull(value)) {
			return null;
		}
		try {
			return Integer.valueOf(value);
		} catch (NumberFormatException e) {
			log.error("System config [{}] value convert error", propKey, e);
			return null;
		}
	}
	/**
	 * 获取Integer类型系统参数
	 * @param propKey
	 * @param defaultVaule
	 * @return 
	 * @throws MyException
	 */
	public int getInteger(String propKey, int defaultVaule) throws MyException{
		String value = getSysConfig(propKey);
		if(isNull(value)) {
			return defaultVaule;
		}
		try {
			return Integer.valueOf(value);
		} catch (NumberFormatException e) {
			log.error("System config [{}] value convert error", propKey, e);
			return defaultVaule;
		}
	}
	/**
	 * 获取boolean类型系统参数
	 * @param propKey
	 * @return 默认false
	 * @throws MyException
	 */
	public boolean getBoolean(String propKey) throws MyException{
		String value = getSysConfig(propKey);
		if(isNull(value)) {
			return false;
		}
		try {
			return Boolean.valueOf(value);
		} catch (NumberFormatException e) {
			log.error("System config [{}] value convert error", propKey, e);
			return false;
		}
	}
	/**
	 * 获取Double类型系统参数
	 * @param propKey
	 * @return 默认null
	 * @throws MyException
	 */
	public Double getDouble(String propKey) throws MyException{
		String value = getSysConfig(propKey);
		if(isNull(value)) {
			return null;
		}
		try {
			return Double.valueOf(value);
		} catch (NumberFormatException e) {
			log.error("System config [{}] value convert error", propKey, e);
			return null;
		}
	}
	/**
	 * 获取Double类型系统参数
	 * @param propKey
	 * @param defaultVaule
	 * @return 
	 * @throws MyException
	 */
	public double getDouble(String propKey, double defaultVaule) throws MyException{
		String value = getSysConfig(propKey);
		if(isNull(value)) {
			return defaultVaule;
		}
		try {
			return Double.valueOf(value);
		} catch (NumberFormatException e) {
			log.error("System config [{}] value convert error", propKey, e);
			return defaultVaule;
		}
	}
	
	@ParameterCheck({
		@ParamRule(name="value,remark", rule=ParamRule.Rule.NOT_NULL),
		@ParamRule(name="propKey", rule=ParamRule.Rule.MATCH, pattern = "^[a-z0-9]+(\\.{1}[a-z0-9]+)+")
	})
	public void save(String propKey, String value, String remark) throws MyException{
		SysConfig query = new SysConfig();
		query.setPropKey(propKey);
		SysConfig conf = sysConfigDao.selectOneByExample(query);
		if(isNotNull(conf)) {
			query.setId(conf.getId());
		}else {
			query.setCtdate(DateTimeUtil.nowTime());
		}
		query.setMddate(DateTimeUtil.nowTime());
		query.setPropValue(value);
		query.setStatus(0);
		query.setRemark(remark);
		sysConfigDao.insert(query);
		
		redisComponent.setMap(KEY, propKey, value);
	}
	
	protected String getSysConfig(String propKey)throws MyException{
		
		Object obj = redisComponent.getMapValue(KEY, propKey);
		if(isNotNull(obj)) {
			return obj.toString();
		}
		SysConfig query = new SysConfig();
		query.setStatus(0);
		query.setPropKey(propKey);
		SysConfig conf = sysConfigDao.selectOneByExample(query);
		if(isNull(conf)) {
			return null;
		}
		String value = conf.getPropValue();
		if(isNullOrTrimEmpty(value)) {
			return null;
		}
		redisComponent.setMap(KEY, propKey, value);
		return value;
	}
}
