package com.polyclinic.library.base;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.polyclinic.library.R;
import com.polyclinic.library.utils.SendCodeUtils;

import butterknife.ButterKnife;

/**
 * @author Lxg
 * @create 2020/2/18
 * @Describe
 */
public abstract class BasePopowindow extends PopupWindow implements PopupWindow.OnDismissListener {
    public Context context;
    public View view;
    private int width;
    private int height;

    public BasePopowindow(Context context, int width, int height) {
        super(context);
        this.context = context;
        this.width = width;
        this.height = height;
        view = LayoutInflater.from(context).inflate(getLayoutId(), null);
        ButterKnife.bind(this, view);
        initPopwindow();
    }

    protected void initPopwindow() {
        setContentView(view);
        setWidth(width);
        setHeight(height);
        setBackgroundDrawable(null);

    }

    public abstract int getLayoutId();

    public void showAtLoataionCenter() {
        setOutsideTouchable(true);
        View decorView = ((Activity) context).getWindow().getDecorView();
        if (decorView != null) {
            showAtLocation(decorView, Gravity.CENTER, 0, 0);
            backgroundAlpha(0.5f, (Activity) context);

        }
        setDissMiss();
    }

    public void showAtLoataionCenter(View view) {
        setOutsideTouchable(true);
        if (view != null) {
            showAtLocation(view, Gravity.CENTER, 0, 0);
            backgroundAlpha(0.8f, (Activity) context);

        }
        setDissMiss();
    }

    public void showAtLocationBottom(View view) {
        setOutsideTouchable(true);
        setAnimationStyle(R.style.pop_animation);
        showAtLocation(view, Gravity.BOTTOM, 0, 0);
        backgroundAlpha(0.5f, (Activity) context);
        setDissMiss();
    }

    public void showDropAt(View view) {
        setOutsideTouchable(true);
        showAsDropDown(view, 0, 300);
    }

    public static void backgroundAlpha(float bgAlpha, Activity activity) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        activity.getWindow().setAttributes(lp);
    }

    public void showAtLoataionCenterFource(View view) {
        setOutsideTouchable(false);
        setFocusable(true);
        showAtLocation(view, Gravity.CENTER, 0, 0);
        setDissMiss();
    }

    public void setDissMiss() {
        setOnDismissListener(this);
    }

    @Override
    public void onDismiss() {
        backgroundAlpha(1.0f, (Activity) context);
        dismiss();
    }

    public static void onPause(BasePopowindow popowindow) {
        if (popowindow != null && popowindow.isShowing()) {
            popowindow.setAnimationStyle(0);
           popowindow.update();
        }
    }

    public static void onResume(final BasePopowindow popowindow, View view) {
        if (popowindow != null) {
            view.postDelayed(new Runnable() {
                @Override
                public void run() {
                    popowindow.setAnimationStyle(R.style.pop_animation);
                    popowindow.update();
                }
            }, 200);
        }
    }
}
