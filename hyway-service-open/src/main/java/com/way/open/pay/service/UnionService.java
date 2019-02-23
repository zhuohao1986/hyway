package com.way.open.pay.service;

import java.util.Map;

public interface UnionService {
	
	/**
	 * 银联支付
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public String unionPay(Map<String,String> paramMap) throws Exception;
	/**
	 * 银联支付通知
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public String unionNotify(Map<String,String> paramMap) throws Exception;
	
	/**
	 * 银联订单查询
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	String unionOrderQuery(Map<String, String> paramMap) throws Exception;
	
	
	public String refundOrder(Map<String, String> closePayMap);

}
