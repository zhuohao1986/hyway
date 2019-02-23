package com.way.open.pay.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.way.common.constant.CodeConstants;
import com.way.common.pay.PayUtils;
import com.way.common.stdo.Result;
import com.way.common.utils.DateUtils;
import com.way.open.pay.service.UnionService;
import com.way.open.pay.util.union.AcpService;
import com.way.open.pay.util.union.LogUtil;
import com.way.open.pay.util.union.SDKConfig;
import com.way.open.pay.util.union.SDKConstants;
import com.way.open.pay.util.wx.WXPayUtil;
@Service
public class UnionServiceImpl implements  UnionService{

	@Override
	public String unionPay(Map<String, String> paramMap) throws Exception {
		String htmlResult = null; 
		Map<String, String> requestData = new HashMap<String, String>();
		/***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
		requestData.put("version", SDKConfig.getConfig().getVersion());   			      //版本号，全渠道默认值
		requestData.put("encoding", SDKConstants.encoding); 	      //字符集编码，可以使用UTF-8,GBK两种方式
		requestData.put("signMethod", SDKConfig.getConfig().getSignMethod()); //签名方法
		requestData.put("txnType", "01");               			  //交易类型 ，01：消费
		requestData.put("txnSubType", "01");            			  //交易子类型， 01：自助消费
		requestData.put("bizType", "000202");           			  //业务类型 000202: B2B
		requestData.put("channelType", "07");           			  //渠道类型 固定07
		
		/***商户接入参数***/
		requestData.put("merId", SDKConfig.getConfig().getMerchantId());    	          			  //商户号码，请改成自己申请的正式商户号或者open上注册得来的777测试商户号
		requestData.put("accessType", "0");             			  //接入类型，0：直连商户 
		requestData.put("orderId",paramMap.get("orderId"));             //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则		
		requestData.put("txnTime", DateUtils.getNowDateTime14String());        //订单发送时间，取系统时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
		requestData.put("currencyCode", "156");         			  //交易币种（境内商户一般是156 人民币）		
		
		requestData.put("txnAmt",WXPayUtil.transUnit(paramMap.get("txnAmt")));   //交易金额，单位分，不要带小数点
		
		//前台通知地址 （需设置为外网能访问 http https均可），支付成功后的页面 点击“返回商户”按钮的时候将异步通知报文post到该地址
		//如果想要实现过几秒中自动跳转回商户页面权限，需联系银联业务申请开通自动返回商户权限
		//异步通知参数详见open.unionpay.com帮助中心 下载  产品接口规范  网关支付产品接口规范 消费交易 商户通知
		requestData.put("frontUrl", SDKConfig.getConfig().getFrontUrl());
		
		//后台通知地址（需设置为【外网】能访问 http https均可），支付成功后银联会自动将异步通知报文post到商户上送的该地址，失败的交易银联不会发送后台通知
		//后台通知参数详见open.unionpay.com帮助中心 下载  产品接口规范  网关支付产品接口规范 消费交易 商户通知
		//注意:1.需设置为外网能访问，否则收不到通知    2.http https均可  3.收单后台通知后需要10秒内返回http200或302状态码 
		//    4.如果银联通知服务器发送通知后10秒内未收到返回状态码或者应答码非http200，那么银联会间隔一段时间再次发送。总共发送5次，每次的间隔时间为0,1,2,4分钟。
		//    5.后台通知地址如果上送了带有？的参数，例如：http://abc/web?a=b&c=d 在后台通知处理程序验证签名之前需要编写逻辑将这些字段去掉再验签，否则将会验签失败
		requestData.put("backUrl", SDKConfig.getConfig().getBackUrl());
		
		//实现网银前置的方法：
		//上送issInsCode字段，该字段的值参考《平台接入接口规范-第5部分-附录》（全渠道平台银行名称-简码对照表）2）联系银联业务运营部门开通商户号的网银前置权限
		//requestData.put("issInsCode", "ABC");                 //发卡机构代码
		
		// 订单超时时间。
		// 超过此时间后，除网银交易外，其他交易银联系统会拒绝受理，提示超时。 跳转银行网银交易如果超时后交易成功，会自动退款，大约5个工作日金额返还到持卡人账户。
		// 此时间建议取支付时的北京时间加15分钟。
		// 超过超时时间调查询接口应答origRespCode不是A6或者00的就可以判断为失败。
		requestData.put("payTimeout", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date().getTime() + 15 * 60 * 1000));

		// 请求方保留域，
        // 透传字段，查询、通知、对账文件中均会原样出现，如有需要请启用并修改自己希望透传的数据。
        // 出现部分特殊字符时可能影响解析，请按下面建议的方式填写：
        // 1. 如果能确定内容不会出现&={}[]"'等符号时，可以直接填写数据，建议的方法如下。
//		requestData.put("reqReserved", "透传信息1|透传信息2|透传信息3");
        // 2. 内容可能出现&={}[]"'符号时：
        // 1) 如果需要对账文件里能显示，可将字符替换成全角＆＝｛｝【】“‘字符（自己写代码，此处不演示）；
        // 2) 如果对账文件没有显示要求，可做一下base64（如下）。
        //    注意控制数据长度，实际传输的数据长度不能超过1024位。
        //    查询、通知等接口解析时使用new String(Base64.decodeBase64(reqReserved), DemoBase.encoding);解base64后再对数据做后续解析。
//		requestData.put("reqReserved", Base64.encodeBase64String("任意格式的信息都可以".toString().getBytes(DemoBase.encoding)));
		
		/**请求参数设置完毕，以下对请求参数进行签名并生成html表单，将表单写入浏览器跳转打开银联页面**/
		Map<String, String> reqData = AcpService.sign(requestData,SDKConstants.encoding);  //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
		String requestFrontUrl = SDKConfig.getConfig().getFrontRequestUrl();  //获取请求银联的前台地址：对应属性文件acp_sdk.properties文件中的acpsdk.frontTransUrl
		htmlResult = AcpService.createAutoFormHtml(requestFrontUrl,reqData,SDKConstants.encoding);   //生成自动跳转的Html表单
		LogUtil.writeLog("打印请求HTML，此为请求报文，为联调排查问题的依据："+htmlResult);
		//将生成的html写到浏览器中完成自动跳转打开银联支付页面；这里调用signData之后，将html写到浏览器跳转到银联页面之前均不能对html中的表单项的名称和值进行修改，如果修改会导致验签不通过
		return htmlResult;
		
	}

	@Override
	public String unionOrderQuery(Map<String, String> paramMap) throws Exception {
		String orderId = (String) paramMap.get("orderId");
		String txnTime = (String) paramMap.get("txnTime");
		Map<String, String> data = new HashMap<String, String>();
		/***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
		data.put("version", SDKConfig.getConfig().getVersion()); // 版本号
		data.put("encoding", SDKConstants.encoding); // 字符集编码 可以使用UTF-8,GBK两种方式
		data.put("signMethod", SDKConfig.getConfig().getSignMethod()); // 签名方法
		data.put("txnType", "00"); // 交易类型 00-默认
		data.put("txnSubType", "00"); // 交易子类型 默认00
		data.put("bizType", "000202"); // 业务类型

		/***商户接入参数***/
		data.put("merId", SDKConfig.getConfig().getMerchantId()); // 商户号码，请改成自己申请的商户号或者open上注册得来的777商户号测试
		data.put("accessType", "0"); // 接入类型，商户接入固定填0，不需修改

		/***要调通交易以下字段必须修改***/
		data.put("orderId", orderId); // ****商户订单号，每次发交易测试需修改为被查询的交易的订单号
		data.put("txnTime", txnTime); // ****订单发送时间，每次发交易测试需修改为被查询的交易的订单发送时间

		/**请求参数设置完毕，以下对请求参数进行签名并发送http post请求，接收同步应答报文------------->**/

		Map<String, String> reqData = AcpService.sign(data, SDKConstants.encoding); // 报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
		String url = SDKConfig.getConfig().getSingleQueryUrl(); // 交易请求url从配置文件读取对应属性文件acp_sdk.properties中的
																// acpsdk.singleQueryUrl
		Map<String, String> rspData = AcpService.post(reqData, url, SDKConstants.encoding); // 发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
		/**对应答码的处理，请根据您的业务逻辑来编写程序,以下应答码处理逻辑仅供参考------------->**/
		// 应答码规范参考open.unionpay.com帮助中心 下载 产品接口规范 《平台接入接口规范-第5部分-附录》
		String resultMsg = null;
		if (rspData.isEmpty()) {
			// 未返回正确的http状态
			resultMsg = "未获取到返回报文或返回http状态码非200";
			LogUtil.writeErrorLog(resultMsg);
			return new Result(CodeConstants.RESULT_FAIL, resultMsg).toString();
		}
		if (!AcpService.validate(rspData, SDKConstants.encoding)) {
			// TODO 检查验证签名失败的原因
			resultMsg = "银联验证签名失败";
			LogUtil.writeErrorLog(resultMsg);
			return new Result(CodeConstants.RESULT_FAIL, resultMsg).toString();
		}
		LogUtil.writeLog("验证签名成功");
		String respCode = rspData.get("respCode");
		if (!("00").equals(respCode)) {
			if (("34").equals(respCode)) {
				// 订单不存在，可认为交易状态未明，需要稍后发起交易状态查询，或依据对账结果为准
				resultMsg = "银联订单不存在，可认为交易状态未明，需要稍后发起交易状态查询，或依据对账结果为准";
			} else {// 查询交易本身失败，如应答码10/11检查查询报文是否正确
				resultMsg = "银联查询交易本身失败，如应答码10/11检查查询报文是否正确";
			}
			return new Result(CodeConstants.RESULT_FAIL,"fail", resultMsg).toString();
		}
		// 如果查询交易成功
		String origRespCode = rspData.get("origRespCode");
		if (!("00").equals(origRespCode)) {
			if (("03").equals(origRespCode) || ("04").equals(origRespCode) || ("05").equals(origRespCode)) {
				// 订单处理中或交易状态未明，需稍后发起交易状态查询交易 【如果最终尚未确定交易是否成功请以对账文件为准】
				resultMsg = "银联订单处理中或交易状态未明，需稍后发起交易状态查询交易 【如果最终尚未确定交易是否成功请以对账文件为准】";
			} else {
				// 其他应答码为交易失败
				resultMsg = "银联交易失败";
			}
			return new Result(CodeConstants.RESULT_FAIL,"fail",resultMsg).toString();
		}
		// 交易成功，更新商户订单状态
		return new Result(CodeConstants.RESULT_SUCCESS,"ok", "银联交易成功").toString();
	}
	
	@Override
	public String unionNotify(Map<String, String> paramMap) throws Exception {
		String resultMsg = null;
		if (!AcpService.validate(paramMap, SDKConstants.encoding)) {
			// TODO 检查验证签名失败的原因
			resultMsg = "银联验证签名失败";
			LogUtil.writeErrorLog(resultMsg);
			return new Result(CodeConstants.RESULT_FAIL,"fail", resultMsg).toString();
		}
		LogUtil.writeLog("验证签名成功");
		String respCode = paramMap.get("respCode");
		if (!("00").equals(respCode)) {
			if (("34").equals(respCode)) {
				// 订单不存在，可认为交易状态未明，需要稍后发起交易状态查询，或依据对账结果为准
				resultMsg = "银联订单不存在，可认为交易状态未明，需要稍后发起交易状态查询，或依据对账结果为准";
			} else {// 查询交易本身失败，如应答码10/11检查查询报文是否正确
				resultMsg = "银联查询交易本身失败，如应答码10/11检查查询报文是否正确";
			}
			return new Result(CodeConstants.RESULT_FAIL,"fail", resultMsg).toString();
		}
		// 如果查询交易成功
		String origRespCode = paramMap.get("origRespCode");
		if (!("00").equals(origRespCode)) {
			if (("03").equals(origRespCode) || ("04").equals(origRespCode) || ("05").equals(origRespCode)) {
				// 订单处理中或交易状态未明，需稍后发起交易状态查询交易 【如果最终尚未确定交易是否成功请以对账文件为准】
				resultMsg = "银联订单处理中或交易状态未明，需稍后发起交易状态查询交易 【如果最终尚未确定交易是否成功请以对账文件为准】";
			} else {
				// 其他应答码为交易失败
				resultMsg = "银联交易失败";
			}
			return new Result(CodeConstants.RESULT_FAIL, "fail",resultMsg).toString();
		}
		// 交易成功
		return new Result(CodeConstants.RESULT_SUCCESS, "ok","银联交易成功").toString();
	}
	
     /**
      * 交易说明 : 	1）以后台通知或交易状态查询交易（Form_6_5_Query）确定交易成功，建议发起查询交易的机制：可查询N次（不超过6次），每次时间间隔2N秒发起,即间隔1，2，4，8，16，32S查询（查询到03，04，05继续查询，否则终止查询）
              	2）退货金额不超过总金额，可以进行多次退货
              	3）退货能对11个月内的消费做（包括当清算日），支持部分退货或全额退货，到账时间较长，一般1-10个清算日（多数发卡行5天内，但工行可能会10天），所有银行都支持
      */
	@Override
	public String refundOrder(Map<String, String> closePayMap) {
		String orderId=DateUtils.getNowDateTime14String()+PayUtils.getRandomStringByLength(6);;
		String origQryId=closePayMap.get("origQryId");
		String txnAmt = WXPayUtil.transUnit(closePayMap.get("txnAmt"));
		Map<String, String> data = new HashMap<String, String>();
		/***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
		data.put("version", SDKConfig.getConfig().getVersion());               //版本号
		data.put("encoding", SDKConstants.encoding);             //字符集编码 可以使用UTF-8,GBK两种方式
		data.put("signMethod", SDKConfig.getConfig().getSignMethod()); //签名方法
		data.put("txnType", "04");                           //交易类型 04-退货		
		data.put("txnSubType", "00");                        //交易子类型  默认00		
		data.put("bizType", "000202");                       //业务类型 B2B网关支付，手机wap支付	
		data.put("channelType", "07");                       //渠道类型，07-PC，08-手机		
		
		/***商户接入参数***/
		data.put("merId", SDKConfig.getConfig().getMerchantId());                //商户号码，请改成自己申请的商户号或者open上注册得来的777商户号测试
		data.put("accessType", "0");                         //接入类型，商户接入固定填0，不需修改		
		data.put("orderId", orderId);          //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则，重新产生，不同于原消费		
		data.put("txnTime", DateUtils.getNowDateTime14String());      //订单发送时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效		
		data.put("currencyCode", "156");                     //交易币种（境内商户一般是156 人民币）		
		data.put("txnAmt", txnAmt);                          //****退货金额，单位分，不要带小数点。退货金额小于等于原消费金额，当小于的时候可以多次退货至退货累计金额等于原消费金额		
		//data.put("reqReserved", "透传信息");                  //请求方保留域，如需使用请启用即可；透传字段（可以实现商户自定义参数的追踪）本交易的后台通知,对本交易的交易状态查询交易、对账文件中均会原样返回，商户可以按需上传，长度为1-1024个字节。出现&={}[]符号时可能导致查询接口应答报文解析失败，建议尽量只传字母数字并使用|分割，或者可以最外层做一次base64编码(base64编码之后出现的等号不会导致解析失败可以不用管)。		
		data.put("backUrl", SDKConfig.getConfig().getBackUrl());               //后台通知地址，后台通知参数详见open.unionpay.com帮助中心 下载  产品接口规范  网关支付产品接口规范 退货交易 商户通知,其他说明同消费交易的后台通知
		
		/***要调通交易以下字段必须修改***/
		data.put("origQryId", origQryId);      //****原消费交易返回的的queryId，可以从消费交易后台通知接口中或者交易状态查询接口中获取
		
		/**请求参数设置完毕，以下对请求参数进行签名并发送http post请求，接收同步应答报文------------->**/
		Map<String, String> reqData  = AcpService.sign(data,SDKConstants.encoding);//报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
		String url = SDKConfig.getConfig().getBackRequestUrl();//交易请求url从配置文件读取对应属性文件acp_sdk.properties中的 acpsdk.backTransUrl

		 Map<String, String> rspData = AcpService.post(reqData,url,SDKConstants.encoding);//这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
		
		/**对应答码的处理，请根据您的业务逻辑来编写程序,以下应答码处理逻辑仅供参考------------->**/
		//应答码规范参考open.unionpay.com帮助中心 下载  产品接口规范  《平台接入接口规范-第5部分-附录》
		if(!rspData.isEmpty()){
			if(AcpService.validate(rspData,SDKConstants.encoding)){
				String respCode = rspData.get("respCode");
				if("00".equals(respCode)){
					LogUtil.printResponseLog(JSONObject.toJSONString(rspData));
					//交易已受理，等待接收后台通知更新订单状态,也可以主动发起 查询交易确定交易状态。
					return new Result(CodeConstants.RESULT_SUCCESS, "ok","银联交易退款交易已受理成功").toString();
					//TODO
				}else if("03".equals(respCode)|| 
						 "04".equals(respCode)||
						 "05".equals(respCode)){
					//后续需发起交易状态查询交易确定交易状态
					return new Result(CodeConstants.RESULT_FAIL, "fail","需查询交易确定交易状态").toString();
				}else{
					return new Result(CodeConstants.RESULT_FAIL, "fail",respCode).toString();
				}
			}else{
				//TODO 检查验证签名失败的原因
				return new Result(CodeConstants.RESULT_FAIL, "fail","验证签名失败").toString();
			}
		}else{
			//未返回正确的http状态
			return new Result(CodeConstants.RESULT_FAIL, "fail","未获取到返回报文或返回http状态码非200").toString();
		}
	}

}
