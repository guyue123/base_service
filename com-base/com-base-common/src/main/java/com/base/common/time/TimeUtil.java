package com.base.common.time;

import org.apache.log4j.Logger;

/**
 * 时间处理类
 * 
 * @author hhx
 *
 */
public final class TimeUtil {

    /**
     * 日志.
     */
    private static Logger log = Logger.getLogger(TimeUtil.class);

    /**
     * 私有化构造函数.
     */
    private TimeUtil() {
    }

    /**
     * 程序运行时间.
     * 
     * @param startTime
     *            运行开始时间
     * @return 时间差
     */
    public static long consumeTime(final long startTime) {
        return System.currentTimeMillis() - startTime;
    }
}
