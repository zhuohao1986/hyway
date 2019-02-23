/*
 * package com.way.open.pay.aop;
 * 
 * import java.lang.annotation.Annotation; import java.lang.reflect.Method;
 * import java.util.HashMap;
 * 
 * import org.aspectj.lang.ProceedingJoinPoint; import
 * org.aspectj.lang.annotation.Around; import
 * org.aspectj.lang.annotation.Aspect; import org.slf4j.Logger; import
 * org.slf4j.LoggerFactory; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.stereotype.Component;
 * 
 * import com.alibaba.fastjson.JSONObject; import
 * com.alibaba.fastjson.TypeReference; import
 * com.mwclg.ltd.common.constants.PayConstants; import
 * com.mwclg.ltd.common.trans.po.TrPayLog; import
 * com.mwclg.ltd.common.utils.pay.PayWayEnum; import
 * com.mwclg.ltd.order.api.OrderApi; import
 * com.way.common.constant.CodeConstants; import
 * com.way.common.constant.enums.PayTypeEnum; import
 * com.way.common.stdo.RequestWrapper; import com.way.common.stdo.Result; import
 * com.way.common.utils.DateUtils; import com.way.open.pay.aop.PayLog.PayType;
 * import com.way.open.pay.util.wx.WXPayUtil;
 * 
 *//**
	 * 交易日志采集注解处理类
	 */
/*
 * @Component
 * 
 * @Aspect public class TransLogAspectHandel { public static Logger logger =
 * LoggerFactory.getLogger(TransLogAspectHandel.class);
 * 
 * @Autowired private OrderApi orderApi;
 * 
 * @Around("@annotation(com.mwclg.ltd.pay.aop.PayLog)") public Object
 * validateAround(ProceedingJoinPoint joinPoint) throws Throwable { Object obj =
 * null;
 * logger.info("===============step 1 生成日志【业务方法执行前before】===================");
 * TrPayLog mTransLog = new TrPayLog();
 * mTransLog.setGmtCreate(DateUtils.getCurrentDate());// 创建时间
 * 
 * PayLog trPayLog = null; Object[] args = null; Method method = null; Object
 * target = null; String methodName = null; try { methodName =
 * joinPoint.getSignature().getName(); target = joinPoint.getTarget(); method =
 * getMethodByClassAndName(target.getClass(), methodName); // 得到拦截的方法 args =
 * joinPoint.getArgs(); // 方法的参数 trPayLog = (PayLog)
 * getAnnotationByMethod(method, PayLog.class); obj = joinPoint.proceed();
 * Result notifyResult=JSONObject.parseObject(String.valueOf(obj),Result.class);
 * if(CodeConstants.RESULT_FAIL.equals(notifyResult.getCode())){ logger.debug(
 * "支付通知失败不记录日志",notifyResult.getValue()); return obj; } if (trPayLog.payType()
 * == PayType.NOTIFY) { // 异步通知 RequestWrapper rw =
 * JSONObject.parseObject(String.valueOf(args[0]), RequestWrapper.class); String
 * valueJson = rw.getValue(); HashMap<String,String>
 * paramsMap=JSONObject.parseObject(valueJson, new TypeReference<HashMap<String,
 * String>>(){}); String payWay=paramsMap.get("payWay"); String outTradeNo = "";
 * String tradeNo=""; String payTime=""; String totalAmount=""; //String
 * notifyTime=""; String tradeCode="";
 * mTransLog.setTradeType(PayType.PAY.name()); if
 * (PayTypeEnum.ALIPAY.name().equals(payWay)) { // 支付宝 outTradeNo
 * =paramsMap.get("out_trade_no"); tradeNo=paramsMap.get("trade_no");
 * totalAmount=paramsMap.get("total_amount");
 * payTime=paramsMap.get("gmt_payment");
 * tradeCode=paramsMap.get("trade_status");
 * //notifyTime=paramsMap.get("notify_time"); } else if
 * (PayWayEnum.WX.name().equals(payWay)) { // 微信 outTradeNo
 * =paramsMap.get("out_trade_no"); tradeNo=paramsMap.get("transaction_id");
 * totalAmount=WXPayUtil.transWxUnit(paramsMap.get("total_fee"));
 * payTime=paramsMap.get("time_end"); tradeCode=paramsMap.get("result_code");
 * }else if (PayWayEnum.UNIONPAY.name().equals(payWay)) { // 银联 //消费类型 01 交易 04
 * 退货 String txnType=paramsMap.get("txnType"); if("04".equals(txnType)) {
 * mTransLog.setTradeType(PayType.REFUND.name()); } outTradeNo
 * =paramsMap.get("orderId"); tradeNo=paramsMap.get("queryId");
 * totalAmount=WXPayUtil.transWxUnit(paramsMap.get("txnAmt"));
 * tradeCode=paramsMap.get("respCode"); payTime=paramsMap.get("txnTime"); }
 * mTransLog.setPayment(payWay); mTransLog.setOutTradeNo(outTradeNo);
 * mTransLog.setTradeNo(tradeNo);
 * mTransLog.setPayTime(DateUtils.convertFormatDateStr(payTime,
 * "yyyyMMddHHmmss")); mTransLog.setTotalAmount(totalAmount);
 * mTransLog.setTradeCode(tradeCode);
 * mTransLog.setGmtModified(DateUtils.getCurrentDate());
 * mTransLog.setTradeStatus(PayConstants.S);
 * 
 * String logStr=orderApi.selectPayLog(new
 * RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB,
 * JSONObject.toJSONString(mTransLog)).toString()); Result
 * logStrResult=JSONObject.parseObject(String.valueOf(logStr), Result.class);
 * if(CodeConstants.RESULT_FAIL.equals(logStrResult.getCode())){ return new
 * Result(CodeConstants.RESULT_SUCCESS, "success","支付日志已存在"); } String
 * paylogStr=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB,
 * JSONObject.toJSONString(mTransLog)).toString(); String
 * logStrStr=orderApi.savePayLog(paylogStr); Result
 * orderResult=JSONObject.parseObject(String.valueOf(logStrStr), Result.class);
 * if(CodeConstants.RESULT_SUCCESS.equals(orderResult.getCode())){
 * logger.debug(orderResult.toString()); } } else if (trPayLog.payType() ==
 * PayType.PAY) { // 同步
 * 
 * }else if (trPayLog.payType() == PayType.REFUND){ RequestWrapper rw =
 * JSONObject.parseObject(String.valueOf(args[0]), RequestWrapper.class); String
 * valueJson = rw.getValue(); HashMap<String,String>
 * paramsMap=JSONObject.parseObject(valueJson, new TypeReference<HashMap<String,
 * String>>(){}); String payWay=paramsMap.get("payWay"); String outTradeNo = "";
 * String tradeNo=""; String totalAmount=""; String tradeCode=""; outTradeNo
 * =paramsMap.get("orderNo"); tradeNo=paramsMap.get("tradeNo");
 * totalAmount=paramsMap.get("totalAmount"); mTransLog.setPayment(payWay);
 * mTransLog.setOutTradeNo(outTradeNo); mTransLog.setTradeNo(tradeNo);
 * mTransLog.setPayTime(DateUtils.getCurrentDate());
 * mTransLog.setTotalAmount(totalAmount); mTransLog.setTradeCode(tradeCode);
 * mTransLog.setGmtModified(DateUtils.getCurrentDate());
 * mTransLog.setTradeStatus(PayConstants.S);
 * mTransLog.setTradeType(PayType.REFUND.name());
 * 
 * if(PayWayEnum.UNIONPAY.name().equals(payWay)) { logger.info("银联不记录退款同步日志");
 * }else { String paylogStr=new
 * RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB,
 * JSONObject.toJSONString(mTransLog)).toString(); String
 * logStrStr=orderApi.savePayLog(paylogStr); Result
 * orderResult=JSONObject.parseObject(String.valueOf(logStrStr), Result.class);
 * if(CodeConstants.RESULT_SUCCESS.equals(orderResult.getCode())){
 * logger.debug(orderResult.toString()); } } } } catch (Exception e) {
 * logger.info("交易日志记录异常"); e.printStackTrace(); }
 * logger.info("===============step 3 生成日志成功==================="); return obj; }
 * 
 *//**
	 * 根据目标方法和注解类型 得到该目标方法的指定注解
	 */
/*
 * public Annotation getAnnotationByMethod(Method method, Class annoClass) {
 * Annotation all[] = method.getAnnotations(); for (Annotation annotation : all)
 * { if (annotation.annotationType() == annoClass) { return annotation; } }
 * return null; }
 * 
 *//**
	 * 根据类和方法名得到方法
	 *//*
		 * public Method getMethodByClassAndName(Class c, String methodName) { Method[]
		 * methods = c.getDeclaredMethods(); for (Method method : methods) { if
		 * (method.getName().equals(methodName)) { return method; } } return null; } }
		 */