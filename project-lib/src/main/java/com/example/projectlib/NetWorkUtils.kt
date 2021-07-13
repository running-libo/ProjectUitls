package com.example.projectlib

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

/**
 * 网络判断工具类
 */
object NetWorkUtils {
    /**
     * 判断网路是否可用
     * @param context
     * @return
     */
    fun isNetWorkAvailable(context: Context): Boolean {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        //获取所有可用的网络连接信息
        val infos = manager.allNetworkInfo
        if (null != infos) {
            for (info in infos) {
                if (info.state == NetworkInfo.State.CONNECTED) {
                    return true
                }
            }
        }
        return false
    }

    /**
     * 返回网络类型
     * @return
     */
    fun getNetworkState(context: Context): NetWorkState {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (manager.activeNetworkInfo!!.type == ConnectivityManager.TYPE_WIFI) {
            NetWorkState.WIFI
        } else if (manager.activeNetworkInfo!!.type == ConnectivityManager.TYPE_MOBILE) {
            NetWorkState.MOBILE
        } else {
            NetWorkState.NONE
        }
    }

    enum class NetWorkState {
        WIFI, MOBILE, NONE
    }
}