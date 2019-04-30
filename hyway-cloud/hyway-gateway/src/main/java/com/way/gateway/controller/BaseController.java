package com.way.gateway.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.alibaba.fastjson.JSONObject;

/**
 * 基础controller
 * 
 * @author
 * @date 2016-06-16
 */
public class BaseController {

	private static Logger logger = LoggerFactory.getLogger(BaseController.class);

	protected String jsonpCallback = null; // jsonp回调函数名称
	protected String params = null; // 请求传来的参数名称，json格式

	protected MultiValueMap<String, String> queryParams = null; // request的map参数

	protected JSONObject jsonData = null; // 前台传来的json数据
	protected JSONObject retJson = null; // 返回给ajax的json数据

	protected ServerHttpRequest request;
	private ServerHttpResponse response;

	private Map<String, String> paramMap;

	@ModelAttribute
	public void setReqAndRes(ServerHttpRequest request, ServerHttpResponse response) {
		this.request = request;
		this.response = response;
	}

	public ServerHttpRequest getRequest() {
		return this.request;
	}

	public ServerHttpResponse getResponse() {
		return this.response;
	}

	/**
	 * controller初始化参数 create by:
	 */
	public void initParams() {
		// 初始化返回json对象
		retJson = new JSONObject();
		if (null != getRequest()) {
			this.paramMap = getRequestParamters();

			if (null != paramMap.get("params")) {
				logger.info("params:" + params);
				this.params = (String) paramMap.get("params");
				this.jsonpCallback = (String) paramMap.get("jsonpCallback");
				this.jsonData = JSONObject.parseObject(this.params);
			}

			this.jsonData = (this.jsonData == null ? new JSONObject() : jsonData);

			this.jsonData = JSONObject.parseObject(JSONObject.toJSONString(this.paramMap)); // paramMap.getJSONObject("params");//JSONObject.parseObject(request.getParameter("params"));
			this.jsonData = (jsonData == null ? new JSONObject() : jsonData);
			this.jsonpCallback = (String) paramMap.get("jsonpCallback");
		}
	}

	/**
	 * 获得所有请求的参数
	 * 
	 * @param request
	 */
	@SuppressWarnings({ })
	public Map<String, String>  getRequestParamters() {
		// 获取所有的请求参数
		MultiValueMap<String, String> properties = getRequest().getQueryParams();
		// 返回值Map
		Map<String, String> returnMap = new LinkedHashMap<>();
		Set<Entry<String, List<String>>> entrySet = properties.entrySet();
		String name = "";
		String value="";
		for (Entry<String, List<String>> entry : entrySet) {
			logger.info("name:" + entry.getKey());
			Object valueObj = entry.getValue();
			if (null == valueObj) {
				value = null;
			} else if (valueObj instanceof String[]) {
				List<String> values = entry.getValue();
				for (String string : values) {
					value=string;
					break;
				}
			} else {
				value=valueObj.toString();
			}
			logger.info("value:" + value);
			// 将读取到的值存入map中
			returnMap.put(name, value);
			// 移除map中为空的参数
			if ("".equals(value)) {
				returnMap.remove(name);
			}
		}
		return returnMap;
	}
}
