package com.polyclinic.library.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.polyclinic.library.utils.AppUtils;
import com.polyclinic.library.utils.ToastUtils;

/**
 * @author Lxg
 * @create 2019/11/15
 * @Describe
 */
public class AppManager {

    public static void init(Context context) {
        AppUtils.init(context);
    }
    public static long exitApp(Activity activity, String content, long fristTime) {
        if ((System.currentTimeMillis() -fristTime) >= 2000) {
            ToastUtils.showToast(content);
            return System.currentTimeMillis();
        }
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        activity.startActivity(intent);
        return 0;

    }
}
