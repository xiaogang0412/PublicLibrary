package com.polyclinic.library.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * @author Lxg
 * @create 2019/11/13
 * @Describe
 */
public class ToastUtils {
    public static void showToast(String content) {
        Toast toast = Toast.makeText(AppUtils.getContxt(), content, Toast.LENGTH_SHORT);
        toast.setText(content);
        toast.show();
    }
}
