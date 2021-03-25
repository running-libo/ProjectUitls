package com.example.project_lib;

import android.util.Log;

/**
 * Created by libo on 2017/1/8.
 *
 * 日志管理工具类，开发中将LOGLEVEL值设为最大，
 * 实际发布后将LOGLEVEL值设为最小
 */
public class LogUtils {
    private static int LOGLEVEL = 6;
    private static int VERBOSE = 1;
    private static int DEBUG = 2;
    private static int INFO = 3;
    private static int WARN = 4;
    private static int ERROR = 5;

    public static void v(String tag, String msg){
        if(LOGLEVEL > VERBOSE) Log.v(tag,msg);
    }

    public static void d(String tag, String msg){
        if(LOGLEVEL > DEBUG) Log.d(tag, msg);
    }

    public static void i(String tag, String msg){
        if(LOGLEVEL > INFO) Log.i(tag, msg);
    }

    public static void w(String tag, String msg){
        if(LOGLEVEL > WARN) Log.w(tag, msg);
    }

    public static void e(String tag, String msg){
        if(LOGLEVEL > ERROR) Log.e(tag,msg);
    }

}
