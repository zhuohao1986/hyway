package com.way.common.constant.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
* @ClassName: PayWayEnum
* @Description: 支付方式枚举
* @author qijian
* @date 2017年10月17日 下午1:37:33
*
 */
public enum PayWayEnum {

	WXPAY("微信"),

	ALIPAY("支付宝"),

	UNIONPAY("银联"),
	
	CMBC("市场通"),

	TEST_PAY("测试模拟支付"),

	TEST_PAY_HTTP_CLIENT("测试模拟httpclient支付");

	/** 描述 */
	private String desc;

	private PayWayEnum(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public static Map<String, Map<String, Object>> toMap() {
		PayWayEnum[] ary = PayWayEnum.values();
		Map<String, Map<String, Object>> enumMap = new HashMap<String, Map<String, Object>>();
		for (int num = 0; num < ary.length; num++) {
			Map<String, Object> map = new HashMap<String, Object>();
			String key = ary[num].name();
			map.put("desc", ary[num].getDesc());
			enumMap.put(key, map);
		}
		return enumMap;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toList() {
		PayWayEnum[] ary = PayWayEnum.values();
		List list = new ArrayList();
		for (int i = 0; i < ary.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("desc", ary[i].getDesc());
			map.put("name", ary[i].name());
			list.add(map);
		}
		return list;
	}

	public static PayWayEnum getEnum(String name) {
		PayWayEnum[] arry = PayWayEnum.values();
		for (int i = 0; i < arry.length; i++) {
			if (arry[i].name().equalsIgnoreCase(name)) {
				return arry[i];
			}
		}
		return null;
	}

	/**
	 * 取枚举的json字符串
	 * 
	 * @return
	 */
	public static String getJsonStr() {
		PayWayEnum[] enums = PayWayEnum.values();
		StringBuffer jsonStr = new StringBuffer("[");
		for (PayWayEnum senum : enums) {
			if (!"[".equals(jsonStr.toString())) {
				jsonStr.append(",");
			}
			jsonStr.append("{id:'").append(senum).append("',desc:'").append(senum.getDesc()).append("'}");
		}
		jsonStr.append("]");
		return jsonStr.toString();
	}

	/**
	 * 根据枚举类型的名称,判断是否存在
	 */
	public static boolean contains(String name) {
		for (PayWayEnum senum : PayWayEnum.values()) {
			if (senum.name().equals(name)) {
				return true;
			}
		}
		return false;
	}
	
}
