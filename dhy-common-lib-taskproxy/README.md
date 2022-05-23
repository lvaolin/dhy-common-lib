# dhy-common-lib-taskproxy
大黄鸭组件库:任务代理组件

/**
 * 这是一个可以解决"线程切换、线程复用时ThreadLocal数据传递问题"的类库
 * 可解决的问题：
 * 1、线程切换时（new Thread 等临时产生子线程）ThreadLocal数据传递问题
 * 2、线程复用时（向线程池提交任务）ThreadLocal数据传递问题
 
 * 用法：
 * 1) 使用 TaskProxy 类对 Runnable、Callable接口进行一个修饰代理即可
 *   Runnable runnable = (Runnable)new TaskProxy(new Runnable() {
                  @Override
                  public void run() {
                      System.out.println("使用代理Runnable："+ ThreadLocalHolder.getUserId());
                      System.out.println("使用代理Runnable："+ ThreadLocalHolder.getAppContext().getSessionId());
                  }
              },ThreadLocalHolder.class);
    
    Callable callable = (Callable) new DhyTaskProxy<String>(new Callable() {
                @Override
                public String call() throws Exception {
                    System.out.println("使用代理Callable："+ ThreadLocalHolder.getUserId());
                    System.out.println("使用代理Callable："+ ThreadLocalHolder.getAppContext());
                    return "ok";
                }
             }, ThreadLocalHolder.class);
             
     就是这样，简单明了。
 **/


