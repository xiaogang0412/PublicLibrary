package com.polyclinic.library.net;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lxg
 * @create 2019/12/3
 * @Describe
 */
public class BasePresenter<T> {
    private final RetrofitHelper<Object> objectRetrofitHelper;
    public final Object object;
    public Map<String, Object> map = new HashMap<>();

    public BasePresenter(Class<?> c,String token) {
        objectRetrofitHelper = new RetrofitHelper();
        object = objectRetrofitHelper.create(c,token);
    }

    public RetriftCallBack setListener() {
        RetriftCallBack<T> retriftCallBack = new RetriftCallBack<T>() {
            @Override
            public void onSucess(T t) {
//                listener.Sucess(t);
            }

            @Override
            public void onError(String message) {
//                listener.Fail(message + "");
            }
        };
        return retriftCallBack;
    }

}
