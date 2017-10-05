package com.base.common.time;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.base.common.string.StringUtil;

/**
 * @author hhx
 *
 */
public class DateUtil {
    private static Logger logger = Logger.getLogger(DateUtil.class);

    // 常用的集中日期格式
    public static String dateFormat_ymdhms = "yyyy/MM/dd HH:mm:ss";
    public static String dateFormat_dmy = "dd/MM/yyyy";
    public static String dateFormat_dmyhms = "dd/MM/yyyy HH:mm:ss";
    public static String dateFormat_ymd = "yyyy/MM/dd";

    public static String dateFormat_x_ymdhms = "yyyyMMdd HH:mm:ss";
    public static String dateFormat_x_ymd = "yyyyMMdd";

    public static String dateFormat_s_ymdhms = "yyyy-MM-dd HH:mm:ss";
    public static String dateFormat_s_dmy = "dd-MM-yyyy";
    public static String dateFormat_s_dmyhms = "dd-MM-yyyy HH:mm:ss";
    public static String dateFormat_s_ymd = "yyyy-MM-dd";

    /**
     * 尝试将多种日期格式数据转换成日期类型
     * 
     * @param dateStr
     * @return
     */
    public static Date string2Date(String dateStr) {
        if (StringUtil.isEmptyOrNull(dateStr)) {
            return null;
        }
        dateStr = dateStr.replace("-", "/");

        DateFormat format = new SimpleDateFormat(dateFormat_ymdhms);
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            logger.error("日期类型转换失败", e);
        }

        format = new SimpleDateFormat(dateFormat_dmyhms);
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            logger.error("日期类型转换失败", e);
        }

        format = new SimpleDateFormat(dateFormat_dmy);
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            logger.error("日期类型转换失败", e);
        }

        format = new SimpleDateFormat(dateFormat_ymd);
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            logger.error("日期类型转换失败", e);
        }

        format = new SimpleDateFormat(dateFormat_x_ymdhms);
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            logger.error("日期类型转换失败", e);
        }

        format = new SimpleDateFormat(dateFormat_x_ymd);
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            logger.error("日期类型转换失败", e);
        }

        return null;
    }

    /**
     * 转换为java.sql.Date
     * 
     * @param date
     * @return
     */
    public static java.sql.Date date2SqlDate(Date date) {
        if (date == null) {
            return null;
        }

        return new java.sql.Date(date.getTime());
    }

    /**
     * 转换为java.sql.Date
     * 
     * @param date
     * @return
     */
    public static java.sql.Timestamp date2SqlTimestamp(Date date) {
        if (date == null) {
            return null;
        }

        return new java.sql.Timestamp(date.getTime());
    }

    /**
     * 将格式为yyyy-MM-dd HH:mm:ss字符串日期转换成日期
     * 
     * @param dateStr
     *            格式为yyyy-MM-dd HH:mm:ss的字符串
     * @return
     */
    public static Date convertStr2Date(String dateStr) {
        return convertStr2Date(dateStr, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 将指定格式的字符串日期转换成日期
     * 
     * @param dateStr
     *            指定格式的字符串
     * @param formatStr
     *            字符串格式
     * @return
     */
    public static Date convertStr2Date(String dateStr, String formatStr) {
        Date time = null;
        try {
            SimpleDateFormat formatDate = new SimpleDateFormat(formatStr);
            time = formatDate.parse(dateStr);
        } catch (Exception e) {
            logger.warn("错误的时间格式值【" + dateStr + "】", e);
        }
        return time;
    }

    /**
     * 获得当前日期指定格式的字符串 格式如：yyyy-MM-dd
     * 
     * @param formatStr
     *            日期各式
     * @return
     */
    public static String getCurrentDateString(String formatStr) {

        return getDateString(formatStr, new Date());
    }

    /**
     * 获得当前日期指定格式的字符串 格式如：yyyy-MM-dd
     * 
     * @param formatStr
     *            日期各式
     * @return
     */
    public static String getDateString(String formatStr, Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);

        return sdf.format(date);
    }

    /**
     * 获得当前日期指定格式的字符串 格式如：yyyy-MM-dd
     * 
     * @param formatStr
     *            日期各式
     * @return
     * @throws ParseException
     */
    public static String getDateString(String formatStr, String dateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);

        return sdf.format(sdf.parse(dateStr));
    }
}
