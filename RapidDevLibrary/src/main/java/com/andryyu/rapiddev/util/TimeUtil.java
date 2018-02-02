package com.andryyu.rapiddev.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by yufei on 2017/9/17.
 */
public class TimeUtil {

    /**
     * A map to cache date pattern string to SimpleDateFormat object
     */
    private static final Map<String, ThreadLocal<SimpleDateFormat>> SDF_MAP = new HashMap<String, ThreadLocal<SimpleDateFormat>>();
    private static final TimeZone GMT_TIMEZONE = TimeZone.getTimeZone("GMT");
    public static final SimpleDateFormat DEFAULT_SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    public static final SimpleDateFormat NURSE_SDF = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());

    /**
     * <p>getCurTimeString</p>
     *
     * @return 时间字符串
     * @Description： 获取当前时间
     */
    public static String getCurTimeString() {
        return date2String(new Date());
    }

    /**
     * <p>date2String</p>
     *
     * @param time Date类型时间
     * @return 时间字符串
     * @Description: 将Date类型转化成格式为yyyy-MM-dd HH:mm:ss<
     */
    public static String date2String(Date time) {
        return date2String(time, DEFAULT_SDF);
    }

    /**
     * <p>date2String</p>
     *
     * @param time   Date类型时间
     * @param format 时间格式
     * @return 时间字符串
     * @Description: 将Date类型转为时间字符串
     */
    public static String date2String(Date time, SimpleDateFormat format) {
        return format.format(time);
    }

    /**
     * <p>dayBeginTime</p>
     *
     * @return
     * @Description 获取当天起始时间
     */
    public static String dayBeginTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Date start = calendar.getTime();
        return date2String(start, NURSE_SDF);
    }

    /**
     * <p>getCurentTime</p>
     *
     * @return
     */
    public static String getCurentTime() {
        return date2String(new Date(), NURSE_SDF);
    }

    /**
     * <p>setDefaultFormat</p>
     *
     * @param hour
     * @param minute
     * @return
     */
    public static String setDefaultFormat(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        Date start = calendar.getTime();
        return date2String(start, NURSE_SDF);
    }

    /**
     * <p>getDayTime</p>
     *
     * @param date
     * @return
     */
    public static String getDayTime(String date) {
        return (String) date.subSequence(date.length() - 8, date.length() - 3);
    }


    private static ThreadLocal<SimpleDateFormat> getSimpleDateFormat(final String pattern) {
        ThreadLocal<SimpleDateFormat> sdf = SDF_MAP.get(pattern);
        if (sdf == null) {
            synchronized (SDF_MAP) {
                sdf = SDF_MAP.get(pattern);
                if (sdf == null) {
                    sdf = new ThreadLocal<SimpleDateFormat>() {
                        @Override
                        protected SimpleDateFormat initialValue() {
                            final SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.US);
                            sdf.setTimeZone(GMT_TIMEZONE);
                            sdf.setLenient(false);
                            return sdf;
                        }
                    };
                    SDF_MAP.put(pattern, sdf);
                }
            }
        }
        return sdf;
    }

    public static String format(String pattern, Date date) {
        return getSimpleDateFormat(pattern).get().format(date);
    }

    private static final String DATE_PATTERN = "yyyyMMdd";
    private static final String TIME_PATTERN = "yyyyMMdd'T'HHmmss'Z'";
    private static final String DATE_SEPARATER_PATTERN = "yyyy-MM-dd";

    /**
     * <p>getTimeStamp</p>
     *
     * @return
     * @Description 获取当前时间戳
     */
    public static String getTimeStamp() {
        return format(TIME_PATTERN, new Date());
    }

    /**
     * <p>getDateStamp</p>
     *
     * @return
     * @Description 获取日期时间戳
     */
    public static String getDateStamp() {
        return format(DATE_PATTERN, new Date());
    }

    /**
     * <p>getWeekDate</p>
     *
     * @return
     * @Description 獲取星期
     */
    public static String getWeekDate() {
        String date = format(DATE_SEPARATER_PATTERN, new Date());
        String week = "星期一";
        try {
            week = dayForWeek(date2String(new Date()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date + " " + week;
    }

    /**
     * <p>dayForWeek</p>
     *
     * @param pTime
     * @return
     * @throws Exception
     */
    public static String dayForWeek(String pTime) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(format.parse(pTime));
        int dayForWeek;
        String week = "";
        dayForWeek = c.get(Calendar.DAY_OF_WEEK);
        switch (dayForWeek) {
            case 1:
                week = "星期日";
                break;
            case 2:
                week = "星期一";
                break;
            case 3:
                week = "星期二";
                break;
            case 4:
                week = "星期三";
                break;
            case 5:
                week = "星期四";
                break;
            case 6:
                week = "星期五";
                break;
            case 7:
                week = "星期六";
                break;
        }
        return week;
    }

    /**
     * 获取yyyy/MM/dd HH:mm:ss格式的时间
     *
     * @return
     */
    public static String getDatePattern1() {
        SimpleDateFormat tempDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date(); //当前日期

        return tempDate.format(date);
    }

    /**
     * 获取HH:mm格式的时间
     *
     * @return
     */
    public static String getTimePattern1() {
        String date = getDatePattern1();
        return date.substring(11, 16);
    }

    //出生日期字符串转化成Date对象
    public static Date parse(String strDate) throws ParseException {
        String replace = strDate.replace("/", "-");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(replace);
    }

    //由出生日期获得年龄
    public static int getAge(Date birthDay) throws Exception {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;
            } else {
                age--;
            }
        }
        return age;
    }

    /**
     *
     * @param recordTime
     * @return
     */
    public static String getTimeStr(long recordTime) {
        int minute = (int)recordTime/(1000 * 60);
        int second = (int)((int)recordTime%(1000 * 60)/1000);
        StringBuffer str = new StringBuffer();
        if(minute >= 10) {
            str.append(minute + ":");
        } else {
            str.append("0" + minute + ":");
        }
        if(second >= 10) {
            str.append(second);
        } else {
            str.append("0" + second);
        }
        return str.toString();
    }
}
