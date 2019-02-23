package com.way.open.pay.service;

import java.util.Map;

public interface WxService {
	/**
	 * 微信下单
	 * @throws Exception 
	 */
	public String placeOrder(Map<String,String> paramMap) throws Exception;
	/**
	 * 微信通知
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public String wxNotify(Map<String,String> paramMap) throws Exception;
	
	/**
	 * 微信关闭订单
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public String refundWxOrder(Map<String,String> paramMap) throws Exception;
	
	
	public String wxOrderQuery(Map<String, String> paramMap) throws Exception;

}
