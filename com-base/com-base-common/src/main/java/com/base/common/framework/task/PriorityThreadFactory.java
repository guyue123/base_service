/**
 * 
 */
package com.base.common.framework.task;

import java.util.concurrent.ThreadFactory;

/**
 * 线程工厂, 设置线程优先级
 * 
 * @author Administrator
 *
 */
public class PriorityThreadFactory implements ThreadFactory {

    /**
     * 默认优先级
     */
    private int priority = Thread.NORM_PRIORITY;

    /**
     * 
     */
    public PriorityThreadFactory(int priority) {
        if (priority >= Thread.MIN_PRIORITY && priority <= Thread.MAX_PRIORITY) {
            this.priority = priority;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.concurrent.ThreadFactory#newThread(java.lang.Runnable)
     */
    @Override
    public Thread newThread(Runnable paramRunnable) {
        Thread t = new Thread(paramRunnable);
        t.setPriority(priority);
        return t;
    }

}
