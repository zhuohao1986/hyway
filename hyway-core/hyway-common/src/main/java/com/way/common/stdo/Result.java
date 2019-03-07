package com.way.common.stdo;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.way.common.constant.CodeConstants;
import com.way.common.exception.IError;


public class Result implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String code;
	private String message;
	private Object value;

	public Result()
	{
		super();
		this.code = CodeConstants.RESULT_SUCCESS;
	}

	public Result(Object value)
	{
		super();
		this.code = CodeConstants.RESULT_SUCCESS;
		this.value = value;
	}

	public Result(String code, String message)
	{
		super();
		this.code = code;
		this.message = message;
		this.value = null;
	}

	public Result(String code, String message, Object value)
	{
		super();
		this.code = code;
		this.message = message;
		this.value = value;
	}

	public Result(String code, IError defaultError) {
		super();
		this.code = code;
		this.value=defaultError.getErrorCode();
		this.message = defaultError.getErrorMessage();
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public Object getValue()
	{
		return value;
	}

	public void setValue(Object value)
	{
		this.value = value;
	}

	@Override
	public String toString()
	{
		return JSONObject.toJSONString(this, SerializerFeature.DisableCircularReferenceDetect);
	}
	/**
	 * 空数据返回空字符串
	 * @return
	 */
	public String toJSONString() {
		//JSONObject.toJSONString(this,SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteNullStringAsEmpty)
		return JSONObject.toJSONString(this,filter);
	}
	private ValueFilter filter = new ValueFilter() {
	    @Override
	    public Object process(Object obj, String s, Object v) {
	    if(v==null)
	        return "";
	    return v;
	    }
	};

}