package com.polyclinic.library.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author Lxg
 * @create 2020/2/17
 * @Describe
 */
public class AppBarUtil {
    public static void setStatusBarFullTransparent(Activity activity) {
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { //4.4以上
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.TRANSPARENT);// SDK21
        } else {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        String manufacturer = Build.MANUFACTURER;
        if (TextUtils.equals(manufacturer, "Xiaomi")) {
            if (Build.VERSION.SDK_INT >Build.VERSION_CODES.LOLLIPOP) {
                setStausBarDark(activity);
            } else {
                setStatusBarDarkMode(true, activity);
            }
        } else if(TextUtils.equals(manufacturer, "HUAWEI")){
            if (Build.VERSION.SDK_INT >Build.VERSION_CODES.LOLLIPOP) {
                setStausBarDark(activity);
            } else {
                setStatusBarDarkMode(true, activity);
            }

        }
    }

    /**
     * Android 6.0以前设置字体黑色
     *
     * @param darkmode
     * @param activity
     */
    public static void setStatusBarDarkMode(boolean darkmode, Activity activity) {
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            int darkModeFlag = 0;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), darkmode ? darkModeFlag : 0, darkModeFlag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setStausBarDark(Activity activity) {
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }
    public static void setBar(View linearLayout, Context context){
        ViewGroup.LayoutParams layoutParams = linearLayout.getLayoutParams();
        layoutParams.height = DensityUtils.dp2px(context, 44)
                + DensityUtils.getAppBarHeight(context);
        linearLayout.setLayoutParams(layoutParams);
    }

}
