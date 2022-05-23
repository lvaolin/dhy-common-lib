package com.dhy.common.lib.taskproxy.test;

/**
 * ThreadLocal工具类
 */
public class ThreadLocalHolder {

    private static ThreadLocal<String> userIdThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<String> traceIdThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<RequestContext> appContextThreadLocal = new ThreadLocal<>();

    public static void setUserId(String userId){
        userIdThreadLocal.set(userId);
    }
    public static void setTraceId(String traceId){
        traceIdThreadLocal.set(traceId);
    }
    public static void setAppContext(RequestContext appContext){
        appContextThreadLocal.set(appContext);
    }

    public static String getUserId(){
        return userIdThreadLocal.get();
    }
    public static String getTraceId(){
        return traceIdThreadLocal.get();
    }

    public static RequestContext getAppContext(){
        return appContextThreadLocal.get();
    }

    public static void removeUserId(){
        userIdThreadLocal.remove();
    }

    public static void removeTraceId(){
        traceIdThreadLocal.remove();
    }

    public static void removeAppContext(){
        appContextThreadLocal.remove();
    }
}


