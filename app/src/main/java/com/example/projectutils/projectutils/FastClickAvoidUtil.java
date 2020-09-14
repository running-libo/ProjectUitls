package com.example.projectutils.projectutils;

/**
 * create by libo
 * create on 2018/1/17
 * description 防止控件快速点击
 */
public class FastClickAvoidUtil {
    private static long lastClickTime;
    /** 连续点击间隔时间 */
    private final static int SPACE_TIME = 500;

    public synchronized static boolean isDoubleClick() {
        long currentTime = System.currentTimeMillis();
        boolean isClickDouble;
        if (currentTime - lastClickTime > SPACE_TIME) {
            isClickDouble = false;
        } else {
            isClickDouble = true;
        }
        lastClickTime = currentTime;
        return isClickDouble;
    }

    /**
     * 自定义间隔时间
     */
    public synchronized static boolean isDoubleClick(int spaceTime) {
        long currentTime = System.currentTimeMillis();
        boolean isClickDouble;
        if (currentTime - lastClickTime > spaceTime) {
            isClickDouble = false;
        } else {
            isClickDouble = true;
        }
        lastClickTime = currentTime;
        return isClickDouble;
    }



}
