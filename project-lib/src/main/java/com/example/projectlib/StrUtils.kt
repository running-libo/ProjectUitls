package com.example.projectlib

import android.text.format.DateFormat
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by libo on 2016/11/24.
 *
 * 字符串、数字操作工具类
 */
object StrUtils {
    private const val patternCoder = "(?<!\\d)\\d{6}(?!\\d)"
    private const val phonePatter = "^1\\d{10}$"

    /** 获得当前时间  */
    fun currentTime(inFormat: CharSequence?): CharSequence {
        return DateFormat.format(inFormat, System.currentTimeMillis())
    }

    /**
     * 时间戳转 yyyy年MM月dd日 HH:mm
     * @param longTime
     * @return
     */
    fun getDateTime(longTime: String?): String {
        val time = java.lang.Long.valueOf(longTime) * 1000
        val sdf = SimpleDateFormat("yyyy年MM月dd日 HH:mm")
        return sdf.format(Date(time))
    }

    /**
     * 时间戳转 yyy年MM月dd日 HH:mm:ss
     * @param longTime
     * @return
     */
    fun getDateSec(longTime: String?): String {
        val time = java.lang.Long.valueOf(longTime) * 1000
        val sdf = SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss")
        return sdf.format(Date(time))
    }

    /**
     * 时间戳转 MM月dd日 HH:mm
     * @param longTime
     * @return
     */
    fun getDateMinite(longTime: String?): String {
        val time = java.lang.Long.valueOf(longTime) * 1000
        val sdf = SimpleDateFormat("MM月dd日 HH:mm")
        return sdf.format(Date(time))
    }

    /**
     * 时间戳转 yyyy-MM-dd HH:mm
     * @param longTime
     * @return
     */
    fun getTime(longTime: String?): String {
        val time = java.lang.Long.valueOf(longTime) * 1000
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm")
        return sdf.format(Date(time))
    }

    /**
     * 时间戳转 yyyy-MM-dd
     * @param longTime
     * @return
     */
    fun getdate(longTime: String?): String {
        val time = java.lang.Long.valueOf(longTime) * 1000
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        return sdf.format(Date(time))
    }

    /**
     * 日期转时间戳
     * @return
     */
    fun getTimeStamp(dateTime: String?, format: String?): String {
        try {
            val simpleDateFormat = SimpleDateFormat(format)
            return (simpleDateFormat.parse(dateTime).time / 1000).toString()
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return ""
    }

    /**
     * 保留指定小数点位数,format传 "0.0" "0.00"形式分别保存一位，两位小数
     * @param num
     * @param format
     * @return
     */
    fun doubleRound(num: Double, format: String?): String {
        val df = DecimalFormat(format)
        return df.format(num)
    }

    /**
     * 判断单个字符串是否为空
     * @param str
     * @return
     */
    fun isStr(str: String?): Boolean {
        return if (null != str && str.length != 0) true else false
    }

    /**
     * 判断多个字符串是否为空
     * @param str
     * @return
     */
    fun isArrStr(vararg str: String?): Boolean {
        if (null == str) return false
        for (s in str) {
            if (!isStr(s)) return false
        }
        return true
    }
}