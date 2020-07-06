package com.polyclinic.library.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lxg
 * @create 2019/11/13
 * @Describe
 */
public class PermisionUtils {
    public static  int PERMISIONCODE ;
    public static List<String> noPersions = new ArrayList<>();

    /**
     * 请求权限
     *
     * @param requestion
     * @param context
     * @return
     */
    public static boolean requestPermissions(String[] requestion, Activity context,int requestcode) {
        PERMISIONCODE=requestcode;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { //Android 6.0以上版本
            List<String> list = checkPermissions(context, requestion); //获取未授权权限集合
            String[] persions = new String[list.size()];
            list.toArray(persions);
            if (persions.length != 0) {
                context.requestPermissions(persions,requestcode);
                return false;
            }
        }
        return true;
    }

    /**
     * 检查是否获得权限
     *
     * @param activity
     * @param permissions
     * @return
     */
    public static List<String> checkPermissions(Activity activity, String[] permissions) {
        noPersions.clear();
        for (int i = 0; i < permissions.length; i++) {
            String permission = permissions[i];
            int i1 = ContextCompat.checkSelfPermission(activity, permission);
            if (i1 != PackageManager.PERMISSION_GRANTED) {
                noPersions.add(permission);
            }

        }
        return noPersions;
    }

    /**
     * 请求权限返回结果
     *
     * @param requestCode
     * @param grantResults
     * @return
     */
    public static boolean requstPerssionsResult(int requestCode, int[] grantResults) {
        if (requestCode == PERMISIONCODE) {
            for (int i = 0; i < grantResults.length; i++) {
                int grantResult = grantResults[i];
                if (grantResult == PackageManager.PERMISSION_DENIED) {
                    return false;
                }
            }

        }
        return true;
    }
}
