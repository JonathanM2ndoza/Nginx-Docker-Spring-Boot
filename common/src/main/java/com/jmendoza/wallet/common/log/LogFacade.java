package com.jmendoza.wallet.common.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

public class LogFacade {

    private Logger logger;

    public static final String KEY_IP_ADDRESS = "ipAddress";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_USER = "user";
    public static final String KEY_SERVICE = "service";
    public static final String WILDCARD_1 = "wildcard1";
    public static final String WILDCARD_2 = "wildcard2";
    public static final String WILDCARD_3 = "wildcard3";

    public LogFacade(Class<?> clazz) {
        logger = LogManager.getLogger(clazz);
    }

    public void debug(LogContext logContext, String message, Object... values) {

        ThreadContext.put(KEY_IP_ADDRESS, logContext.getIpAddress());
        ThreadContext.put(KEY_DESCRIPTION, logContext.getDescription());
        ThreadContext.put(KEY_USER, logContext.getUser());
        ThreadContext.put(KEY_SERVICE, logContext.getService());

        if (logContext.getWildcard1() != null) {
            ThreadContext.put(WILDCARD_1, logContext.getWildcard1());
        }
        if (logContext.getWildcard2() != null) {
            ThreadContext.put(WILDCARD_2, logContext.getWildcard2());
        }
        if (logContext.getWildcard3() != null) {
            ThreadContext.put(WILDCARD_3, logContext.getWildcard3());
        }

        logger.debug(message, values);
    }

    public void error(LogContext logContext, String message, Object... values) {

        ThreadContext.put(KEY_IP_ADDRESS, logContext.getIpAddress());
        ThreadContext.put(KEY_DESCRIPTION, logContext.getDescription());
        ThreadContext.put(KEY_USER, logContext.getUser());
        ThreadContext.put(KEY_SERVICE, logContext.getService());

        if (logContext.getWildcard1() != null) {
            ThreadContext.put(WILDCARD_1, logContext.getWildcard1());
        }
        if (logContext.getWildcard2() != null) {
            ThreadContext.put(WILDCARD_2, logContext.getWildcard2());
        }
        if (logContext.getWildcard3() != null) {
            ThreadContext.put(WILDCARD_3, logContext.getWildcard3());
        }

        logger.error(message, values);
    }

    public void error(Object value) {
        logger.error(value);
    }

    public void info(LogContext logContext, String message, Object... values) {

        ThreadContext.put(KEY_IP_ADDRESS, logContext.getIpAddress());
        ThreadContext.put(KEY_DESCRIPTION, logContext.getDescription());
        ThreadContext.put(KEY_USER, logContext.getUser());
        ThreadContext.put(KEY_SERVICE, logContext.getService());

        if (logContext.getWildcard1() != null) {
            ThreadContext.put(WILDCARD_1, logContext.getWildcard1());
        }
        if (logContext.getWildcard2() != null) {
            ThreadContext.put(WILDCARD_2, logContext.getWildcard2());
        }
        if (logContext.getWildcard3() != null) {
            ThreadContext.put(WILDCARD_3, logContext.getWildcard3());
        }
        logger.info(message, values);
    }
}
