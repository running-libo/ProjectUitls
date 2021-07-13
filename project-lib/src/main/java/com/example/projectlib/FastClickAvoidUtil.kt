package com.example.projectlib

/**
 * create by libo
 * create on 2018/1/17
 * description 防止控件快速点击
 */
object FastClickAvoidUtil {
    private var lastClickTime: Long = 0

    /** 连续点击间隔时间  */
    private const val SPACE_TIME = 500

    @get:Synchronized
    val isDoubleClick: Boolean
        get() {
            val currentTime = System.currentTimeMillis()
            val isClickDouble: Boolean
            isClickDouble = if (currentTime - lastClickTime > SPACE_TIME) {
                false
            } else {
                true
            }
            lastClickTime = currentTime
            return isClickDouble
        }

    /**
     * 自定义间隔时间
     */
    @Synchronized
    fun isDoubleClick(spaceTime: Int): Boolean {
        val currentTime = System.currentTimeMillis()
        val isClickDouble: Boolean
        isClickDouble = if (currentTime - lastClickTime > spaceTime) {
            false
        } else {
            true
        }
        lastClickTime = currentTime
        return isClickDouble
    }
}