package com.dhy.common.lib.peakcut;

import java.io.Serializable;
import java.util.concurrent.Callable;

/**
 * 任务请求参数
 */
public class TaskRequestDto implements Serializable {
    public TaskRequestDto(){

    }
    public TaskRequestDto(Callable callable){
        this.taskPoolKey = "shared-pool";
        this.callable = callable;
    }
    public TaskRequestDto(String taskPoolKey,Callable callable){
        this.taskPoolKey = taskPoolKey;
        this.callable = callable;
    }

    /**
     * 这个参数用来决定把任务task放到哪个专用线程池中
     * 比如想限定某一个方法的并发则 key 应该指向 方法名称的唯一值
     * 限定某个接口内的并发 则key 应该指向 接口名称的唯一值
     * 依次类推，可以限定任意范围，只要用了同一个线程池
     * 建议命名规则：微服务名称+包名+类名称+方法名称  ，根据需要使用不同的组合
     */
    private String taskPoolKey;
    /**
     * 任务请求id，唯一的
     */
    private String taskRequestId ;

   /**
     * 任务内容
     */
    private Callable callable;

    public Callable getCallable() {
        return callable;
    }

    public void setCallable(Callable callable) {
        this.callable = callable;
    }

    public String getTaskRequestId() {
        return taskRequestId;
    }

    public void setTaskRequestId(String taskRequestId) {
        this.taskRequestId = taskRequestId;
    }

    public String getTaskPoolKey() {
        return taskPoolKey;
    }

    public void setTaskPoolKey(String taskPoolKey) {
        this.taskPoolKey = taskPoolKey;
    }

}
