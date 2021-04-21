package com.dhy.common.lib.peakcut.spring;


import com.dhy.common.lib.peakcut.config.IThreadPoolConfigService;

//@Component
public class TaskFacadeForSpringService extends TaskFacadeForSpring implements ITaskFacadeForSpringService {
    //@Reference
    private IThreadPoolConfigService sysThreadPoolConfigService;
    @Override
    //@PostConstruct
    public void init(){
        String serviceName ="";
        if (serviceName==null||"".equals(serviceName)) {
            return;
        }
        super.init(serviceName,sysThreadPoolConfigService);
    }

}
