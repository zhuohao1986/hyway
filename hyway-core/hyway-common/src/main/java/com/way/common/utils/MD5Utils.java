/**
 * Simple To Introduction 项目名称: [g2usercenter] 包:
 * [com.hnmmli.g2usercenter.utils] 类名称: [Utils] 类描述: [一句话描述该类的功能] 创建人: [dev]
 * 创建时间: [2014年4月21日 下午6:57:13] 修改人: [dev] 修改时间: [2014年4月21日 下午6:57:13] 修改备注:
 * [说明本次修改内容] 版本: [v1.0]
 */
package com.way.common.utils;

import java.security.MessageDigest;

/**
 * @author 
 * @ClassName: Utils
 * @Description: TODO
 * @date 2015年12月2日 下午6:57:13
 */
public class MD5Utils
{
	public final static String MD5(String s)
	{
		char hexDigits[] =
		{ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

		try
		{
			byte[] btInput = s.getBytes("utf-8");
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++)
			{
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0x0f];
				str[k++] = hexDigits[byte0 & 0x0f];
				
				/*buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
	            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);*/
			}
			return new String(str);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @author 
	 * @Description: 用户密码加密算法
	 * @date 
	 * @param user
	 * @param password
	 * @return
	 */
	public static String getMD5Password(String password)
	{
		return MD5( password);
	}
	
	public static void main(String argus[])
	{
	
		System.out.println(MD5("111311"));
	}

}
