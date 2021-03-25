package com.example.projectutils.projectutils;

import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;

/**
 * create by libo
 * create on 2018/12/7
 * description 常用动画调用类
 */
public class AnimUtils {

    /**
     * 以中心缩放属性动画
     * @param view
     * @param from
     * @param to
     */
    public static void startScaleAnim(View view, long time, float from, float to) {
        ObjectAnimator animX = ObjectAnimator.ofFloat(view, "scaleX", from, to);
        ObjectAnimator animY = ObjectAnimator.ofFloat(view, "scaleY", from, to);
        animX.setDuration(time);
        animY.setDuration(time);
        animX.start();
        animY.start();
    }

    /**
     * 旋转属性动画
     * @param view
     * @param time
     * @param fromAngle
     * @param toAngle
     */
    public static void startRotateAnim(View view, long time, int fromAngle, float toAngle){
        ObjectAnimator animRotate = ObjectAnimator.ofFloat(view,"rotation",fromAngle,toAngle);
        animRotate.setDuration(time);
        animRotate.start();
    }

    /**
     * 移动动画
     * @param view
     * @param fromX
     * @param toX
     * @param fromY
     * @param toY
     */
    public static void translationAnim(View view, long duration, float fromX, float toX, float fromY, float toY){
        TranslateAnimation anim = new TranslateAnimation(fromX,toX,fromY,toY);
        anim.setDuration(duration);
        anim.setFillAfter(true);
        anim.setInterpolator(new DecelerateInterpolator());
        view.startAnimation(anim);
    }

    /**
     * 透明度改变动画
     * @param view
     * @param fromAlpha
     * @param toAlpha
     * @param duration
     */
    public static void alphaAnim(View view, float fromAlpha, float toAlpha, long duration){
        AlphaAnimation anim = new AlphaAnimation(fromAlpha,toAlpha);
        anim.setDuration(duration);
        view.startAnimation(anim);
    }
}
