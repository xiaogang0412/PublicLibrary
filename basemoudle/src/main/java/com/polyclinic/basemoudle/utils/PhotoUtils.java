package com.polyclinic.basemoudle.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.polyclinic.basemoudle.activity.PhotoViewActivity;
import com.polyclinic.basemoudle.bean.PhotoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lxg
 * @create 2020/6/10
 * @Describe
 */
public class PhotoUtils {
    public static void scanImages(List<String> url, Context context,int postion) {
        Intent intent = new Intent(context, PhotoViewActivity.class);
        Bundle bundle = new Bundle();
        PhotoBean photoBean = new PhotoBean();
        photoBean.setPostion(0);
        List<String> datas = new ArrayList<>();
        for(int i=0;i<url.size();i++){
            datas.add(url.get(i));
        }
        photoBean.setPostion(postion);
        photoBean.setPhotos(datas);
        bundle.putSerializable("photo", photoBean);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
