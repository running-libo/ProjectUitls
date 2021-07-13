package com.example.projectlib

import android.content.Context

/**
 * Created by libo on 2017/10/28.
 *
 * 共享参数操作工具类，根据值的类型存取对应类型的值
 */
object SharedPrefUtils {
    const val SHAREFRE_FILENAME = "sharefile"

    /**
     * 保存数据到共享参数中
     * @param context
     * @param key  键
     * @param object  值
     */
    fun setParams(context: Context, key: String?, `object`: Any?) {
        val sharedPreferences = context.getSharedPreferences(SHAREFRE_FILENAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        if (`object` is String) {
            editor.putString(key, `object` as String?)
        } else if (`object` is Int) {
            editor.putInt(key, (`object` as Int?)!!)
        } else if (`object` is Boolean) {
            editor.putBoolean(key, (`object` as Boolean?)!!)
        } else if (`object` is Float) {
            editor.putFloat(key, (`object` as Float?)!!)
        } else if (`object` is Long) {
            editor.putLong(key, (`object` as Long?)!!)
        }
        editor.commit()
    }

    /**
     * 获取共享参数，根据默认数据类型获取相对应key的值
     * @param context
     * @param key  键
     * @param defaultObject  默认值
     * @return
     */
    fun getParams(context: Context, key: String?, defaultObject: Any?): Any? {
        val sharedPreferences = context.getSharedPreferences(SHAREFRE_FILENAME, Context.MODE_PRIVATE)
        if (defaultObject is String) {
            return sharedPreferences.getString(key, defaultObject as String?)
        } else if (defaultObject is Int) {
            return sharedPreferences.getInt(key, (defaultObject as Int?)!!)
        } else if (defaultObject is Boolean) {
            return sharedPreferences.getBoolean(key, (defaultObject as Boolean?)!!)
        } else if (defaultObject is Float) {
            return sharedPreferences.getFloat(key, (defaultObject as Float?)!!)
        } else if (defaultObject is Long) {
            return sharedPreferences.getLong(key, (defaultObject as Long?)!!)
        }
        return null
    }
}