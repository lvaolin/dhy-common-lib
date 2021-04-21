package com.dhy.common.lib.peakcut;

import java.io.Serializable;

/**
 * 任务结果
 */
public class TaskResponseDto implements Serializable {
    private TaskStatus taskStatus;
    private Object taskResult;
    private String taskErrorMsg;

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }
    public Object getTaskResult() {
        return taskResult;
    }

    public void setTaskResult(Object taskResult) {
        this.taskResult = taskResult;
    }

    public String getTaskErrorMsg() {
        return taskErrorMsg;
    }

    public void setTaskErrorMsg(String taskErrorMsg) {
        this.taskErrorMsg = taskErrorMsg;
    }

    @Override
    public String toString() {
        return "TaskResponseDto{" +
                "taskStatus=" + taskStatus +
                ", taskResult=" + taskResult +
                ", taskErrorMsg='" + taskErrorMsg + '\'' +
                '}';
    }

}
