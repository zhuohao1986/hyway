package com.way.common.context;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.alibaba.fastjson.JSONObject;
import com.way.common.utils.StringUtils;

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

	protected Map paramMap = null; // request的map参数

	protected JSONObject jsonData = null; // 前台传来的json数据
	protected JSONObject retJson = null; // 返回给ajax的json数据

	protected HttpServletRequest request;
	private HttpServletResponse response;

	@ModelAttribute
	public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	public HttpServletRequest getRequest() {
		return this.request;
	}

	public HttpServletResponse getResponse() {
		return this.response;
	}

	/**
	 * 获取session对象
	 * 
	 * @return
	 */
	public HttpSession getSession() {
		return this.request.getSession();
	}

	/**
	 * controller初始化参数
	 *  create by:
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getRequestParamters() {
		// 获取所有的请求参数
		Map properties = getRequest().getParameterMap();
		// 返回值Map
		Map returnMap = new HashMap();
		Iterator entries = properties.entrySet().iterator();
		Map.Entry entry;
		String name = "";
		String value = "";
		// 读取map中的值
		while (entries.hasNext()) {

			entry = (Map.Entry) entries.next();
			logger.info("name:" + entry.getKey());

			name = (String) entry.getKey();
			Object valueObj = entry.getValue();
			if (null == valueObj) {
				value = " ";
			} else if (valueObj instanceof String[]) {
				String[] values = (String[]) valueObj;
				for (int i = 0; i < values.length; i++) {
					value = values[i] + ",";
				}
				value = value.substring(0, value.length() - 1);
			} else {
				value = valueObj.toString();
			}
			logger.info("value:" + value);
			// 将读取到的值存入map中
			returnMap.put(name, value);
			// 移除map中为空的参数
			if ("".equals(value)) {
				returnMap.remove(name);
			}
		}

		/*
		 * String userJson = userApi.getUserByLoginName(loginName); User user =
		 * null; Result result = null; result = (Result)
		 * JSONObject.parse(userJson); user = (User) result.getValue();
		 * returnMap.put("updateby", user.getUserId());
		 * returnMap.put("createby", user.getUserId());
		 * returnMap.put("createTime", new Date()); returnMap.put("updateTime",
		 * new Date()); returnMap.put("companyId", user.getCompanyId());
		 * returnMap.put("locationId", user.getLocationId());
		 */

		return returnMap;
	}

	/**
	 * ajax输出
	 * @param content
	 * @param type
	 * @return
	 */
	public String ajax(String content, String type) {
		try {
			HttpServletResponse response = getResponse();
			response.setContentType(type + ";charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);// 指定实体有效期
			PrintWriter out = response.getWriter();
			out.println(content);
			out.flush();
			out.close();
		} catch (IOException e) {
			logger.error("basecontroller-ajax输入异常！", e);
		}
		return null;
	}

	/**
	 * ajax,mime类型，text/html
	 * @param xml
	 * @return
	 */
	@SuppressWarnings("unused") // 取消警告
	private String ajaxText(String text) {
		return ajax(text, "text/plain");// 纯文本格式
	}

	/**
	 * ajax,mime类型，text/html
	 * @param xml
	 * @return
	 */
	@SuppressWarnings("unused")
	private String ajaxHtml(String html) {
		return ajax(html, "text/html");
	}

	/**
	 * ajax,mime类型，text/xml
	 * @param xml
	 * @return
	 */
	@SuppressWarnings("unused")
	private String ajaxXml(String xml) {
		return ajax(xml, "text/xml");
	}

	/**
	 * ajax,mime类型，text/javascript
	 * @param xml
	 * @return
	 */
	private String ajaxJavaScript(String xml) {
		return ajax(xml, "text/javascript");
	}

	/**
	 * 公共的处理和封装返回给前台ajax的数据
	 * @param jo
	 */
	public void renderJson4JsonObj(JSONObject jo) {
		String redata = null;
		redata = (jo == null ? "" : jo.toJSONString());
		// 如果jsonpCallback回调函数不为空，则说明是jsonp方式的请求，则封装格式
		// 否则为普通的ajax提交
		if (StringUtils.isNotEmpty(jsonpCallback)) {
			StringBuffer sb = new StringBuffer();
			redata = sb.append(jsonpCallback).append("(").append(redata).append(")").toString();
		}
		ajaxJavaScript(redata);
	}

	/**
	 * 公共的处理和封装返回给前台ajax的数据
	 * @param jo
	 */
	public void renderJson4Str(String reval) {
		String redata = null;
		retJson = JSONObject.parseObject(reval);
		redata = (retJson == null ? new JSONObject().toString() : retJson.toString());
		// 如果jsonpCallback回调函数不为空，则说明是jsonp方式的请求，则封装格式
		// 否则为普通的ajax提交
		if (StringUtils.isNotEmpty(jsonpCallback)) {
			StringBuffer sb = new StringBuffer();
			redata = sb.append(jsonpCallback).append("(").append(redata).append(")").toString();
		}
		ajaxJavaScript(redata);
	}
}
