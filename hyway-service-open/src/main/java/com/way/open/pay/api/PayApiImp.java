package com.way.open.pay.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alipay.api.internal.util.AlipaySignature;
import com.way.common.cache.JedisClient;
import com.way.common.constant.CodeConstants;
import com.way.common.constant.enums.PayWayEnum;
import com.way.common.stdo.RequestWrapper;
import com.way.common.stdo.Result;
import com.way.common.utils.DEncryptionUtils;
import com.way.common.utils.StringUtils;
import com.way.open.dto.PayVo;
import com.way.open.pay.aop.PayLog;
import com.way.open.pay.aop.PayLog.PayType;
import com.way.open.pay.service.AlipayService;
import com.way.open.pay.service.UnionService;
import com.way.open.pay.service.WxService;
import com.way.open.pay.util.alipay.AlipayConfig;
import com.way.open.pay.util.wx.WXPayConstants;

@Service("payApi")
@SuppressWarnings("unchecked")
public class PayApiImp {
	@Autowired
	private AlipayService alipayService;
	@Autowired
	private WxService wxService;
	@Autowired
	private UnionService unionService;
	@Autowired
	private OrderApi orderApi;
	@Autowired
	private JedisClient jedisClient;

	public String syncPage(String param) {
		HashMap<String,String> paramsMap=new HashMap<String,String>();
		try {
			RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
			String valueJson = rw.getValue();
			JSONObject valueStr = JSONObject.parseObject(valueJson);
			paramsMap=JSONObject.parseObject(valueJson, new TypeReference<HashMap<String, String>>(){});
			//根据支付方式获取订单号
			String payWay=valueStr.getString("payWay");
			String outTradeNo = null;
			if (PayWayEnum.ALIPAY.name().equals(payWay)) { // 支付宝
				outTradeNo =valueStr.getString("out_trade_no");
			}else if (PayWayEnum.WXPAY.name().equals(payWay)) { // 银联
					outTradeNo =valueStr.getString("out_trade_no");
			}else if (PayWayEnum.UNIONPAY.name().equals(payWay)) { // 银联
				outTradeNo =valueStr.getString("orderId");
			}
			//本地订单查询
			Map<String, Object> map = new HashMap<>();
			map.put("orderNo", outTradeNo);
			String orderStr = orderApi.getOrderByOrderNo(new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, JSONObject.toJSONString(map)).toString());
			Result result = JSONObject.parseObject(orderStr, Result.class);
			if (CodeConstants.RESULT_FAIL.equals(result.getCode())) {
				return new Result(CodeConstants.RESULT_FAIL, "fail", "订单号不存在").toString();
			}
			Map<String,Object> orderMap=JSONObject.parseObject(String.valueOf(result.getValue()), new HashMap<>().getClass());
			OdOrders order=JSONObject.parseObject(String.valueOf(orderMap.get("order")), OdOrders.class);
			
			Map<String, Object> returnMap = new HashMap<>();
			paramsMap.remove("payWay");
			if (PayWayEnum.ALIPAY.name().equals(payWay)) { // 支付宝
				boolean signVerified = AlipaySignature.rsaCheckV1(paramsMap, AlipayConfig.ALIPAY_PUBLIC_KEY,AlipayConfig.CHARSET, AlipayConfig.SIGN_TYPE); // 调用SDK验证签名
				if (signVerified) {
					//商户订单号
					String out_trade_no = valueStr.getString("out_trade_no");
					// 支付宝交易号
					String trade_no = valueStr.getString("trade_no");
					// 付款金额
					String total_amount = valueStr.getString("total_amount");
					returnMap.put("out_trade_no",out_trade_no);
					returnMap.put("trade_no", trade_no);
					returnMap.put("total_amount", total_amount);
					returnMap.put("orderId", order.getOrderId());
				} else {
					String sWord = AlipaySignature.getSignCheckContentV1(paramsMap);
					AlipayConfig.logResult(sWord);
					return new Result(CodeConstants.RESULT_FAIL, "fail",sWord).toString();
				}
			}else if (PayWayEnum.UNIONPAY.name().equals(payWay)) { // 银联
				String out_trade_no = valueStr.getString("orderId");
				// 交易号
				String trade_no = valueStr.getString("queryId");
				// 付款金额
				String total_amount = valueStr.getString("txnAmt");
				returnMap.put("out_trade_no",out_trade_no);
				returnMap.put("trade_no", trade_no);
				returnMap.put("total_amount", total_amount);
				returnMap.put("orderId", order.getOrderId());
			}else if (PayWayEnum.WX.name().equals(payWay)) { // 微信
				String out_trade_no = valueStr.getString("out_trade_no");
				// 交易号
				String trade_no = valueStr.getString("transaction_id");
				// 付款金额
				String total_amount = valueStr.getString("total_fee");
				returnMap.put("out_trade_no",out_trade_no);
				returnMap.put("trade_no", trade_no);
				returnMap.put("total_amount", total_amount);
				returnMap.put("orderId", order.getOrderId());
			}
			return new Result(CodeConstants.RESULT_SUCCESS, "success",returnMap).toString();// 异常信息
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(CodeConstants.RESULT_FAIL, "fail",e.getMessage()).toString();
		}
	}
	public String pay(String param) {
		//第三方支付同步返回数据字符串
		String returnStr="";
		try {
			RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
			String valueJson = rw.getValue();
			JSONObject valueStr = JSONObject.parseObject(valueJson);
			HashMap<String,String> paramsMap=JSONObject.parseObject(valueJson, new TypeReference<HashMap<String, String>>(){});
			String payWay=valueStr.getString("payWay");
			//Integer userId=valueStr.getInteger("userId");
			if(StringUtils.isEmpty(payWay)){
				return new Result(CodeConstants.RESULT_FAIL, "fail", "支付方式无效").toString();
			}
			String orderNo=valueStr.getString("orderNo");
			if(StringUtils.isEmpty(orderNo)){
				return new Result(CodeConstants.RESULT_FAIL, "fail", "订单号错误").toString();
			}
			RequestWrapper rworder = new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB,JSONObject.toJSONString(paramsMap));
			String orderStr=orderApi.getOrderByOrderNo(rworder.toString());
			Result orderResult=JSONObject.parseObject(orderStr, Result.class);
			if(CodeConstants.RESULT_FAIL.equals(orderResult.getCode())){
				return new Result(CodeConstants.RESULT_FAIL, "fail", "订单不存在").toString();
			}
			Map<String,Object> orderMap=JSONObject.parseObject(String.valueOf(orderResult.getValue()), new HashMap<>().getClass());
			OdOrders order=JSONObject.parseObject(String.valueOf(orderMap.get("order")), OdOrders.class);   
			/*if(order.getUserId()!=userId){
				return new Result(CodeConstants.RESULT_FAIL, "fail", "订单不存在").toString();
			}*/
			if(!StatusUtils.ORDER_NO_PAY.equals(order.getOrderState())){
				return new Result(CodeConstants.RESULT_FAIL, "fail", "订单已支付,请勿重复提交").toString();
			}
			//根据支付方式选择支付
			String out_trade_no=order.getOrderNo();
			String total_amount=String.valueOf(order.getOrderActualPrice());
			List<OdDetail>  odlist=JSONObject.parseArray(String.valueOf(orderMap.get("odetaillist")), OdDetail.class);				
			String subject=odlist.get(0).getProductName();
			String content="零元素-"+subject;
			order.setOrderName(content);

			//请求的集合Map
			Map<String,String> reqMap=new HashMap<String,String>();
			if(PayWayEnum.ALIPAY.name().equals(payWay)){
				reqMap.put("WIDout_trade_no", out_trade_no);
				reqMap.put("WIDtotal_amount", total_amount);
				reqMap.put("WIDsubject", content);
				returnStr=alipayService.pay(reqMap);
			}else if(PayWayEnum.WX.name().equals(payWay)){
				reqMap.put("out_trade_no", out_trade_no);
				reqMap.put("total_fee", total_amount);
				reqMap.put("body", content);
				String wxPayUrl=jedisClient.get(WXPayConstants.WX_PAY_URL_KEY+out_trade_no);
				if(StringUtils.isNotEmpty(wxPayUrl)){
					returnStr=wxPayUrl;
				}else{
					//执行微信预支付请求返回支付链接
					//存储到redis缓存并设置过期时间
					returnStr=wxService.placeOrder(reqMap);
					String codeUrl=DEncryptionUtils.aesEncoder(returnStr);
					returnStr=codeUrl;
					jedisClient.set(WXPayConstants.WX_PAY_URL_KEY+out_trade_no, codeUrl);
					jedisClient.expire(WXPayConstants.WX_PAY_URL_KEY+out_trade_no,900);
				}
			}else if(PayWayEnum.UNIONPAY.name().equals(payWay)){
				reqMap.put("orderId", out_trade_no);
				reqMap.put("txnAmt", total_amount);
				//reqMap.put("body", content);
				returnStr=unionService.unionPay(reqMap);
			}else{
				return new Result(CodeConstants.RESULT_FAIL, "fail", "无效的支付方式").toString();
			}
			PayVo pay=new PayVo();
			pay.setPayResult(returnStr);
			pay.setPayType(payWay);
			pay.setOrder(order);
			return new Result(CodeConstants.RESULT_SUCCESS, "success",pay).toString();
		} catch (Exception e) {
			return new Result(CodeConstants.RESULT_FAIL, "fail",returnStr).toString();
		}
	}
	@Override
	@PayLog(payType=PayType.REFUND)
	public String refundPay(String param) {
		HashMap<String,String> paramsMap=new HashMap<String,String>();
		try {
			RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
			String valueJson = rw.getValue();
			JSONObject valueStr = JSONObject.parseObject(valueJson);
			paramsMap=JSONObject.parseObject(valueJson, new TypeReference<HashMap<String, String>>(){});
			String payWay=valueStr.getString("payWay");
			paramsMap.remove("payWay");
			//第三方支付同步返回数据字符串
			String returnStr="";
			
			//查询三方支付订单是否存在
			String payOrderStr="";
			Map<String,String> reqQueryMap=new HashMap<String,String>();
			if(PayWayEnum.ALIPAY.name().equals(payWay)){
				reqQueryMap.put("WIDTQout_trade_no", paramsMap.get("orderNo"));
				reqQueryMap.put("WIDTQtrade_no", paramsMap.get("tradeNo"));
				payOrderStr=alipayService.alipayQuery(reqQueryMap);
			}else if(PayWayEnum.WX.name().equals(payWay)){
				reqQueryMap.put("out_trade_no", paramsMap.get("orderNo"));
				payOrderStr=wxService.wxOrderQuery(reqQueryMap);
			}else if(PayWayEnum.UNIONPAY.name().equals(payWay)){
				reqQueryMap.put("txnTime", paramsMap.get("txnTime"));
				reqQueryMap.put("orderId", paramsMap.get("orderNo"));
				payOrderStr=unionService.unionOrderQuery(reqQueryMap);
			}
			
			//订单查询返回数据
			Result payOrderResult=JSONObject.parseObject(payOrderStr, Result.class);
			if(CodeConstants.RESULT_FAIL.equals(payOrderResult.getCode())){
				return payOrderResult.toString();
			}
			
			Map<String,String> closePayMap=new HashMap<String,String>();
			if(PayWayEnum.ALIPAY.name().equals(payWay)){
				closePayMap.put("WIDTCout_trade_no", paramsMap.get("orderNo"));
				closePayMap.put("WIDTRrefund_amount", paramsMap.get("totalAmount"));
				closePayMap.put("WIDTRrefund_reason", paramsMap.get("refundReason"));
				closePayMap.put("WIDTRtrade_no", paramsMap.get("tradeNo"));
				returnStr=alipayService.refundPayOrder(closePayMap);
			}else if(PayWayEnum.WX.name().equals(payWay)){
				closePayMap.put("out_trade_no", paramsMap.get("orderNo"));
				closePayMap.put("out_refund_no", paramsMap.get("out_refund_no"));
				closePayMap.put("total_fee", paramsMap.get("totalAmount"));
				closePayMap.put("refund_fee", paramsMap.get("refund_fee"));
				closePayMap.put("refund_desc", paramsMap.get("refundReason"));
				returnStr=wxService.refundWxOrder(closePayMap);
			}else if(PayWayEnum.UNIONPAY.name().equals(payWay)){
				closePayMap.put("orderId", paramsMap.get("orderNo"));
				closePayMap.put("txnAmt", paramsMap.get("totalAmount"));
				closePayMap.put("origQryId", paramsMap.get("tradeNo"));
				returnStr=unionService.refundOrder(closePayMap);
			}
			//订单关闭业务处理结果
			Result payResult=JSONObject.parseObject(returnStr, Result.class);
			if(CodeConstants.RESULT_SUCCESS.equals(payResult.getCode())){
				//已支付订单更新订单状态
				Map<String, String> reqMap=new HashMap<String, String>();
				closeOrder(reqMap);
			}
			return payResult.toString();
		}catch(Exception e){
			return new Result(CodeConstants.RESULT_FAIL,"fail", "交易处理失败").toString();
		}
	}
	/**
	 * 修改订单状态
	 * @param reqMap
	 * @return
	 */
	private  String closeOrder(Map<String, String> reqMap) {
		Result payOrderStrresult=null;
		try {
			String paramStr = new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB,JSONObject.toJSONString(reqMap)).toString();
			String payOrderStr = orderApi.closePayState(paramStr);
			payOrderStrresult = JSONObject.parseObject(payOrderStr, Result.class);
		} catch (Exception e) {
			return new Result(CodeConstants.RESULT_FAIL, "fail",payOrderStrresult.getValue()).toString();
		}
		return payOrderStrresult.toString();
	}

}
