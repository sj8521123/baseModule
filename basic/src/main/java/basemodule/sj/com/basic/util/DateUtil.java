package basemodule.sj.com.basic.util;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 日期操作类
 * 日期转换成指定格式字符串  字符串转换日期
 * Created by sj on 2015/11/25.
 */
public class DateUtil {
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT2 = "yyyy-MM-dd HH:mm";
    public static final String DATE_FORMAT3 = "yyyy-MM-dd";
    public static final String DATE_FORMAT4 = "HH:mm:ss";
    private static final String[][] dateRegFormat = {
            //匹配正则，解析格式，重新格式化，拼接字符串
            {"(^\\d{4}\\D+\\d{2}\\D+\\d{2})\\D*$", "yyyy-MM-dd", "yyyy-MM-dd", null},//2015-05-06
            {"(^\\d{4}\\D+\\d{1}\\D+\\d{1})\\D*$", "yyyy-M-d", "yyyy-MM-dd", null},//2015-05-06
            {"(^\\d{4}\\D+\\d{2}\\D+\\d{1})\\D*$", "yyyy-MM-d", "yyyy-MM-dd", null},//2015-05-6
            {"(^\\d{4}\\D+\\d{1}\\D+\\d{2})\\D*$", "yyyy-M-dd", "yyyy-MM-dd", null},//2015-5-06

            {"(^\\d{4}\\D+\\d{2})\\D*$", "yyyy-MM-dd", "yyyy-MM", "%s-01"},//2016-06
            {"(^\\d{4}\\D+\\d{1})\\D*$", "yyyy-M-d", "yyyy-MM", "%s-1"},//2015-1
            {"(^\\d{4})\\D*$", "yyyy", "yyyy", null}/*,//2015
            {"^\\d{8}$", "yyyyMMdd", "yyyy-MM-dd", null},//20150201
            {"^\\d{6}$", "yyyyMMdd", "yyyy-MM", "%s01"},//201403
            {"^\\d{2}\\D+\\d{1,2}\\D+\\d{1,2}$", "yy-MM-dd", "yyyy-MM-dd", null},//14.10.18(年.月.日)
            {"^\\d{1,2}\\D+\\d{1,2}$", "yyyy-dd-MM", "yyyy-MM-dd", "2012-%s"},//30.12(日.月) 拼接当前年份
            {"^\\d{1,2}\\D+\\d{1,2}\\D+\\d{4}$", "dd-MM-yyyy", "yyyy-MM-dd", null},//12.21.2013(日.月.年)*/
    };

    /**
     * 字符串转换日期,date部分获取日期方法已被废弃，故采用Calendar类
     *
     * @param dateStr
     * @param formatStr
     * @return Calendar
     */
    public static Calendar parseCalendar(String dateStr, String formatStr) {
        Calendar result = Calendar.getInstance();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(formatStr, Locale.CHINA);
            result.setTime(sdf.parse(dateStr));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 字符串转换日期,date部分获取日期方法已被废弃，故采用Calendar类
     *
     * @param dateStr
     * @param format
     * @return Calendar
     */
    public static Calendar parseCalendar(String dateStr, SimpleDateFormat format) {
        Calendar result = Calendar.getInstance();
        try {
            result.setTime(format.parse(dateStr));
        } catch (Exception e) {
            e.printStackTrace();
            //todo remove me
            return Calendar.getInstance();
        }
        return result;
    }

    /**
     * 日期转换成指定格式字符串
     *
     * @param calendar
     * @param formatStr
     * @return
     */
    public static String formatCalendar(Calendar calendar, String formatStr) {
        String result = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
            result = sdf.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @SuppressWarnings("finally")
    public static String FormatDate(String dateStr) {

        HashMap<String, String> dateRegFormat = new HashMap<String, String>();
        dateRegFormat.put(
                "^\\d{4}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D*$",
                "yyyy-MM-dd-HH-mm-ss");//2014年3月12日 13时5分34秒，2014-03-12 12:05:34，2014/3/12 12:5:34
        dateRegFormat.put("^\\d{4}\\D+\\d{2}\\D+\\d{2}\\D+\\d{2}\\D+\\d{2}$",
                "yyyy-MM-dd-HH-mm");//2014-03-12 12:05
        dateRegFormat.put("^\\d{4}\\D+\\d{2}\\D+\\d{2}\\D+\\d{2}$",
                "yyyy-MM-dd-HH");//2014-03-12 12
        dateRegFormat.put("^\\d{4}\\D+\\d{2}\\D+\\d{2}$", "yyyy-MM-dd");//2014-03-12
        dateRegFormat.put("^\\d{4}\\D+\\d{2}$", "yyyy-MM");//2014-03
        dateRegFormat.put("^\\d{4}$", "yyyy");//2014
        dateRegFormat.put("^\\d{14}$", "yyyyMMddHHmmss");//20140312120534
        dateRegFormat.put("^\\d{12}$", "yyyyMMddHHmm");//201403121205
        dateRegFormat.put("^\\d{10}$", "yyyyMMddHH");//2014031212
        dateRegFormat.put("^\\d{8}$", "yyyyMMdd");//20140312
        dateRegFormat.put("^\\d{6}$", "yyyyMM");//201403
        dateRegFormat.put("^\\d{2}\\s*:\\s*\\d{2}\\s*:\\s*\\d{2}$",
                "yyyy-MM-dd-HH-mm-ss");//13:05:34 拼接当前日期
        dateRegFormat.put("^\\d{2}\\s*:\\s*\\d{2}$", "yyyy-MM-dd-HH-mm");//13:05 拼接当前日期
        dateRegFormat.put("^\\d{2}\\D+\\d{1,2}\\D+\\d{1,2}$", "yy-MM-dd");//14.10.18(年.月.日)
        dateRegFormat.put("^\\d{1,2}\\D+\\d{1,2}$", "yyyy-dd-MM");//30.12(日.月) 拼接当前年份
        dateRegFormat.put("^\\d{1,2}\\D+\\d{1,2}\\D+\\d{4}$", "dd-MM-yyyy");//12.21.2013(日.月.年)

        String curDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        DateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat formatter2;
        String dateReplace;
        String strSuccess = "";
        try {
            for (String key : dateRegFormat.keySet()) {
                if (Pattern.compile(key).matcher(dateStr).matches()) {
                    formatter2 = new SimpleDateFormat(dateRegFormat.get(key));
                    if (key.equals("^\\d{2}\\s*:\\s*\\d{2}\\s*:\\s*\\d{2}$")
                            || key.equals("^\\d{2}\\s*:\\s*\\d{2}$")) {//13:05:34 或 13:05 拼接当前日期
                        dateStr = curDate + "-" + dateStr;
                    } else if (key.equals("^\\d{1,2}\\D+\\d{1,2}$")) {//21.1 (日.月) 拼接当前年份
                        dateStr = curDate.substring(0, 4) + "-" + dateStr;
                    }
                    dateReplace = dateStr.replaceAll("\\D+", "-");
                    // System.out.println(dateRegExpArr[i]);
                    strSuccess = formatter1.format(formatter2.parse(dateReplace));
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("-----------------日期格式无效:" + dateStr);
            throw new Exception("日期格式无效");
        } finally {
            return strSuccess;
        }
    }

    @SuppressWarnings("finally")
    public static String FormatDateStr(String dateStr) {
        Log.d("-->", "FormatDateStr-berofre: " + dateStr);
        for (int i = 0; i < dateRegFormat.length; i++) {
            String[] item = dateRegFormat[i];
            Matcher matcher = Pattern.compile(item[0]).matcher(dateStr);
            if (matcher.find()) {
                dateStr = matcher.group(matcher.groupCount());
                dateStr = dateStr.replaceAll("\\D", "-");
                Log.d("-->", "FormatDateStr-regex: " + item[0]);
                DateFormat format = new SimpleDateFormat(item[1], Locale.SIMPLIFIED_CHINESE);
                DateFormat format1 = new SimpleDateFormat(item[2], Locale.SIMPLIFIED_CHINESE);
                if (item[3] != null) {
                    dateStr = String.format(item[3], dateStr);
                }
                try {
                    String restult = format1.format(format.parse(dateStr));
                    Log.d("-->", "FormatDateStr-afeter: " + restult);
                    return restult;
                } catch (ParseException e) {
                    Log.d("-->", "FormatDateStr-error: ", e);
                    return null;
                }
            }
        }
        return null;
    }

    public static long getTimestamp(String time, String type) {
        SimpleDateFormat sdr = new SimpleDateFormat(type, Locale.CHINA);
        Date date;
        long l = 0;
        try {
            date = sdr.parse(time);
            l = date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return l;
    }

    public static long getTimestamp(Calendar calendar, String type) {
        String time = formatCalendar(calendar, type);
        SimpleDateFormat sdr = new SimpleDateFormat(type, Locale.CHINA);
        Date date;
        String times = null;
        long l = 0;
        try {
            date = sdr.parse(time);
            l = date.getTime();
            //            String stf = String.valueOf(l);
            //            times = stf.substring(0, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return l;
    }

    /**
     * 有时分秒的时间字符串，去掉时分秒
     *
     * @param date
     * @return
     */
    public static String getSimpleDate(String date) {
        String time = null;
        if (date.contains(" ")) {
            time = date.substring(0, date.indexOf(" "));
        }
        return time;
    }
}