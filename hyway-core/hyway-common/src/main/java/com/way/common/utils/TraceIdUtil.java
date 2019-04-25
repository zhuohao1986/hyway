package com.way.common.utils;

import java.util.UUID;

public class TraceIdUtil {

    private static final ThreadLocal<String> TRACE_ID = new ThreadLocal<String>();

    public static String getTraceId() {
        if(TRACE_ID.get() == null) {
            String s = UUID.randomUUID().toString().replaceAll("-", "");
            setTraceId(s);
        }
        return TRACE_ID.get();
    }

    public static void setTraceId(String traceId) {
        TRACE_ID.set(traceId);
    }

	public static String generateTraceId(String ip) {
		StringBuffer buf=new StringBuffer();
		buf.append(ip);
		buf.append("-");
		buf.append(UUID.randomUUID().toString().replaceAll("-", ""));
		// TODO Auto-generated method stub
		return buf.toString();
	}
}
