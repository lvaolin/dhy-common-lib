/**
 * @Project horcrux
 * @Description 主要用途描述
 * @Author lvaolin
 * @Date 2021/4/21 5:30 下午
 */
package com.dhy.common.lib.peakcut;

/**
 *
 * peakcut 组件的目标是提供
 * 1、一种异步任务接收、任务执行、任务结果存储、任务结果查询的通用模型
 * 2、同时支持共享线程池与专用线程池
 * 3、扩展方便
 *
 * 原理是：
 * 1、调用 TaskFacade.createTask 来提交任务，同时会得到 此任务的taskId
 * 2、任务异步执行
 * 3、任务执行完毕后将结果存储到 一种存储介质中，比如redis
 * 4、调用方通过 taskId从存储介质中查询任务状态和结果
 *
 *
 **/