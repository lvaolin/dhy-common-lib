package com.dhy.common.lib.peakcut.spring;

import com.dhy.common.lib.peakcut.TaskRequestDto;

public interface ITaskFacadeForSpringService {
    void init();
    String createTask(TaskRequestDto taskRequestDto);
    int getQueueSize(String taskPoolKey);
}
