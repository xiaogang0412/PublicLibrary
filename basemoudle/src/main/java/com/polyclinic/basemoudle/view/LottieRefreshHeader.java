package com.polyclinic.basemoudle.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.polyclinic.basemoudle.R;
import com.polyclinic.library.view.LoadingView;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;

/**
 * @author Lxg
 * @create 2020/4/23
 * @Describe
 */
@SuppressLint("RestrictedApi")
public class LottieRefreshHeader extends LinearLayout implements RefreshHeader {
    LoadingView lvLoading;
    private ImageView ivHeader;

    public LottieRefreshHeader(Context context) {
        this(context, null);
    }

    public LottieRefreshHeader(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LottieRefreshHeader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.basemodel_refresh_header, null);
        lvLoading=view.findViewById(R.id.lv_loading);

    }


    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @NonNull
    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;
    }

    @Override
    public void setPrimaryColors(int... colors) {

    }

    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int extendHeight) {

    }

    @Override
    public void onPulling(float percent, int offset, int height, int extendHeight) {


    }

    @Override
    public void onReleasing(float percent, int offset, int height, int extendHeight) {

    }

    @Override
    public void onReleased(RefreshLayout refreshLayout, int height, int extendHeight) {

    }

    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int extendHeight) {
        lvLoading.start();
    }

    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
        return 0;
    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {
        switch (newState){
            case None:
            case PullDownToRefresh:
                setVisibility(GONE);
                lvLoading.stop();
                Log.i("weewewewew", "stop");

                break;
            case Refreshing://正在刷新
                lvLoading.start();
                Log.i("weewewewew", "start");

                break;
            case ReleaseToRefresh://开始下拉操作
                setVisibility(VISIBLE);
                Log.i("weewewewew", "pull");

        }
    }
}
