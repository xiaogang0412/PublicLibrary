package com.polyclinic.library.utils;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.lang.ref.WeakReference;

/**
 * @author Lxg
 * @create 2019/11/13
 * @Describe
 */
public class WeakRefHandler extends Handler{
    public interface IHandler {
        void handleMsg(Message msg);
    }

    private final WeakReference<IHandler> mRef;

    public WeakRefHandler (IHandler handler) {
        mRef = new WeakReference<>(handler);
    }

    public WeakRefHandler (Looper looper, IHandler handler) {
        super(looper);
        mRef = new WeakReference<>(handler);
    }

    @SuppressWarnings("unused")
    @Override
    public void handleMessage(Message msg) {
        IHandler handler = mRef.get();
        if (handler != null && msg != null)
            handler.handleMsg(msg);
    }

}
