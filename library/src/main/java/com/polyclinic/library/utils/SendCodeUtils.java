package com.polyclinic.library.utils;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.util.Log;

import com.polyclinic.library.listener.SendCodeLisenter;

/**
 * @author Lxg
 * @create 2020/5/25
 * @Describe
 */
public class SendCodeUtils implements ValueAnimator.AnimatorUpdateListener, TimeInterpolator {
    private SendCodeLisenter lisenter;

    public void sendCode(int time, SendCodeLisenter lisenter) {
        this.lisenter = lisenter;
        ValueAnimator valueAnimator = ValueAnimator.ofInt(time, 0);
        valueAnimator.setDuration(time*1000);
        valueAnimator.start();
        valueAnimator.addUpdateListener(this);
        valueAnimator.setInterpolator(this);
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        int value = (int) animation.getAnimatedValue();
        if (lisenter != null) {
            if (value != 0) {
                lisenter.update(value);
            } else {
                lisenter.end();
            }
        }
    }

    @Override
    public float getInterpolation(float input) {
        return input;
    }
}
