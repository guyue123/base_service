package com.base.common.framework.task;

import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

import com.base.common.time.TimeUtil;

/**
 * 抽象任务类
 * 
 * @author Administrator
 * 
 */
public abstract class CallableTask<T> implements Callable<T> {
    /**
     * 日志对象
     */
    private static Logger logger = Logger.getLogger(CallableTask.class);

    /**
     * 任务参数
     */
    private Map<String, Object> taskParams;

    /**
     * 构造函数
     */
    public CallableTask(Map<String, Object> taskParams) {
        this.taskParams = taskParams;
    }

    /**
     * 获得指定参数的值
     * 
     * @param key
     * @return
     */
    public Object getParam(String key) {
        if (this.taskParams == null) {
            return null;
        }

        return this.taskParams.get(key);
    }

    /**
     * 任务执行主函数
     */
    public abstract T execute();

    /*
     * (non-Javadoc)
     * 
     * @see java.util.concurrent.Callable#call()
     */
    @Override
    public T call() throws Exception {
        long sTime = System.currentTimeMillis();
        T result = this.execute();
        logger.info("多线程任务[" + this.getClass() + "]执行消耗时间：" + TimeUtil.consumeTime(sTime) + "毫秒");
        return result;
    }

    /**
     * @return the taskParams
     */
    public Map<String, Object> getTaskParams() {
        return taskParams;
    }

}
