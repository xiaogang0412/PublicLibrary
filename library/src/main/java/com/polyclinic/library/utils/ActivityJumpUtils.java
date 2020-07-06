package com.polyclinic.library.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * @author Lxg
 * @create 2020/4/22
 * @Describe
 */
public class ActivityJumpUtils {
    protected void startActivity(Class<?> targetClass, Bundle bundle, Context context) {
        Intent intent = new Intent(context, targetClass);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    /**
     * 带参数带请求码跳转
     *
     * @param targetClass
     * @param bundle
     * @param requestCode
     */
    protected void startActivity(Class<?> targetClass, Bundle bundle, int requestCode, Activity context) {
        Intent intent = new Intent(context, targetClass);
        intent.putExtras(bundle);
      context.startActivityForResult(intent, requestCode);

    }

    /**
     * 带请求码跳转
     *
     * @param targetClass
     * @param requstCode
     */
    protected void startActivity(Class<?> targetClass, int requstCode,Activity activity) {
        Intent intent = new Intent(activity, targetClass);
        activity.startActivityForResult(intent, requstCode);
    }

    protected void startActivity(Class<?> targetClass,Context context) {
        Intent intent = new Intent(context, targetClass);
        context.startActivity(intent);

    }
}
