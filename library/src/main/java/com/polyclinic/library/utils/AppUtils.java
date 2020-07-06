package com.polyclinic.library.utils;

import android.content.Context;

import retrofit2.http.PUT;

/**
 * @author Lxg
 * @create 2019/11/15
 * @Describe
 */
public class AppUtils {
    private static Context context;

    private AppUtils() {

    }

    public static Context getContxt() {
        return context;
    }

    public static void init(Context context1) {
        context = context1;
    }
}
