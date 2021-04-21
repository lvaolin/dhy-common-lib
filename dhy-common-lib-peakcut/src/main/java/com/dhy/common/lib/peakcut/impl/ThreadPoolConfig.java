package com.dhy.common.lib.peakcut.impl;

/**
 * 线程池配置信息
 */
public class ThreadPoolConfig {
    /**
     * 核心线程数量
     */
    private Integer corePoolSize;
    /**
     * 线程池可扩容到的最大线程数量
     */
    private Integer maxPoolSize;
    /**
     * 请求排队的队列容量
     */
    private Integer queueCapacity;
    /**
     * 空闲线程存活时间(单位是 秒)
     */
    private Long keepAliveTime;

    public ThreadPoolConfig() {
        //默认参数
        corePoolSize = 50;
        maxPoolSize = 50;
        keepAliveTime = 60L;
        queueCapacity = 10000;
    }

    public ThreadPoolConfig(int corePoolSize, int maxPoolSize, int queueCapacity, long keepAliveTime) {
        this.corePoolSize = corePoolSize;
        this.maxPoolSize = maxPoolSize;
        this.queueCapacity = queueCapacity;
        this.keepAliveTime = keepAliveTime;
    }

    public Integer getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(Integer corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public Integer getQueueCapacity() {
        return queueCapacity;
    }

    public void setQueueCapacity(Integer queueCapacity) {
        this.queueCapacity = queueCapacity;
    }


    public Integer getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(Integer maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public Long getKeepAliveTime() {
        return keepAliveTime;
    }

    public void setKeepAliveTime(Long keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }

    @Override
    public String toString() {
        return "ThreadPoolConfig{" +
                "corePoolSize=" + corePoolSize +
                ", maxPoolSize=" + maxPoolSize +
                ", queueCapacity=" + queueCapacity +
                ", keepAliveTime=" + keepAliveTime +
                '}';
    }
}
