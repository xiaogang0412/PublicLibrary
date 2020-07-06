package com.polyclinic.basemoudle.activity;

import android.os.Build;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.polyclinic.basemoudle.R;
import com.polyclinic.basemoudle.view.TopBarView;
import com.polyclinic.library.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import retrofit2.http.PUT;

public class BaseWebActivity extends BaseActivity {
    WebView webview;
    TopBarView topbar;
    String url;

    @Override
    public int getLayoutId() {
        return R.layout.activity_base_web;
    }

    @Override
    public void initView() {
        webview = findViewById(R.id.webview);
        topbar = findViewById(R.id.topbar);
        topbar.setSingle(true);
        url = extras.getString("url");
        Log.i("weewweew", url);
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setSupportZoom(true); //设置可以支持缩放
        webview.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webview.getSettings().setDomStorageEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webview.getSettings()
                    .setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        }

    }

    @Override
    public void loadData() {
        webview.loadUrl(url);

    }

    @Override
    public void setListener() {
        webview.setWebViewClient(
                new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        webview.loadUrl(url);
                        return super.shouldOverrideUrlLoading(view, url);
                    }

                    @Override
                    public void onReceivedError(WebView view, WebResourceRequest request,
                                                WebResourceError error) {
                        super.onReceivedError(view, request, error);
                    }
                }
        );
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (topbar != null) {
                    topbar.setText(title);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (webview.canGoBack()) {
            webview.goBack();
            return;
        }
        super.onBackPressed();
    }

}
