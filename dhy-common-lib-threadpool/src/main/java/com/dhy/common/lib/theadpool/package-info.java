/**
 * @Project horcrux
 * @Description 自定义线程池
 * @Author lvaolin
 * @Date 2021/1/6 9:28 上午
 */
package com.dhy.common.lib.theadpool;


/**
 * 这是一个方便获取 一个可命名的线程池的 类库
 * 可解决的问题：
 * 1、微服务中线程池滥用，参数的设置往往不合理，造成资源浪费，另外没有给线程池命名，给监控和排查问题带来不便
 * 2、其实大部分场景不需要一个专用的线程池，只需要一个共享的线程池足矣
 * 3、此类库可以方便提供一个微服务内全局共享的线程池，也方便提供由你来命名的为某些接口使用的专用线程池
 * 用法：
 * 1)获取共享线程池的方式
 *   ExecutorService sharedEs = MyThreadPoolFacade.getSharedExecutorService()
 * 2)获取一个专用线程池的方式
 *   下面获取一个名称为mypool的大小为20的固定线程数目的线程池
 *   ExecutorService myEs =  MyThreadPoolFacade.getExecutorService(new MyThreadPoolConfig("mypool",20));
 *   如果需要更详细的参数设置可以对MyThreadPoolConfig进行设置来定制
 **/