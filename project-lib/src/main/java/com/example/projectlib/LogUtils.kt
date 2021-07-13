package com.example.projectlib

import android.util.Log

/**
 * Created by libo on 2017/1/8.
 *
 * 日志管理工具类，开发中将LOGLEVEL值设为最大，
 * 实际发布后将LOGLEVEL值设为最小
 */
object LogUtils {
    private const val LOGLEVEL = 6
    private const val VERBOSE = 1
    private const val DEBUG = 2
    private const val INFO = 3
    private const val WARN = 4
    private const val ERROR = 5
    fun v(tag: String?, msg: String?) {
        if (LOGLEVEL > VERBOSE) Log.v(tag, msg!!)
    }

    fun d(tag: String?, msg: String?) {
        if (LOGLEVEL > DEBUG) Log.d(tag, msg!!)
    }

    fun i(tag: String?, msg: String?) {
        if (LOGLEVEL > INFO) Log.i(tag, msg!!)
    }

    fun w(tag: String?, msg: String?) {
        if (LOGLEVEL > WARN) Log.w(tag, msg!!)
    }

    fun e(tag: String?, msg: String?) {
        if (LOGLEVEL > ERROR) Log.e(tag, msg!!)
    }
}