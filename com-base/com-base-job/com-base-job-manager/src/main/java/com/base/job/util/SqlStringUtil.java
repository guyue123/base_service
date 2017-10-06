/** 
 *@Project: 定时任务
 *@Date: 2015年7月29日 
 *@Copyright: 2015 www.base.com.cn Inc. All rights reserved. 
 */
package com.base.job.util;

import java.util.Date;

import com.base.common.time.DateUtil;

/**
 * SQL条件
 *
 * @author <a href="mailto:huhuaxiang@base.com.cn">hhx</a>
 * @since 1.0.0
 * @version 版本：1.0.0
 * @from 2015年7月29日
 */

public class SqlStringUtil {

    /**
     * 组合插入时间条件
     * 
     * @param startTime
     *            开始时间
     * @param endTime
     *            结束时间
     * @return
     */
    public static String getTimeCondition(String field, Date startTime, Date endTime) {
        if (startTime == null && endTime == null) {
            return " 1=1";
        }

        if (startTime == null && endTime != null) {
            return " " + field + " <=STR_TO_DATE('" + DateUtil.getDateString("yyyy-MM-dd hh:mm:ss", endTime)
                    + "','%Y-%m-%d %H:%i:%s')";
        }

        if (startTime != null && endTime == null) {
            return " " + field + " >=STR_TO_DATE('" + DateUtil.getDateString("yyyy-MM-dd hh:mm:ss", startTime)
                    + "','%Y-%m-%d %H:%i:%s')";
        }

        return " (" + field + " >=STR_TO_DATE('" + DateUtil.getDateString("yyyy-MM-dd hh:mm:ss", startTime)
                + "','%Y-%m-%d %H:%i:%s') and " + field + "<=STR_TO_DATE('"
                + DateUtil.getDateString("yyyy-MM-dd hh:mm:ss", endTime) + "','%Y-%m-%d %H:%i:%s'))";
    }

    /**
     * 查询翻页
     * 
     * @param pageIndx
     *            从1开始
     * @param pageSize
     *            偏移量
     * @return
     */
    public static String getLimit(int pageIndx, int pageSize) {
        if (pageIndx < 1) {
            pageIndx = 1;
        }
        return " limit " + (pageIndx - 1) * pageSize + "," + pageSize;
    }
}
