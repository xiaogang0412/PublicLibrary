package com.polyclinic.library.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Lxg
 * @create 2020/2/10
 * @Describe
 */
public abstract class BaseFragment extends Fragment {

    private Unbinder bind;
    protected View view;
    public Bundle arguments;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(getLayoutId(), container, false);
        bind = ButterKnife.bind(this, view);
        arguments = getArguments();
        initView();
        loadData();
        setListener();
        return view;
    }

    public abstract int getLayoutId();

    /**
     * 初始化控件
     */
    public abstract void initView();

    /**
     * 加载数据
     */
    public abstract void loadData();

    public abstract void setListener();

    /**
     * 带参数跳转
     *
     * @param targetClass
     * @param bundle
     */
    protected void startActivity(Class<?> targetClass, Bundle bundle) {
        Intent intent = new Intent(getActivity(), targetClass);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    protected void startActivity(Class<?> targetClass) {
        Intent intent = new Intent(getActivity(), targetClass);
        startActivity(intent);

    }

    /**
     * 带参数带请求码跳转
     *
     * @param targetClass
     * @param bundle
     * @param requestCode
     */
    protected void startActivity(Class<?> targetClass, Bundle bundle, int requestCode) {
        Intent intent = new Intent(getActivity(), targetClass);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);

    }

    @Override
    public void onDestroyView() {
        if (bind != null) {
            bind.unbind();
        }
        super.onDestroyView();

    }

    /**
     * 带请求码跳转
     *
     * @param targetClass
     * @param requstCode
     */
    protected void startActivity(Class<?> targetClass, int requstCode) {
        Intent intent = new Intent(getActivity(), targetClass);
        startActivityForResult(intent, requstCode);
    }

    public void setTitle(String title, TextView tvTitle) {
        tvTitle.setText(title);
    }


    private boolean fastClick() {
        long lastClick = 0;
        if (System.currentTimeMillis() - lastClick <= 1000) {
            return false;
        }
        lastClick = System.currentTimeMillis();
        return true;
    }
}
