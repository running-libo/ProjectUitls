package com.example.projectlib;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络判断工具类
 */
public class NetWorkUtils {

    /**
     * 判断网路是否可用
     * @param context
     * @return
     */
    public static boolean isNetWorkAvailable(Context context){
        ConnectivityManager manager  = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //获取所有可用的网络连接信息
        NetworkInfo[] infos = manager.getAllNetworkInfo();
        if(null != infos){
            for(NetworkInfo info : infos){
                if(info.getState() == NetworkInfo.State.CONNECTED){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 返回网络类型
     * @return
     */
    public static NetWorkState getNetworkState(Context context){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(manager.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI){
            return NetWorkState.WIFI;
        }else if(manager.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_MOBILE){
            return NetWorkState.MOBILE;
        }else {
            return NetWorkState.NONE;
        }
    }

    public enum NetWorkState {
        WIFI,MOBILE,NONE
    }
}
