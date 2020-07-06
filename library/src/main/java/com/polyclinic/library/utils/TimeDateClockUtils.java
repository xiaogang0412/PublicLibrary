package com.polyclinic.library.utils;

import android.animation.ValueAnimator;
import android.util.Log;
import android.view.animation.LinearInterpolator;

import java.util.Calendar;
import java.util.Date;

import static android.animation.ValueAnimator.INFINITE;

/**
 * @author Lxg
 * @create 2020/6/5
 * @Describe
 */
public class TimeDateClockUtils implements ValueAnimator.AnimatorUpdateListener {
    private onTimeListener listener;

    public void setListener(onTimeListener listener) {
        this.listener = listener;
    }

    public void setTime(){
        ValueAnimator valueAnimator=ValueAnimator.ofInt(0,1);
        valueAnimator.setDuration(10);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(this);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(INFINITE);
        valueAnimator.start();
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        if(listener!=null){
            String currentTime = TimeFormatUtils.getCurrentTime("HH:mm:ss");
            listener.time(currentTime);
            listener.date(Calendar.getInstance().getTimeInMillis());
        }

    }
    public interface onTimeListener{
        void time(String time);
        void date(long date);
    }

}
