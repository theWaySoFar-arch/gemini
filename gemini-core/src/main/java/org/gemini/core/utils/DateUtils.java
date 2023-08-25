package org.gemini.core.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author TheWaySoFar
 * @version 1.0
 * @describe 日期工具类
 * @date 2023/8/25 19:50
 */
public class DateUtils {
    /**
     * 日期格式：年-月-日 时-分-秒
     */
    public static final String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss";
    /**
     * 日期格式：年-月-日
     */
    public static final String FORMAT_DATE = "yyyy-MM-dd";
    /**
     * 日期格式：时-分-秒
     */
    public static final String FORMAT_TIME = "HH:mm:ss";

    public static final String FORMAT_FULL_MS= "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * 将时间戳格式化为特定的日期格式
     * @param timestamp
     * @param format
     * @return
     */
    public static String formatTimestamp(final long timestamp, final String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(timestamp));
    }

    /**
     * 将Date类型的时间数据转化为特定的日期格式
     * @param date
     * @param format
     * @return
     */
    public static String formatDate(final Date date, final String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 获取指定时间的年份
     * @param date
     * @return
     */
    public static Integer getYear(final Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    /**
     * 获取指定时间的月份
     * @param date Date类型时间
     * @return 指定时间的月
     */
    public static Integer getMonth(final Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH);
    }

    /**
     * 获取指定时间的日
     * @param date Date类型时间
     * @return  指定时间的日
     */
    public static Integer getDay(final Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DATE);
    }
}
