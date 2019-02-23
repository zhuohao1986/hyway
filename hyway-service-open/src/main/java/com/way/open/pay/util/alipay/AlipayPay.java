package com.way.open.pay.util.alipay;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeCloseRequest;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
@Component
public class AlipayPay {
	
	/**
	 * 支付
	 * 
	 * @param paramMap
	 * @return
	 */
	
	public String pay(Map<String, String> paramMap) {
		String result = "";
		try {
			AlipayClient alipayClient = AlipayFactory.getAlipayClientInstance();
			// 设置请求参数
			AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
			alipayRequest.setReturnUrl(AlipayConfig.RETURN_URL);
			alipayRequest.setNotifyUrl(AlipayConfig.NOTIFY_URL);
			// 商户订单号
			String out_trade_no = paramMap.get("WIDout_trade_no");
			// 付款金额，必填
			String total_amount = paramMap.get("WIDtotal_amount");
			// 订单名称，必填
			String subject = paramMap.get("WIDsubject");
			// 商品描述，可空

			alipayRequest.setBizContent(
					"{\"out_trade_no\":\"" + out_trade_no + "\"," + "\"total_amount\":\"" + total_amount + "\","
							+ "\"subject\":\"" + subject + "\"," + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

			// 请求
			result = alipayClient.pageExecute(alipayRequest).getBody();
		} catch (Exception e) {
			AlipayConfig.logResult(e.getMessage());
		}
		return result;
	}

	/**
	 * 关闭交易
	 * 
	 * @param paramMap
	 * @return
	 */
	
	public String close(Map<String, String> paramMap) {
		String result = "";
		try {
			AlipayClient alipayClient = AlipayFactory.getAlipayClientInstance();
			AlipayTradeCloseRequest alipayRequest = new AlipayTradeCloseRequest();
			// 商户订单号
			String out_trade_no = paramMap.get("WIDTCout_trade_no");
			// 支付宝交易号
			String trade_no = paramMap.get("WIDTCtrade_no");
			// 请二选一设置
			alipayRequest.setBizContent(
					"{\"out_trade_no\":\"" + out_trade_no + "\"," + "\"trade_no\":\"" + trade_no + "\"}");
			// 请求
			result = alipayClient.execute(alipayRequest).getBody();
			;
		} catch (Exception e) {
			AlipayConfig.logResult(e.getMessage());
		}
		return result;
	}

	/**
	 * 退款
	 * 
	 * @param paramMap
	 * @return
	 */
	
	public String refund(Map<String, String> paramMap) {
		String result = "";
		try {
			AlipayClient alipayClient = AlipayFactory.getAlipayClientInstance();
			AlipayTradeRefundRequest alipayRequest = new AlipayTradeRefundRequest();
			// 商户订单号
			String out_trade_no = paramMap.get("WIDTRout_trade_no");
			// 支付宝交易号
			String trade_no = paramMap.get("WIDTRtrade_no");
			// 请二选一设置
			// 需要退款的金额，该金额不能大于订单金额，必填
			String refund_amount = paramMap.get("WIDTRrefund_amount");
			// 退款的原因说明
			String refund_reason = paramMap.get("WIDTRrefund_reason");
			// 标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传
			// String out_request_no = new
			// String(paramMap.get("WIDTRout_request_no");
			alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\"," + "\"trade_no\":\"" + trade_no
					+ "\"," + "\"refund_amount\":\"" + refund_amount + "\"," + "\"refund_reason\":\"" + refund_reason
					+ "\"}");
			// 请求
			result = alipayClient.execute(alipayRequest).getBody();
		} catch (Exception e) {

		}
		return result;
	}

	/**
	 * 交易查询
	 * 
	 * @param paramMap
	 * @return
	 */
	
	public String tradeQuery(Map<String, String> paramMap) {
		String result = "";
		try {
			// 获得初始化的AlipayClient
			AlipayClient alipayClient = AlipayFactory.getAlipayClientInstance();
			// 设置请求参数
			AlipayTradeQueryRequest alipayRequest = new AlipayTradeQueryRequest();
			// 商户订单号，商户网站订单系统中唯一订单号
			String out_trade_no = paramMap.get("WIDTQout_trade_no");
			// 支付宝交易号
			String trade_no = paramMap.get("WIDTQtrade_no");
			// 请二选一设置
			alipayRequest.setBizContent(
					"{\"out_trade_no\":\"" + out_trade_no + "\"," + "\"trade_no\":\"" + trade_no + "\"}");
			// 请求
			result = alipayClient.execute(alipayRequest).getBody();
		} catch (Exception e) {
			AlipayConfig.logResult(e.getMessage());
		}
		return result;
	}

	
	public String refundQuery(Map<String, String> paramMap) {
		String result = "";
		try {
			AlipayClient alipayClient = AlipayFactory.getAlipayClientInstance();
			// 设置请求参数
			AlipayTradeFastpayRefundQueryRequest alipayRequest = new AlipayTradeFastpayRefundQueryRequest();
			// 商户订单号，商户网站订单系统中唯一订单号
			String out_trade_no = paramMap.get("WIDRQout_trade_no");
			// 支付宝交易号
			String trade_no = paramMap.get("WIDRQtrade_no");
			// 请二选一设置
			// 请求退款接口时，传入的退款请求号，如果在退款请求时未传入，则该值为创建交易时的外部交易号，必填
			String out_request_no = paramMap.get("WIDRQout_request_no");
			alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\"," + "\"trade_no\":\"" + trade_no
					+ "\"," + "\"out_request_no\":\"" + out_request_no + "\"}");
			// 请求
			result = alipayClient.execute(alipayRequest).getBody();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
