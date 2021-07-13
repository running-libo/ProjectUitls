package com.example.projectlib

import com.example.projectlib.DateUtils.DateFormatEnum
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*

/**
 * create by libo
 * create on 2018/11/20
 * description Date转化工具类
 */
object DateUtils {
    /**
     * Date转字符串日期
     *
     * @param date 日期，单位是毫秒
     */
    fun date2Str(date: Long, format: DateFormatEnum): String {
        val sdr = SimpleDateFormat(format.format)
        return sdr.format(date)
    }

    /**
     * 时间戳转字符串日期
     *
     * @param timeStamp 时间戳，单位是毫秒
     */
    fun timeStamp2Str(timeStamp: Long, format: DateFormatEnum): String {
        val sdr = SimpleDateFormat(format.format)
        return sdr.format(Date(timeStamp))
    }

    /**
     * 获取当前的 yyyy-MM-dd HH:mm 时间
     */
    val curTime: String
        get() = timeStamp2Str(System.currentTimeMillis(), DateFormatEnum.YMDHM)

    /**
     * 获取当前的 yyyy-MM-dd HH:mm:ss 时间
     */
    val curTimeSS: String
        get() = timeStamp2Str(System.currentTimeMillis(), DateFormatEnum.YMDHMS2)

    /**
     * 毫秒转为 00:00 ，单位为毫秒
     * @param duration
     */
    fun duration2Time(duration: Long): String {
        val m = duration / 1000 / 60
        val s = duration / 1000 % 60
        val builder = StringBuilder()
        builder.append(m / 10).append(m % 10).append(":").append(s / 10).append(s % 10)
        return builder.toString()
    }

    /**
     * 发布时间显示
     * 规则：当年 去掉年 显示格式为  x月x日  时:分
     * 非当年 年取后两位
     * 24小时内格式为  刚刚 和多少分钟以前  多少小时以前
     * >24  && <48 显示为前一天
     * >48 显示具体的时间规则
     */
    fun getRealShowTime(showTime: String?): String {
        if (showTime == null) return ""
        val curTime = curTimeSS //当前时间
        return if (showTime.substring(0, 4) != curTime.substring(0, 4)) {  //不同年
            showTime.substring(0, 16)
        } else {  //同年
            if (showTime.substring(5, 7) != curTime.substring(5, 7)) {  //非同月
                showTime.substring(5, 16)
            } else {  //同月
                if (showTime.substring(8, 10) != curTime.substring(8, 10)) {  //非同一天
                    showTime.substring(5, 16)
                } else {  //同一天
                    val curHour = curTime.substring(11, 13).toInt()
                    val publishHour = showTime.substring(11, 13).toInt()
                    if (curHour - publishHour > 1) {
                        (curHour - publishHour).toString() + "小时前"
                    } else {  //1小时内
                        "刚刚"
                    }
                }
            }
        }
    }

    enum class DateFormatEnum(val format: String) {
        YMDHMS("yyyy年MM月dd日HH时mm分ss秒"), YMDHM("yyyy-MM-dd HH:mm"), YMDHMS2("yyyy-MM-dd HH:mm:ss"), YMD("yyyy-MM-dd"), MD("MM-dd"), HM("HH:mm"), MS("mm:ss");

    }
}