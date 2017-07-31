package com.example.asus.xyd_order.utils;

import android.graphics.Bitmap;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Zheng on 2017/3/7.
 */
public class CompressUtil {
    /**
     * bitmap转成file
     * @param bitmap
     * @param fileName
     */
    public static void saveBitmapFile(Bitmap bitmap, String fileName) {
        File file = new File(fileName);//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
