package com.dhy.common.lib.theadpool;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

/**
 * @Description 线程池管理器
 * @Author lvaolin
 * @Date 2020/12/29 12:04 下午
*/
public class MyThreadPoolManager {

    public   static Map<String,ExecutorService> executorServiceMap = new ConcurrentHashMap<>();

    public static ExecutorService getExecutorService(MyThreadPoolConfig threadPoolConfig){
        return getExecutorService(threadPoolConfig,null);
    }
    public static ExecutorService getExecutorService(MyThreadPoolConfig threadPoolConfig, MyExecutorServiceHandler executorServiceHandler){
        if (threadPoolConfig.getThreadName() == null) {
            throw new IllegalArgumentException("ThreadName不能为空");
        }

        String key = threadPoolConfig.getThreadName();
        ExecutorService executorService = executorServiceMap.get(key);
        if (executorService==null) {
            synchronized (MyThreadPoolManager.class){
                executorService = executorServiceMap.get(key);
                if (executorService==null) {
                    executorService = createExecutorService(threadPoolConfig,executorServiceHandler);
                    executorServiceMap.put(key,executorService);
                }
            }
        }
        return executorService;
    }

    public static ExecutorService getExecutorServiceFromCache(String key){
        return executorServiceMap.get(key);
    }

    /**
     * 销毁所有线程池
     */
    public static void shutdown(){
        executorServiceMap.values().stream().forEach((es)->{
            es.shutdown();
            es=null;
        });
        executorServiceMap = null;
        System.out.println("MyhreadPoolManager is shutdown");
    }

    /**
     * 线程池扩展点
     * @param executorServiceHandler
     * @return
     */
    private static ExecutorService createExecutorService(MyThreadPoolConfig threadPoolConfig, MyExecutorServiceHandler executorServiceHandler){
        if(executorServiceHandler!=null){
            return executorServiceHandler.createExecutorService(threadPoolConfig);
        }
        return new MyDefaultExecutorServiceHandler().createExecutorService(threadPoolConfig);

    }
}
