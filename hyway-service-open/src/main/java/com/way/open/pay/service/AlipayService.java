package com.way.open.pay.service;

import java.util.Map;

public interface AlipayService {
	
	/**
	 * 支付
	 * @param paramMap
	 * @return
	 */
	public String pay(Map<String, String> paramMap);
	
	public String alipayNotify(Map<String, String> paramMap);
	
	
	/**
     * 支付宝自定义查询
     * @param paramMap
     * @return
     */
	public String alipayQuery(Map<String, String> paramMap);
	
	/**
	 * 支付宝自定义退款返回
	 * @param paramMap
	 * @return
	 */
	public String refundPayOrder(Map<String, String> paramMap);
	
	
}
