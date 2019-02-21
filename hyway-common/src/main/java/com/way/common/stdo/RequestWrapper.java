package com.way.common.stdo;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;
import com.way.common.utils.DateUtils;


/**
 * 请求数据包装类
 * 
 * @author 
 */
public class RequestWrapper implements Serializable
{
	private static final long serialVersionUID = 1L;
	/** 来源 **/
	private String source;
	/** 登陆者token **/
	private String loginToken;
	

	/** 发起请求时间戳,格式：yyyyMMddHHmmss 例如：20140920082030 **/
	private String reqDate;
	/** 请求值 JSON格式的具体参数**/
	private String value;
    private JSONObject json;
	public RequestWrapper()
	{
	}
	/*public RequestWrapper(JSONObject json)
	{
		super();
		this.source = ConfigurationUtil.getInstance().getHessianConfig(SysConstants.HESSIAN_CONFIG_REQ_SOURCE);
		this.reqDate = DateUtils.getNowDateTime14String();
		this.json = json;
	}*/
	public RequestWrapper(String source, String value)
	{
		super();
		this.source = source;
		this.reqDate = DateUtils.getNowDateTime14String();
		this.value = value;
	}
	
	public RequestWrapper(String source,String loginToken, String value)
	{
		super();
		this.source = source;
		this.loginToken=loginToken;
		this.reqDate = DateUtils.getNowDateTime14String();
		this.value = value;
	}

	public String getSource()
	{
		return source;
	}

	public void setSource(String source)
	{
		this.source = source;
	}
	


	public String getReqDate()
	{
		return reqDate;
	}

	public void setReqDate(String reqDate)
	{
		this.reqDate = reqDate;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	@Override
	public String toString()
	{
		return JSONObject.toJSONString(this);
	}

	public JSONObject getJson() {
		return json;
	}

	public void setJson(JSONObject json) {
		this.json = json;
	}
	public String getLoginToken() {
		return loginToken;
	}
	public void setLoginToken(String loginToken) {
		this.loginToken = loginToken;
	}

}