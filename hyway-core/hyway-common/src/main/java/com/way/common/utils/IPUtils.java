package com.way.common.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;

public class IPUtils{
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
	
	public static String parseIpBaidu(String fakeip) throws Exception {
        // 通过获取到的客户端IP地址确定客户端所在地理位置
        String address;
        // 使用baidu接口获取IP地址
        String url = "http://api.map.baidu.com/location/ip?ip=" + fakeip + "&ak=VW2ubONTZa3zvjnwsK7X51KO&coor=bd09ll";
        // 通过Http工具访问接口
        String result = HttpUtils.get(url, null, 0, 0);
        // 对返回的数据进行解析
        JSONObject jsonObject = JSONObject.parseObject(result);
        if (jsonObject != null) {
            Integer status = jsonObject.getInteger("status");
            if (status == 0) {
                address = jsonObject.getJSONObject("content").getString("address");
                System.out.println(address);
            } else {
                address = "江苏省南京市"; // 解析失败，默认地址
            }
        } else {
            address = "江苏省南京市"; // 获取baidu接口数据失败
        }
		return address;
	}
	
	public static String parseIpAmap(String fakeip) throws Exception {
        // 通过获取到的客户端IP地址确定客户端所在地理位置
        String address;
        // 使用高德接口获取IP地址
        String url="https://restapi.amap.com/v3/ip?ip=" + fakeip + "&key=6de74452027099565b8bbf490e2a758b";
        // 通过Http工具访问接口
        String result = HttpUtils.get(url, null, 0, 0);
        // 对返回的数据进行解析
        JSONObject jsonObject = JSONObject.parseObject(result);
        if (jsonObject != null) {
            Integer status = jsonObject.getInteger("status");
            if (status == 1) {
                address = jsonObject.getString("province")+jsonObject.getString("city");
            } else {
                address = "上海市"; // 解析失败，默认地址
            }
        } else {
            address = "上海市"; // 获取baidu接口数据失败
        }
		return address;
	}

}
