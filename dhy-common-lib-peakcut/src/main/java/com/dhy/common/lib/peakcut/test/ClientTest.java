package com.dhy.common.lib.peakcut.test;

import com.dhy.common.lib.peakcut.TaskFacade;
import com.dhy.common.lib.peakcut.TaskRequestDto;
import com.dhy.common.lib.peakcut.TaskResponseDto;
import com.dhy.common.lib.peakcut.TaskStatus;
import com.dhy.common.lib.peakcut.impl.DefaultTaskResultHandlerForRAM;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ClientTest {

    TaskFacade taskFacade = new TaskFacade();
    {
        taskFacade.setTaskResultHandler(new DefaultTaskResultHandlerForRAM());
        taskFacade.getThreadPoolConfig().setCorePoolSize(2);
        taskFacade.getThreadPoolConfig().setMaxPoolSize(4);
        taskFacade.getThreadPoolConfig().setKeepAliveTime(10L);
        taskFacade.getThreadPoolConfig().setQueueCapacity(10);
    }



    public String test1(long logid) {
        TaskRequestDto taskRequestDto = new TaskRequestDto();
        //taskRequestDto.setTaskPoolKey("ClientTest.test1");
        taskRequestDto.setCallable(() -> {
            return "";
        });
        String taskId = taskFacade.createTask(taskRequestDto);
        System.out.println("logid:" + logid + ",任务ID：" + taskId + ",前面排队：" + taskFacade.getQueueSize(taskRequestDto.getTaskPoolKey()));
        return taskId;
    }

    public TaskResponseDto getResult(String taskId) {
        TaskResponseDto taskResponseDto = taskFacade.queryTaskResult(taskId);
        return taskResponseDto;
    }

    public static void main(String[] args) throws InterruptedException {

        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
        ClientTest clientTest = new ClientTest();
        int x = 20;
        CountDownLatch count = new CountDownLatch(x);
        for (int i = 0; i < x; i++) {
            newCachedThreadPool.submit(() -> {
                try {
                    //while (true) {
                        TimeUnit.MILLISECONDS.sleep(5);
                        String taskid = clientTest.test1(System.currentTimeMillis());
                        TaskResponseDto taskResponseDto = clientTest.getResult(taskid);
                        while (taskResponseDto.getTaskStatus() != TaskStatus.status_completed) {
                            System.out.println("taskid:" + taskid + ";task status:" + taskResponseDto.getTaskStatus().name() + ";task result:" + taskResponseDto.getTaskResult());
                            TimeUnit.MILLISECONDS.sleep(500);
                            taskResponseDto = clientTest.getResult(taskid);
                        }
                        System.out.println("taskid:" + taskid + ";task status:" + taskResponseDto.getTaskStatus().name() + ";task result:" + taskResponseDto.getTaskResult());
                    //}
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    count.countDown();
                }
            });

        }

        count.await();
        //newCachedThreadPool.shutdown();
        //TaskThreadPoolManager.shutdown();
        System.out.println("测试 结束");
        synchronized (ClientTest.class) {
            ClientTest.class.wait();
        }
    }
}
