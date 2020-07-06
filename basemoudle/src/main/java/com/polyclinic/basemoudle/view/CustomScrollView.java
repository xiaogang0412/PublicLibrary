package com.polyclinic.basemoudle.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

/**
 * @author Lxg
 * @create 2020/5/28
 * @Describe
 */
public class CustomScrollView extends HorizontalScrollView {
    private onScrollListener listener;

    public void setListener(onScrollListener listener) {
        this.listener = listener;
    }

    public CustomScrollView(Context context) {
        this(context, null);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (listener != null) {
            listener.scroll(l);
        }
    }

    public interface onScrollListener {
        void scroll(int x);
    }
}
