package com.polyclinic.library.utils;

import android.os.CountDownTimer;

/**
 * @author Lxg
 * @create 2020/3/10
 * @Describe
 */
public class CountDownTimerUtils {
    private CountDownListener listener;
    private long time = 60000;
    CountDownTimer timer;
    public CountDownListener getListener() {
        return listener;
    }

    public void setListener(final CountDownListener listener, long time) {
        this.listener = listener;
         timer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String value = String.valueOf((int) (millisUntilFinished / 1000));
                if (listener != null) {
                    listener.Value(value);
                }

            }

            @Override
            public void onFinish() {
                listener.complete();
            }
        };
    }



    public void sendCode() {
        if (timer != null) {
            timer.start();
        }
    }

    public void cancelCount() {
        if (timer != null) {
            timer.cancel();
            setFinish();
        }
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setFinish() {
        timer.onFinish();
    }
}
