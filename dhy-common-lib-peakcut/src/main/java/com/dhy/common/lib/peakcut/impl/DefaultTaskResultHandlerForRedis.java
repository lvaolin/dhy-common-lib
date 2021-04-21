package com.dhy.common.lib.peakcut.impl;

import com.dhy.common.lib.cache.util.CacheUtil;
import com.dhy.common.lib.peakcut.TaskHelper;
import com.dhy.common.lib.peakcut.TaskRequestDto;
import com.dhy.common.lib.peakcut.TaskResponseDto;
import com.dhy.common.lib.peakcut.TaskStatus;
import com.dhy.common.lib.peakcut.spi.TaskResultHandler;

public class DefaultTaskResultHandlerForRedis implements TaskResultHandler {
    public String DEFAULT_ORGID = TaskHelper.peakcut;
    public int DEFAULT_EXPIRE = 60 * 60;

    @Override
    public void initResult(TaskRequestDto requestDto) {
        TaskResponseDto taskResponseDto = new TaskResponseDto();
        taskResponseDto.setTaskStatus(TaskStatus.status_queue);
        CacheUtil.string$set(DEFAULT_ORGID, requestDto.getTaskRequestId(), taskResponseDto, DEFAULT_EXPIRE);
    }

    @Override
    public void beforeResult(TaskRequestDto requestDto) {
        TaskResponseDto res = CacheUtil.string$get(DEFAULT_ORGID, requestDto.getTaskRequestId(), TaskResponseDto.class);
        if (res == null) {
            res = new TaskResponseDto();
        }
        res.setTaskStatus(TaskStatus.status_running);
        CacheUtil.string$set(DEFAULT_ORGID, requestDto.getTaskRequestId(), res, DEFAULT_EXPIRE);
    }

    @Override
    public void afterResult(TaskRequestDto requestDto, Object result) {
        TaskResponseDto res = CacheUtil.string$get(DEFAULT_ORGID, requestDto.getTaskRequestId(), TaskResponseDto.class);
        if (res == null) {
            res = new TaskResponseDto();
        }
        res.setTaskStatus(TaskStatus.status_completed);
        res.setTaskResult(result);
        CacheUtil.string$set(DEFAULT_ORGID, requestDto.getTaskRequestId(), res, DEFAULT_EXPIRE);
    }

    @Override
    public void exceptionResult(TaskRequestDto requestDto, Object result) {
        TaskResponseDto res = CacheUtil.string$get(DEFAULT_ORGID, requestDto.getTaskRequestId(), TaskResponseDto.class);
        if (res == null) {
            res = new TaskResponseDto();
        }
        res.setTaskStatus(TaskStatus.status_exception);
        if (result instanceof Exception) {
            res.setTaskErrorMsg(((Exception) result).getMessage());
        } else {
            res.setTaskErrorMsg(result.toString());
        }
        CacheUtil.string$set(DEFAULT_ORGID, requestDto.getTaskRequestId(), res, DEFAULT_EXPIRE);

    }

    @Override
    public TaskResponseDto queryResult(String taskRequestId) {
        TaskResponseDto res = CacheUtil.string$get(DEFAULT_ORGID, taskRequestId, TaskResponseDto.class);
        if (res == null) {
            res = new TaskResponseDto();
            res.setTaskStatus(TaskStatus.status_not_exist);
            res.setTaskErrorMsg("任务不存在或已过期");
        }
        return res;
    }

    @Override
    public void removeResult(String taskRequestId) {
        CacheUtil.delete(DEFAULT_ORGID, taskRequestId);
    }

}
