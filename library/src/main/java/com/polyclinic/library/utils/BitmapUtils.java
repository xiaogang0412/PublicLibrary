package com.polyclinic.library.utils;

import android.graphics.Bitmap;
import android.view.View;

/**
 * @author Lxg
 * @create 2020/4/27
 * @Describe
 */
public class BitmapUtils {
    public static Bitmap getsnapShotScroll(View view) {
        int me = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(me, me);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }
}
