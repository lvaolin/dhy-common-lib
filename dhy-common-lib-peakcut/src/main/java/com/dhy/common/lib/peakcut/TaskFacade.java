package com.dhy.common.lib.peakcut;

import com.dhy.common.lib.peakcut.impl.DefaultExecutorServiceHandler;
import com.dhy.common.lib.peakcut.impl.DefaultTaskResultHandlerForRedis;
import com.dhy.common.lib.peakcut.impl.ThreadPoolConfig;
import com.dhy.common.lib.peakcut.internal.TaskThreadPoolManager;
import com.dhy.common.lib.peakcut.spi.ExecutorServiceHandler;
import com.dhy.common.lib.peakcut.spi.TaskResultHandler;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 调用者入口 API
 */
public class TaskFacade {
    private  TaskResultHandler taskResultHandler ;
    private  ExecutorServiceHandler executorServiceHandler;
    private  ThreadPoolConfig threadPoolConfig ;
    private  Map<String,ThreadPoolConfig> threadPoolConfigMap;

    public TaskFacade(){
        this(null,null,null);
    }
    public TaskFacade(ThreadPoolConfig threadPoolConfig){
        this(threadPoolConfig,null,null);
    }

    public TaskFacade(TaskResultHandler taskResultHandler){
        this(null,taskResultHandler,null);
    }

    public TaskFacade(ThreadPoolConfig threadPoolConfig,TaskResultHandler taskResultHandler,ExecutorServiceHandler executorServiceHandler){
        if(taskResultHandler==null){
            this.taskResultHandler = new DefaultTaskResultHandlerForRedis();
        }else{
            this.taskResultHandler = taskResultHandler;
        }

        if(executorServiceHandler==null){
            this.executorServiceHandler = new DefaultExecutorServiceHandler();
        }else{
            this.executorServiceHandler = executorServiceHandler;
        }

        this.threadPoolConfig = threadPoolConfig;

    }

    /**
     * 提交任务
     * @param taskRequestDto
     * @return 任务ID
     */
    public  String createTask(TaskRequestDto taskRequestDto){
        if (taskRequestDto==null) {
            throw new IllegalArgumentException("taskRequestDto不能为null");
        }
        if (taskRequestDto.getTaskPoolKey()==null) {
            //默认取当前类名称+方法名称
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            taskRequestDto.setTaskPoolKey(stackTrace[2].getClassName()+"@"+stackTrace[2].getMethodName());
        }
        taskRequestDto.setTaskRequestId(UUID.randomUUID().toString());

        if (this.getThreadPoolConfig()==null) {
            //如果没有则优先取数据库中的配置
            this.setThreadPoolConfig(threadPoolConfigMap.get(taskRequestDto.getTaskPoolKey()));
        }
        if (this.getThreadPoolConfig()==null) {
            //如果数据库中没有配置则取默认值
            this.setThreadPoolConfig(new ThreadPoolConfig());
        }

        //初始化结果为 排队中
        taskResultHandler.initResult(taskRequestDto);
        TaskThreadPoolManager
                .getExecutorService(taskRequestDto,this.threadPoolConfig,this.executorServiceHandler)//获取专用线程池
                .submit(()->{
                    try {
                        //状态更新为处理中
                        taskResultHandler.beforeResult(taskRequestDto);
                        //执行任务
                        Object result = taskRequestDto.getCallable().call();
                        //任务完成
                        taskResultHandler.afterResult(taskRequestDto,result);

                    } catch (Exception e) {
                        e.printStackTrace();
                        taskResultHandler.exceptionResult(taskRequestDto,e);
                    }
                });

        return  taskRequestDto.getTaskRequestId();
    }

    /**
     * 查询任务结果
     * 考虑各种场景：
     *  1）有请求，但是还没有返回 -- 返回waiting，保留该条记录，前端继续轮询等待
     *  2）请求已经返回，有正常返回值 -- 返回response（value），删除该条记录，前端结束轮询
     *  3）请求已经返回，接口内报错BusinessException -- 返回response（error），删除该条记录，前端结束轮询
     *  4）该请求不存在 -- 返回no request，前端结束轮询，提示错误
     *  5）请求发起后，长时间没有被取走结果 -- 定时清理该类记录，前端应该已经异常或退出 -- 通过Redis可以不用
     * @param taskRequestId
     * @return
     */
    public  TaskResponseDto queryTaskResult(String taskRequestId ){
        return  (TaskResponseDto)taskResultHandler.queryResult(taskRequestId);
    }

    public  void shutdown(){
          TaskThreadPoolManager.shutdown();
    }


    public TaskResultHandler getTaskResultHandler() {
        return taskResultHandler;
    }

    public void setTaskResultHandler(TaskResultHandler taskResultHandler) {
        this.taskResultHandler = taskResultHandler;
    }

    public int getQueueSize(String taskPoolKey){
        ThreadPoolExecutor executor = (ThreadPoolExecutor)TaskThreadPoolManager.getExecutorServiceFromCache(taskPoolKey);
        return executor.getQueue().size();
    }

    public ExecutorServiceHandler getExecutorServiceHandler() {
        return executorServiceHandler;
    }

    public void setExecutorServiceHandler(ExecutorServiceHandler executorServiceHandler) {
        this.executorServiceHandler = executorServiceHandler;
    }
    public ThreadPoolConfig getThreadPoolConfig() {
        return threadPoolConfig;
    }

    public void setThreadPoolConfig(ThreadPoolConfig threadPoolConfig) {
        this.threadPoolConfig = threadPoolConfig;
    }
    public Map<String, ThreadPoolConfig> getThreadPoolConfigMap() {
        return threadPoolConfigMap;
    }

    public void setThreadPoolConfigMap(Map<String, ThreadPoolConfig> threadPoolConfigMap) {
        this.threadPoolConfigMap = threadPoolConfigMap;
    }


}
