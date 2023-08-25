package com.jweb.common.util;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jweb.common.exception.CommonException;
import com.jweb.common.exception.MyException;

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
@Slf4j
@Component
public class LocaleUtil extends DataUtil{
	
	private static Country country;
	@Value("${locale.country}")
	public void setCountry(Country country){
		LocaleUtil.country = country;
	}
	
	private static String language;
	@Value("${locale.language:}")//"冒号表示：配置文件中可以不配置"
	public void setLanguage(String language){
		LocaleUtil.language = language;
	}
	
	private static String timeZoneId;
	@Value("${locale.time.zoneId:}")
	public void setTimeZoneId(String timeZoneId)throws MyException{
		if(isNotTrimEmpty(timeZoneId)){
			if(!isMatch(timeZoneId, RegularMatchUtil.Patter.TIME_ZONE_ID)){
				CommonException.parameterInvalid();
			}
			int timeEquationUTC = 0;
			if(isContain(timeZoneId, "-")){
				timeEquationUTC = parseint(timeZoneId.split("-")[1]);
			}else if(isContain(timeZoneId, "+")){
				timeEquationUTC = parseint(timeZoneId.split("+")[1]);
			}
			if(timeEquationUTC > 12){
				CommonException.parameterInvalid();
			}
		}
		LocaleUtil.timeZoneId = timeZoneId;
	}
	
	private static Locale locale;

	public static Country getCountry() {
		return country;
	}
	public static TimeZone getTimeZone() {
		TimeZone tz = country.getTimeZone();
		if(isNullOrTrimEmpty(timeZoneId)){
			timeZoneId = tz.getZoneId();
		}else{
			if(!tz.getZoneId().equalsIgnoreCase(timeZoneId)){
				log.warn("配置文件中时区与国家不匹配");
			}
		}
		return tz;
	}

	public static String getLanguage() {
		language = isNullOrTrimEmpty(language) ? country.getNativeLanguage() : language;
		return language;
	}

	public static String getTimeZoneId() {
		TimeZone tz = country.getTimeZone();
		if(isNullOrTrimEmpty(timeZoneId)){
			timeZoneId = tz.getZoneId();
		}else{
			if(!tz.getZoneId().equalsIgnoreCase(timeZoneId)){
				log.warn("配置文件中时区与国家不匹配");
			}
		}
		return timeZoneId;
	}
	public static Locale getLocale(){
		if(isNull(locale)){
			locale = new Locale(getLanguage());
		}
		return locale;
	}
	public enum Country{
		
		China("Chinese", "中国", TimeZone.EAST_8, "86", 11);
	
		Country(String nativeLanguage, String chineseName, TimeZone timeZone, String mobileCode, int mobileLength){
			this.nativeLanguage = nativeLanguage;
			this.chineseName = chineseName;
			this.timeZone = timeZone;
		}
		
		private String nativeLanguage;
		private String chineseName;
		private TimeZone timeZone;
		private String mobileCode;
		private int mobileLength;
		public String getNativeLanguage() {
			return nativeLanguage;
		}
		public void setNativeLanguage(String nativeLanguage) {
			this.nativeLanguage = nativeLanguage;
		}
		public TimeZone getTimeZone() {
			return timeZone;
		}
		public void setTimeZone(TimeZone timeZone) {
			this.timeZone = timeZone;
		}
		public String getChineseName() {
			return chineseName;
		}
		public void setChineseName(String chineseName) {
			this.chineseName = chineseName;
		}
		public String getMobileCode() {
			return mobileCode;
		}
		public void setMobileCode(String mobileCode) {
			this.mobileCode = mobileCode;
		}
		public int getMobileLength() {
			return mobileLength;
		}
		public void setMobileLength(int mobileLength) {
			this.mobileLength = mobileLength;
		}
		
	}
	public enum TimeZone{
		WEST_11("西十一区","UTC-11", "-11"),
		WEST_10("西十区","UTC-10", "-10"),
		WEST_9("西九区","UTC-9", "-9"),
		WEST_8("西八区","UTC-8", "-8"),
		WEST_7("西七区","UTC-7", "-7"),
		WEST_6("西六区","UTC-6", "-6"),
		WEST_5("西五区","UTC-5", "-5"),
		WEST_4("西四区","UTC-4", "-4"),
		WEST_3("西三区","UTC-3", "-3"),
		WEST_2("西二区","UTC-2", "-2"),
		WEST_1("西一区","UTC-1", "-1"),
		ZERO("中时区","UTC+0", "+0"),
		EAST_1("东一区","UTC+1", "+1"),
		EAST_2("东二区","UTC+2", "+2"),
		EAST_3("东三区","UTC+3", "+3"),
		EAST_4("东四区","UTC+4", "+4"),
		EAST_5("东五区","UTC+5", "+5"),
		EAST_6("东六区","UTC+6", "+6"),
		EAST_7("东七区","UTC+7", "+7"),
		EAST_8("东八区","UTC+8", "+8"),
		EAST_9("东九区","UTC+9", "+9"),
		EAST_10("东十区","UTC+10", "+10"),
		EAST_11("东十一区","UTC+11", "+11"),
		EAST_12("东十二区","UTC+12", "+12");
		
		TimeZone(String name, String zoneId, String timeEquationUTC){
			this.name = name;
			this.zoneId = zoneId;
			this.timeEquationUTC = timeEquationUTC;
		}
		
		private String name;
		private String zoneId;
		private String timeEquationUTC;
		public String getName() {
			return name;
		}
	
		public String getZoneId() {
			return zoneId;
		}
		
		public String getTimeEquationUTC() {
			return timeEquationUTC;
		}
	
	}
}
