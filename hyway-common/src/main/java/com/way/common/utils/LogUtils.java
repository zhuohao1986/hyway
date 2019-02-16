package com.way.common.utils;

import java.text.MessageFormat;
import java.util.UUID;

import org.slf4j.Logger;

import com.alibaba.fastjson.JSON;

/**
 * 日志输出工具类： LogUtil
 * @version LogUtil.java 2016年8月25日 下午12:01:48
 */
public class LogUtils {
	
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
 
    /**
     * 输出error level的log信息
     *
     * @param logger  日志记录器
     * @param message log信息,如:<code>xxx{0},xxx{1}...</code>
     */
    public static void error(Logger logger, String message) {
        if (logger.isErrorEnabled()) {
            logger.error(format(message));
        }
    }
 
    /**
     * 输出error level的log信息
     *
     * @param logger  日志记录器
     * @param message log信息,如:<code>xxx{0},xxx{1}...</code>
     */
    public static void error(Logger logger, String message, Throwable throwable) {
        if (logger.isErrorEnabled()) {
            logger.error(format(message), throwable);
        }
    }
 
    /**
     * 输出error level的log信息.
     *
     * @param logger    日志记录器
     * @param throwable 异常对象
     * @param message   log信息,如:<code>xxx{0},xxx{1}...</code>
     * @param params    log格式化参数,数组length与message参数化个数相同,
     *                  如:<code>Object[]  object=new Object[]{"xxx","xxx"}</code>
     */
    public static void error(Logger logger, Throwable throwable, String message, Object... params) {
        if (logger.isErrorEnabled()) {
            logger.error(format(message, params), throwable);
        }
    }
 
    /**
     * 输出warn level的log信息
     *
     * @param logger  日志记录器
     * @param message log信息,如:<code>xxx{0},xxx{1}...</code>
     */
    public static void warn(Logger logger, String message) {
        if (logger.isWarnEnabled()) {
            logger.warn(format(message));
        }
    }
 
    /**
     * 输出warn level的log信息
     *
     * @param logger  日志记录器
     * @param message log信息,如:<code>xxx{0},xxx{1}...</code>
     */
    public static void warn(Logger logger, String message, Throwable throwable) {
        if (logger.isWarnEnabled()) {
            logger.warn(format(message), throwable);
        }
    }
 
    /**
     * 输出info level的log信息
     *
     * @param logger  日志记录器
     * @param message log信息,如:<code>xxx{0},xxx{1}...</code>
     */
    public static void info(Logger logger, String message) {
        if (logger.isInfoEnabled()) {
            logger.info(format(message));
        }
    }
 
    /**
     * 输出debug level的log信息
     *
     * @param logger  日志记录器
     * @param message log信息,如:<code>xxx{0},xxx{1}...</code>
     */
    public static void debug(Logger logger, String message) {
        if (logger.isDebugEnabled()) {
            logger.debug(format(message));
        }
    }
 
    /**
     * 输出debug level的log信息
     *
     * @param logger  日志记录器
     * @param message log信息,如:<code>xxx{0},xxx{1}...</code>
     */
    public static void debug(Logger logger, String message, Throwable throwable) {
        if (logger.isDebugEnabled()) {
            logger.debug(format(message), throwable);
        }
    }
 
    /**
     * 输出info level的log信息.
     *
     * @param logger  日志记录器
     * @param message log信息,如:<code>xxx{0},xxx{1}...</code>
     * @param params  log格式化参数,数组length与message参数化个数相同,
     *                如:<code>Object[]  object=new Object[]{"xxx","xxx"}</code>
     */
    public static void info(Logger logger, String message, Object... params) {
        if (logger.isInfoEnabled()) {
            logger.info(format(message, params));
        }
    }
 
    /**
     * 输出info level的输入参数信息.
     *
     * @param logger  日志记录器
     * @param message log信息,如:<code>xxx{0},xxx{1}...</code>
     * @param params  log格式化参数,数组length与message参数化个数相同,
     *                如:<code>Object[]  object=new Object[]{"xxx","xxx"}</code>
     */
    public static void input(Logger logger, String message, Object... params) {
        if (!logger.isInfoEnabled()) {
            return;
        }
 
        String[] strs = new String[params.length];
        try {
            for(int i=0; i< params.length;i++) {
                String str = JSON.toJSONString(params[i]);
                strs[i] = str;
            }
        } catch (Exception ex) {
            logger.error("parse error {}", ex);
        }
 
        logger.info(format(message, strs));
    }
 
    /**
     * 输出info level的log信息.
     *
     * @param throwable 异常对象
     * @param logger    日志记录器
     * @param message   log信息,如:<code>xxx{0},xxx{1}...</code>
     * @param params    log格式化参数,数组length与message参数化个数相同,
     *                  如:<code>Object[]  object=new Object[]{"xxx","xxx"}</code>
     */
    public static void info(Throwable throwable, Logger logger, String message, Object... params) {
        if (logger.isInfoEnabled()) {
            logger.info(format(message, params), throwable);
        }
    }
 
    /**
     * 输出warn level的log信息.
     *
     * @param logger  日志记录器
     * @param message log信息,如:<code>xxx{0},xxx{1}...</code>
     * @param params  log格式化参数,数组length与message参数化个数相同,
     *                如:<code>Object[]  object=new Object[]{"xxx","xxx"}</code>
     */
    public static void warn(Logger logger, String message, Object... params) {
        if (logger.isWarnEnabled()) {
            logger.warn(format(message, params));
        }
    }
 
    /**
     * 输出warn level的log信息.
     *
     * @param throwable 异常对象
     * @param logger    日志记录器
     * @param message   log信息,如:<code>xxx{0},xxx{1}...</code>
     * @param params    log格式化参数,数组length与message参数化个数相同,
     *                  如:<code>Object[]  object=new Object[]{"xxx","xxx"}</code>
     */
    public static void warn(Throwable throwable, Logger logger, String message, Object... params) {
        if (logger.isWarnEnabled()) {
            logger.warn(format(message, params), throwable);
        }
    }
 
    /**
     * 输出debug level的log信息.
     *
     * @param logger  日志记录器
     * @param message log信息,如:<code>xxx{0},xxx{1}...</code>
     * @param params  log格式化参数,数组length与message参数化个数相同,
     *                如:<code>Object[]  object=new Object[]{"xxx","xxx"}</code>
     */
    public static void debug(Logger logger, String message, Object... params) {
        if (logger.isDebugEnabled()) {
            logger.debug(format(message, params));
        }
    }
 
    /**
     * 输出debug level的log信息.
     *
     * @param throwable 异常对象
     * @param logger    日志记录器
     * @param message   log信息,如:<code>xxx{0},xxx{1}...</code>
     * @param params    log格式化参数,数组length与message参数化个数相同,
     *                  如:<code>Object[]  object=new Object[]{"xxx","xxx"}</code>
     */
    public static void debug(Throwable throwable, Logger logger, String message, Object... params) {
        if (logger.isDebugEnabled()) {
            logger.debug(format(message, params), throwable);
        }
    }
 
    /**
     * 输出error level的log信息.
     *
     * @param logger  日志记录器
     * @param message log信息,如:<code>xxx{0},xxx{1}...</code>
     * @param params  log格式化参数,数组length与message参数化个数相同,
     *                如:<code>Object[]  object=new Object[]{"xxx","xxx"}</code>
     */
    public static void error(Logger logger, String message, Object... params) {
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
    public static void error(Logger logger, Throwable e, Object... objs) {
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
     */
    private static String format(String message) {
        return format(message, null);
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
       /* try {
            if (EagleEye.getTraceId() == null) {
                String ip = null;
 
                ip = InetAddress.getLocalHost().getHostAddress();
 
                return EagleEye.generateTraceId(ip);
            }
            return EagleEye.getTraceId();
        } catch (Exception e) {
 
        }*/
        return UUID.randomUUID().toString();
    }
 
}
