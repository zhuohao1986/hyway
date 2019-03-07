package com.way.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtil {
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
}
