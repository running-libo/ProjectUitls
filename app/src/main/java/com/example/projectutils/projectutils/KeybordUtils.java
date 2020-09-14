package com.example.projectutils.projectutils;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * create by libo
 * create on 2018/11/20
 * description 打开关闭软键盘工具类
 */
public class KeybordUtils {

    /**
     * 打开软键盘
     *
     * @param context
     * @param et
     */
    public static void showKeybord(final Context context, final EditText et) {
        et.setFocusable(true);
        et.setFocusableInTouchMode(true);
        //请求获得焦点
        et.requestFocus();
        //调用系统输入法
        InputMethodManager inputManager = (InputMethodManager) et
                .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(et, 0);
    }

    /**
     * 关闭软键盘
     *
     * @param context
     * @param editText
     */
    public static void hideKeybord(Context context, EditText editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(editText.getApplicationWindowToken(), 0);
        }
    }
}
