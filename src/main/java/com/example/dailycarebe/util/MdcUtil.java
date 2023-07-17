package com.example.dailycarebe.util;

import org.slf4j.MDC;
import org.slf4j.spi.MDCAdapter;

import java.util.Map;

public class MdcUtil {
    private static MDCAdapter mdc = MDC.getMDCAdapter();

    public static final String HEADER_MAP_MDC = "HEADER_MAP_MDC";

    public static final String QUERY_PARAMETER_MAP_MDC = "PARAMETER_MAP_MDC";

    public static final String BODY_MAP_MDC = "BODY_MAP_MDC";

    public static final String USER_INFO_MDC = "USER_INFO_MDC";

    public static final String REQUEST_URI_MDC = "REQUEST_URI_MDC";

    public static final String REQUEST_METHOD_MDC = "REQUEST_METHOD_MDC";

    public static final String USER_AGENT_DETAIL_MDC = "AGENT_DETAIL_MDC";

    public static void set(String key, String value) {
        mdc.put(key, value);
    }

    public static String get(String key) {
        return mdc.get(key);
    }

    public static void setContext(Map<String, String> context) {
        mdc.setContextMap(context);
    }

    public static Map<String, String> getCopyOfContext() {
        return mdc.getCopyOfContextMap();
    }

    public static void clear() {
        mdc.clear();
    }
}
