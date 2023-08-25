package com.jweb.common.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

public class IpUtil {

	public static String getIpAddress(HttpServletRequest request){
		String ip = null;
		try
		{
			// 获取用户真是的地址
			String Xip = request.getHeader("X-Real-IP");
			// 获取多次代理后的IP字符串值
			String XFor = request.getHeader("X-Forwarded-For");
			if (StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor))
			{
				// 多次反向代理后会有多个IP值，第一个用户真实的IP地址
				int index = XFor.indexOf(",");
				if (index >= 0)
				{
					return XFor.substring(0, index);
				} else
				{
					return XFor;
				}
			}
			ip = Xip;
			if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip))
			{
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (StringUtils.isEmpty(ip) || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
			{
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip))
			{
				ip = request.getHeader("HTTP_CLIENT_IP");
			}
			if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip))
			{
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			}
			if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip))
			{
				ip = request.getRemoteAddr();
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return ip;
	}
}
