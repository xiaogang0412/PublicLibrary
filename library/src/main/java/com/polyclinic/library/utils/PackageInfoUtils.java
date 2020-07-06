package com.polyclinic.library.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Lxg
 * @create 2020/3/16
 * @Describe
 */
public class PackageInfoUtils {

    private static PackageManager getPackageManager(Activity activity){
        PackageManager packageManager = activity.getPackageManager();
        return packageManager;
    }
    public static Drawable getApplicationIcon(Activity activity){
        Drawable applicationIcon = getApplicationIcon(activity);
        return applicationIcon;
    }
    public static boolean isExitApp(Activity activity, String packageName) {
        List<PackageInfo> installedPackages = getPackageManager(activity).getInstalledPackages(
                PackageManager.CERT_INPUT_RAW_X509);

        if (installedPackages != null) {
            for (int i = 0; i < installedPackages.size(); i++) {
                PackageInfo packageInfo = installedPackages.get(i);
                if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                    if (TextUtils.equals(installedPackages.get(i).packageName, packageName)) {
                        return true;
                    }
                }
            }
        }
        return false;

    }

    private void launchApp(String packname, Context context, Map<String, String> map) {
        Intent intent =getPackageManager((Activity) context).getLaunchIntentForPackage(packname);
        if (intent != null) {
            if (map != null) {
                Set<String> strings = map.keySet();
                while (strings.iterator().hasNext()) {
                    String next = strings.iterator().next();
                    String value = map.get(next);
                    intent.putExtra(next, value);
                }
            }
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "没有安装这个app", Toast.LENGTH_LONG).show();
        }
    }

}
