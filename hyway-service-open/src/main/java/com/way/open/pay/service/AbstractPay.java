package com.way.open.pay.service;

import java.util.Map;

public abstract class AbstractPay {
	
	 public abstract  String pay(Map<String, String> paramMap);
	
	 public abstract  String refund(Map<String, String> paramMap);
	
	 public abstract  String query(Map<String, String> paramMap);

}
