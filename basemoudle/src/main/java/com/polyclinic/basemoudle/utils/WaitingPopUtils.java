package com.polyclinic.basemoudle.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.LinearLayout;

import com.polyclinic.basemoudle.pop.WaitingPopowindow;
import com.polyclinic.library.utils.AppUtils;

/**
 * @author Lxg
 * @create 2020/6/19
 * @Describe
 */
public class WaitingPopUtils {
    private static WaitingPopowindow waitingPopowindow;

    public static void showWait(Context context) {
        waitingPopowindow = new WaitingPopowindow(context);
        waitingPopowindow.showAtLoataionCenterFource(((Activity) context).getWindow().getDecorView());
        waitingPopowindow.startLoadind();
    }

    public static void hide() {
        if (waitingPopowindow != null) {
            waitingPopowindow.stopLoading();
            waitingPopowindow.dismiss();

        }
    }
}
