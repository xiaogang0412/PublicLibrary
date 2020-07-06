package com.polyclinic.library.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * @author Lxg
 * @create 2020/6/16
 * @Describe
 */
public class MobileUtils {
    public static void call(String number, Context context) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + number);
        intent.setData(data);
        context.startActivity(intent);

    }

    ;
}
