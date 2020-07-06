package com.polyclinic.basemoudle.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.bumptech.glide.request.transition.Transition;
import com.polyclinic.basemoudle.R;

/**
 * @author Lxg
 * @create 2020/3/3
 * @Describe
 */
public class GlideUtils {
    private onLoadListener loadListener;

    public void setLoadListener(onLoadListener loadListener) {
        this.loadListener = loadListener;
    }

    /**
     * 普通图片处理
     *
     * @param context
     * @param url
     * @param imageView
     * @param placeholder
     */
    public static void getInstance(Context context, Object url, ImageView imageView,
                                   Object placeholder) {
        DrawableCrossFadeFactory drawableCrossFadeFactory =
                new DrawableCrossFadeFactory.Builder(500).setCrossFadeEnabled(true).build();
        RequestOptions options = new RequestOptions();
        if (placeholder != null) {
            options.placeholder((Drawable) placeholder);
        }
        options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        if (context != null) {
            try {
                Glide.with(context).load(url).apply(options).transition(DrawableTransitionOptions.with(drawableCrossFadeFactory)).into(imageView);

            } catch (Exception e) {
            }

        }
    }

    /**
     * 圆形图片处理
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void getCircleInstance(Context context, Object url, ImageView imageView) {
        RequestOptions options = new RequestOptions();
//        options.placeholder(R.mipmap.img_default_avator).error(R.mipmap.img_default_avator);
//        GlideCircleTransform glideCircleTransform = new GlideCircleTransform();
//        options.transform(glideCircleTransform);
        options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        if (context != null) {
            Glide.with(context).load(url).apply(options).into(imageView);
        }
    }

    public static void getCircleImageView(Context context, Object url, ImageView imageView,
                                          Object placeholder) {
        RequestOptions options = new RequestOptions();
        GlideCircleTransform glideCircleTransform = new GlideCircleTransform();
        if (placeholder != null) {
            options.placeholder((Integer) placeholder);
        }
        options.transform(glideCircleTransform);
        options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        if (context != null) {
            try {
                Glide.with(context).load(url).apply(options).into(imageView);

            } catch (Exception e) {
            }
        }
    }

    @SuppressLint("CheckResult")
    public void load(String url, final Context context, final String codeUrl) {
        Glide.with(context).asBitmap().load(url).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull final Bitmap resource, @Nullable Transition<?
                    super Bitmap> transition) {
                if (loadListener != null) {
                    if (codeUrl != null) {
                        Glide.with(context).asBitmap().load(codeUrl).into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource1,
                                                        @Nullable Transition<? super Bitmap> transition) {
                                loadListener.loadReady(resource, resource1);

                            }
                        });
                    }
                }
            }
        });
    }

    public interface onLoadListener {
        void loadReady(Bitmap bitmap, Bitmap bitmap1);
    }

    private SimpleTarget<Bitmap> simpleTarget = new SimpleTarget<Bitmap>() {
        @Override
        public void onResourceReady(@NonNull Bitmap resource,
                                    @Nullable Transition<? super Bitmap> transition) {
            Log.i("ZZAAAW", "aswwqqwqwqwqwq");
            loadListener.loadReady(resource, null);

        }

        @Override
        public void onLoadFailed(@Nullable Drawable errorDrawable) {
            super.onLoadFailed(errorDrawable);
            Log.i("ewweweewew", "weeeweewwe");
        }
    };

    public void loadBitMap(String url, Context context) {
        Glide.with(context).asBitmap().load(url).into(simpleTarget);
    }
}
