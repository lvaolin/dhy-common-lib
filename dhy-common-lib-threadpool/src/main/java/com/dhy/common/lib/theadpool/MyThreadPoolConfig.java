package com.dhy.common.lib.theadpool;

/**
 * @Description 线程池配置信息
 * @Author lvaolin
 * @Date 2020/12/29 12:04 下午
*/
public class MyThreadPoolConfig {
    /**
     * 线程名称
     */
    private String threadName= "shared";;
    /**
     * 核心线程数量
     */
    private Integer corePoolSize=200;
    /**
     * 线程池可扩容到的最大线程数量
     */
    private Integer maxPoolSize=200;
    /**
     * 请求排队的队列容量
     */
    private Integer queueCapacity=10000;
    /**
     * 空闲线程存活时间(单位是 秒)
     */
    private Long keepAliveTime=60L;

    public MyThreadPoolConfig() {

    }

    public MyThreadPoolConfig(String threadName) {
        this.threadName = threadName;
    }

    public MyThreadPoolConfig(String threadName,int poolSize) {
        this.threadName = threadName;
        this.corePoolSize = poolSize;
        this.maxPoolSize = poolSize;
    }

    public MyThreadPoolConfig(String threadName, int corePoolSize, int maxPoolSize, int queueCapacity, long keepAliveTime) {
        this.threadName = threadName;
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
                "threadName='" + threadName + '\'' +
                ", corePoolSize=" + corePoolSize +
                ", maxPoolSize=" + maxPoolSize +
                ", queueCapacity=" + queueCapacity +
                ", keepAliveTime=" + keepAliveTime +
                '}';
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }
}
