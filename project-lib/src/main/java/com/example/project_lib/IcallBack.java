package com.example.projectutils.projectutils;

import android.graphics.Bitmap;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by libo on 2017/5/8.
 *
 * 适配器模式的回调接口
 */
public interface IcallBack {

    void callBack(List list);

    void callBack(List list, boolean charge);

    void callBack(String str);

    void callBack(String str, int code);

    void callBack(byte[] bytes);

    void callBack(JSONObject obj);

    void callBack(Bitmap bitmap);

    void confirmHandle();

    void callBack(int progress);

    void callBack(float num);

    void callBack(boolean boo);

    /** 可变长int参数 */
    void callBack(int page, int subPage);

    /** 可变长字符串参数 */
    void callBack(String... args);

    void onFail();
}
