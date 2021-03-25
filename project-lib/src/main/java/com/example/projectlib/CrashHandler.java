package com.example.projectlib;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by libo on 2016/11/24.
 *
 * 异常崩溃处理类:当程序发生未处理异常时，该类进行记录日志并发送至服务器
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler{

    private Thread.UncaughtExceptionHandler exceptionHandler;
    private MyApp myApp;
    /** 错误日志保存名称 */
    private Map<String, String> infos = new HashMap<>();
    /** 错误日志文件名 */
    private final String LOGFILE_NAME = FileUtils.getCacheDir() + "crash.txt";

    public CrashHandler(MyApp myApp){
        exceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        this.myApp = myApp;
    }

    /**
     * 当未捕获的异常发生时会传入此方中处理
     * @param thread
     * @param ex
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if(!handException(ex) && exceptionHandler != null){
            exceptionHandler.uncaughtException(thread,ex);
        }else{
            //异常处理并结束程序
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    /**
     * 异常处理方法，处理了返回true，未处理返回false
     * @param ex  异常
     * @return
     */
    private boolean handException(final Throwable ex){
        if(ex == null) return true;
        ex.printStackTrace();
        Toast.makeText(myApp, "应用发生异常，即将退出！", Toast.LENGTH_LONG).show();
        collectVersionInfo(myApp);
        collectDeviceInfo();
        saveCrashInfoToFile(ex);
        return true;
    }

    /**
     * 收集版本信息信息
     * @param context
     */
    private void collectVersionInfo(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存错误日志到文件中
     * @param ex
     */
    private void saveCrashInfoToFile(Throwable ex) {
        //换行符
        String lineFeed = "\r\n";
        StringBuffer sb = new StringBuffer();
        for(Map.Entry<String,String> entry: infos.entrySet()){
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + lineFeed);
        }

        StackTraceElement[] stack = ex.getStackTrace();
        String message = ex.getMessage();
        //准备错误日志文件
        File logFile = new File(LOGFILE_NAME);
        if(!logFile.getParentFile().exists()){
            logFile.getParentFile().mkdirs();
        }
        //写入错误日志
        FileWriter writer = null;
        try {
            //获取当前时间、异常message信息，异常栈跟踪信息写入日志文件
            writer = new FileWriter(logFile);
            writer.write("创建时间：" + StrUtils.currentTime("yy-MM-dd hh:mm:ss").toString()+lineFeed+lineFeed);
            writer.write(message+lineFeed);
            for(int i=0;i<stack.length;i++){
                writer.write(stack[i].toString() + lineFeed);
            }
            writer.write(lineFeed);
            writer.write(sb.toString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null != writer){
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 根据Build类搜集设备信息
     */
    private void collectDeviceInfo(){
        Field[] fields = Build.class.getDeclaredFields();
        for(Field field : fields){
            try {
                field.setAccessible(true);
                infos.put(field.getName(),field.get(null).toString());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

}
