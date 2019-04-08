package util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {
    /** 缺省的日期显示格式： yyyy-MM-dd. */
    public static final String DEFAULT_DATE_FORMAT = "yyyyMMdd";

    /** 缺省的日期时间显示格式：yyyy-MM-dd HH:mm:ss. */
    public static final String DEFAULT_DATETIME_FORMAT ="yyyy-MM-dd HH:mm:ss"; //ConfigManager.get(Constans.UNIFIED_JSON_TIME_FORMAT,"yyyyMMddHHmmss");

    public static final String DEFAULT_DAY_FORMAT ="yyyy-MM-dd"; //ConfigManager.get(Constans.UNIFIED_JSON_TIME_FORMAT,"yyyyMMddHHmmss");

    /** 精确到月份的日期时间显示格式：yyyy-MM-dd HH:mm:ss. */
    public static final String DATETIME_FORMAT_TO_MONTH ="yyyy-MM";

    /** 缺省的日期显示格式： yyyy-MM-dd. */
    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String DATE_FORMAT_YYYY_MM_DD_HH = "yyyy-MM-dd HH";
    public static final String DATE_FORMAT_HH = "HH";
    public static final String DATE_FORMAT_HH_MM = "HH:mm";
    public static final String TIME_FORMAT_YYYY_MM_DD_HH_MM = "yyyy.MM.dd HH:mm";

    /** 1s中的毫秒数. */
    private static final int MILLIS = 1000;

    /** 一年当中的月份数. */
    private static final int MONTH_PER_YEAR = 12;


    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

    /**
     * 得到系统当前日期时间.
     *
     * @return 当前日期时间
     */

    public static Date getNow() {
        return Calendar.getInstance().getTime();
    }

    /**
     * 得到用缺省方式格式化的当前日期.
     *
     * @return 当前日期
     */
    public static String getDate() {
        return getDateTime(DEFAULT_DATE_FORMAT);
    }

    /**
     * 得到用缺省方式格式化的当前日期及时间.
     *
     * @return 当前日期及时间
     */
    public static String getDateTime() {
        return getDateTime(DEFAULT_DATETIME_FORMAT);
    }

    /**
     * 得到系统当前日期及时间，并用指定的方式格式化.
     *
     * @param pattern 显示格式
     * @return 当前日期及时间
     */
    public static String getDateTime(String pattern) {
        Date datetime = Calendar.getInstance().getTime();
        return getDateTime(datetime, pattern);
    }


    /**
     * 得到用指定方式格式化的日期.
     *
     * @param date 需要进行格式化的日期
     * @param pattern 显示格式
     * @return 日期时间字符串
     */
    public static String getDateTime(Date date, String pattern) {
        if (null == pattern || "".equals(pattern)) {
            pattern = DEFAULT_DATETIME_FORMAT;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    /**
     * 得到当前年份.
     *
     * @return 当前年份
     */
    public static int getCurrentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);

    }

    /**
     * 得到当前月份.
     *
     * @return 当前月份
     */
    public static int getCurrentMonth() {
        //用get得到的月份数比实际的小1，需要加上
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    /**
     * 得到当前日.
     *
     * @return 当前日
     */
    public static int getCurrentDay() {
        return Calendar.getInstance().get(Calendar.DATE);
    }

    /**
     * 取得当前日期以后若干天的日期。如果要得到以前的日期，参数用负数。 例如要得到上星期同一天的日期，参数则为-7.
     *
     * @param days 增加的日期数
     * @return 增加以后的日期
     */
    public static Date addDays(int days) {
        return add(getNow(), days, Calendar.DATE);
    }

    /**
     * 取得指定日期以后若干天的日期。如果要得到以前的日期，参数用负数。.
     *
     * @param date 基准日期
     * @param hours the hours
     * @return 增加以后的日期
     */
    public static Date addHours(Date date, int hours) {
        return add(date, hours, Calendar.HOUR);
    }

    /**
     * 取得指定时间后若干分钟
     * @param date
     * @param minute
     * @return
     */
    public static Date addMinute(Date date, int minute) {
        return add(date, minute, Calendar.MINUTE);
    }
    /**
     * 取得指定日期以后若干天的日期。如果要得到以前的日期，参数用负数。.
     *
     * @param date 基准日期
     * @param days 增加的日期数
     * @return 增加以后的日期
     */
    public static Date addDays(Date date, int days) {
        return add(date, days, Calendar.DATE);
    }

    /**
     * 取得当前日期以后某月的日期。如果要得到以前月份的日期，参数用负数。.
     *
     * @param months 增加的月份数
     * @return 增加以后的日期
     */
    public static Date addMonths(int months) {
        return add(getNow(), months, Calendar.MONTH);
    }

    /**
     * 取得指定日期以后某月的日期。如果要得到以前月份的日期，参数用负数。
     *  注意，可能不是同一日子，例如2003-1-31加上一个月是2003-2-28.
     *
     * @param date 基准日期
     * @param months 增加的月份数
     * @return 增加以后的日期
     */
    public static Date addMonths(Date date, int months) {
        return add(date, months, Calendar.MONTH);
    }

    /**
     * 内部方法。为指定日期增加相应的天数或月数.
     *
     * @param date 基准日期
     * @param amount 增加的数量
     * @param field 增加的单位，年，月或者日
     * @return 增加以后的日期
     */
    private static Date add(Date date, int amount, int field) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);
        calendar.add(field, amount);

        return calendar.getTime();
    }

    /**
     * 通过date对象取得格式为小时:分钟的实符串.
     *
     * @param date the date
     * @return the hour min
     */
    @SuppressWarnings("deprecation")
    public static String getHourMin(Date date){
        StringBuffer sf = new StringBuffer();
        if (date.getHours() <10){
            sf.append("0");
        }
        sf.append(date.getHours());
        sf.append(":");
        if (date.getMinutes() <10){
            sf.append("0");
        }
        sf.append(date.getMinutes());
        return sf.toString();
    }

    /**
     * 计算两个日期相差天数。 用第一个日期减去第二个。如果前一个日期小于后一个日期，则返回负数.
     *
     * @param one 第一个日期数，作为基准
     * @param two 第二个日期数，作为比较
     * @return 两个日期相差天数
     */
    public static long diffDays(Date one, Date two) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(one);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONDAY)
                , calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        Date d1 = calendar.getTime();
        calendar.clear();
        calendar.setTime(two);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONDAY)
                , calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        Date d2 = calendar.getTime();
        final int MILISECONDS = 24 * 60 * 60 * 1000;
        BigDecimal r = new BigDecimal(new Double((d1.getTime() - d2.getTime()))
                / MILISECONDS);
        return Math.round(r.doubleValue());
    }

    /**
     * 相差小时
     * @param one Date
     * @param two Date
     * @return long
     */
    public static long diffHours(Date one, Date two) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(one);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONDAY)
                , calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY), 0, 0);
        Date d1 = calendar.getTime();
        calendar.clear();
        calendar.setTime(two);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONDAY)
                , calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY), 0, 0);
        Date d2 = calendar.getTime();
        final int MILISECONDS = 60 * 60 * 1000;
        BigDecimal r = new BigDecimal(new Double((d1.getTime() - d2.getTime()))
                / MILISECONDS);
        return Math.round(r.doubleValue());
    }

    /**
     * 计算两个日期相差月份数 如果前一个日期小于后一个日期，则返回负数.
     *
     * @param one 第一个日期数，作为基准
     * @param two 第二个日期数，作为比较
     * @return 两个日期相差月份数
     */
    public static int diffMonths(Date one, Date two) {

        Calendar calendar = Calendar.getInstance();

        //得到第一个日期的年分和月份数
        calendar.setTime(one);
        int yearOne = calendar.get(Calendar.YEAR);
        int monthOne = calendar.get(Calendar.MONDAY);
        //得到第二个日期的年份和月份
        calendar.setTime(two);
        int yearTwo = calendar.get(Calendar.YEAR);
        int monthTwo = calendar.get(Calendar.MONDAY);

        return (yearOne - yearTwo) * MONTH_PER_YEAR + (monthOne - monthTwo);
    }

    /**
     * 获取某一个日期的年份.
     *
     * @param d the d
     * @return the year
     */
    public static int getYear(Date d) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 将一个字符串用给定的格式转换为日期类型。 <br>
     * 注意：如果返回null，则表示解析失败.
     *
     * @param datestr 需要解析的日期字符串
     * @param pattern 日期字符串的格式，默认为"yyyy-MM-dd"的形式
     * @return 解析后的日期
     */
    public static Date parse(String datestr, String pattern) {
        Date date = null;

        if (null == pattern || "".equals(pattern)) {
            pattern = DEFAULT_DATE_FORMAT;
        }

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            date = dateFormat.parse(datestr);
        } catch (ParseException e) {
            logger.error("parse exception", e);
        }

        return date;
    }

    /**
     * 返回本月的最后一天.
     *
     * @return 本月最后一天的日期
     */
    public static Date getMonthLastDay() {
        return getMonthLastDay(getNow());
    }


    /**
     * 获取时间点
     * @param date
     * @return
     */
    public static int getHour(Date date){
        //可以对每个时间域单独修改
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        return hour;
    }

    /**
     * 返回给定日期中的月份中的最后一天.
     *
     * @param date 基准日期
     * @return 该月最后一天的日期
     */
    public static Date getMonthLastDay(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        //将日期设置为下一月第一天
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, 1);

        //减去1天，得到的即本月的最后一天
        calendar.add(Calendar.DATE, -1);

        return calendar.getTime();
    }


    /**
     * 获得该月第一天
     * @param year
     * @param month
     * @return
     */
    public static Date getFirstDayOfMonth(int year,int month){
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR,year);
        //设置月份
        cal.set(Calendar.MONTH, month-1);
        //获取某月最小天数
        int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最小天数
        cal.set(Calendar.DAY_OF_MONTH, firstDay);
        //格式化日期
        return cal.getTime();
    }


    /**
     * 获得该月最后一天
     * @param year
     * @param month
     * @return
     */
    public static Date getLastDayOfMonth(int year,int month){
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR,year);
        //设置月份
        cal.set(Calendar.MONTH, month-1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //格式化日期
        return cal.getTime();
    }


    /**
     * 计算两个具体日期之间的秒差，第一个日期-第二个日期.
     *
     * @param date1 the date1
     * @param date2 the date2
     * @param onlyTime 是否只计算2个日期的时间差异，忽略日期，true代表只计算时间差
     * @return the long
     */
    public static long diffSeconds(Date date1,Date date2,boolean onlyTime) {
        if (onlyTime) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date1);
            //calendar.set(1984, 5, 24);
            long t1 = calendar.getTimeInMillis();
            calendar.setTime(date2);
            //calendar.set(1984, 5, 24);
            long t2 = calendar.getTimeInMillis();
            return (t1-t2)/MILLIS;
        } else {
            return (date1.getTime()-date2.getTime())/MILLIS;
        }
    }

    /**
     * Diff seconds.
     *
     * @param date1 the date1
     * @param date2 the date2
     * @return the long
     */
    public static long diffSeconds(Date date1,Date date2) {
        return diffSeconds(date1,date2,false);
    }

    /**
     * 根据日期确定星期几:1-星期日，2-星期一.....s
     *
     * @param date the date
     * @return the day of week
     */
    public static int getDayOfWeek(Date date){
        Calendar   cd   =   Calendar.getInstance();
        cd.setTime(date);
        int   mydate = cd.get(Calendar.DAY_OF_WEEK);
        return mydate;
    }

    /**
     * 验证用密码是否在有效期内(跟当前日期比较).
     *
     * @param validDate the valid date
     * @param format "yyyyMMdd"
     * @return true, if is valid date
     */
    public static boolean isValidDate(String validDate, String format) {
        Date valid = parse(validDate,format);
        Date now = new Date();
        String nowStr = new SimpleDateFormat(format).format(now);
        try {
            now = new SimpleDateFormat(format).parse(nowStr);
        } catch (ParseException e) {
            logger.error("isValidDate exception", e);
        }
        return valid.after(now);
    }

    /**
     * 获取当前Unix时间(秒数).
     *
     * @return the long
     */
    public static Long unixTime(){
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 获取Unix时间(秒数).
     *
     * @param date the date
     * @return the long
     */
    public static Long toUnixTime(Date date){
        return date == null ? null : date.getTime() / 1000;
    }

    /**
     * To unix time.
     *
     * @param dateStr the date str
     * @param pattern the pattern
     * @return the long
     */
    public static Long toUnixTime(String dateStr , String pattern){
        if(StringUtils.isEmpty(dateStr) || StringUtils.isEmpty(pattern)){
            return null;
        }
        Date date = parse(dateStr, pattern);

        return date == null ? null : date.getTime() / 1000;

    }

    /**
     * Format unix time.
     *
     * @param longStr the long str
     * @param pattern the pattern
     * @return the string
     */
    public static String formatUnixTime(String longStr , String pattern){
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(new Date(NumberUtils.toLong(longStr)*1000));
    }

    /**
     * Format unix time.
     *
     * @param time the time
     * @param pattern the pattern
     * @return the string
     */
    public static String formatUnixTime(Long time , String pattern){
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(new Date(time*1000));
    }

    /**
     * 将UNIX时间转成Date类型
     * @param time
     * @return
     */
    public static Date unixTimeToDate(Integer time){
        String timeStr = formatUnixTime(time.longValue(),DEFAULT_DATE_FORMAT);
        return parse(timeStr,DEFAULT_DATE_FORMAT);
    }

    /**
     * 将UNIX时间转成Date类型
     * @param time
     * @return
     */
    public static Date unixTimeToDate(Long time){
        String timeStr = formatUnixTime(time,DEFAULT_DATE_FORMAT);
        return parse(timeStr,DEFAULT_DATE_FORMAT);
    }

    /**
     * 将UNIX时间转成Date类型
     * @param time
     * @return
     */
    public static Date unixTimeToDateTime(Integer time){
        String timeStr = formatUnixTime(time.longValue(),DEFAULT_DATETIME_FORMAT);
        return parse(timeStr,DEFAULT_DATETIME_FORMAT);
    }

    /**
     * Parses the unix time.
     *
     * @param dateString the date string
     * @param pattern the pattern
     * @param defaultIfError the default if error
     * @return the long
     */
    public static Long parseUnixTime(String dateString, String pattern, Long defaultIfError){
        if(StringUtils.isBlank(dateString)){
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        try {
            return dateFormat.parse(dateString).getTime()/1000;
        } catch (ParseException e) {
            return defaultIfError;
        }

    }

    /**
     * start时间是否在 end 之前
     * @param start 默认格式 "yyyy-MM-dd HH:mm:ss"
     * @param end 默认格式 "yyyy-MM-dd HH:mm:ss"
     * @return
     */
    public static boolean after(String start,String end){
        return after(start,end,DEFAULT_DATETIME_FORMAT);
    }

    /**
     * start时间是否在 end 之前
     * @param start
     * @param end
     * @param pattern 时间格式
     * @return
     */
    public static boolean after(String start,String end,String pattern){
        try{
            long startTime = toUnixTime(start,pattern);
            long endTime = toUnixTime(end,pattern);
            return endTime > startTime;
        }catch(Exception e){
            return false;
        }
    }

    public static Integer LongTimeToUnixTime(Long time){
        time = time / 1000;
        return time.intValue();
    }


    /**
     * 将Unix时间转成某一天
     * @param time
     * @return
     */
    public static Integer unixTimeToDay(Long time){
        if(time ==null){
            return 0;
        }
        String  dStr = formatUnixTime(time,"dd ");
        if(dStr == null || "".equals(dStr)){
            return 0;
        }
        dStr = dStr.trim();
        return Integer.valueOf(dStr);
    }

    /**
     * 获取下月第一天
     * @return
     */
    public static Date getFirstDayOfNextMonth() {
        SimpleDateFormat dft = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    /**
     * 获取当月第一天
     * @return
     */
    public static Date getFirstDayOfMonth() {
        SimpleDateFormat dft = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
        return calendar.getTime();
    }

    /**
     * 获取今天最后一秒的时间
     * @return
     */
    public static Date getLastDateToday(){
        Calendar c2 = new GregorianCalendar();
        c2.set(Calendar.HOUR_OF_DAY, 23);
        c2.set(Calendar.MINUTE, 59);
        c2.set(Calendar.SECOND, 59);
        return c2.getTime();
    }

    public static Date getFirstDateToday(Date date){
        Calendar c2 = new GregorianCalendar();
        c2.setTime(date);
        c2.set(Calendar.HOUR_OF_DAY, 0);
        c2.set(Calendar.MINUTE, 0);
        c2.set(Calendar.SECOND, 0);
        return c2.getTime();
    }

    public static boolean isToday(Date d){
        DateTime now=new DateTime(System.currentTimeMillis());
        DateTime p=new DateTime(d.getTime());
        //年月日相等就是当天了
        if(now.getYear()==p.getYear()
                && now.getMonthOfYear() == p.getMonthOfYear()
                && now.getDayOfMonth() == p.getDayOfMonth()){
            return true;
        }
        return false;
    }

    /**
     * 当前时间在两个时间之间,若结束时间小于开始时间，认为结束时间是第二天的时间
     * @param startTIme 开始时间 00:00
     * @param endTime   结束时间 00:00
     * @return
     */
    public static boolean betweenStartAndEndTime(String startTIme, String endTime) {
        if (!StringUtils.isAnyEmpty(startTIme, endTime) && startTIme.equals(endTime)){
            return true;
        }
        if (StringUtils.isEmpty(startTIme)) startTIme = "00:00";
        if (StringUtils.isEmpty(endTime)) endTime = "23:59";

        String[] start = startTIme.split(":");
        String[] end = endTime.split(":");
        int startMinute = Integer.valueOf(start[0]) * 60 + Integer.valueOf(start[1]);
        int endtMinute = Integer.valueOf(end[0]) * 60 + Integer.valueOf(end[1]);

        DateTime now=new DateTime(System.currentTimeMillis());
        int hours = now.getHourOfDay();
        int minute = now.getMinuteOfHour();
        int currentMinute = hours * 60 + minute;

        if (endtMinute >= startMinute) {
           if (currentMinute >= startMinute && currentMinute <= endtMinute) {
               return true;
           }
        } else {
            if ((currentMinute >= startMinute && currentMinute < 24*60) || 0 <= currentMinute && currentMinute <= endtMinute) {
                return true;
            }
        }
        return false;
    }

    public static Long getDayBegin() {
         Calendar cal = new GregorianCalendar();
         cal.set(Calendar.HOUR_OF_DAY, 0);
         cal.set(Calendar.MINUTE, 0);
         cal.set(Calendar.SECOND, 0);
         cal.set(Calendar.MILLISECOND, 0);
         return cal.getTime().getTime()/1000;
    }

    /**
     * 获取指定的年月yyyyMM
     * @param year
     * @param month
     * @return
     */
    public static String getYearMonth(Integer year, Integer month){
        year = (year == null || year == 0) ? getCurrentYear() : year;
        month = (month == null ||month == 0) ? getCurrentMonth() : month;

        StringBuffer startDate = new StringBuffer();
        if(month < 10 ){
            startDate.append(year);
            startDate.append(0);
            startDate.append(month);
        } else if (month <= 12){
            startDate.append(year);
            startDate.append(month);
        }else if (month > 12){
            return getYearMonth(year+(month/12),month % 12);
        }
        return startDate.toString();

    }

    /**
     * 昨天
     * @return
     */
    public static Date getYesterday(){
        Calendar   cal   =   Calendar.getInstance();
        cal.add(Calendar.DATE,   -1);
        return cal.getTime();
    }

    /**
     * 取某天的最后一刻
     * @param date
     * @return
     */
    public static Date getLastDateToday(Date date){
        Calendar c2 = new GregorianCalendar();
        c2.setTime(date);
        c2.set(Calendar.HOUR_OF_DAY, 23);
        c2.set(Calendar.MINUTE, 59);
        c2.set(Calendar.SECOND, 59);
        return c2.getTime();
    }

    /**
     * 获取本周的开始时间
     * @return
     */
    public static Date getBeginTimeOfWeek() {
        Date date = new Date();
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek);
        return getFirstDateToday(cal.getTime());
    }

    /**
     * 获取本周的结束时间
     * @return
     */
    public static Date getEndTimeOfWeek(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(getBeginTimeOfWeek());
        cal.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEndSta = cal.getTime();
        return getLastDateToday(weekEndSta);
    }

    /**
     * 获取上周的开始时间
     * @return
     */
    public static Date getLastWeekBegin() {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        int dayOfWeek = calendar1.get(Calendar.DAY_OF_WEEK) - 1;
        int offset1 = 1 - dayOfWeek;
        int offset2 = 7 - dayOfWeek;
        calendar1.add(Calendar.DATE, offset1 - 7);
        calendar2.add(Calendar.DATE, offset2 - 7);
        return getFirstDateToday(calendar1.getTime());
    }

    /**
     * 获取上周的结束时间
     * @return
     */
    public static Date getLastWeekEnd() {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        int dayOfWeek = calendar1.get(Calendar.DAY_OF_WEEK) - 1;
        int offset2 = 7 - dayOfWeek;
        calendar2.add(Calendar.DATE, offset2 - 7);
        return getLastDateToday(calendar2.getTime());
    }

    /**
     * 距离明天多少秒
     * @return
     */
    public static int diffSecondsTomorrow() {
        Long lastToday = DateUtil.toUnixTime(DateUtil.getLastDateToday());//今天最后一秒时间戳23：59:59
        int diff = Integer.valueOf(String.valueOf(lastToday - DateUtil.unixTime())) + 1;
        return diff;
    }





    public static void main(String[] args) {
        System.out.println(getDayBegin());
        System.out.println(DateUtil.getDateTime("yyyyMMddHHmmss"));
        System.out.println(DateUtil.getDateTime(addMinute(getNow(),480),"yyyyMMddHHmmss"));
    }
}
