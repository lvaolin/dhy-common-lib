package com.dhy.common.lib.peakcut;

public enum TaskStatus {
    status_queue ,       //排队中
    status_running ,    //正在执行
    status_not_exist ,  //不存在或已经被清理掉
    status_completed ,     //任务已完成
    status_exception      //有异常发生
}
