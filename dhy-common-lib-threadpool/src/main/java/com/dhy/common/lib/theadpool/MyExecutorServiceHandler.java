package com.dhy.common.lib.theadpool;


import java.util.concurrent.ExecutorService;

/**
 * @Description 线程池创建接口
 * @Author lvaolin
 * @Date 2020/12/29 12:03 下午
*/
public interface MyExecutorServiceHandler {
    ExecutorService createExecutorService( MyThreadPoolConfig threadPoolConfig);

}
