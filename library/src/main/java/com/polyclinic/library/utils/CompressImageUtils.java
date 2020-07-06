package com.polyclinic.library.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * @author Lxg
 * @create 2020/6/10
 * @Describe
 */
public class CompressImageUtils {
    private ComprsssCompleteListener listener;

    public void setListener(ComprsssCompleteListener listener) {
        this.listener = listener;
    }

    public  void  compressImage(final Bitmap image, final int size, final File file) {
        new Thread(){
            @Override
            public void run() {
                super.run();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
                int options = 100;
                if(baos.toByteArray().length/1024>size){
                    while (baos.toByteArray().length / 1024 > size) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
                        baos.reset();//重置baos即清空baos
                        image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
                        options -= 10;//每次都减少10
                    }
                    ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos
                    // 存放到ByteArrayInputStream中
                    Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//
                    int i = readPictureDegree(file.getAbsolutePath());
                    Bitmap bitmap1 = rotateBitmap(bitmap, i);
                    listener.compress(bitmap1);
                }else {
                    listener.compress(image);
                }
            }
        }.start();

    }
    public interface ComprsssCompleteListener{
        void compress(Bitmap bitmap);
    }
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

        return degree;
    }
    private static Bitmap rotateBitmap(Bitmap bitmap, int rotate) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postRotate(rotate);
        return Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
    }
}
