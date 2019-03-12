/*
 * package com.way.open.controller;
 * 
 * import java.util.HashMap; import java.util.Map;
 * 
 * import javax.servlet.http.HttpServletRequest; import
 * javax.servlet.http.HttpServletResponse;
 * 
 * import org.slf4j.Logger; import org.slf4j.LoggerFactory; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.web.bind.annotation.PathVariable; import
 * org.springframework.web.bind.annotation.RequestMapping; import
 * org.springframework.web.bind.annotation.ResponseBody; import
 * org.springframework.web.bind.annotation.RestController;
 * 
 * import com.alibaba.fastjson.JSON; import com.alibaba.fastjson.JSONObject;
 * import com.way.common.constant.CodeConstants; import
 * com.way.common.constant.enums.PayWayEnum; import
 * com.way.common.context.BaseController; import com.way.common.pay.PayUtils;
 * import com.way.common.stdo.RequestWrapper; import com.way.common.stdo.Result;
 * 
 * @RestController
 * 
 * @RequestMapping("/notify") public class NotifyController extends
 * BaseController{
 * 
 * private final static Logger logger =
 * LoggerFactory.getLogger(NotifyController.class);
 * 
 * @Autowired private PayNotifyApi payNotifyApi;
 *//**
	 * 通用支付异步返回通知 接收三方支付平台POST过来的数据
	 * 
	 * @param request
	 * @param payWay
	 * @return
	 *//*
		 * @RequestMapping("/payNotify/{payWay}")
		 * 
		 * @ResponseBody public String payNotify(HttpServletRequest
		 * request,@PathVariable("payWay") String payWay,HttpServletResponse response) {
		 * String resultData=""; try { RequestWrapper requestWrapper=null;
		 * //微信单独处理将XML转为MAP if(PayWayEnum.WXPAY.name().equals(payWay)){
		 * Map<String,String> wxMap=PayUtils.getRequestMap(request); wxMap.put("payWay",
		 * payWay); requestWrapper = new
		 * RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB,JSONObject.toJSONString(
		 * wxMap)); }else{ initParams(); requestWrapper = new
		 * RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB,jsonData.toString()); }
		 * String resultStr=payNotifyApi.payNotify(requestWrapper.toString()); Result
		 * result=JSONObject.parseObject(resultStr, Result.class);
		 * if(CodeConstants.RESULT_SUCCESS.equals(result.getCode())){
		 * if(PayWayEnum.WXPAY.name().equals(payWay)){ resultData=
		 * "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
		 * }else { resultData=String.valueOf(result.getMessage()); } }else{
		 * if(PayWayEnum.WXPAY.name().equals(payWay)){ resultData=
		 * "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[NO]]></return_msg></xml>";
		 * }else { resultData=String.valueOf(result.getMessage()); } } } catch
		 * (Exception e) { if(PayWayEnum.WXPAY.name().equals(payWay)){
		 * response.setCharacterEncoding("UTF-8"); return
		 * "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[NO]]></return_msg></xml>";
		 * }else{ return "fail"; } } return resultData; }
		 * 
		 * }
		 */