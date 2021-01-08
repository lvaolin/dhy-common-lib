package com.dhy.common.lib.theadpool;

import java.util.concurrent.ExecutorService;

/**
 * @Project horcrux
 * @Description 主要用途描述
 * @Author lvaolin
 * @Date 2020/12/29 1:37 下午
 */
public class Test {
    public static void main(String[] args) {
        MyThreadPoolFacade.getExecutorService(new MyThreadPoolConfig("mypool",20));
        MyThreadPoolConfig myThreadPoolConfig = new MyThreadPoolConfig("laolv");
        myThreadPoolConfig.setCorePoolSize(10);
        myThreadPoolConfig.setMaxPoolSize(100);
        ExecutorService sharedExecutorService = MyThreadPoolFacade.getExecutorService(myThreadPoolConfig);
        for (int i = 0; i <100 ; i++) {
            sharedExecutorService.submit(() -> {
                System.out.println(System.currentTimeMillis());
            });
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                MyThreadPoolConfig myThreadPoolConfig = new MyThreadPoolConfig("lvaolin");
                myThreadPoolConfig.setCorePoolSize(10);
                myThreadPoolConfig.setMaxPoolSize(10);
                ExecutorService sharedExecutorService = MyThreadPoolFacade.getExecutorService(myThreadPoolConfig);
                for (int i = 0; i <100 ; i++) {
                    sharedExecutorService.submit(() -> {
                        System.out.println(System.currentTimeMillis());
                    });
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                ExecutorService sharedExecutorService = MyThreadPoolFacade.getSharedExecutorService();
                for (int i = 0; i <5 ; i++) {
                    sharedExecutorService.submit(() -> {
                        System.out.println(System.currentTimeMillis());
                    });
                }
            }
        }).start();


    }

}
