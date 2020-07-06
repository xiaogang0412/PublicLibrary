package com.polyclinic.basemoudle.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.polyclinic.library.utils.DensityUtils;


/**
 * @author Lxg
 * @create 2020/4/2
 * @Describe
 */
public class FlexibleLayout extends LinearLayout {

    private float initX;
    private float initY;
    private boolean isDrug;
    private FrameLayout llHeader;
    private int height;
    private int width;
    private Context context;

    public void setLlHeader(FrameLayout llHeader) {
        this.llHeader = llHeader;
        init(context);
    }

    public FlexibleLayout(Context context) {
        this(context, null);
    }

    public FlexibleLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlexibleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

    }

    private void init(Context context) {
        height = llHeader.getLayoutParams().height;
        width = getWindowWidth(context);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                initX = ev.getX();
                initY = ev.getY();
                isDrug = false;

                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                float diffY = ev.getY() - initY;
                float diffX = ev.getX() - initX;
                if (diffY >0 && diffY / Math.abs(diffX) > 2) {
                    isDrug = true;
                    return true;
                }else {
                    isDrug=false;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (isDrug) {
                    float v = event.getY() - initY;
                    if(v>=0) {
                        changleHeader(v);
                    }
                    return true;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (isDrug) {
                    resetHeader();
                    return true;

                }
                break;

        }
        return super.onTouchEvent(event);
    }

    private void changleHeader(float v) {
        double changleY = Math.pow(v, 0.9);
        int chrrentHeight = (int) (changleY + height);
        int currentWidth = (width * chrrentHeight) / height;
        if (chrrentHeight < DensityUtils.dp2px(context, 300)) {
            llHeader.getLayoutParams().height = chrrentHeight;
            llHeader.requestLayout();
        }


    }

    private void resetHeader() {
        final ValueAnimator animator = ValueAnimator.ofInt(llHeader.getLayoutParams().height, height);
        animator.setDuration(300);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if (llHeader.getLayoutParams().height > height) {
                    llHeader.getLayoutParams().height = (int) valueAnimator.getAnimatedValue();
                    llHeader.requestLayout();
                } else {
                }
            }
        });
        animator.start();
    }

    public static int getWindowWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        return width;
    }
}
