package com.polyclinic.basemoudle.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.NumberPicker;

/**
 * @author Lxg
 * @create 2020/5/31
 * @Describe
 */
public class MyNumberPicker  extends NumberPicker {
    public MyNumberPicker(Context context) {
        super(context);
    }

    public MyNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyNumberPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyNumberPicker(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
