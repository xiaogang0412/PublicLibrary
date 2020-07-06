package com.polyclinic.library.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.List;

/**
 * @author Lxg
 * @create 2020/1/11
 * @Describe
 */
public class ActivityUtils {
    /**
     * 私有化，防止被初始化
     */
    private ActivityUtils() {

    }

    public static void startHomeActivity() {
        Intent intent = new Intent(Intent.ACTION_MAIN);

    }

    public static boolean isExsitMianActivity(String name, Context context) {
        boolean flag = false;
        if (name != null) { // 说明系统中存在这个activity
            ActivityManager am =
                    (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> taskInfoList = am.getRunningTasks(100);
            for (ActivityManager.RunningTaskInfo taskInfo : taskInfoList) {
                Log.i("wewewe", taskInfo.baseActivity.getClassName());
                if (taskInfo.baseActivity.getClassName().equals(name)) { // 说明它已经启动了
                    flag = true;
                    break;  //跳出循环，优化效率
                }
            }
        }
        Log.i("weewwe", flag + "");
        return flag;
    }

    public static boolean isAvailable(String mapPackageName, Context context) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(mapPackageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        return packageInfo == null ? false : true;
    }
}
