package com.dhy.common.lib.theadpool;

import java.util.concurrent.ExecutorService;

/**
 * @Project horcrux
 * @Description 线程池组件入口:目的是统一线程池的管理，给监控和排错带来便利，防止乱用没有名称、不能共享的线程池导致线程爆炸式增长失控
 * @Author lvaolin
 * @Date 2020/12/29 12:06 下午
 */
public class MyThreadPoolFacade {
    /**
     * @Description 获取一个自定义的线程池
     * @Author lvaolin
     * @Date 2020/12/29 12:08 下午
    */
    public static ExecutorService getExecutorService(MyThreadPoolConfig threadPoolConfig){
        if (threadPoolConfig.getThreadName().equalsIgnoreCase("shared")) {
            throw new IllegalArgumentException("请为你的线程池起一个响亮的好听的名字！！！名称'shared'已经被系统占用，请更换其它名字");
        }
        if (threadPoolConfig.getCorePoolSize()!=threadPoolConfig.getMaxPoolSize()) {
            threadPoolConfig.setQueueCapacity(0);
        }
        if (threadPoolConfig.getCorePoolSize()>threadPoolConfig.getMaxPoolSize()) {
            threadPoolConfig.setMaxPoolSize(threadPoolConfig.getCorePoolSize());
        }
        return MyThreadPoolManager.getExecutorService(threadPoolConfig);
    }
    /**
     * @Description 获取一个基于JVM共享的线程池
     * @Author lvaolin
     * @Date 2020/12/29 12:08 下午
     */
    public static ExecutorService getSharedExecutorService(){
        MyThreadPoolConfig myThreadPoolConfig = new MyThreadPoolConfig();
        myThreadPoolConfig.setCorePoolSize(20);
        myThreadPoolConfig.setMaxPoolSize(300);
        myThreadPoolConfig.setQueueCapacity(0);
        return MyThreadPoolManager.getExecutorService(myThreadPoolConfig);
    }
}
