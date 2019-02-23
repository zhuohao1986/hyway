package com.way.open.pay.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.way.common.cache.JedisClient;
import com.way.common.constant.CodeConstants;
import com.way.common.constant.enums.PayWayEnum;
import com.way.common.stdo.RequestWrapper;
import com.way.common.stdo.Result;
import com.way.open.pay.aop.PayLog;
import com.way.open.pay.aop.PayLog.PayType;
import com.way.open.pay.service.AlipayService;
import com.way.open.pay.service.UnionService;
import com.way.open.pay.service.WxService;
import com.way.open.pay.util.wx.WXPayConstants;
/**
 * 操作成功通知
 * @author wangw
 *
 */
@Service("payNotifyApi")
@SuppressWarnings("unchecked")
public class PayNotifyApiImp implements PayNotifyApi {
	
	@Autowired
	private OrderApi orderApi;
	
	@Autowired
	private WxService wxService;
	
	@Autowired
	private AlipayService alipayService;
	
	@Autowired
	private UnionService unionService;
	
	@Autowired
	private JedisClient jedisClient;

	@Override
	@PayLog(payType=PayType.NOTIFY)
	public String payNotify(String param) {
		HashMap<String,String> paramsMap=new HashMap<String,String>();
		try {
			RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
			String valueJson = rw.getValue();
			JSONObject valueStr = JSONObject.parseObject(valueJson);
			paramsMap=JSONObject.parseObject(valueJson, new TypeReference<HashMap<String, String>>(){});			
			String payWay=valueStr.getString("payWay");
			//消费类型 01 交易 04 退货
			String txnType=valueStr.getString("txnType");
			if("04".equals(txnType)){
				String respCode=paramsMap.get("respCode");
				if(("00").equals(respCode)) {
					return new Result(CodeConstants.RESULT_SUCCESS, "ok","银联退款成功").toString();
				}else {
					return new Result(CodeConstants.RESULT_FAIL, "fail","银联退款成功").toString();
				}
			}
			String outTradeNo = null;
			if (PayWayEnum.ALIPAY.name().equals(payWay)) { // 支付宝
				outTradeNo =valueStr.getString("out_trade_no");
			} else if (PayWayEnum.WX.name().equals(payWay)) { // 微信
				outTradeNo =valueStr.getString("out_trade_no");
			}else if (PayWayEnum.UNIONPAY.name().equals(payWay)) { // 银联
				outTradeNo =valueStr.getString("orderId");
			}
			Map<String,String> orderReqMap=new HashMap<>();
			orderReqMap.put("orderNo", outTradeNo);
			orderReqMap.put("outTradeNo", outTradeNo);
			orderReqMap.put("payment", payWay);
			
			String orderStr=orderApi.getOrderByOrderNo(new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB,JSONObject.toJSONString(orderReqMap)).toString());
			Result orderResult=JSONObject.parseObject(orderStr, Result.class);
			String payStr="";
			OdOrders order=null;
			if(CodeConstants.RESULT_SUCCESS.equals(orderResult.getCode())){
				//订单查询查询成功
				Map<String,Object> orderMap=JSONObject.parseObject(String.valueOf(orderResult.getValue()), new HashMap<>().getClass());
				order=JSONObject.parseObject(String.valueOf(orderMap.get("order")), OdOrders.class); 
				 //根据支付方式执行验证通知是否正确
				 paramsMap.remove("payWay");
				 if(PayWayEnum.ALIPAY.name().equals(payWay)){
					payStr=alipayService.alipayNotify(paramsMap);
				 }else if(PayWayEnum.WX.name().equals(payWay)){
					payStr=wxService.wxNotify(paramsMap);
				 }else if(PayWayEnum.UNIONPAY.name().equals(payWay)){
					payStr=unionService.unionOrderQuery(paramsMap);
				 }
			}
			Result payResult=JSONObject.parseObject(payStr, Result.class);
			if(CodeConstants.RESULT_SUCCESS.equals(payResult.getCode())){
				//待支付更新订单
				if(StatusUtils.ORDER_NO_PAY.equals(order.getOrderState())){
					Map<String, String> reqMap=new HashMap<String, String>();
					reqMap.put("orderNo", outTradeNo);
					reqMap.put("payWay", payWay);
					updateOrder(reqMap);
					//删微信缓存支付URl
					if(PayWayEnum.WX.name().equals(payWay)){
						jedisClient.del(WXPayConstants.WX_PAY_URL_KEY+outTradeNo);
					 }
				}
			}
			return payResult.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(CodeConstants.RESULT_FAIL, "fail", e.getMessage()).toString();
		}
	}

	/**
	 * 修改订单状态
	 * @param reqMap
	 * @return
	 */
	private  String updateOrder(Map<String, String> reqMap) {
		Result payOrderStrresult=null;
		try {
			String paramStr = new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB,JSONObject.toJSONString(reqMap)).toString();
			String payOrderStr = orderApi.updatePayState(paramStr);
			payOrderStrresult = JSONObject.parseObject(payOrderStr, Result.class);
		} catch (Exception e) {
			return new Result(CodeConstants.RESULT_FAIL, "fail",payOrderStrresult.getValue()).toString();
		}
		return payOrderStrresult.toString();
	}
	/**
	 * 修改订单状态
	 * @param reqMap
	 * @return
	 */
	protected  boolean orderPayStatus(Map<String, String> reqMap) {
		Result payLogResult=null;
		boolean flag=false;
		try {
			String paramStr = new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB,JSONObject.toJSONString(reqMap)).toString();
			String payOrderlogStr = orderApi.selectPayLog(paramStr);
			payLogResult = JSONObject.parseObject(payOrderlogStr, Result.class);
			if(CodeConstants.RESULT_SUCCESS.equals(payLogResult.getCode())){
				flag=true;
			}
		} catch (Exception e) {
			flag=false;
		}
		return flag;
	}

}
