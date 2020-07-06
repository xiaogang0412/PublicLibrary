package com.polyclinic.library.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.polyclinic.library.R;
import com.polyclinic.library.utils.AppBarUtil;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Lxg
 * @create 2019/11/13
 * @Describe
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    private Unbinder bind;
    public Bundle extras;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_AppCompat_Light_NoActionBar);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        extras = getIntent().getExtras();
        initView();
        loadData();
        setListener();

    }

    /**
     * 加载布局
     *
     * @return
     */
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


    public void setBack(View... views) {
        for (View view : views) {
            view.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    /**
     * 带参数跳转
     *
     * @param targetClass
     * @param bundle
     */
    protected void startActivity(Class<?> targetClass, Bundle bundle) {
        Intent intent = new Intent(this, targetClass);
        intent.putExtras(bundle);
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
        Intent intent = new Intent(this, targetClass);
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
        Intent intent = new Intent(this, targetClass);
        startActivityForResult(intent, requstCode);
    }

    protected void startActivity(Class<?> targetClass) {
        Intent intent = new Intent(this, targetClass);
        startActivity(intent);


    }

    public void setTitle(String title, TextView tvTitle) {
        tvTitle.setText(title);
    }

    public static void hideSoftKeyboard(Context context, ArrayList<EditText> viewList) {
        if (viewList == null) return;
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        for (View v : viewList) {
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    public void showInputMethod() {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.showSoftInputFromInputMethod(getCurrentFocus().getWindowToken(), 0);
        }
    }

    private boolean fastClick() {
        long lastClick = 0;
        if (System.currentTimeMillis() - lastClick <= 1000) {
            return false;
        }
        lastClick = System.currentTimeMillis();
        return true;
    }

    public void setFullScreen() {
        AppBarUtil.setStatusBarFullTransparent(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bind != null) {
            bind.unbind();
        }

    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1) {
            getResources();
        }
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public Resources getResources() {
        Resources resources = super.getResources();
        Configuration configuration = resources.getConfiguration();
        if (configuration.fontScale != 1.0f) {
            configuration.fontScale = 1.0f;
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        }
        return resources;
    }

}

