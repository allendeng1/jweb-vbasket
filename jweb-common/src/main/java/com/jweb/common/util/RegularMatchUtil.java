package com.jweb.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jweb.common.exception.CommonException;
import com.jweb.common.exception.MyException;

/**
 * 正则匹配
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
public class RegularMatchUtil {
	
	public static enum Patter{
		/**
		 * locale.time.zoneId配置格式
		 */
		TIME_ZONE_ID("((?i)utc){1}(-|\\+){1}[0-9]{1,2}"),
		/**
		 * 纯数字
		 */
		NUMBER("[0-9]+"),
		/**
		 * 数字以逗号分隔
		 */
		NUMBER_SPLIT_BY_COMMA("([0-9]+,{1}[0-9]+)+"),
		/**
		 * 纯字母
		 */
		ABC("[a-zA-Z]+"),
		/**
		 * 数字和字母组合
		 */
		NUMBER_AND_ABC("[0-9a-zA-Z]+"),
		/**
		 * 纯汉字
		 */
		CHINESE_CHARACTER("[\\u4E00-\\u9FA5]+"),
		
		/**
		 * http url 地址
		 */
		HTTP_URL("(?i)^((http|https)://)([\\w-]+\\.)+[\\w-]+(/[\\w-\\./?%&=]*)?$"),
		/**
		 * 邮箱地址
		 */
		EMAIL("^([A-Za-z0-9_\\-])+\\@([A-Za-z0-9_\\-])+\\.([A-Za-z]{2,4})$"),
		/**
		 * 消息模板变量
		 */
		MESSAGE_TEMPLATE_VARIABLE("\\{{1}[A-Z_]+\\}{1}");
		
		Patter(String regex){
			this.regex = regex;
		}
		
		private String regex;

		public String getRegex() {
			return regex;
		}
	}
	
	/**
	 * 是否完全匹配
	 * @param source 原始内容
	 * @param p
	 * @return
	 * @throws MyException
	 */
	public static boolean isMatch(String source, Patter p)throws MyException{
		if(source == null || p == null){
			CommonException.parameterLost();
		}

		Pattern pattern = Pattern.compile(p.getRegex());
		return pattern.matcher(source).matches();
	}
	/**
	 * 是否能找到
	 * @param source 原始内容
	 * @param p
	 * @return
	 * @throws MyException
	 */
	public static boolean isFind(String source, Patter p)throws MyException{
		if(source == null || p == null){
			CommonException.parameterLost();
		}
		Pattern pattern = Pattern.compile(p.getRegex());
		return pattern.matcher(source).find();
	}
	/**
	 * 找到的次数
	 * @param source 原始内容
	 * @param p
	 * @return
	 * @throws MyException
	 */
	public static int findTimes(String source, Patter p)throws MyException{
		if(source == null || p == null){
			CommonException.parameterLost();
		}
		Pattern pattern = Pattern.compile(p.getRegex());
		Matcher m = pattern.matcher(source);
		
		int count = 0;
		while(m.find()){
			count++;
		}
		return count;
	}
	
	/**
	 * 最先匹配的索引位置
	 * @param source 原始内容
	 * @param p
	 * @return -1表示没找到
	 * @throws MyException
	 */
	public static int findStartIndex(String source, Patter p)throws MyException{
		if(source == null || p == null){
			CommonException.parameterLost();
		}
		Pattern pattern = Pattern.compile(p.getRegex());
		Matcher m = pattern.matcher(source);
		if(m.find()){
			return m.start();
		}
		return -1;
	}
	/**
	 * 最后匹配的索引位置
	 * @param source 原始内容
	 * @param p
	 * @return -1表示没找到
	 * @throws MyException
	 */
	public static int findEndIndex(String source, Patter p)throws MyException{
		if(source == null || p == null){
			CommonException.parameterLost();
		}
		Pattern pattern = Pattern.compile(p.getRegex());
		Matcher m = pattern.matcher(source);
		if(m.find()){
			return m.end();
		}
		return -1;
	}
	
	/**
	 * 提取找到的结果
	 * @param source 原始内容
	 * @param p
	 * @param distinct 是否去重
	 * @return
	 * @throws MyException
	 */
	public static List<String> findList(String source, Patter p, boolean distinct)throws MyException{
		if(source == null || p == null){
			CommonException.parameterLost();
		}
		Pattern pattern = Pattern.compile(p.getRegex());
		Matcher m = pattern.matcher(source);
		List<String> list = new ArrayList<String>();
		while(m.find()){
			String find = m.group();
			if(distinct && list.contains(find)){
				continue;
			}
			list.add(find);
		}
		return list;
	}
	/**
	 * 提取内容
	 * @param source 原始内容
	 * @param p
	 * @return
	 * @throws MyException
	 */
	public static String extract(String source, Patter p) throws MyException{
		if(source == null || "".equals(source)) {
			return null;
		}
		if(p == null) {
			CommonException.parameterLost();
		}
		Pattern pattern = Pattern.compile(p.getRegex());
		Matcher m = pattern.matcher(source);
		String ss = "";
		while(m.find()) {
			ss += m.group();
		}
		return ss;
	}
	
	/**
	 * 提取内容
	 * @param source 原始内容
	 * @param startChar 从startChar字符开始
	 * @param endChar   到endChar字符结束
	 * @param ignoreCase  忽略大小写
	 * @return
	 * @throws MyException
	 */
	public static String extract(String source, String startChar, String endChar, boolean ignoreCase) throws MyException{
		if(source == null || "".equals(source)) {
			return null;
		}
		String regex = "";
		if(ignoreCase) {
			regex += "(?i)";
		}
		regex += addEscape(startChar)+".*?"+addEscape(endChar);
		Pattern pattern = Pattern.compile(regex);
		Matcher m = pattern.matcher(source);
		if(m.find()) {
			return m.group();
		}
		return null;
	}
	/**
	 * 添加转义符
	 * @param ss
	 * @return
	 */
	private static String addEscape(String ss) {
		return ss.replaceAll("\\\\", "\\\\\\\\")
				.replaceAll("\\.", "\\\\.")
				.replaceAll("\\?", "\\\\?")
				.replaceAll("\\^", "\\\\^")
				.replaceAll("\\+", "\\\\+")
				.replaceAll("\\*", "\\\\*")
				.replaceAll("\\|", "\\\\|")
				.replaceAll("\\(", "\\\\(")
				.replaceAll("\\)", "\\\\)")
				.replaceAll("\\[", "\\\\[")
				.replaceAll("\\{", "\\\\{")
				.replaceAll("\\$", "\\\\$");
	}
	
}
