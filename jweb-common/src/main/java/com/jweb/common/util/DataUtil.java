package com.jweb.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jweb.common.exception.CommonException;
import com.jweb.common.exception.MyException;

/**
 * 数据校验
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
public class DataUtil extends RegularMatchUtil{
	/**
	 * 字符串为""
	 * @param s  s != null
	 * @return
	 */
	public static boolean isEmpty(String s){
		return s != null && "".equals(s);
	}
	/**
	 * 字符串为""，去掉所有\s+
	 * @param s s != null
	 * @return
	 */
	public static boolean isTrimEmpty(String s){
		return s != null && "".equals(s.replaceAll("\\s+", ""));
	}
	/**
	 * 字符串为null或者""
	 * @param s
	 * @return
	 */
	public static boolean isNullOrEmpty(String s){
		return s == null || "".equals(s);
	}
	/**
	 * 字符串为null或者""，去掉所有\s+
	 * @param s
	 * @return
	 */
	public static boolean isNullOrTrimEmpty(String s){
		return s == null || "".equals(s.replaceAll("\\s+", ""));
	}
	/**
	 * 字符串不为""
	 * @param s  s != null
	 * @return
	 */
	public static boolean isNotEmpty(String s){
		return s != null && !"".equals(s);
	}
	/**
	 * 字符串不为""，去掉所有\s+
	 * @param s s != null
	 * @return
	 */
	public static boolean isNotTrimEmpty(String s){
		return s != null && !"".equals(s.replaceAll("\\s+", ""));
	}
	/**
	 * 对象为null
	 * @param o
	 * @return
	 */
	public static boolean isNull(Object o){
		return o == null;
	}
	/**
	 * 对象不为null
	 * @param o
	 * @return
	 */
	public static boolean isNotNull(Object o){
		return o != null;
	}
	/**
	 * List为null，或size == 0
	 * @return
	 */
	public static boolean isNullOrEmpty(List<?> list){
		return isNull(list) || list.isEmpty();
	}
	/**
	 * Object[]为null，或length == 0
	 * @return
	 */
	public static boolean isNullOrEmpty(Object[] objs){
		return isNull(objs) || objs.length == 0;
	}
	/**
	 * 包含字符串（区分大小写）
	 * @param source
	 * @param c
	 * @return
	 */
	public static boolean isContain(String source, String c){
		return isNotNull(source) && isNotNull(c) && source.contains(c);
	}
	/**
	 * 包含字符串（不区分大小写）
	 * @param source
	 * @param c
	 * @return
	 */
	public static boolean isContainIgnoreCase(String source, String c){
		return isNotNull(source) && isNotNull(c) && source.toLowerCase().contains(c.toLowerCase());
	}
	/**
	 * 不包含字符串（区分大小写）
	 * @param source
	 * @param c
	 * @return
	 */
	public static boolean isNotContain(String source, String c){
		if(isNullOrTrimEmpty(source)) {
			return true;
		}
		return !isContain(source, c);
	}
	/**
	 * 不包含字符串（不区分大小写）
	 * @param source
	 * @param c
	 * @return
	 */
	public static boolean isNotContainIgnoreCase(String source, String c){
		if(isNullOrTrimEmpty(source)) {
			return true;
		}
		return !isContainIgnoreCase(source, c);
	}
	/**
	 * String 转 Integer
	 * @param s 可以为null
	 * @return
	 * @throws MyException
	 */
	public static Integer parseInteger(String s)throws MyException{
		if(isNull(s)){
			return null;
		}
		if(isTrimEmpty(s)){
			CommonException.parameterLost();
		}
		return Integer.valueOf(s);
	}
	/**
	 * String 转 int
	 * @param s 不可以为null或""
	 * @return
	 * @throws MyException
	 */
	public static int parseint(String s)throws MyException{
		if(isNullOrTrimEmpty(s)){
			CommonException.parameterLost();
		}
		return Integer.parseInt(s);
	}
	/**
	 * String 转 Double
	 * @param s 可以为null
	 * @return
	 * @throws MyException
	 */
	public static Double parseDouble(String s)throws MyException{
		if(isNull(s)){
			return null;
		}
		if(isTrimEmpty(s)){
			CommonException.parameterLost();
		}
		return Double.valueOf(s);
	}
	/**
	 * String 转 double
	 * @param s 不可以为null或""
	 * @return
	 * @throws MyException
	 */
	public static double parsedouble(String s)throws MyException{
		if(isNullOrTrimEmpty(s)){
			CommonException.parameterLost();
		}
		return Double.parseDouble(s);
	}
	/**
	 * String 转 Float
	 * @param s 可以为null
	 * @return
	 * @throws MyException
	 */
	public static Float parseFloat(String s)throws MyException{
		if(isNull(s)){
			return null;
		}
		if(isTrimEmpty(s)){
			CommonException.parameterLost();
		}
		return Float.valueOf(s);
	}
	/**
	 * String 转 float
	 * @param s 不可以为null或""
	 * @return
	 * @throws MyException
	 */
	public static float parsefloat(String s)throws MyException{
		if(isNullOrTrimEmpty(s)){
			CommonException.parameterLost();
		}
		return Float.parseFloat(s);
	}
	/**
	 * 四舍五入
	 * @param d 可以为null
	 * @param decimal 保留的小数位数
	 * @return
	 * @throws MyException
	 */
	public static Double preserveByRound(Double d, int decimal)throws MyException{
		if(isNull(d)){
			return null;
		}
		BigDecimal b = new BigDecimal(d);
		return b.setScale(decimal, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	/**
	 * 四舍五入
	 * @param s 可以为null
	 * @param decimal 保留的小数位数
	 * @return
	 * @throws MyException
	 */
	public static Double preserveByRound(String s, int decimal)throws MyException{
		if(isNull(s)){
			return null;
		}
		BigDecimal b = new BigDecimal(parseDouble(s));
		return b.setScale(decimal, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	/**
	 * 清除多余小数位
	 * @param d 可以为null
	 * @param decimal 保留的小数位数
	 * @return
	 * @throws MyException
	 */
	public static Double preserveByClear(Double d, int decimal){
		if(isNull(d)){
			return null;
		}
		BigDecimal b = new BigDecimal(d);
		return b.setScale(decimal, BigDecimal.ROUND_DOWN).doubleValue();
	}
	/**
	 * 清除多余小数位
	 * @param s 可以为null
	 * @param decimal 保留的小数位数
	 * @return
	 * @throws MyException
	 */
	public static Double preserveByClear(String s, int decimal)throws MyException{
		if(isNull(s)){
			return null;
		}
		BigDecimal b = new BigDecimal(parseDouble(s));
		return b.setScale(decimal, BigDecimal.ROUND_DOWN).doubleValue();
	}
	/**
	 * 格式化Double
	 * @param d 可以为null
	 * @param format 例：#.00、#0.00、.00、0.00、0、00
	 * @return
	 */
	public static String formatDouble(Double d, String format){
		if(isNull(d)){
			return null;
		}
		DecimalFormat df = new DecimalFormat(format);
		return df.format(d);
	}
	/**
	 * 格式化Double
	 * @param s 可以为null
	 * @param format 例：#.00、#0.00、.00、0.00、0、00
	 * @return
	 */
	public static String formatDouble(String s, String format)throws MyException{
		if(isNull(s)){
			return null;
		}
		Double d = parseDouble(s);
		DecimalFormat df = new DecimalFormat(format);
		return df.format(d);
	}
	
	/**
	 * 两个Integer相等
	 * @param inta
	 * @param intb
	 * @return
	 */
	public static boolean isEqual(Integer inta, Integer intb){
		if(isNull(inta) && isNull(intb)){
			return true;
		}
		if(isNotNull(inta) && isNotNull(intb)){
			return inta.intValue() == intb.intValue();
		}
		return false;
	}
	/**
	 * 两个Long相等
	 * @param longa
	 * @param longb
	 * @return
	 */
	public static boolean isEqual(Long longa, Long longb){
		if(isNull(longa) && isNull(longb)){
			return true;
		}
		if(isNotNull(longa) && isNotNull(longb)){
			return longa.longValue() == longb.longValue();
		}
		return false;
	}
	/**
	 * 两个Double相等(精度四舍五入)
	 * @param doublea
	 * @param doubleb
	 * @param decimal 精度：-1-忽略精度，1-一位小数相等，2-二位小数相等.......
	 * @return
	 */
	public static boolean isEqual(Double doublea, Double doubleb, int decimal){
		if(isNull(doublea) && isNull(doubleb)){
			return true;
		}
		if(isNotNull(doublea) && isNotNull(doubleb)){
			if(decimal == -1){
				return doublea.doubleValue() == doubleb.doubleValue();
			}else{
				double a = new BigDecimal(doublea).setScale(decimal, BigDecimal.ROUND_HALF_UP).doubleValue();
				double b = new BigDecimal(doubleb).setScale(decimal, BigDecimal.ROUND_HALF_UP).doubleValue();
				return a == b;
			}
			
		}
		return false;
	}
	/**
	 * 两个Double相等(精度丢弃多余小数位)
	 * @param doublea
	 * @param doubleb
	 * @param decimal 精度：-1-忽略精度，1-一位小数相等，2-二位小数相等.......
	 * @return
	 */
	public static boolean isEqualClear(Double doublea, Double doubleb, int decimal){
		if(isNull(doublea) && isNull(doubleb)){
			return true;
		}
		if(isNotNull(doublea) && isNotNull(doubleb)){
			if(decimal == -1){
				return doublea.doubleValue() == doubleb.doubleValue();
			}else{
				double a = new BigDecimal(doublea).setScale(decimal, BigDecimal.ROUND_DOWN).doubleValue();
				double b = new BigDecimal(doubleb).setScale(decimal, BigDecimal.ROUND_DOWN).doubleValue();
				return a == b;
			}
			
		}
		return false;
	}
	/**
	 * 两个Float相等(精度四舍五入)
	 * @param floata
	 * @param floatb
	 * @param decimal 精度：-1-忽略精度，1-一位小数相等，2-二位小数相等.......
	 * @return
	 */
	public static boolean isEqual(Float floata, Float floatb, int decimal){
		if(isNull(floata) && isNull(floatb)){
			return true;
		}
		if(isNotNull(floata) && isNotNull(floatb)){
			if(decimal == -1){
				return floata.floatValue() == floatb.floatValue();
			}else{
				float a = new BigDecimal(floata).setScale(decimal, BigDecimal.ROUND_HALF_UP).floatValue();
				float b = new BigDecimal(floatb).setScale(decimal, BigDecimal.ROUND_HALF_UP).floatValue();
				return a == b;
			}
			
		}
		return false;
	}
	/**
	 * 两个Float相等(精度丢弃多余小数位)
	 * @param floata
	 * @param floatb
	 * @param decimal 精度：-1-忽略精度，1-一位小数相等，2-二位小数相等.......
	 * @return
	 */
	public static boolean isEqualClear(Float floata, Float floatb, int decimal){
		if(isNull(floata) && isNull(floatb)){
			return true;
		}
		if(isNotNull(floata) && isNotNull(floatb)){
			if(decimal == -1){
				return floata.floatValue() == floatb.floatValue();
			}else{
				float a = new BigDecimal(floata).setScale(decimal, BigDecimal.ROUND_DOWN).floatValue();
				float b = new BigDecimal(floatb).setScale(decimal, BigDecimal.ROUND_DOWN).floatValue();
				return a == b;
			}
			
		}
		return false;
	}
	/**
	 * 两个String相等(区分大小写)
	 * @param stra
	 * @param strb
	 * @return
	 */
	public static boolean isEqual(String stra, String strb){
		if(isNull(stra) && isNull(strb)){
			return true;
		}
		if(isNotNull(stra) && isNotNull(strb)){
			return stra.equals(strb);
		}
		return false;
	}
	/**
	 * 两个String相等(不区分大小写)
	 * @param stra
	 * @param strb
	 * @return
	 */
	public static boolean isEqualIgnoreCase(String stra, String strb){
		if(isNull(stra) && isNull(strb)){
			return true;
		}
		if(isNotNull(stra) && isNotNull(strb)){
			return stra.equalsIgnoreCase(strb);
		}
		return false;
	}
	/**
	 * String convert to jsonObject
	 * @param json
	 * @return
	 */
	public static JSONObject toJsonObject(String json) {
		if(isNullOrTrimEmpty(json)) {
			return null;
		}
		return JSONObject.parseObject(json);
	}
	
	/**
	 * String convert to jsonArray
	 * @param json
	 * @return
	 */
	public static JSONArray toJsonArray(String json) {
		if(isNullOrTrimEmpty(json)) {
			return null;
		}
		return JSONObject.parseArray(json);
	}
	/**
	 * Object convert to jsonString
	 * @param o
	 * @return
	 */
	public static String toJsonString(Object o) {
		if(isNull(o)) {
			return null;
		}
		if(o instanceof List) {
			return JSONArray.toJSONString(o);
		}
		return JSONObject.toJSONString(o);
	}
	
	/**
	 * 拼接 http url
	 * @param domainPrefix
	 * @param url
	 * @return
	 */
	public static String spliceUrl(String domainPrefix, String url) {
		if(isNullOrTrimEmpty(domainPrefix)) {
			return url;
		}
		if(isNullOrTrimEmpty(url)) {
			return domainPrefix;
		}
		String spliceUrl = null;
		if(domainPrefix.startsWith("http:")) {
			spliceUrl = domainPrefix;
		}else {
			spliceUrl = "http://" + domainPrefix;
		}
		if(spliceUrl.endsWith("/")) {
			if(url.startsWith("/")) {
				url = url.substring(1, url.length());
			}
		}else {
			if(!url.startsWith("/")) {
				url = "/" + url;
			}
		}
		return spliceUrl + url;
	}
}
