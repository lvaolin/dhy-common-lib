package com.dhy.common.lib.taskproxy;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * 代理类：增加了ThreadLocal上绑定的数据中转功能
 */
@Slf4j
public class TaskProxy<V> implements Runnable, Callable {

    /**
     * 被代理没有返回值的任务
     */
    private Runnable runnable;
    /**
     * 被代理有返回值的任务
     */
    private Callable<V> callable;
    /**
     * 数据临时中转站
     */
    private Map<String, Object> localData = new HashMap<>();
    /**
     * ThreadLocal 工具类
     */
    private Class threadLocalHolder;


    public TaskProxy(Runnable runnable, Class threadLocalHolder){
        this.runnable = runnable;
        this.threadLocalHolder = threadLocalHolder;
        storeThreadLocal();
    }
    public TaskProxy(Callable callable, Class threadLocalHolder){
        this.callable = callable;
        this.threadLocalHolder = threadLocalHolder;
        storeThreadLocal();
    }

    @Override
    public void run() {
        restoreThreadLocal();
        this.runnable.run();
        clearThreadLocal();
    }

    @Override
    public Object call() throws Exception {
        restoreThreadLocal();
        V v = this.callable.call();
        clearThreadLocal();
        return v;
    }



    private void storeThreadLocal() {
        Method[] methods = threadLocalHolder.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().startsWith("get")) {
                storeField(method);
            }
        }
    }

    private void storeField(Method method) {
        try {
            Object result = method.invoke(null, null);
            localData.put(method.getName(),result);
            log.info(method.getName()+" invoke");
        } catch (Throwable t){
            log.error(t.getMessage(),t);
        }
    }

    private void restoreThreadLocal() {
        Method[] methods = threadLocalHolder.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().startsWith("set")) {
                restoreField(method);
            }
        }

    }

    private void restoreField(Method method) {
        try {
            Object filedValue = localData.get(method.getName().replaceFirst("s", "g"));
            method.invoke(null, filedValue);
            log.info(method.getName()+" invoke");
        } catch (Throwable t){
            log.error(t.getMessage(),t);
        }
    }

    private void clearThreadLocal() {
        Method[] methods = threadLocalHolder.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().startsWith("remove")) {
                try {
                    method.invoke(null, null);
                    log.info(method.getName()+" invoke");
                } catch (Throwable t){
                    log.error(t.getMessage(),t);
                }
            }
        }
    }

}


