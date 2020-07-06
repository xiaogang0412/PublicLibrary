package com.polyclinic.library.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lxg
 * @create 2019/12/6
 * @Describe
 */
public abstract class TBaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    public List<T> data;
    public Context context;

    public TBaseAdapter(Context context) {
        this.context = context;
    }

    public void addData(List<T> data) {
        if (this.data == null) {
            this.data = new ArrayList<>();
        }
        if (data != null) {
            this.data.addAll(data);
        }
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(getLayoutId(), viewGroup, false);
        return getViewHolder(view);
    }


    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void cleanData() {
        if (data == null) {
            return;
        }
        data.clear();
        notifyDataSetChanged();
    }

    public abstract BaseViewHolder getViewHolder(View view);

    public abstract int getLayoutId();

    /**
     * 带参数跳转
     *
     * @param targetClass
     * @param bundle
     */
    protected void startActivity(Class<?> targetClass, Bundle bundle) {
        Intent intent = new Intent(context, targetClass);
        intent.putExtras(bundle);
        context.startActivity(intent);

    }

    /**
     * 带参数带请求码跳转
     *
     * @param targetClass
     * @param bundle
     * @param requestCode
     */
    protected void startActivity(Class<?> targetClass, Bundle bundle, int requestCode) {
        Intent intent = new Intent(context, targetClass);
        intent.putExtras(bundle);
        ((Activity) context).startActivityForResult(intent, requestCode);

    }

    /**
     * 带请求码跳转
     *
     * @param targetClass
     * @param requstCode
     */
    protected void startActivity(Class<?> targetClass, int requstCode) {
        Intent intent = new Intent(context, targetClass);
        ((Activity) context).startActivityForResult(intent, requstCode);
    }

    protected void startActivity(Class<?> targetClass) {
        Intent intent = new Intent(context, targetClass);
        context.startActivity(intent);


    }

    public void setPostion(int postion, T t) {
        data.add(postion, t);
        notifyDataSetChanged();
    }
}
