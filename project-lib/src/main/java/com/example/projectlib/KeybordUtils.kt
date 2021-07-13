package com.example.projectlib

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 * create by libo
 * create on 2018/11/20
 * description 打开关闭软键盘工具类
 */
object KeybordUtils {
    /**
     * 打开软键盘
     *
     * @param context
     * @param et
     */
    fun showKeybord(context: Context?, et: EditText) {
        et.isFocusable = true
        et.isFocusableInTouchMode = true
        //请求获得焦点
        et.requestFocus()
        //调用系统输入法
        val inputManager = et
                .context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.showSoftInput(et, 0)
    }

    /**
     * 关闭软键盘
     *
     * @param context
     * @param editText
     */
    fun hideKeybord(context: Context, editText: EditText) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm.isActive) {
            imm.hideSoftInputFromWindow(editText.applicationWindowToken, 0)
        }
    }
}