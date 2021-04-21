package com.dhy.common.lib.peakcut.spring;

import com.dhy.common.lib.peakcut.TaskFacade;
import com.dhy.common.lib.peakcut.config.IThreadPoolConfigService;
import com.dhy.common.lib.peakcut.config.ThreadPoolConfigDto;
import com.dhy.common.lib.peakcut.impl.ThreadPoolConfig;
import com.dhy.common.lib.peakcut.internal.TaskThreadPoolManager;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;


@Slf4j
public class TaskFacadeForSpring extends TaskFacade {

    public void init(String serviceName, IThreadPoolConfigService sysThreadPoolConfigService){
        if (serviceName==null) {
            log.error("MyTaskFacade serviceName is null");
            serviceName = "service-default";
        }
        if (sysThreadPoolConfigService==null) {
            log.error("MyTaskFacade sysThreadPoolConfigService is null");
            return;
        }
        Map<String, ThreadPoolConfig> threadPoolConfigMap = getThreadPoolConfigMapFromDb(serviceName,sysThreadPoolConfigService);
        super.setThreadPoolConfigMap(threadPoolConfigMap);
        updateConfig(serviceName,sysThreadPoolConfigService);
    }

    private Map<String, ThreadPoolConfig> getThreadPoolConfigMapFromDb(String serviceName, IThreadPoolConfigService sysThreadPoolConfigService) {
        Map<String,ThreadPoolConfig> threadPoolConfigMap = new HashMap<>();
        ThreadPoolConfigDto dto = new ThreadPoolConfigDto();
        dto.setServiceName(serviceName);
        List<ThreadPoolConfigDto> list = sysThreadPoolConfigService.query(dto);
        list.stream().forEach((configDto)->{
            ThreadPoolConfig poolConfig = new ThreadPoolConfig();
            poolConfig.setCorePoolSize(configDto.getCorePoolSize());
            poolConfig.setMaxPoolSize(configDto.getMaxPoolSize());
            poolConfig.setQueueCapacity(configDto.getQueueCapacity());
            poolConfig.setKeepAliveTime(configDto.getKeepAliveTime());
            threadPoolConfigMap.put(configDto.getTaskPoolKey(),poolConfig);
        });
        return threadPoolConfigMap;
    }

    /**
     * 定时更新
     */
    private void updateConfig(String serviceName, IThreadPoolConfigService sysThreadPoolConfigService){
        Executors
                .newSingleThreadScheduledExecutor()
                .scheduleWithFixedDelay(new Runnable() {
                    @Override
                    public void run() {
                        Map<String, ThreadPoolConfig> threadPoolConfigMap = getThreadPoolConfigMapFromDb(serviceName,sysThreadPoolConfigService);
                        TaskThreadPoolManager.executorServiceMap.entrySet().forEach((e)->{
                            ThreadPoolConfig poolConfig = threadPoolConfigMap.get(e.getKey());
                            log.error("peakcut线程池参数定时更新："+poolConfig.toString());
                            if (poolConfig!=null) {
                                ExecutorService executorService = e.getValue();
                                if (executorService!=null) {
                                    if(executorService instanceof ThreadPoolExecutor){
                                        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor)executorService;
                                        threadPoolExecutor.setCorePoolSize(poolConfig.getCorePoolSize());
                                        threadPoolExecutor.setMaximumPoolSize(poolConfig.getMaxPoolSize());
                                        threadPoolExecutor.setKeepAliveTime(poolConfig.getKeepAliveTime(),TimeUnit.SECONDS);
                                    }
                                }
                            }

                        });
                    }
                },60,60, TimeUnit.SECONDS);
    }

}
