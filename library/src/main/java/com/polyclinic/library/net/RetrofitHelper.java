package com.polyclinic.library.net;

import android.content.Context;

import com.polyclinic.library.utils.AppUtils;
import com.polyclinic.library.utils.ToastUtils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.PUT;

/**
 * @author Lxg
 * @create 2019/11/15
 * @Describe
 */
public class RetrofitHelper<T> {
    private static Retrofit retrofit;
    private static int connectTimeOut = 10000;
    private static int writeTimeOut = 10000;
    private static int readTimeOut = 10000;
    private static RetrofitHelper retrofitHelper;
    private static boolean isCache;
    private static Context context;
    private static String host;

    public boolean isCache() {
        return isCache;
    }

    public void setCache(boolean cache) {
        this.isCache = cache;
    }

    public RetrofitHelper(Builder builder) {
        this.connectTimeOut = builder.connectTimeOut;
        this.writeTimeOut = builder.writeTimeOut;
        this.readTimeOut = builder.readTimeOut;
    }

    public int getconnectTimeOut() {
        return connectTimeOut;
    }

    public void setconnectTimeOut(int connectTimeOut) {
        this.connectTimeOut = connectTimeOut;
    }

    public int getConnectTimeOut() {
        return connectTimeOut;
    }

    public RetrofitHelper() {
    }

    public void setConnectTimeOut(int connectTimeOut) {
        this.connectTimeOut = connectTimeOut;
    }

    public int getWriteTimeOut() {
        return writeTimeOut;
    }

    public void setWriteTimeOut(int writeTimeOut) {
        this.writeTimeOut = writeTimeOut;
    }

    public int getReadTimeOut() {
        return readTimeOut;
    }

    public void setReadTimeOut(int readTimeOut) {
        this.readTimeOut = readTimeOut;
    }

    public int getreadTimeOut() {
        return readTimeOut;
    }

    public void setreadTimeOut(int readTimeOut) {
        this.readTimeOut = readTimeOut;
    }

    public static RetrofitHelper getInstance() {
        if (retrofitHelper == null) {
            synchronized (RetrofitHelper.class) {
                if (retrofitHelper == null) {
                    retrofitHelper = new RetrofitHelper();
                }
            }
        }
        return retrofitHelper;
    }

    public static void init(String hostq) {
        context = AppUtils.getContxt();
        host = hostq;

    }

    public static OkHttpClient getOkhttpClient(final String token) {
        Cache cache = new Cache(context.getCacheDir(), 10 * 1024 * 1024);
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(readTimeOut, TimeUnit.SECONDS).cache(cache)
                .connectTimeout(connectTimeOut, TimeUnit.SECONDS).writeTimeout
                        (writeTimeOut, TimeUnit.SECONDS).addInterceptor(LoggerInterceptor
                        .getLoggerInterceptor()).addInterceptor(new Interceptor() {
                    @NotNull
                    @Override
                    public Response intercept(@NotNull Chain chain) throws IOException {
                        Request builder = chain.request().newBuilder().addHeader("Authorization",
                                "Bearer " + token).build();
                        return chain.proceed(builder);
                    }
                });
        if (isCache) {
            builder.cache(cache);
            builder.addInterceptor(new CacheInterceptor());
        }
        OkHttpClient okHttpClient = builder.build();
        return okHttpClient;
    }

    public static <T> T create(Class<T> t,String token) {
        retrofit = new Retrofit.Builder().baseUrl(host)
                .addConverterFactory(GsonConverterFactory.create()).
                        client(getOkhttpClient(token)).addCallAdapterFactory
                        (RxJavaCallAdapterFactory.create()).build();
        return retrofit.create(t);
    }

    static class Builder {
        private int connectTimeOut = 10000;
        private int writeTimeOut = 10000;
        private int readTimeOut = 10000;
        private boolean cache;

        public Builder cache(boolean cache) {
            this.cache = cache;
            return this;
        }

        public Builder connectTimeOut(int connectTimeOut) {
            this.connectTimeOut = connectTimeOut;
            return this;
        }

        public Builder writeTimeOut(int writeTimeOut) {
            this.writeTimeOut = writeTimeOut;
            return this;
        }

        public Builder readTimeOut(int readTimeOut) {
            this.readTimeOut = readTimeOut;
            return this;
        }
    }
}