package com.way.open.pay.util.alipay;

import java.io.FileWriter;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
/**
 * 支付宝配置类
 * @author wangw
 *
 */
public class AlipayConfig {
	/**
	 * APPID，收款账号既是您的APPID对应支付宝账号
	 */
	@Value("${APP_ID}")
	public String APP_ID;
	/**
	 * 商户私钥
	 */
	@Value("${MERCHANT_PRIVATE_KEY}")
	public String MERCHANT_PRIVATE_KEY;
	
	/**
	 * 支付宝公钥
	 */
	@Value("${ALIPAY_PUBLIC_KEY}")
	public static String ALIPAY_PUBLIC_KEY;
	 /**
	  * 异步通知接口                                             
     */
	@Value("${NOTIFY_URL}")
	public static String NOTIFY_URL;
	 /**
	 * 同步跳转接口  
	 */
	@Value("${RETURN_URL}")
	public static String RETURN_URL;
	
	/**
	 *  支付宝网关
	 */
	@Value("${GATEWAYURL}")
	public String GATEWAYURL;
	
	/**
	 *  签名方式
	 */
	public static final String SIGN_TYPE= "RSA2";
	
	public static final String FORMAT= "json";
	/**
	 *  字符编码格式
	 */
	public static final String CHARSET= "utf-8";
	
	
	
	public static String log_path = "/home/mwclg/alipay";
	/**
	 * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
	 * 
	 * @param sWord
	 * 要写入日志里的文本内容
	 */
	public static void logResult(String sWord) {
		FileWriter writer = null;
		try {
			writer = new FileWriter(log_path + "alipay_log.txt");
			writer.append(sWord);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
