package top.bootzhong.common.util;

import lombok.SneakyThrows;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 * @author bootzhong
 */
public class TimeUtil {
    private static final String DAY_NUM_FORMAT= "yyyyMMdd";
    private static final String MONTH_NUM_FORMAT= "yyyyMM";
    private static final String DAY_STR_FORMAT= "yyyy-MM-dd";
    private static final String MONTH_STR_FORMAT= "yyyy-MM";

    /**
     * 转换成天编号
     * @param date
     * @return
     */
    public static Integer toDay(Date date){
        return toNumber(date, DAY_NUM_FORMAT);
    }

    /**
     * 转换成天编号
     * @param date
     * @return
     */
    public static Integer toDay(String date){
        return toNumber(parseDay(date), DAY_NUM_FORMAT);
    }

    /**
     * 转行成月编号
     * @param date
     * @return
     */
    public static Integer toMon(Date date){
        return toNumber(date, MONTH_NUM_FORMAT);
    }

    /**
     * 转行成整数类型如 20210809
     * @param date
     * @return
     */
    private static Integer toNumber(Date date, String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String result = sdf.format(date);
        return Integer.valueOf(result);
    }

    /**
     * 转行成月字符串
     * @param date
     * @return
     */
    public static String toMonString(Date date){
        return toString(date, MONTH_STR_FORMAT);
    }

    /**
     * 转行成月字符串
     * @param month
     * @return
     */
    public static String toMonString(Integer month){
        return toString(parseMon(month), MONTH_STR_FORMAT);
    }

    /**
     * 转成日字符串
     * @param date
     * @return
     */
    public static String toDayString(Date date){
        return toString(date, DAY_STR_FORMAT);
    }

    /**
     * 转成日字符串
     * @param day
     * @return
     */
    public static String toDayString(Integer day){
        return toDayString(parseDay(day));
    }

    /**
     * 转行成字符串 2021-08-09
     * @param date
     * @return
     */
    public static String toString(Date date, String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 把number解析成 date
     * @param mon
     * @return
     */
    public static Date parseMon(Integer mon) {
        return parse(mon, MONTH_NUM_FORMAT);
    }

    /**
     * 转换成月date
     * @param month
     * @return
     */
    public static Date parseMon(String month) {
        return parse(month, MONTH_STR_FORMAT);
    }

    /**
     * 把number解析成 date
     * @param day
     * @return
     */
    public static Date parseDay(Integer day) {
        return parse(day, DAY_NUM_FORMAT);
    }

    /**
     * 把number解析成 date
     * @param day
     * @return
     */
    public static Date parseDay(String day) {
        return parse(day, DAY_STR_FORMAT);
    }

    /**
     * 把number类型解析回 date类型
     * @param number
     * @param format
     * @return
     */
    @SneakyThrows
    private static Date parse(Integer number, String format) {
        return parse(String.valueOf(number), format);
    }

    /**
     * 把number类型解析回 date类型
     * 这里使用规则+3个小时
     * @param source
     * @param format
     * @return
     */
    @SneakyThrows
    private static Date parse(String source, String format) {
        if (format.length() != source.length()){
            throw new IllegalArgumentException(source + "时间格式不符合：" + format);
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.parse(source);
    }

    /**
     * 获取当前月份的下个月第一天
     * @return
     */
    public static Date nextMonthFirstDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, 1); //自然月加一天
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取指定时间的上个月
     * @param date
     * @return
     */
    public static Date lastMon(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1); //自然月减一天
        return calendar.getTime();
    }

    public static int getDaysOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 检查日期格式是否正常
     * @param month
     * @return
     */
    public static boolean checkMonth(String month){
        try{
            parseMon(month);
        } catch (Exception e){
          return false;
        }
        return true;
    }

    /**
     * 获取指定月份的最后一天
     * @param month
     * @return
     */
    public static String lastDayStringOfMonth(String month){
        return lastDayStringOfMonth(parseMon(month));
    }

    /**
     * 获取指定月份的最后一天
     * @param date
     * @return
     */
    public static String lastDayStringOfMonth(Date date){
        return toDayString(lastDayOfMonth(date));
    }

    /**
     * 获取指定月份的最后一天
     * @param date
     * @return
     */
    public static Integer lastDayOfMonth(Date date){
        return toDay(lastDateOfMonth(date));
    }


        /**
         * 获取指定月份的最后一天
         * @param month
         * @return
         */
    public static Date lastDateOfMonth(String month){
        return lastDateOfMonth(parseMon(month));
    }

    /**
     * 获取指定月份的最后一天
     * @param month
     * @return
     */
    public static Date lastDateOfMonth(Date month){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(month);

        int actualMaximum = calendar.getActualMaximum(Calendar.DATE);
        calendar.set(Calendar.DAY_OF_MONTH, actualMaximum);
        return calendar.getTime();
    }

    /**
     * 获取本月第一天
     * @param
     * @return
     */
    public static String firstDayStringOfMonth(Date date){
        return toDayString(firstDayOfMonth(date));
    }

    /**
     * 获取本月第一天
     * @param
     * @return
     */
    public static String firstDayStringOfMonth(String month){
        return firstDayStringOfMonth(parseMon(month));
    }

    /**
     * 获取本月第一天
     * @param
     * @return
     */
    public static Integer firstDayIntegerOfMonth(Date date){
        return toDay(firstDayOfMonth(date));
    }

    /**
     * 获取本月第一天
     * @param
     * @return
     */
    public static Date firstDayOfMonth(String month){
        return firstDayOfMonth(parseMon(month));
    }

    /**
     * 获取本月第一天
     * @param
     * @return
     */
    public static Date firstDayOfMonth(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int actualMaximum = calendar.getActualMinimum(Calendar.DATE);
        calendar.set(Calendar.DAY_OF_MONTH, actualMaximum);
        return calendar.getTime();
    }

    /**
     * 获取本年的第一天
     * @param
     * @return
     */
    public static Integer firstDayOfYear(Date date){
        return toDay(firstDateOfYear(date));
    }

    /**
     * 获取本年的第一天
     * @param
     * @return
     */
    public static String firstDayStringOfYear(Date date){
        return toDayString(firstDateOfYear(date));
    }

    /**
     * 获取本年的第一天
     * @param
     * @return
     */
    public static Date firstDateOfYear(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int actualMaximum = calendar.getActualMinimum(Calendar.DATE);
        calendar.set(Calendar.DAY_OF_YEAR, actualMaximum);
        return calendar.getTime();
    }

    /**
     * 判断日期格式
     * @param startDate
     * @param format1
     * @param format2
     * @return
     */
    public static boolean matchAny(String startDate, String format1, String format2) {
        SimpleDateFormat f1 = new SimpleDateFormat(format1);
        SimpleDateFormat f2 = new SimpleDateFormat(format2);

        try {
            f1.parse(startDate);
            return true;
        } catch (ParseException e) {
            try {
                f2.parse(startDate);
                return true;
            } catch (ParseException ignored) { }
        }

        return false;
    }
}
