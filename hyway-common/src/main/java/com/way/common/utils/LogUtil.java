package com.way.common.utils;

import org.apache.log4j.Logger;

public class LogUtil {
    private static Logger log = Logger.getLogger(LogUtil.class);

    public static void errorLogs(Exception e) {
        e.printStackTrace();
        log.error("** Exception ** ", e);
    }

    public static void errorLogs(Exception e, String s) {
        e.printStackTrace();
        log.error("** Exception ** " + s, e);
    }

    public static void warnLogs(String s, Exception e) {
        String ex = "Class: [" + e.getStackTrace()[0].getClassName() + "] Method: <" + e.getStackTrace()[0].getMethodName() + "> Line: " + e.getStackTrace()[0].getLineNumber() + " WARN: " + s;
        log.warn("** WARN ** " + ex, e);
    }
    public static void warnLogs(String s, String tsx) {
        log.warn("** WARN ** "+s);
    }

    public static void infoLogs(String s, Exception e) {
        String ex = "Class: [" + e.getStackTrace()[0].getClassName() + "] Method: <" + e.getStackTrace()[0].getMethodName() + "> Line: " + e.getStackTrace()[0].getLineNumber() + " INFO: " + s;
        log.info("** INFO ** " + ex, e);
    }

    public static void infoLogs(String s) {
        log.info(s);
    }
}
