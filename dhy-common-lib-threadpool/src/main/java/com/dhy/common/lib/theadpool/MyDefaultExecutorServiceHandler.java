package com.dhy.common.lib.theadpool;


import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description 线程池创建接口默认实现
 * @Author lvaolin
 * @Date 2020/12/29 12:03 下午
*/
public class MyDefaultExecutorServiceHandler implements MyExecutorServiceHandler {
    /**
     *  高峰期可上去，低谷期可下来
     */
    @Override
    public ExecutorService createExecutorService(MyThreadPoolConfig threadPoolConfig) {
        //默认参数
        int corePoolSize = 50;
        int maximumPoolSize = 50;
        long keepAliveTime = 60;
        int queueCapacity = 1000;
        TimeUnit unit = TimeUnit.SECONDS;

        //如果有定制化参数则覆盖下
        String taskPoolKey = threadPoolConfig.getThreadName();
        if (threadPoolConfig.getMaxPoolSize()!=null) {
            maximumPoolSize = threadPoolConfig.getMaxPoolSize();
        }
        if (threadPoolConfig.getCorePoolSize()!=null) {
            corePoolSize = threadPoolConfig.getCorePoolSize();
        }
        if (threadPoolConfig.getQueueCapacity()!=null) {
            queueCapacity = threadPoolConfig.getQueueCapacity();
        }
        if (threadPoolConfig.getKeepAliveTime()!=null) {
            keepAliveTime = threadPoolConfig.getKeepAliveTime();
        }

        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(queueCapacity);
        ThreadFactory threadFactory = new MyDefaultThreadFactory(taskPoolKey);
        RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();

        ThreadPoolExecutor executorService = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                unit,
                workQueue,
                threadFactory,
                handler);
        //允许核心线程在低谷期退出，释放资源
        //executorService.allowCoreThreadTimeOut(true);
        return executorService;
    }



    static class MyDefaultThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        MyDefaultThreadFactory(String taskPoolKey) {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            namePrefix = "my-"+taskPoolKey+"-" +
                    poolNumber.getAndIncrement() +
                    "-thread-";
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                    namePrefix + threadNumber.getAndIncrement(),
                    0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }
}
