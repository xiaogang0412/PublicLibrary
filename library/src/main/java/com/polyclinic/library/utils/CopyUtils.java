package com.polyclinic.library.utils;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

/**
 * @author Lxg
 * @create 2020/3/25
 * @Describe
 */
public class CopyUtils {
    /**
     * 粘贴板copy
     *
     * @param context
     * @param copy
     */
    public static void copy(Context context, String copy) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        //创建ClipData对象
        ClipData clipData = ClipData.newPlainText("copy", copy);
        //添加ClipData对象到剪切板中
        clipboardManager.setPrimaryClip(clipData);
        ToastUtils.showToast("复制成功");
    }

    /**
     * 获取粘贴板数据
     *
     * @param context
     * @return
     */
    public static String getCopy(Context context) {
        try {
            ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData data = cm.getPrimaryClip();
            if (data != null && data.getItemCount() != 0) {
                ClipData.Item item = data.getItemAt(0);
                if (item != null && item.getText() != null) {
                    String content = item.getText().toString();
                    return content + "";
                } else {
                    return "";
                }

            }
        } catch (Exception e) {
        }


        return "";
    }
}
