package com.base.common.framework.task;

import java.util.Map;

import org.apache.log4j.Logger;

import com.base.common.time.TimeUtil;

/**
 * 抽象任务类
 * 
 * @author Administrator
 * 
 */
public abstract class AbstractTask implements Runnable {
    /**
     * 日志对象
     */
    private static Logger logger = Logger.getLogger(AbstractTask.class);

    /**
     * 任务参数
     */
    private Map<String, Object> taskParams;

    /**
     * 构造函数
     */
    public AbstractTask(Map<String, Object> taskParams) {
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

    @Override
    public void run() {
        long sTime = System.currentTimeMillis();
        execute();
        logger.info("多线程任务[" + this.getClass() + "]执行消耗时间：" + TimeUtil.consumeTime(sTime) + "毫秒");
    }

    /**
     * 任务执行主函数
     */
    public abstract void execute();

    /**
     * @return the taskParams
     */
    public Map<String, Object> getTaskParams() {
        return taskParams;
    }

}
