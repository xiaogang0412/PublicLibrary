package com.polyclinic.library.net;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.JsonSyntaxException;
import com.polyclinic.library.base.BaseBean;
import com.polyclinic.library.base.MessageEvent;
import com.polyclinic.library.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Lxg
 * @create 2019/4/3
 * @Describe
 */
public abstract class RetriftCallBack<T> implements Callback<T> {

    public abstract void onSucess(T t);

    public abstract void onError(String message);

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            BaseBean baseBean = (BaseBean) response.body();
            if (TextUtils.equals(baseBean.getMessage(), "ok")) {
                onSucess(response.body());
            } else {
                onError(baseBean.getMessage());

            }
        } else {
            onError(NetConstants.SERVERERROR);
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        Log.i("erererre", t.toString());
        if (t instanceof SocketTimeoutException) {
            onError(NetConstants.SOCKTIMEOUT);
        } else if (t instanceof JsonSyntaxException) {
            onError(NetConstants.PASARERROR);
        } else if (t instanceof ConnectException) {
            onError(NetConstants.CONNECTIONECT);
        } else if (t instanceof UnknownError) {
            onError(NetConstants.UNKNOWNERROR);
        } else if (t instanceof UnknownHostException) {
            if (NetWorkUtils.isConnected()) {
                onError(NetConstants.SERVERERROR);
            } else {
                onError(NetConstants.UNKNOWHOST);
            }
        } else {
            onError(NetConstants.UNKNOWNERROR);
        }

    }
}
