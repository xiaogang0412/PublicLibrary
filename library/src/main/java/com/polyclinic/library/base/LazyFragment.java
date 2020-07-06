package com.polyclinic.library.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Lxg
 * @create 2020/4/21
 * @Describe
 */
public abstract class LazyFragment extends Fragment {
    public boolean isBoundView;
    private Unbinder unbinder;
    private boolean isLazyLoaded;
    private boolean isPrepared;
    public View root;
    public   Bundle arguments;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        arguments = getArguments();
        if (root == null) {
            root = inflater.inflate(getLayoutId(), container,false);
            unbinder = ButterKnife.bind(this, root);
            isBoundView = true;
            initView();
            setListener();
        }

        return root;

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            isBoundView = false;
            try {
                unbinder.unbind();

            } catch (Exception e) {

            }
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPrepared = true;
        lazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        lazyLoad();
    }

    private void lazyLoad() {
        if (getUserVisibleHint() && isPrepared && !isLazyLoaded) {
           loadData();
            isLazyLoaded = true;
        }
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
