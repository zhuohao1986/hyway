package com.way.common.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

/**
 * 字符串工具类
 * 
 * @author
 * 
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {
	/**
	 * 字符串去除头尾空格
	 * 
	 * @param str
	 * @return
	 */
	public static String trim(String str) {
		return str == null ? "" : str.trim();
	}

	/**
	 * 字符串（js数组字符串）转 list集合 整数
	 * 
	 * @param str
	 * @return
	 */
	public static List<Integer> getList(String str) {
		List<Integer> list = new ArrayList<Integer>();
		String str1 = str.substring(1, str.length());
		String str2 = str1.substring(0, str1.length() - 1);
		String[] strs = str2.split(",");
		for (String s : strs) {
			list.add(Integer.parseInt(s.trim()));
		}
		return list;
	}

	/**
	 * 字符串（js数组字符串）转 list集合 字符串
	 * 
	 * @param str
	 * @return
	 */
	public static List<String> getListStr(String str) {
		if (StringUtils.isEmpty(str)) {
			return null;
		}
		List<String> list = new ArrayList<String>();
		String str1 = str.substring(1, str.length());
		String str2 = str1.substring(0, str1.length() - 1);
		String[] strs = str2.split(",");
		for (String s : strs) {
			list.add(s.trim());
		}
		return list;
	}

	/**
	 * 
	* @Title: splitStr
	* @Description: 根据分割符，  生成List
	* @author Alvin
	* @param str
	* @return
	* @throws
	 */
	public static List<String> splitStr(String str) {
		List<String> strlist = new ArrayList<String>();
		String[] list = str.split(",");
		for (String string : list) {
			strlist.add(string);
		}
		return strlist;
	}

	/**
	 * 
	* @Title: splitStr
	* @Description: 根据分割符，  生成List
	* @author Alvin
	* @param str 分隔符
	* @param separator
	* @return
	* @throws
	 */
	public static List<String> splitStr(String str, String separator) {
		List<String> strlist = new ArrayList<String>();
		String[] list = str.split(separator);
		for (String string : list) {
			strlist.add(string);
		}
		return strlist;
	}

	/**
	 * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址;
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public static String getIpAddress(HttpServletRequest request) throws IOException {
		// 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址

		String ip = request.getHeader("X-Forwarded-For");
		// if (logger.isInfoEnabled()) {
		// logger.info("getIpAddress(HttpServletRequest) - X-Forwarded-For -
		// String ip="
		// + ip);
		// }

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
				// if (logger.isInfoEnabled()) {
				// logger.info("getIpAddress(HttpServletRequest) -
				// Proxy-Client-IP - String ip="
				// + ip);
				// }
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
				// if (logger.isInfoEnabled()) {
				// logger.info("getIpAddress(HttpServletRequest) -
				// WL-Proxy-Client-IP - String ip="
				// + ip);
				// }
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_CLIENT_IP");
				// if (logger.isInfoEnabled()) {
				// logger.info("getIpAddress(HttpServletRequest) -
				// HTTP_CLIENT_IP - String ip="
				// + ip);
				// }
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");
				// if (logger.isInfoEnabled()) {
				// logger.info("getIpAddress(HttpServletRequest) -
				// HTTP_X_FORWARDED_FOR - String ip="
				// + ip);
				// }
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
				// if (logger.isInfoEnabled()) {
				// logger.info("getIpAddress(HttpServletRequest) - getRemoteAddr
				// - String ip="
				// + ip);
				// }
			}
			if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
				try {
					ip = InetAddress.getLocalHost().getHostAddress();
				} catch (UnknownHostException unknownhostexception) {
				}
			}
		} else if (ip.length() > 15) {
			String[] ips = ip.split(",");
			for (int index = 0; index < ips.length; index++) {
				String strIp = (String) ips[index];
				if (!("unknown".equalsIgnoreCase(strIp))) {
					ip = strIp;
					break;
				}
			}
		}
		return ip;
	}

	public static String getLocalHostIP() {
		String localHostIP = "";
		try {
			localHostIP = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return localHostIP;
	}

	/**
	 * 
	 * @Title: isInnerIP @Description: 判断一个IP地址是否为内网IP @author qijian @param
	 *         ipAddress @return @throws
	 */
	public static boolean isInnerIP(String ipAddress) {
		boolean isInnerIp = false;
		long ipNum = getIpNum(ipAddress);
		/**
		 * 私有IP：A类 10.0.0.0-10.255.255.255 B类 172.16.0.0-172.31.255.255 C类
		 * 192.168.0.0-192.168.255.255 当然，还有127这个网段是环回地址
		 **/
		long aBegin = getIpNum("10.0.0.0");
		long aEnd = getIpNum("10.255.255.255");
		long bBegin = getIpNum("172.16.0.0");
		long bEnd = getIpNum("172.31.255.255");
		long cBegin = getIpNum("192.168.0.0");
		long cEnd = getIpNum("192.168.255.255");
		isInnerIp = isInner(ipNum, aBegin, aEnd) || isInner(ipNum, bBegin, bEnd) || isInner(ipNum, cBegin, cEnd)
				|| ipAddress.equals("127.0.0.1");
		return isInnerIp;
	}

	private static long getIpNum(String ipAddress) {
		String[] ip = ipAddress.split("\\.");
		long a = Integer.parseInt(ip[0]);
		long b = Integer.parseInt(ip[1]);
		long c = Integer.parseInt(ip[2]);
		long d = Integer.parseInt(ip[3]);

		long ipNum = a * 256 * 256 * 256 + b * 256 * 256 + c * 256 + d;
		return ipNum;
	}

	private static boolean isInner(long userIp, long begin, long end) {
		return (userIp >= begin) && (userIp <= end);
	}

	public static String renameFileName(String url, String newName) {
		if (StringUtils.isEmpty(url)) {
			return DateUtils.getCurDateTimeStr23();
		}
		String fileName = StringUtils.splitStr(url, "/").get(1);
		String[] files = fileName.split("\\.");
		String str = newName + "." + fileName.split("\\.")[files.length - 1];
		return str;
	}

	public static String getUUIDString() {
		String uuid=UUID.randomUUID().toString().replaceAll("-", "");
		return uuid;
	}
}
