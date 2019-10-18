package com.way.authentication.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.way.api.feign.SysAuthFeignApi;
import com.way.common.constant.CodeConstants;
import com.way.common.context.BaseController;
import com.way.common.stdo.Result;
import com.way.common.utils.IPUtils;
import com.way.common.utils.QRCodeUtil;

/**
 * @ClassNameQRScanController
 * @Description
 * @Author @Date2019/10/10 19:10
 * @Version V1.0
 **/
@RestController
@RequestMapping(value = "/scan", produces = MediaType.APPLICATION_JSON_VALUE)
public class ScanController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

	@Autowired
	private SysAuthFeignApi sysAuthFeignApi;

	/**
	 * 查询用户分页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/token")
	public String authToken(HttpServletRequest request) {
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		try {
			initParams();
			//RequestWrapper rw = new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
			String ipAddress = IPUtils.getIpAddress(request);
			jsonData.put("ip", ipAddress);
			String authTokenStr = sysAuthFeignApi.authToken(jsonData);
			result = JSONObject.parseObject(authTokenStr, Result.class);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return result.toString();
	}
	// token对应的二维码图像
	@RequestMapping(value = "/qrcode")
	public void getQRCodeImg(String token, HttpServletResponse response) {
		try {
			// 传入图像链接，还能够生成带logo的二维码
			QRCodeUtil.encode(token, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询用户
	 * 
	 * @return
	 */
	@RequestMapping(value = "/info")
	public String getAuthInfo() {
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		try {
			initParams();
			String authInfo = sysAuthFeignApi.getAuthInfo(jsonData);
			result = JSONObject.parseObject(authInfo, Result.class);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return result.toString();
	}
	/**
	 * setAuthState
	 * 
	 * @return
	 */
	@RequestMapping(value = "/user")
	public String setAuthState() {
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		try {
			initParams();
			String authState = sysAuthFeignApi.setAuthState(jsonData);
			result = JSONObject.parseObject(authState, Result.class);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return result.toString();
	}

}