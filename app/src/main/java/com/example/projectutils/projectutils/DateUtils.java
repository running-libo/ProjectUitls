package com.example.projectutils.projectutils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * create by libo
 * create on 2018/11/20
 * description Date转化工具类
 */
public class DateUtils {

    /**
     * Date转字符串日期
     *
     * @param date 日期，单位是毫秒
     */
    public static String date2Str(long date, DateFormatEnum format) {
        SimpleDateFormat sdr = new SimpleDateFormat(format.getFormat());
        String times = sdr.format(date);
        return times;
    }

    /**
     * 时间戳转字符串日期
     *
     * @param timeStamp 时间戳，单位是毫秒
     */
    public static String timeStamp2Str(long timeStamp, DateFormatEnum format) {
        SimpleDateFormat sdr = new SimpleDateFormat(format.getFormat());
        return sdr.format(new Date(timeStamp));
    }

    /**
     * 获取当前的 yyyy-MM-dd HH:mm 时间
     */
    public static String getCurTime() {
        return timeStamp2Str(System.currentTimeMillis(), DateFormatEnum.YMDHM);
    }

    /**
     * 获取当前的 yyyy-MM-dd HH:mm:ss 时间
     */
    public static String getCurTimeSS() {
        return timeStamp2Str(System.currentTimeMillis(), DateFormatEnum.YMDHMS2);
    }

    /**
     * 毫秒转为 00:00 ，单位为毫秒
     * @param duration
     */
    public static String duration2Time(long duration) {
        long m = duration / 1000 / 60;
        long s = duration / 1000 % 60;
        StringBuilder builder = new StringBuilder();
        builder.append(m / 10).append(m % 10).append(":").append(s / 10).append(s % 10);
        return builder.toString();
    }

    /**
     * 发布时间显示
     * 规则：当年 去掉年 显示格式为  x月x日  时:分
     *         非当年 年取后两位
     *          24小时内格式为  刚刚 和多少分钟以前  多少小时以前
     *          >24  && <48 显示为前一天
     *          >48 显示具体的时间规则
     */
    public static String getRealShowTime(String showTime) {
        if (showTime == null) return "";

        String curTime = DateUtils.getCurTimeSS();  //当前时间

        if (!showTime.substring(0, 4).equals(curTime.substring(0, 4))) {  //不同年
            return showTime.substring(0, 16);
        } else {  //同年
            if (!showTime.substring(5, 7).equals(curTime.substring(5, 7))) {  //非同月
                return showTime.substring(5, 16);
            } else {  //同月
                if (!showTime.substring(8, 10).equals(curTime.substring(8, 10))) {  //非同一天
                    return showTime.substring(5, 16);
                } else {  //同一天
                    int curHour = Integer.parseInt(curTime.substring(11, 13));
                    int publishHour = Integer.parseInt(showTime.substring(11, 13));
                    if (curHour - publishHour > 1) {
                        return (curHour - publishHour) + "小时前";
                    } else {  //1小时内
                        return "刚刚";
                    }
                }
            }
        }
    }

    public enum DateFormatEnum {

        YMDHMS("yyyy年MM月dd日HH时mm分ss秒"),
        YMDHM("yyyy-MM-dd HH:mm"),
        YMDHMS2("yyyy-MM-dd HH:mm:ss"),
        YMD("yyyy-MM-dd"),
        MD("MM-dd"),
        HM("HH:mm"),
        MS("mm:ss");

        private String format;

        DateFormatEnum(String format){
            this.format = format;
        }

        public String getFormat(){
            return format;
        }
    }


}
