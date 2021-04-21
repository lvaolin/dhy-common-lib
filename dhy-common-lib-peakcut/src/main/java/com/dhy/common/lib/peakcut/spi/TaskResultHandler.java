package com.dhy.common.lib.peakcut.spi;

import com.dhy.common.lib.peakcut.TaskRequestDto;
import com.dhy.common.lib.peakcut.TaskResponseDto;

/**
 * 任务结果管理器
 */
public interface TaskResultHandler {

    /**
     * 任务进入线程池之前的排队状态（初始化结果，更新状态为"排队中"）
     * @param requestDto
     * @return
     */
    void initResult(TaskRequestDto requestDto);
    /**
     * 任务进入了线程池（更新结果状态为"处理中"）
     * @param requestDto
     * @return
     */
    void beforeResult(TaskRequestDto requestDto);

    /**
     * 任务完成之后（结果回写，更新状态为"完成"）
     * @param requestDto
     * @param result
     * @return
     */
    void afterResult(TaskRequestDto requestDto,Object result);

    /**
     * 任务发生异常后 （异常回写，更新状态为"异常"）
     * @param requestDto
     * @param exception
     * @return
     */
    void exceptionResult(TaskRequestDto requestDto,Object exception);

    /**
     * 结果查询
     * @param taskRequestId
     * @return
     */
    TaskResponseDto queryResult(String taskRequestId);

    /**
     * 结果的清理释放
     * @param taskRequestId
     */
    void removeResult(String taskRequestId);
}
