package com.example.projectutils.projectutils;

import android.graphics.Bitmap;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by libo on 2017/5/8.
 *
 * 接口回调抽象类,可实现任意类型回调
 */
public abstract class CallBackimpl implements IcallBack{

    @Override
    public void callBack(List datas) {

    }

    @Override
    public void callBack(List list, boolean charge) {

    }

    @Override
    public void callBack(String str) {

    }

    @Override
    public void callBack(String str, int code) {

    }

    @Override
    public void callBack(byte[] bytes) {

    }

    @Override
    public void callBack(Bitmap bitmap) {

    }

    @Override
    public void confirmHandle() {

    }

    @Override
    public void callBack(int progress) {

    }

    @Override
    public void callBack(float num) {

    }

    @Override
    public void callBack(int page,int subpage) {

    }

    @Override
    public void callBack(boolean boo) {

    }

    @Override
    public void callBack(JSONObject obj) {

    }

    @Override
    public void callBack(String... args) {

    }

    @Override
    public void onFail() {

    }
}
