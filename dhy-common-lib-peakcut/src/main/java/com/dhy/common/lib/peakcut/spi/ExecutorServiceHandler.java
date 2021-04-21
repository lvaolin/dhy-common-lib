package com.dhy.common.lib.peakcut.spi;

import com.dhy.common.lib.peakcut.TaskRequestDto;
import com.dhy.common.lib.peakcut.impl.ThreadPoolConfig;

import java.util.concurrent.ExecutorService;

/**
 * 创建线程池
 */
public interface ExecutorServiceHandler {
    ExecutorService createExecutorService(TaskRequestDto taskRequestConfig,ThreadPoolConfig threadPoolConfig);

}
