package com.ck.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.StringUtils;

public class TimeUtils {

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final String DATE_FORMAT_0 = "yyyyMMddHHmmssSSS";
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final String DATE_FORMAT_1 = "yyyy-MM-dd HH:mm:ss";

    /**
     * yyyy-MM-dd
     */
    public static final String DATE_FORMAT_2 = "yyyy-MM-dd";

    /**
     * hh:mm:ss
     */
    public static final String DATE_FORMAT_3 = "hh:mm:ss";

    /**
     * yyyyMMddHHmmss
     */
    public static final String DATE_FORMAT_4 = "yyyyMMddHHmmss";

    /**
     * yyyy年MM月dd日 HH:mm
     */
    public static final String DATE_FORMAT_5 = "yyyy年MM月dd日 HH:mm";

    /**
     * MM月dd日
     */
    public static final String DATE_FORMAT_6 = "MM月dd日";

    /**
     * yyyy-MM
     */
    public static final String DATE_FORMAT_7 = "yyyy-MM";

    /**
     * yyyyMMdd
     */
    public static final String DATE_FORMAT_8 = "yyyyMMdd";

    /**
     * M月d日 HH:mm
     */
    public static final String DATE_FORMAT_9 = "M月d日  HH:mm";

    /**
     * yyyy年M月d日 HH时mm分ss秒
     */
    public static final String DATE_FORMAT_10 = "yyyy年M月d日 HH时mm分ss秒";

    /**
     * yyyy-MM-dd HH:mm
     */
    public static final String DATE_FORMAT_12 = "yyyy-MM-dd HH:mm";

    /**
     * yyyy年MM月dd日HH时mm分ss秒
     */
    public static final String DATE_FORMAT_13 = "yyyy年MM月dd日HH时mm分ss秒";

    /**
     * HH:mm:ss
     */
    public static final String DATE_FORMAT_14 = "HH:mm:ss";

    /**
     * HHmmss
     */
    public static final String DATE_FORMAT_15 = "HHmmss";

    /**
     * Comment for <code>HH_MM</code>
     */
    public static final String HH_MM = "HH:mm";

    /**
     * 获取当前时间
     * 
     * @return
     */
    public static String getCurTime() {
        return dateToString(new Date(), DATE_FORMAT_14);
    }

    /**
     * 返回当前时间字符串。
     * <p>
     * 格式：yyyy-MM-dd HH:mm:ss
     * 
     * @return String 指定格式的日期字符串.
     */
    public static String getCurrentTime() {
        return dateToString(new Date(), DATE_FORMAT_1);
    }

    /**
     * 根据给定的格式，返回时间字符串。
     * <p>
     * 格式参照类描绘中说明.
     * 
     * @param format
     *            日期格式字符串
     * @return String 指定格式的日期字符串.
     */
    public static String getFormatCurrentTime(String format) {
        return dateToString(new Date(), format);
    }

    /**
     * 字符串时间转换为时间类型
     * 
     * @param dateString
     * @param format
     * @return
     */
    public static Date stringToDate(String dateString, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException("Error when  getDateFromString from dateString, errmsg: " + e.getMessage(), e);
        }
    }

    /**
     * toDate for format "yyyy-MM-dd HH:mm:ss"
     * 
     * @param dateString
     * @return
     */
    public static Date toDateFormat_1(String dateString) {
        return stringToDate(dateString, DATE_FORMAT_1);
    }

    /**
     * toDate for format "yyyy-MM-dd"
     * 
     * @param dateString
     * @return
     */
    public static Date toDateFormat_2(String dateString) {
        return stringToDate(dateString, DATE_FORMAT_2);
    }

    /**
     * toDate for format "yyyyMMddHHmmss"
     * 
     * @param dateString
     * @return
     */
    public static Date toDateFormat_4(String dateString) {
        return stringToDate(dateString, DATE_FORMAT_4);
    }

    /**
     * 时间类型转换为字符串类型
     * 
     * @param date
     * @param format
     * @return
     */
    public static String dateToString(Date date, String format) {
        if (null == date) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 比较时间
     * 
     * @param endDate
     * @param startDate
     * @return
     */
    public static long diff(Date endDate, Date startDate) {
        long endTime = getMillis(endDate);
        long startTime = getMillis(startDate);
        return endTime - startTime;
    }

    public static boolean diff(Date endDate, Date startDate, int n) {
        long endTime = getMillis(endDate);
        long startTime = getMillis(startDate);
        return (endTime - startTime - n * 24 * 3600 * 1000L) > 0 ? true : false;
    }

    public static boolean diffY(Date endDate, Date startDate) {
        String endTime = toStringFormat_2(endDate);
        String startTime = toStringFormat_2(startDate);

        return endTime.compareTo(startTime) >= 1 ? true : false;
    }

    /**
     * get million
     * 
     * @param dt
     * @return
     */
    public static long getMillis(Date dt) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        return cal.getTimeInMillis();
    }

    /**
     * toString for format 1 yyyy-MM-dd HH:mm:ss
     * 
     * @param date
     * @return
     */
    public static String toStringFormat_1(Date date) {

        return dateToString(date, DATE_FORMAT_1);
    }

    /**
     * toString for format 2 yyyy-MM-dd
     * 
     * @param date
     * @return
     */
    public static String toStringFormat_2(Date date) {

        return dateToString(date, DATE_FORMAT_2);
    }

    /**
     * toString for format 4 yyyyMMddHHmmss
     * 
     * @param date
     * @return
     */
    public static String toStringFormat_4(Date date) {

        return dateToString(date, DATE_FORMAT_4);
    }

    /**
     * yyyy年MM月dd日 HH:mm
     * 
     * @param date
     * @return
     */
    public static String toStringFormat_5(Date date) {
        return dateToString(date, DATE_FORMAT_5);
    }

    /**
     * MM月dd日
     * 
     * @param date
     * @return
     */
    public static String toStringFormat_6(Date date) {
        return dateToString(date, DATE_FORMAT_6);
    }

    /**
     * toString for format 7 yyyy-MM
     * 
     * @param date
     * @return
     */
    public static String toStringFormat_7(Date date) {

        return dateToString(date, DATE_FORMAT_7);
    }

    /**
     * toString for format 8 yyyyMMdd
     * 
     * @param date
     * @return
     */
    public static String toStringFormat_8(Date date) {

        return dateToString(date, DATE_FORMAT_8);
    }

    /**
     * toString for format 9 M月d日 HH:mm
     * 
     * @param date
     * @return
     */
    public static String toStringFormat_9(Date date) {

        return dateToString(date, DATE_FORMAT_9);
    }

    /**
     * toString for format 10 yyyy年M月d日 HH时mm分ss秒
     * 
     * @param date
     * @return
     */
    public static String toStringFormat_10(Date date) {

        return dateToString(date, DATE_FORMAT_10);
    }

    /**
     * toString for format 6.<br>
     * <b>yyyy-MM-dd HH:mm</b>
     * 
     * @param date
     * @return
     */
    public static String toStringFormat_12(Date date) {
        if (date == null)
            return "";
        return dateToString(date, DATE_FORMAT_12);
    }

    /**
     * 与当前时间的间隔
     * 
     * @param n
     * @return
     * @author weiyuanhua
     * @date 2012-8-16 下午2:40:29
     */
    public static Date getDateDiff(int n) {
        Date d = new Date();
        Date returnDay = new Date(d.getTime() + n * 24 * 3600 * 1000L);
        return returnDay;
    }

    public static String getDiffToString(Date date, int n, String pattern) {
        Date returnDay = new Date(date.getTime() + n * 24 * 3600 * 1000L);
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.format(returnDay);
    }

    /**
     * 与当前时间的间隔
     * 
     * @param n
     * @return
     * @author weiyuanhua
     * @date 2012-8-16 下午2:39:21
     */
    public static Timestamp getTimestampDiff(int n) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = new Date();
        Date dayDiff = new Date(d.getTime() + n * 24 * 3600 * 1000L);
        String time = df.format(dayDiff);
        return Timestamp.valueOf(time);
    }

    /**
     * Date 转 Timestamp
     * 
     * @return
     * @author weiyuanhua
     * @date 2012-8-16 下午2:41:01
     */
    public static Timestamp stringToTimestamp() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = df.format(new Date());
        return Timestamp.valueOf(time);
    }

    /**
     * 时间格式化
     * 
     * @param date
     * @param style
     * @return
     * @author weiyuanhua
     * @date 2012-8-16 下午2:41:40
     */
    public static String parseToString(Date date, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyPattern(pattern);
        String str = null;
        if (date == null)
            return null;
        str = simpleDateFormat.format(date);
        return str;
    }

    /**
     * 时间比大小
     */
    public static int compareWithNow(String t) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_4);
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            c1.setTime(formatter.parse(t));
            c2.setTime(new Date());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return c1.compareTo(c2);
    }

    /**
     * after day.
     * 
     * @param dt
     * @param amount
     * @return
     */
    public static Date afterDateTime(Date dt, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        cal.add(Calendar.DAY_OF_YEAR, amount);
        return cal.getTime();
    }

    /**
     * after day.
     * 
     * @param dt
     * @param amount
     * @return
     */
    public static Date afterDateTimeByHour(Date dt, int amount) {
        int days = amount / 24;
        int hours = amount % 24;
        Calendar cal = Calendar.getInstance();

        cal.setTime(dt);
        if (days > 0) {
            cal.add(Calendar.DAY_OF_YEAR, days);
        }
        if (hours > 0) {
            cal.add(Calendar.HOUR_OF_DAY, hours);
        }

        return cal.getTime();
    }

    /**
     * get max time in that day.
     * 
     * @param dateStr
     * @param dateFormat
     * @return
     */
    public static Date getMaxDate(String dateStr, String dateFormat) {
        Date dt = stringToDate(dateStr, dateFormat);
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        cal.set(Calendar.HOUR, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    /**
     * get min time in that day.
     * 
     * @param dateStr
     * @param dateFormat
     * @return
     */
    public static Date getMinDate(String dateStr, String dateFormat) {
        Date dt = stringToDate(dateStr, dateFormat);

        return getMinDate(dt);
    }

    /**
     * get min time in that day.
     * 
     * @param dt
     * @return
     */
    public static Date getMinDate(Date dt) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    /**
     * 比较当前时间是否在活动范围内
     * 
     * @param startDate
     * @param endDate
     * @return
     */
    public static boolean isActivitied(String startDate, String endDate) {
        if (StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate)) {
            startDate = startDate.trim();
            endDate = endDate.trim();
            Date sd = null;
            Date ed = null;
            if (startDate.length() == 10 && endDate.length() == 10) {
                sd = toDateFormat_2(startDate);
                ed = toDateFormat_2(endDate);
            } else if (startDate.length() == 19 && endDate.length() == 19) {
                sd = toDateFormat_1(startDate);
                ed = toDateFormat_1(endDate);
            }
            if (sd != null && ed != null) {
                Calendar startCal = Calendar.getInstance();
                startCal.setTime(sd);
                Calendar endCal = Calendar.getInstance();
                endCal.setTime(ed);

                Calendar curCal = Calendar.getInstance();

                if (curCal.after(startCal) && curCal.before(endCal)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 比较两个HH：mm:ss时间的大小
     * 
     * @param HH
     *            ：mm:ss
     * @param HH
     *            ：mm:ss
     * @return
     */
    public static boolean comepareTime(String time1, String time2) {
        String time1Str = time1.replace(":", "");
        String time2Str = time2.replace(":", "");
        return (Integer.parseInt(time1Str) < Integer.parseInt(time2Str));
    }

    /**
     * 取前一天的日期.
     * 
     * @param dt
     * @param amount
     * @return
     */
    public static Date beforeDate(Date dt) {

        Calendar calendar = Calendar.getInstance(); // 得到日历
        calendar.setTime(dt);// 把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, -1); // 设置为前一天
        return calendar.getTime(); // 得到前一天的时间

    }

    /**
     * 后期某个日期的下一天的日期
     * 
     * @param txDate
     * @return
     */
    public static String getNextDay(Date txDate) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(txDate);
        calendar.add(Calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
        Date nextDay = calendar.getTime();
        String nextDayStr = dateToString(nextDay, DATE_FORMAT_8);
        return nextDayStr;
    }

    /**
     * 获取当前日期+1个小时的日期.
     * 
     * @param dt
     * @param hours
     * @return
     */
    public static Date newDatePlusHours(Date dt, int hours) {

        Calendar calendar = Calendar.getInstance(); // 得到日历
        calendar.setTime(dt);// 把当前时间赋给日历
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();

    }

    /**
     * 获取当前日期+秒数的日期.
     * 
     * @param dt
     * @param secondes
     * @return
     */
    public static Date datePlusSeconds(Date dt, int secondes) {

        Calendar calendar = Calendar.getInstance(); // 得到日历
        calendar.setTime(dt);// 把当前时间赋给日历
        calendar.add(Calendar.SECOND, secondes);
        return calendar.getTime();

    }

}
