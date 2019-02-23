package com.way.open.pay.util.union;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
/**
 * 启动加载银联支付相关配置
 *
 */
@Component
public class UnionConfigLoad implements ApplicationContextAware{

     @Override
     public void setApplicationContext(ApplicationContext var1)
                 throws BeansException {
            //调用线程
           RunTask r = new RunTask();
           r.start();
     }
     //线程加载
     class RunTask extends Thread{
            public void run(){
            	SDKConfig.getConfig().loadPropertiesFromSrc();// 从classpath加载acp_sdk.properties文件
           }
     }

}
