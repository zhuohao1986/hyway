package com.way.open.pay.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;  
  
@Retention(RetentionPolicy.RUNTIME)  
@Target(ElementType.METHOD)  
public @interface PayLog {  
	
	 public enum PayType{PAY,NOTIFY,REFUND}; //CMBC:市场通,SYNC:支付宝,WX:微信,UNION:银联
	 
	 PayType payType() default PayType.PAY;  
}