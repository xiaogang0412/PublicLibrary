package com.polyclinic.library.utils;

import android.app.Activity;

/**
 * @author Lxg
 * @create 2020/5/20
 * @Describe
 */
public class InstallUtils {
    private static final int REQUEST_CODE_UNKNOWN_APP = 10;

    public static void install(String url, String title, String des, String path,
                        Activity activity) {
                DownLoadManagerUtils utils = new DownLoadManagerUtils();
                utils.downLoad(url, title, path, des, activity);

    }


}
