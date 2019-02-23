package com.way.open.pay.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.way.common.constant.CodeConstants;
import com.way.common.stdo.Result;
import com.way.open.pay.service.AlipayService;
import com.way.open.pay.util.alipay.AlipayConfig;
import com.way.open.pay.util.alipay.AlipayPay;
import com.way.open.pay.util.alipay.TradeStatus;

@Service
public class AlipayServiceImpl implements AlipayService{
	
	@Autowired
	private AlipayPay alipayPay;

	/**
	 * 支付
	 * 
	 * @param paramMap
	 * @return
	 */
	@Override
	public String pay(Map<String, String> paramMap) {
		
		return alipayPay.pay(paramMap);
	}


	@Override
	public String alipayNotify(Map<String, String> paramMap) {
		try {
			boolean signVerified = AlipaySignature.rsaCheckV1(paramMap, AlipayConfig.ALIPAY_PUBLIC_KEY,AlipayConfig.CHARSET, AlipayConfig.SIGN_TYPE); // 调用SDK验证签名
			// 验证成功
			if (signVerified) {
				// 交易状态
				String trade_status = paramMap.get("trade_status");
				if (TradeStatus.TRADE_FINISHED.equals(trade_status)) {
					// 判断该笔订单是否在商户网站中已经做过处理
					// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					// 如果有做过处理，不执行商户的业务程序
					// 注意：
					// 退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
					return new Result(CodeConstants.RESULT_SUCCESS, "success", "支付宝交易完成").toString();
				} else if (TradeStatus.TRADE_SUCCESS.equals(trade_status)) {
					// 注意：
					// 付款完成后，支付宝系统发送该交易状态通知
					return new Result(CodeConstants.RESULT_SUCCESS, "success", "支付宝交易成功").toString();
				}
			} else {
				// 验证失败
				// 调试用，写文本函数记录程序运行情况是否正常
				String sWord = AlipaySignature.getSignCheckContentV1(paramMap);
				return new Result(CodeConstants.RESULT_FAIL, "fail", sWord).toString();
			}
		} catch (Exception e) {
			return new Result(CodeConstants.RESULT_FAIL, "fail", e.getMessage()).toString();
		}
		return null;
	}

	@Override
	public String alipayQuery(Map<String, String> paramMap) {
		String resultStr=alipayPay.tradeQuery(paramMap);
		JSONObject valueStr=JSONObject.parseObject(resultStr);
		JSONObject value=valueStr.getJSONObject("alipay_trade_query_response");
		AlipayTradeQueryResponse res=JSONObject.parseObject(value.toJSONString(),AlipayTradeQueryResponse.class);
		if(TradeStatus.TRADE_SUCCESS.equals(res.getTradeStatus())){
			return new Result(CodeConstants.RESULT_SUCCESS,"success", "交易支付成功").toString();
		}else{
			return new Result(CodeConstants.RESULT_FAIL,"fail", res.getSubMsg()).toString();
		}
	}

	@Override
	public String refundPayOrder(Map<String, String> paramMap) {
		String resultStr=alipayPay.refund(paramMap);
		JSONObject valueStr=JSONObject.parseObject(resultStr);
		JSONObject value=valueStr.getJSONObject("alipay_trade_refund_response");
		AlipayTradeRefundResponse res=JSONObject.parseObject(value.toJSONString(),AlipayTradeRefundResponse.class);
		if(res.isSuccess()){
			return new Result(CodeConstants.RESULT_SUCCESS,"success", "退款成功").toString();
		}else{
			return new Result(CodeConstants.RESULT_FAIL,"fail", res.getSubMsg()).toString();
		}
	}
}
