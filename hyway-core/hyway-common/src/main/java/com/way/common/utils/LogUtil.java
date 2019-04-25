package com.way.common.utils;

import java.net.InetAddress;
import java.text.MessageFormat;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtil {
	
	/**
     * 摘要日志的内容分隔符
     */
    public static final String SEP = ",";
 
    /**
     * 修饰符
     */
    private static final char RIGHT_TAG = ']';
 
    /**
     * 修饰符
     */
    private static final char LEFT_TAG = '[';
	
	//定义一个全局的记录器，通过LoggerFactory获取
    private final static Logger logger = LoggerFactory.getLogger(LogUtil.class); 

    public static void errorLogs(Exception e) {
        e.printStackTrace();
        logger.error("** Exception ** ", e);
    }

    public static void errorLogs(Exception e, String s) {
        e.printStackTrace();
        logger.error("** Exception ** " + s, e);
    }

    public static void warnLogs(String s, Exception e) {
        String ex = "Class: [" + e.getStackTrace()[0].getClassName() + "] Method: <" + e.getStackTrace()[0].getMethodName() + "> Line: " + e.getStackTrace()[0].getLineNumber() + " WARN: " + s;
        logger.warn("** WARN ** " + ex, e);
    }
    public static void warnLogs(String s, String tsx) {
    	logger.warn("** WARN ** "+s);
    }

    public static void infoLogs(String s, Exception e) {
        String ex = "Class: [" + e.getStackTrace()[0].getClassName() + "] Method: <" + e.getStackTrace()[0].getMethodName() + "> Line: " + e.getStackTrace()[0].getLineNumber() + " INFO: " + s;
        logger.info("** INFO ** " + ex, e);
    }

    public static void infoLogs(String s) {
    	logger.info(s);
    }

    public static void error(String message, Object... params) {
        if (logger.isErrorEnabled()) {
            logger.error(format(message, params));
        }
    }
 
    /**
     * 打印error日志。
     *
     * @param logger 日志对象
     * @param e      异常信息
     * @param objs   任意个要输出到日志的参数
     */
    public static void error(Throwable e, Object... objs) {
        logger.error(getLogString(objs), e);
    }
 
    /**
     * 生成输出到日志的字符串
     *
     * @param objs 任意个要输出到日志的参数
     * @return 日志字符串
     */
    public static String getLogString(Object... objs) {
        StringBuilder log = new StringBuilder();
        log.append(LEFT_TAG);
        //log.append(EagleEye.getTraceId()).append(SEP);
        if (objs != null) {
            for (Object o : objs) {
                log.append(o).append(SEP);
            }
        }
        // 预留扩展位
        log.append(RIGHT_TAG);
 
        return log.toString();
    }
 
    /**
     * 日志信息参数化格式化
     *
     * @param message log信息,如:<code>xxx{0},xxx{1}...</code>
     * @param params  log格式化参数,数组length与message参数化个数相同,
     *                如:<code>Object[]  object=new Object[]{"xxx","xxx"}</code>
     */
    private static String format(String message, Object... params) {
        if ((message == null) || (params == null) || (params.length == 0)) {
            return "[" + getTraceId() + "]" + message;
        }
 
        //防止数字输出时带“,”分隔符
        Object[] temp = new Object[params.length];
        for (int i = 0; i < params.length; i++) {
            temp[i] = String.valueOf(params[i]);
        }
 
        return "[" + getTraceId() + "] " + MessageFormat.format(message, temp);
    }
 
    /**
     * 获取traceId
     *
     * @return
     */
    public static String getTraceId() {
       try {
            if (TraceIdUtil.getTraceId() == null) {
                String ip = null;
 
                ip = InetAddress.getLocalHost().getHostAddress();
 
                return TraceIdUtil.generateTraceId(ip);
            }
            return TraceIdUtil.getTraceId();
        } catch (Exception e) {
 
        }
        return UUID.randomUUID().toString();
    }
}
