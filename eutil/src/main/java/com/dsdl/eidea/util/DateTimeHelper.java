/*
 版权所有 刘大磊 2013-07-01
 作者：刘大磊
 电话：13336390671
 email:ldlqdsd@126.com
 */

package com.dsdl.eidea.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author 刘大磊 2014年12月22日 上午10:59:16
 *         常用日期格式转换工具类
 */
public class DateTimeHelper {
    private final static ThreadLocal<DateFormat> DATEFORMAT =  ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));
    private final static ThreadLocal<DateFormat> DATE_TIME_FORMAT = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    public static String getCurrentYear() {
        Date currDate = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(currDate);
        int year = calendar.get(Calendar.YEAR);
        return String.valueOf(year);
    }

    public static int getHourMinute(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }

    public static String getCurrentMonth() {
        Date currDate = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(currDate);
        int month = calendar.get(Calendar.MONTH);
        return String.valueOf(month + 1);
    }

    public static String getCurrentDayInMonth() {
        Date currDate = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(currDate);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return String.valueOf(day);
    }

    /**
     * 解析日期时间字符串为日期类型
     *
     * @param dateTimeString 参数格式为 yyyy-MM-dd HH:mm:ss 例如：2017-01-05 13:01:22
     * @return 返回java日期对象
     */
    public static Date parseDateTime(String dateTimeString) {
        if (StringUtil.isEmpty(dateTimeString)) return null;
        Date date = null;
        try {
            date = DATE_TIME_FORMAT.get().parse(dateTimeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 解析日期字符串为日期类型
     *
     * @param dateString 参数格式为 yyyy-MM-dd 例如：2017-01-05
     * @return 返回java日期对象
     */
    public static Date parseDate(String dateString) {

        if (StringUtil.isEmpty(dateString)) return null;
        Date date = null;
        try {
            date = DATEFORMAT.get().parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 把日期转换为日期时间字符串
     *
     * @param date java 日期对象
     * @return 返回 yyyy-MM-dd HH:mm:ss 时间格式 例如：2017-01-05 09:56:56
     */
    public static String formatDateTime(Date date) {
        if (date == null) return "";
        else
            return DATE_TIME_FORMAT.get().format(date);
    }

    /**
     * 把日期转换为日期字符串
     *
     * @param date java 日期对象
     * @return 返回 yyyy-MM-dd时间格式 例如：2017-01-05
     */
    public static String formatDate(Date date) {
        if (date == null) return "";
        else
            return DATEFORMAT.get().format(date);
    }

    /**
     * 把日期字符串转换为日期对象， yyyy-MM-dd和yyyy-MM-dd HH:mm:ss格式日期不建议使用该方法。
     * parseDate(String)和parseDateTime(String)性能更高
     *
     * @param value   日期字符值
     * @param pattern 日期格式 标准java日期pattern.例如：
     *                yyyy-MM-dd
     *                yyyy-MM-dd HH:mm:ss
     *                yyyy-MM-dd'T'HH:mm:ss.SSSZ 等等
     * @return 返回java日期对象
     */
    public static Date parseDate(String value, String pattern) {

        DateFormat dateFormat = new SimpleDateFormat(pattern);
        try {
            if (StringUtil.isEmpty(value)) {
                return null;
            }
            return dateFormat.parse(value);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 根据指定格式输出日期字符串  yyyy-MM-dd和yyyy-MM-dd HH:mm:ss格式日期不建议使用该方法
     * formatDate(Date)和formatDateTime(Date)性能更高
     *
     * @param date    日期参数
     * @param pattern 输出格式
     *                yyyy-MM-dd
     *                yyyy-MM-dd HH:mm:ss
     *                yyyy-MM-dd'T'HH:mm:ss.SSSZ 等等
     * @return 指定输出格式日期字符串
     */
    public static String formatDate(Date date, String pattern) {
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        if (date == null) {
            return "";
        }
        return dateFormat.format(date);
    }
}
