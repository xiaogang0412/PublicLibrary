package com.polyclinic.library.utils;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;

import androidx.core.content.FileProvider;

import java.io.File;

/**
 * @author Lxg
 * @create 2020/5/20
 * @Describe
 */
public class DownLoadManagerUtils {
    private long id;
    DownloadManager downloadManager;
    private Context context;
    private String absolutePath;
    private String path;
    private File file;

    public void downLoad(String url, String title, String path, String des,
                         Activity activity) {
        this.context = activity;
        this.path = path;
        file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                "kangyang1.apk");
        absolutePath = file.getAbsolutePath();
        downloadManager = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
        String downUrl = url;// 下载的地址 URL
        if (!TextUtils.isEmpty(downUrl)) {
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downUrl));
            Uri uri = Uri.fromFile(file);
            request.setDestinationUri(uri);
            request.setTitle(title);// 通知栏的标题
            request.setDescription(des);// 通知栏的内容
            request.addRequestHeader("Content-Type", "text/xml");// 添加 请求头
            id = downloadManager.enqueue(request);// 开始下载
        } else {
            ToastUtils.showToast("请开通储存权限");
        }
        //注册广播接收者，监听下载状态
        context.registerReceiver(receiver,
                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    //检查下载状态
    private void checkStatus() {
        DownloadManager.Query query = new DownloadManager.Query();
        //通过下载的id查找
        query.setFilterById(id);
        Cursor cursor = downloadManager.query(query);
        if (cursor.moveToFirst()) {
            int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch (status) {
                //下载暂停
                case DownloadManager.STATUS_PAUSED:
                    break;
                //下载延迟
                case DownloadManager.STATUS_PENDING:
                    break;
                //正在下载
                case DownloadManager.STATUS_RUNNING:
                    break;
                //下载完成
                case DownloadManager.STATUS_SUCCESSFUL:
                    //下载完成安装APK
                    installAPK();
                    cursor.close();
                    break;
                //下载失败
                case DownloadManager.STATUS_FAILED:
                    ToastUtils.showToast("下载失败");
                    cursor.close();
                    context.unregisterReceiver(receiver);
                    break;
            }
        }
    }

    //广播监听下载的各个状态
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            checkStatus();
        }
    };

    private void installAPK() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        // 由于没有在Activity环境下启动Activity,设置下面的标签
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //Android 7.0以上要使用FileProvider
        if (Build.VERSION.SDK_INT >= 24) {
            File file = (new File(absolutePath));
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            Uri apkUri = FileProvider.getUriForFile(context, "com.polyclinic.kangyang.fileprovider",
                    file);

            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory()
                            , path)),
                    "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }
}
