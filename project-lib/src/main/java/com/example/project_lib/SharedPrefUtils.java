package com.example.project_lib;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by libo on 2017/10/28.
 *
 * 共享参数操作工具类，根据值的类型存取对应类型的值
 */
public class SharedPrefUtils {
    public static final String SHAREFRE_FILENAME = "sharefile";

    /**
     * 保存数据到共享参数中
     * @param context
     * @param key  键
     * @param object  值
     */
    public static void setParams(Context context, String key, Object object){

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHAREFRE_FILENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if(object instanceof String){
            editor.putString(key, (String) object);
        }else if(object instanceof Integer){
            editor.putInt(key, (Integer) object);
        }else if(object instanceof Boolean){
            editor.putBoolean(key, (Boolean) object);
        }else if(object instanceof Float){
            editor.putFloat(key, (Float) object);
        }else if(object instanceof Long){
            editor.putLong(key, (Long) object);
        }

        editor.commit();
    }

    /**
     *  获取共享参数，根据默认数据类型获取相对应key的值
     * @param context
     * @param key  键
     * @param defaultObject  默认值
     * @return
     */
    public static Object getParams(Context context, String key, Object defaultObject){

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHAREFRE_FILENAME, Context.MODE_PRIVATE);

        if(defaultObject instanceof String){
            return sharedPreferences.getString(key, (String) defaultObject);
        }else if(defaultObject instanceof Integer){
            return sharedPreferences.getInt(key, (Integer) defaultObject);
        }else if(defaultObject instanceof Boolean){
            return sharedPreferences.getBoolean(key, (Boolean) defaultObject);
        }else if(defaultObject instanceof Float){
            return sharedPreferences.getFloat(key, (Float) defaultObject);
        }else if(defaultObject instanceof Long){
            return sharedPreferences.getLong(key, (Long) defaultObject);
        }
        return null;
    }

}
