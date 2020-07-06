package com.polyclinic.library.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.polyclinic.library.R;
import com.polyclinic.library.net.NetConstants;
import com.polyclinic.library.net.NetWorkUtils;

/**
 * @author Lxg
 * @create 2019/12/3
 * @Describe
 */
public class LoadStausLayout extends FrameLayout {
    View mEmptyView, mErrorView, mLoadView;
    private int mEmptySrc, myErrorSrc, myLoadSrc;
    private String mEmptyBtText, mErrorBtText;
    private String mEmptyText, mErrorText, mLoadingText;
    private LoadingView loadingView;
    private TextView tvError;
    private TextView tvEmpty;

    public LoadStausLayout(@NonNull Context context) {
        this(context, null);
    }

    public LoadStausLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadStausLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.LoadStausLayout, 0, 0);
        mEmptySrc = typedArray.getResourceId(R.styleable.LoadStausLayout_empty_src, 0);
        myErrorSrc = typedArray.getResourceId(R.styleable.LoadStausLayout_error_src, 0);
        myLoadSrc = typedArray.getResourceId(R.styleable.LoadStausLayout_loading_src, 0);
        mEmptyBtText = typedArray.getString(R.styleable.LoadStausLayout_empty_bt_text);
        mErrorBtText = typedArray.getString(R.styleable.LoadStausLayout_error_bt_text);
        mLoadingText = typedArray.getString(R.styleable.LoadStausLayout_loading_text);
        mEmptyText = typedArray.getString(R.styleable.LoadStausLayout_empty_tv_text);
        mErrorBtText = typedArray.getString(R.styleable.LoadStausLayout_error_tv_text);
        mEmptyView = LayoutInflater.from(context).inflate(R.layout.item_library_empty, null);
        mErrorView = LayoutInflater.from(context).inflate(R.layout.item_library_error, null);
        mLoadView = LayoutInflater.from(context).inflate(R.layout.item_library_loading, null);
        tvEmpty = mEmptyView.findViewById(R.id.tv_empty);
        loadingView = mLoadView.findViewById(R.id.lv_loading);
        mLoadView.setTag("load");
        mEmptyView.setTag("empty");
        mErrorView.setTag("error");
        addView(mErrorView);
        addView(mEmptyView);
        addView(mLoadView);
        showLoad();
        tvError = mErrorView.findViewById(R.id.tv_error);
        setContent();
    }

    private void setContent() {
        if (!TextUtils.isEmpty(mEmptyText))
            tvEmpty.setText(mEmptyText);
    }

    public void showEmpty() {
        stopLoading();
        setStaus("empty");
    }

    public void showError() {
        stopLoading();
        if (NetWorkUtils.isConnected()) {
            tvError.setText(NetConstants.SERVERERROR);
        } else {
            tvError.setText("网络错误,请检查您的网络是否正常");
        }
        setStaus("error");
    }

    private void setStaus(String error) {
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            String tag = String.valueOf(childAt.getTag());
            if (TextUtils.equals(tag, error)) {
                childAt.setVisibility(VISIBLE);
            } else {
                childAt.setVisibility(GONE);
            }
        }
    }

    public void showLoad() {
        setStaus("load");
        startLoading();

    }


    public void startLoading() {
        loadingView.start();
    }

    public void stopLoading() {
        loadingView.stop();
    }

    public void showContent() {
        for (int i = 0; i < getChildCount(); i++) {
            final View childAt = getChildAt(i);
            String tag = String.valueOf(childAt.getTag());
            if (TextUtils.equals(tag, "load") ||
                    TextUtils.equals("error", tag)
                    || TextUtils.equals(tag, "empty")) {
                childAt.setVisibility(GONE);
            } else {
                childAt.setVisibility(VISIBLE);
            }
        }
    }
}
