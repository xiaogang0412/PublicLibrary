package com.polyclinic.library.net;


import android.util.Log;

import org.jetbrains.annotations.NotNull;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @author Lxg
 * @create 2019/11/15
 * @Describe
 */
public class LoggerInterceptor {
    public static HttpLoggingInterceptor getLoggerInterceptor() {
        return new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(@NotNull String s) {
                Log.i("logger", s);

            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY);

    }
}
