package com.dhy.common.lib.peakcut.config;


import java.io.Serializable;

public class ThreadPoolConfigDto implements Serializable {
	private Long id;
	private String serviceName;
	private String taskPoolKey;
	private Integer corePoolSize;
	private Integer maxPoolSize;
	private Integer queueCapacity;
	private Long keepAliveTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getTaskPoolKey() {
		return taskPoolKey;
	}

	public void setTaskPoolKey(String taskPoolKey) {
		this.taskPoolKey = taskPoolKey;
	}

	public Integer getCorePoolSize() {
		return corePoolSize;
	}

	public void setCorePoolSize(Integer corePoolSize) {
		this.corePoolSize = corePoolSize;
	}

	public Integer getMaxPoolSize() {
		return maxPoolSize;
	}

	public void setMaxPoolSize(Integer maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	public Integer getQueueCapacity() {
		return queueCapacity;
	}

	public void setQueueCapacity(Integer queueCapacity) {
		this.queueCapacity = queueCapacity;
	}

	public Long getKeepAliveTime() {
		return keepAliveTime;
	}

	public void setKeepAliveTime(Long keepAliveTime) {
		this.keepAliveTime = keepAliveTime;
	}


}