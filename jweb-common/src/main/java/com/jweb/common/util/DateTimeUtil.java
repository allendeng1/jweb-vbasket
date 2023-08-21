package com.jweb.common.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.jweb.common.exception.CommonException;
import com.jweb.common.exception.MyException;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
@Component
public class DateTimeUtil extends DataUtil{
	
	private static String zoneId;
	
	private static String timeEquationUTC;
	
	/**
	 * 当前时间毫秒数
	 * @return
	 */
	public static long nowTime(){
		getLocaleTimeZone();
		return LocalDateTime.now(ZoneId.of(zoneId)).toInstant(ZoneOffset.of(timeEquationUTC)).toEpochMilli();
	}
	/**
	 * 当天起始时间毫秒数
	 * @return
	 */
	public static long currentDayStartTime(){
		return dayStartTime(nowTime());
	}
	/**
	 * 当天结束时间毫秒数
	 * @return
	 */
	public static long currentDayEndTime(){
		return dayEndTime(nowTime());
	}
	/**
	 * 当月起始时间毫秒数
	 * @return
	 */
	public static long currentMonthStartTime(){
		return monthStartTime(nowTime());
	}
	/**
	 * 当月结束时间毫秒数
	 * @return
	 */
	public static long currentMonthEndTime(){
		return monthEndTime(nowTime());
	}
	/**
	 * 当年起始时间毫秒数
	 * @return
	 */
	public static long currentYearStartTime(){
		return yearStartTime(nowTime());
	}
	/**
	 * 当年结束时间毫秒数
	 * @return
	 */
	public static long currentYearEndTime(){
		return yearEndTime(nowTime());
	}
	/**
	 * millisecond 转 string
	 * @param millisecond
	 * @param pattern
	 * @return
	 * @throws MyException
	 */
	public static String timeToString(long millisecond, Format pattern)throws MyException{
		if(isNull(pattern)){
			CommonException.parameterLost();
		}
		getLocaleTimeZone();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern.getFormat());
		LocalDateTime ldt = LocalDateTime.ofInstant(Instant.ofEpochMilli(millisecond), ZoneId.of(zoneId));
		
		return ldt.format(dtf);
	}
	/**
	 * string 转  millisecond
	 * @param dataTime 可以为null或""
	 * @param pattern
	 * @return
	 * @throws MyException
	 */
	public static Long stringToTime(String dataTime, Format pattern)throws MyException{
		if(isNull(pattern)){
			CommonException.parameterLost();
		}
		if(isNullOrTrimEmpty(dataTime)){
			return null;
		}
		getLocaleTimeZone();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern.getFormat());
		LocalDateTime ldt = LocalDateTime.parse(dataTime, dtf);
		return ldt.toInstant(ZoneOffset.of(timeEquationUTC)).toEpochMilli();
	}
	/**
	 * millisecond 转 date
	 * @param millisecond
	 * @return
	 */
	public static Date timeToDate(long millisecond){
		LocalDateTime ldt = LocalDateTime.ofInstant(Instant.ofEpochMilli(millisecond), ZoneId.of(zoneId));
		return Date.from(ldt.toInstant(ZoneOffset.of(timeEquationUTC)));
	}
	/**
	 * millisecond 转 LocalDateTime
	 * @param millisecond
	 * @return
	 */
	public static LocalDateTime timeToLocalDateTime(long millisecond){
		LocalDateTime ldt = LocalDateTime.ofInstant(Instant.ofEpochMilli(millisecond), ZoneId.of(zoneId));
		return ldt;
	}
	/**
	 * 天起点时刻(00:00:00) millisecond
	 * @param millisecond 可以为null
	 * @return
	 */
	public static Long dayStartTime(Long millisecond){
		if(isNull(millisecond)){
			return null;
		}
		getLocaleTimeZone();
		LocalDate localDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(millisecond), ZoneId.of(zoneId)).toLocalDate();
		return LocalDateTime.of(localDate, LocalTime.MIN).toInstant(ZoneOffset.of(timeEquationUTC)).toEpochMilli();
	}
	/**
	 * 天终点时刻(23:59:59) millisecond
	 * @param millisecond 可以为null
	 * @return
	 */
	public static Long dayEndTime(Long millisecond){
		if(isNull(millisecond)){
			return null;
		}
		getLocaleTimeZone();
		LocalDate localDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(millisecond), ZoneId.of(zoneId)).toLocalDate();
		return LocalDateTime.of(localDate, LocalTime.MAX).toInstant(ZoneOffset.of(timeEquationUTC)).toEpochMilli();
	}
	
	/**
	 * 月起点时刻(00:00:00) millisecond
	 * @param millisecond 可以为null
	 * @return
	 */
	public static Long monthStartTime(Long millisecond){
		millisecond = dayStartTime(millisecond);
		if(isNull(millisecond)){
			return null;
		}
		LocalDateTime ldt = LocalDateTime.ofInstant(Instant.ofEpochMilli(millisecond), ZoneId.of(zoneId));
		LocalDateTime date = ldt.with(TemporalAdjusters.firstDayOfMonth());
		return date.toInstant(ZoneOffset.of(timeEquationUTC)).toEpochMilli();
	}
	/**
	 * 月终点时刻(23:59:59) millisecond
	 * @param millisecond 可以为null
	 * @return
	 */
	public static Long monthEndTime(Long millisecond){
		millisecond = dayEndTime(millisecond);
		if(isNull(millisecond)){
			return null;
		}
		LocalDateTime ldt = LocalDateTime.ofInstant(Instant.ofEpochMilli(millisecond), ZoneId.of(zoneId));
		LocalDateTime date = ldt.with(TemporalAdjusters.lastDayOfMonth());
		return date.toInstant(ZoneOffset.of(timeEquationUTC)).toEpochMilli();
	}
	/**
	 * 年起点时刻(00:00:00) millisecond
	 * @param millisecond 可以为null
	 * @return
	 */
	public static Long yearStartTime(Long millisecond){
		millisecond = dayStartTime(millisecond);
		if(isNull(millisecond)){
			return null;
		}
		LocalDateTime ldt = LocalDateTime.ofInstant(Instant.ofEpochMilli(millisecond), ZoneId.of(zoneId));
		LocalDateTime date = ldt.with(TemporalAdjusters.firstDayOfYear());
		return date.toInstant(ZoneOffset.of(timeEquationUTC)).toEpochMilli();
	}
	/**
	 * 年终点时刻(23:59:59) millisecond
	 * @param millisecond 可以为null
	 * @return
	 */
	public static Long yearEndTime(Long millisecond){
		millisecond = dayEndTime(millisecond);
		if(isNull(millisecond)){
			return null;
		}
		LocalDateTime ldt = LocalDateTime.ofInstant(Instant.ofEpochMilli(millisecond), ZoneId.of(zoneId));
		LocalDateTime date = ldt.with(TemporalAdjusters.lastDayOfYear());
		return date.toInstant(ZoneOffset.of(timeEquationUTC)).toEpochMilli();
	}
	
	/**
	 * 几天前
	 * @param millisecond
	 * @param days 天数
	 * @return
	 */
	public static Long beforeDays(long millisecond, long days){
		LocalDateTime ldt = LocalDateTime.ofInstant(Instant.ofEpochMilli(millisecond), ZoneId.of(zoneId));
		LocalDateTime date = ldt.minusDays(days);
		return date.toInstant(ZoneOffset.of(timeEquationUTC)).toEpochMilli();
	}
	/**
	 * 几天前
	 * @param days 天数
	 * @return
	 */
	public static Long beforeDays(long days){
		return beforeDays(nowTime(), days);
	}
	
	/**
	 * 几周前
	 * @param millisecond
	 * @param weeks 周数
	 * @return
	 */
	public static Long beforeWeeks(long millisecond, long weeks){
		LocalDateTime ldt = LocalDateTime.ofInstant(Instant.ofEpochMilli(millisecond), ZoneId.of(zoneId));
		LocalDateTime date = ldt.minusWeeks(weeks);
		return date.toInstant(ZoneOffset.of(timeEquationUTC)).toEpochMilli();
	}
	/**
	 * 几周前
	 * @param weeks 周数
	 * @return
	 */
	public static Long beforeWeeks(long weeks){
		return beforeWeeks(nowTime(), weeks);
	}
	/**
	 * 几月前
	 * @param millisecond
	 * @param months 月数
	 * @return
	 */
	public static Long beforeMonths(long millisecond, long months){
		LocalDateTime ldt = LocalDateTime.ofInstant(Instant.ofEpochMilli(millisecond), ZoneId.of(zoneId));
		LocalDateTime date = ldt.minusMonths(months);
		return date.toInstant(ZoneOffset.of(timeEquationUTC)).toEpochMilli();
	}
	/**
	 * 几月前
	 * @param months 月数
	 * @return
	 */
	public static Long beforeMonths(long months){
		return beforeMonths(nowTime(), months);
	}
	/**
	 * 几年前
	 * @param millisecond
	 * @param years 年数
	 * @return
	 */
	public static Long beforeYears(long millisecond, long years){
		LocalDateTime ldt = LocalDateTime.ofInstant(Instant.ofEpochMilli(millisecond), ZoneId.of(zoneId));
		LocalDateTime date = ldt.minusYears(years);
		return date.toInstant(ZoneOffset.of(timeEquationUTC)).toEpochMilli();
	}
	/**
	 * 几年前
	 * @param years 年数
	 * @return
	 */
	public static Long beforeYears(long years){
		return beforeYears(nowTime(), years);
	}
	/**
	 * 几小时后
	 * @param millisecond
	 * @param hours 天数
	 * @return
	 */
	public static Long afterHours(long millisecond, long hours){
		LocalDateTime ldt = LocalDateTime.ofInstant(Instant.ofEpochMilli(millisecond), ZoneId.of(zoneId));
		LocalDateTime date = ldt.plusHours(hours);
		return date.toInstant(ZoneOffset.of(timeEquationUTC)).toEpochMilli();
	}
	/**
	 * 几小时后
	 * @param hours 天数
	 * @return
	 */
	public static Long afterHours(long hours){
		return afterHours(nowTime(), hours);
	}
	/**
	 * 几天后
	 * @param millisecond
	 * @param days 天数
	 * @return
	 */
	public static Long afterDays(long millisecond, long days){
		LocalDateTime ldt = LocalDateTime.ofInstant(Instant.ofEpochMilli(millisecond), ZoneId.of(zoneId));
		LocalDateTime date = ldt.plusDays(days);
		return date.toInstant(ZoneOffset.of(timeEquationUTC)).toEpochMilli();
	}
	/**
	 * 几天后
	 * @param days 天数
	 * @return
	 */
	public static Long afterDays(long days){
		return afterDays(nowTime(), days);
	}
	
	/**
	 * 几周后
	 * @param millisecond
	 * @param weeks 周数
	 * @return
	 */
	public static Long afterWeeks(long millisecond, long weeks){
		LocalDateTime ldt = LocalDateTime.ofInstant(Instant.ofEpochMilli(millisecond), ZoneId.of(zoneId));
		LocalDateTime date = ldt.plusWeeks(weeks);
		return date.toInstant(ZoneOffset.of(timeEquationUTC)).toEpochMilli();
	}
	/**
	 * 几周后
	 * @param weeks 周数
	 * @return
	 */
	public static Long afterWeeks(long weeks){
		return afterWeeks(nowTime(), weeks);
	}
	/**
	 * 几月后
	 * @param millisecond
	 * @param months 月数
	 * @return
	 */
	public static Long afterMonths(long millisecond, long months){
		LocalDateTime ldt = LocalDateTime.ofInstant(Instant.ofEpochMilli(millisecond), ZoneId.of(zoneId));
		LocalDateTime date = ldt.plusMonths(months);
		return date.toInstant(ZoneOffset.of(timeEquationUTC)).toEpochMilli();
	}
	/**
	 * 几月后
	 * @param months 月数
	 * @return
	 */
	public static Long afterMonths(long months){
		return afterMonths(nowTime(), months);
	}
	/**
	 * 几年后
	 * @param millisecond
	 * @param years 年数
	 * @return
	 */
	public static Long afterYears(long millisecond, long years){
		LocalDateTime ldt = LocalDateTime.ofInstant(Instant.ofEpochMilli(millisecond), ZoneId.of(zoneId));
		LocalDateTime date = ldt.plusYears(years);
		return date.toInstant(ZoneOffset.of(timeEquationUTC)).toEpochMilli();
	}
	/**
	 * 几年后
	 * @param millisecond
	 * @param years 年数
	 * @return
	 */
	public static Long afterYears(long years){
		return afterYears(nowTime(), years);
	}
	/**
	 * 时间信息
	 * @param millisecond
	 * @return int[] 0=年   1=月   2=日   3=星期几   4=当前月天数  5=当前年天数
	 */
	public static int[] dateInfo(long millisecond){
		getLocaleTimeZone();
		LocalDate localDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(millisecond), ZoneId.of(zoneId)).toLocalDate();
		int[] ymd = new int[6];
		ymd[0] = localDate.getYear();
		ymd[1] = localDate.getMonthValue();
		ymd[2] = localDate.getDayOfMonth();
		ymd[3] = localDate.getDayOfWeek().getValue();
		ymd[4] = localDate.lengthOfMonth();
		ymd[5] = localDate.lengthOfYear();
		return ymd;
   }
	
	private static void getLocaleTimeZone(){
		if(isNullOrTrimEmpty(zoneId) || isNullOrTrimEmpty(timeEquationUTC)){
			zoneId = LocaleUtil.getTimeZone().getZoneId();
			timeEquationUTC = LocaleUtil.getTimeZone().getTimeEquationUTC();
		}
	}

	public static enum Format{
		/**
		 * 年-月-日 时:分:秒
		 */
		YYYYMMDDHHMMSS_1("yyyy-MM-dd HH:mm:ss"),
		/**
		 * 年/月/日 时:分:秒
		 */
		YYYYMMDDHHMMSS_2("yyyy/MM/dd HH:mm:ss"),
		/**
		 * 年.月.日 时:分:秒
		 */
		YYYYMMDDHHMMSS_3("yyyy.MM.dd HH:mm:ss"),
		/**
		 * 年月日时分秒
		 */
		YYYYMMDDHHMMSS_4("yyyyMMddHHmmss"),
		/**
		 * 日-月-年 时:分:秒
		 */
		DDMMYYYYHHMMSS_1("dd-MM-yyyy HH:mm:ss"),
		/**
		 * 日/月/年 时:分:秒
		 */
		DDMMYYYYHHMMSS_2("dd/MM/yyyy HH:mm:ss"),
		/**
		 * 日.月.年 时:分:秒
		 */
		DDMMYYYYHHMMSS_3("dd.MM.yyyy HH:mm:ss"),
		
		/**
		 * 年-月-日
		 */
		YYYYMMDD_1("yyyy-MM-dd"),
		/**
		 * 年/月/日
		 */
		YYYYMMDD_2("yyyy/MM/dd"),
		/**
		 * 年.月.日
		 */
		YYYYMMDD_3("yyyy.MM.dd"),
		/**
		 * 年月日
		 */
		YYYYMMDD_4("yyyyMMdd"),
		/**
		 * 日-月-年
		 */
		DDMMYYYY_1("dd-MM-yyyy"),
		/**
		 * 日/月/年
		 */
		DDMMYYYY_2("dd/MM/yyyy"),
		/**
		 * 日.月.年
		 */
		DDMMYYYY_3("dd.MM.yyyy"),
		
		/**
		 * 时:分:秒
		 */
		HHMMSS("HH:mm:ss");
		

		private String format;
		Format(String format){
			this.format = format;
		}
		
		public String getFormat() {
			return format;
		}
		
		
	}
}
