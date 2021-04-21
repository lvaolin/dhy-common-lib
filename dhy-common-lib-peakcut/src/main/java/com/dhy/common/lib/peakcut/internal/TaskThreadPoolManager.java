package com.dhy.common.lib.peakcut.internal;

import com.dhy.common.lib.peakcut.TaskRequestDto;
import com.dhy.common.lib.peakcut.impl.DefaultExecutorServiceHandler;
import com.dhy.common.lib.peakcut.impl.ThreadPoolConfig;
import com.dhy.common.lib.peakcut.spi.ExecutorServiceHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

/**
 * 线程池管理器
 */
public class TaskThreadPoolManager {
    public   static Map<String,ExecutorService> executorServiceMap = new ConcurrentHashMap<>();

    public static ExecutorService getExecutorService(TaskRequestDto config, ThreadPoolConfig threadPoolConfig,ExecutorServiceHandler executorServiceHandler){
        if (config.getTaskPoolKey() == null) {
            throw new IllegalArgumentException("TaskPoolKey不能为空");
        }

        String key = config.getTaskPoolKey();
        ExecutorService executorService = executorServiceMap.get(key);
        if (executorService==null) {
            synchronized (TaskThreadPoolManager.class){
                executorService = executorServiceMap.get(key);
                if (executorService==null) {
                    executorService = createExecutorService(config,threadPoolConfig,executorServiceHandler);
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
        System.out.println("TaskThreadPoolManager is shutdown");
    }

    /**
     * 线程池扩展点
     * @param config
     * @param executorServiceHandler
     * @return
     */
    private static ExecutorService createExecutorService(TaskRequestDto config,ThreadPoolConfig threadPoolConfig,ExecutorServiceHandler executorServiceHandler){
        if(executorServiceHandler!=null){
            return executorServiceHandler.createExecutorService(config,threadPoolConfig);
        }
        return new DefaultExecutorServiceHandler().createExecutorService(config,threadPoolConfig);

    }
}
