package com.way.open.pay.util.alipay;

import org.springframework.beans.factory.annotation.Value;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;

/**
 * 获取alipayClient
 * 
 * @author wangw
 *
 */
public class AlipayFactory {
	/**
	 * APPID，收款账号既是您的APPID对应支付宝账号
	 */
	@Value("${APP_ID}")
	private static String APP_ID;
	/**
	 * 商户私钥
	 */
	@Value("${MERCHANT_PRIVATE_KEY}")
	private static String MERCHANT_PRIVATE_KEY;
	
	/**
	 * 支付宝公钥
	 */
	@Value("${ALIPAY_PUBLIC_KEY}")
	private static String ALIPAY_PUBLIC_KEY;
	 /**
	  * 异步通知接口                                             
     */
	@Value("${NOTIFY_URL}")
	private static String NOTIFY_URL;
	 /**
	 * 同步跳转接口  
	 */
	@Value("${RETURN_URL}")
	private static String RETURN_URL;
	
	/**
	 *  支付宝网关
	 */
	@Value("${GATEWAYURL}")
	private static String GATEWAYURL;
	
	/**
	 *  签名方式
	 */
	public static final String SIGN_TYPE= "RSA2";
	
	public static final String FORMAT= "json";
	/**
	 *  字符编码格式
	 */
	public static final String CHARSET= "utf-8";

	private static  AlipayClient client = new DefaultAlipayClient(GATEWAYURL, APP_ID,
			MERCHANT_PRIVATE_KEY, FORMAT, CHARSET,
			ALIPAY_PUBLIC_KEY, SIGN_TYPE);

	public AlipayFactory() {
	    
	}
	
	public static AlipayClient getAlipayClientInstance() {
		return client;
	}

	

}
