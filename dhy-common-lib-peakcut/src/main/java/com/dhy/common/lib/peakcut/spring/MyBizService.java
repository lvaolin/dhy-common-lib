package com.dhy.common.lib.peakcut.spring;

import com.dhy.common.lib.peakcut.TaskRequestDto;

import java.util.concurrent.Callable;

//@Component("MyBizService1")
public class MyBizService {
    //@Autowired
    private TaskFacadeForSpringService taskFacade;

    public String myMethod(Object parameter){
        TaskRequestDto taskRequestDto = new TaskRequestDto();
        taskRequestDto.setCallable(new Callable() {
            @Override
            public Object call() throws Exception {
                return rpcMethod(parameter);
            }
        });
        return taskFacade.createTask(taskRequestDto);
    }

    public Object rpcMethod(Object object){
        //---rpc----
        return new Object();
    }
}
