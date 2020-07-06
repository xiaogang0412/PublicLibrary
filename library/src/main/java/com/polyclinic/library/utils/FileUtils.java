package com.polyclinic.library.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.webkit.MimeTypeMap;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author Lxg
 * @create 2020/3/23
 * @Describe
 */
public class FileUtils {
    /**
     * 下载后通知相册
     *
     * @param context
     * @param mCurrentPhotoPath
     */
    public static void sendSavePhoto(Context context, File mCurrentPhotoPath) {
        MediaStore.Images.Media.insertImage(context.getContentResolver(), BitmapFactory.decodeFile(mCurrentPhotoPath.getAbsolutePath()), mCurrentPhotoPath.getName(), null);
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(mCurrentPhotoPath);
        intent.setData(uri);
        context.sendBroadcast(intent);

    }

    /**
     * 将bitmap专成file文件
     *
     * @param bitmap
     * @param filepath
     * @return
     */
    public static File saveBitmapToFile(Bitmap bitmap, String filepath) {
        File file = new File(filepath);//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 将file 转成bitmap
     *
     * @param urlpath
     * @return
     */
    public static Bitmap getFileToBitmap(String urlpath) {

        Bitmap map = null;
        try {
            URL url = new URL(urlpath);
            URLConnection conn = url.openConnection();
            conn.connect();
            InputStream in;
            in = conn.getInputStream();
            map = BitmapFactory.decodeStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static String getAssetFile(Context context, String name) {
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open(name);
            int size = inputStream.available();
            int len = -1;
            byte[] bytes = new byte[size];
            inputStream.read(bytes);
            inputStream.close();
            String string = new String(bytes);
            return string;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static void startVideo(String url, Context context) {
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        Intent mediaIntent = new Intent(Intent.ACTION_VIEW);
        mediaIntent.setDataAndType(Uri.parse(url), mimeType);
        context.startActivity(mediaIntent);
    }
}
