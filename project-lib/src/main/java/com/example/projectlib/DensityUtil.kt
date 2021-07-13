package com.example.projectlib

import android.content.Context

fun Context.dip2pxInt(dpValue: Float): Int {
    val scale = resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}

/**
 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
 */
fun Context.px2dipInt(pxValue: Float): Int {
    val scale = resources.displayMetrics.density
    return (pxValue / scale + 0.5f).toInt()
}

/**
 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
 */
fun Context.dip2px(dpValue: Float): Float {
    val scale = resources.displayMetrics.density
    return dpValue * scale
}

/**
 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
 */
fun Context.px2dip(pxValue: Float): Float {
    val scale = resources.displayMetrics.density
    return pxValue / scale
}

fun Context.getScreenWidthPixels() = resources.displayMetrics.widthPixels

fun Context.getScreenHeightPixels() = resources.displayMetrics.heightPixels

/**
 * 获取屏幕密度
 * @param context
 * @return
 */
fun Context.getScreenDensity(): Float {
    return resources.displayMetrics.density
}
