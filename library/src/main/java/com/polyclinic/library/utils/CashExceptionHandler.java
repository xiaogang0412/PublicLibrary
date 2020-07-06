package com.polyclinic.library.utils;

import android.util.Log;

/**
 * @author Lxg
 * @create 2019/11/14
 * @Describe
 */
public class CashExceptionHandler implements Thread.UncaughtExceptionHandler {

    private Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler;

    public void init() {
        defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public static CashExceptionHandler getInstance() {
        return new CashExceptionHandler();
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        defaultUncaughtExceptionHandler.uncaughtException(t, e);
        Log.i("ewewwewewe", e.toString());
    }
}
