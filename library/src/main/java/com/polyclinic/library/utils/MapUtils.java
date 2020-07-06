package com.polyclinic.library.utils;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
/**
 * @author Lxg
 * @create 2020/6/16
 * @Describe
 */
public class MapUtils {
    private static String BADAIDU = "com.baidu.BaiduMap";
    private static String GAODE = "com.autonavi.minimap";
    private static String TX = "com.tencent.map";

    public static void openMap(Context context, String lat, String longiute,
                               String address) {
        if (TextUtils.isEmpty(lat) || TextUtils.isEmpty(longiute)
                || TextUtils.isEmpty(address)) {
            ToastUtils.showToast("地址不能为空");
            return;
        }
        if (ActivityUtils.isAvailable(BADAIDU, context)) {
            openBaiDu(context, lat, longiute, address);
            return;
        }  if (ActivityUtils.isAvailable(GAODE, context)) {
            openGaoDe(context, lat, longiute, address);
            return;
        }  if (ActivityUtils.isAvailable(TX, context)) {

        }
    }

    public static void openBaiDu(Context context, String lat,
                                 String longiute, String address) {
        Uri uri =
                Uri.parse("baidumap://map/direction?destination=latlng:"+lat+","
                        + longiute + "|name:" + address + "&mode=driving");
        context.startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    public static void openGaoDe(Context context, String lat, String longiute, String address) {
        Uri uri =
                Uri.parse("amapuri://route/plan/?dlat=" + lat + "&dlon=" +
                        longiute + "&dname=" + address + "&dev=0&t=0");
        context.startActivity(new Intent(Intent.ACTION_VIEW, uri));

    }
}
