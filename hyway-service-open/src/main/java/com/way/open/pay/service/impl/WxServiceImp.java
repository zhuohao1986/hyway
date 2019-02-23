package com.way.open.pay.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.way.common.constant.CodeConstants;
import com.way.common.stdo.Result;
import com.way.common.utils.DateUtils;
import com.way.open.pay.service.WxService;
import com.way.open.pay.util.wx.WXPay;
import com.way.open.pay.util.wx.WXPayConfigImpl;
import com.way.open.pay.util.wx.WXPayConstants;
import com.way.open.pay.util.wx.WXPayUtil;

@Service
public class WxServiceImp implements WxService {

	private static WXPay wxpay;

	private WXPayConfigImpl config;

	public WxServiceImp() throws Exception {
		config = WXPayConfigImpl.getInstance();
		wxpay = new WXPay(config, WXPayConstants.NOTIFY_URL, true, false);
	}

	@Override
	public String placeOrder(Map<String, String> paramMap) throws Exception {
		String returnData = "";
		try {
			HashMap<String, String> data = new HashMap<String, String>();
			data.put("body", paramMap.get("body"));
			
			data.put("out_trade_no", paramMap.get("out_trade_no"));

			data.put("appid", config.getAppID());

			data.put("mch_id", config.getMchID());
			
			data.put("total_fee",WXPayUtil.transUnit(paramMap.get("total_fee")));

			data.put("nonce_str", WXPayUtil.generateNonceStr());

			data.put("spbill_create_ip", config.getDomainIp());

			data.put("notify_url", WXPayConstants.NOTIFY_URL);

			data.put("trade_type", "NATIVE");
			//设置支付的限定时间当前时间加上15分钟
			data.put("time_expire",DateUtils.getDateTO14String(DateUtils.addDate(DateUtils.getCurrentDate(), 15)));
			
			data.put("sign", WXPayUtil.generateSignature(data, config.getKey()));
			
			Map<String, String> map = wxpay.unifiedOrder(data);
			if(WXPayConstants.SUCCESS.equals(map.get("return_code"))){
				returnData = map.get("code_url");
			}else{
				returnData=map.get("return_msg");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnData;
	}
	
	@Override
	public String wxNotify(Map<String, String> paramMap) throws Exception {
		try {
			String result_code=paramMap.get("result_code");
			if(WXPayConstants.SUCCESS.equals(result_code)){
				return new Result(CodeConstants.RESULT_SUCCESS, "success", "微信订单支付成功验证成功").toString();
			}else{
				return new Result(CodeConstants.RESULT_FAIL, "fail", "微信订单支付成功验证失败").toString();
			}
		} catch (Exception e) {
			return new Result(CodeConstants.RESULT_FAIL, "success", e.getMessage()).toString();
		}
	}
	
	@Override
	public String wxOrderQuery(Map<String, String> paramMap) throws Exception {
		HashMap<String, String> data = new HashMap<String, String>();
	    Map<String, String> wxdata = new HashMap<String, String>();
		data.put("out_trade_no", paramMap.get("out_trade_no"));
		data.put("appid", config.getAppID());
		data.put("mch_id", config.getMchID());
		data.put("nonce_str", WXPayUtil.generateNonceStr());
		data.put("sign", WXPayUtil.generateSignature(data, config.getKey()));
		wxdata=wxpay.orderQuery(data);
		String return_code=wxdata.get("return_code");
		String result_msg=wxdata.get("result_msg");
		String err_code=wxdata.get("result_msg");
		String err_code_des=wxdata.get("result_msg");
		if(WXPayConstants.SUCCESS.equals(return_code)){
			return new Result(CodeConstants.RESULT_SUCCESS,"success", result_msg).toString();
		}else{
			return new Result(CodeConstants.RESULT_FAIL,err_code, err_code_des).toString();
		}
	}

	@Override
	public String refundWxOrder(Map<String, String> paramMap) throws Exception {
		try {
			HashMap<String, String> data = new HashMap<String, String>();
			
			data.put("appid", config.getAppID());
			
			data.put("mch_id", config.getMchID());
			
			data.put("nonce_str", WXPayUtil.generateNonceStr());
			
			data.put("out_trade_no", paramMap.get("out_trade_no"));
			
			data.put("out_refund_no", paramMap.get("out_refund_no"));
			
			data.put("total_fee", WXPayUtil.transUnit(paramMap.get("total_fee")));
			
			data.put("refund_fee", WXPayUtil.transUnit(paramMap.get("refund_fee")));
			
			data.put("refund_desc", paramMap.get("refund_desc"));
			
			data.put("sign", WXPayUtil.generateSignature(data, config.getKey()));
			Map<String, String> map = wxpay.refund(data);
			if(WXPayConstants.SUCCESS.equals(map.get("return_code"))){
				return new Result(CodeConstants.RESULT_SUCCESS,"success", map.get("return_code")).toString();
			}else{
				return new Result(CodeConstants.RESULT_FAIL,"fail", map.get("err_code_des")).toString();
			}
		} catch (Exception e) {
			return new Result(CodeConstants.RESULT_FAIL,"fail","交易异常").toString();
		}
	}
	public static void main(String[] args) throws Exception {
		//测试微信支付回调验证签名
		WXPayConfigImpl config= WXPayConfigImpl.getInstance();
		WXPay wxpay= new WXPay(config, WXPayConstants.NOTIFY_URL, true, false);
		
		Map<String,String> paramMap=new HashMap<>();
		
		paramMap.put("appid", config.getAppID());
		
		paramMap.put("mch_id", config.getMchID());
		
		paramMap.put("nonce_str", "233f60938b4d4e728c93761f570a1754");
		
		paramMap.put("transaction_id", "4200000046201712251223685611");
		
		paramMap.put("bank_type", "CFT");
		
		paramMap.put("sign", "8242351F0ABDF29A11566A2EBAF475A92E67F49534599BFA210F29C941B869C9");
		
		paramMap.put("openid", "oHkLxt_9IbjqYnr4Dik9xUweswaI");
		
		paramMap.put("fee_type", "CNY");
		
		paramMap.put("cash_fee", "1");
		
		paramMap.put("out_trade_no", "TSHN2017122500003");
		
		paramMap.put("total_fee", "1");
		
		paramMap.put("trade_type", "NATIVE");
		
		paramMap.put("result_code", "SUCCESS");
		
		paramMap.put("time_end", "20171225165016");
		
		paramMap.put("is_subscribe", "20171225165016");

		paramMap.put("return_code", "SUCCESS");
		
		boolean notifyFlag=wxpay.isPayResultNotifySignatureValid(paramMap);
		
		System.out.println(notifyFlag);
		
	}
	 

}
