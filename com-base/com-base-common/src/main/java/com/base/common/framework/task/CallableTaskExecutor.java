package com.base.common.framework.task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

/**
 * 
 * 将一组任务放到线程池中执行
 * 
 * @author: hhx
 * @version: 1.0, 2014-08-07
 * 
 */
public class CallableTaskExecutor<T> {

    /**
     * 日志对象
     */
    private static Logger logger = Logger.getLogger(CallableTaskExecutor.class);

    /**
     * 固定线程数量的线程池
     */
    private ExecutorService executorService;

    /**
     * 任务列表
     */
    private List<CallableTask<T>> list;

    /**
     * 结果对象
     */
    private List<Future<T>> futureList = new ArrayList<Future<T>>();

    /**
     * 任务队列
     * 
     * @param poolSize
     *            缓存次大小
     */
    public CallableTaskExecutor(int poolSize) {
        // 有效处理器数
        executorService = Executors.newFixedThreadPool(poolSize);
        list = new ArrayList<CallableTask<T>>();
    }

    /**
     * 任务队列
     * 
     * @param poolSize
     *            缓存次大小
     * @param priority
     *            线程执行优先级
     */
    public CallableTaskExecutor(int poolSize, int priority) {
        // 有效处理器数
        executorService = Executors.newFixedThreadPool(poolSize, new PriorityThreadFactory(priority));
        list = new ArrayList<CallableTask<T>>();
    }

    /**
     * 添加任务
     * 
     * @param task
     *            任务对象
     */
    public void addTask(CallableTask<T> task) {
        list.add(task);
    }

    /**
     * 运行任务队列
     * 
     * @return
     */
    public void run() {
        logger.info("任务数量：" + list.size());
        for (int i = 0; i < list.size(); i++) {
            futureList.add(executorService.submit(list.get(i)));
        }
        executorService.shutdown();
    }

    /**
     * 获得多线程执行结果
     * 
     * @return
     */
    public List<T> getResultList() {
        List<T> result = new ArrayList<T>();

        for (Future<T> future : futureList) {
            try {
                result.add(future.get());
            } catch (Exception e) {
                logger.error("获取多线程结果失败：", e);
            }
        }

        return result;
    }

    /**
     * 是否所有线程任务已经执行完毕
     * 
     * @return
     */
    public boolean isTerminated() {
        return executorService.isTerminated();
    }

    /**
     * 等待一定时间，判断线程是否结束
     * 
     * @param num
     *            时间单元值
     * @param unit
     *            时间单元
     * @return 线程是否结束
     * @throws InterruptedException
     */
    public boolean awaitTermination(long num, TimeUnit unit) throws InterruptedException {
        return executorService.awaitTermination(num, unit);
    }

    /**
     * 立即结束当前线程池中的线程
     */
    public void shutdownNow() {
        executorService.shutdownNow();
    }

    /**
     * 等待直到结束
     */
    public void waitUntilTerminated() {
        while (!isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                logger.error("等待多线程任务结束报错", e);
            }
        }
    }

    /**
     * 等待指定时间
     * 
     * @param ms
     *            等待毫秒
     */
    public void waitUntilTerminated(int ms) {
        while (!isTerminated()) {
            try {
                Thread.sleep(ms);
            } catch (InterruptedException e) {
                logger.error("等待多线程任务结束报错", e);
            }
        }
    }
}
