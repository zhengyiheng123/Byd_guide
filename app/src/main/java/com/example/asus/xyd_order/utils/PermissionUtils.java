package com.example.asus.xyd_order.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

/**
 * Created by Zheng on 2017/7/31.
 */

public class PermissionUtils {
    public static final int REQUEST_WRITE_EXTERNAL_STORAGE = 1;//请求照片、媒体内容和文件
    public static final int REQUEST_READ_PHONE_STATE = 2;//打电话和管理通话
    public static final int REQUEST_CAMERA = 3;//拍照和录制视频
    public static final int FINE_LOCATION =4; //位置
    public static final int CONTACTS =5;//通讯录
    public static final int SMS =6;//短信功能

    public static final int success=1;//请求权限成功
    public static final int failed=0;//请求权限失败

    public static void show(Handler handler, int flag, int arg1) {
        Message msg=Message.obtain();
        msg.what=flag;
        msg.arg1=arg1;
        handler.sendMessage(msg);
        Log.e("PermissionUtils...","....成功拥有权限...."+flag);
    }
    //请求照片、媒体内容和文件
    public static void checkWriteExternalStorage(Context context, Handler handler) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_EXTERNAL_STORAGE);
        } else {
            //已经取得权限
            show(handler,REQUEST_WRITE_EXTERNAL_STORAGE,success);
        }
    }
    //打电话和管理通话
    public static void checkIMEI(Context context,Handler handler) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_PHONE_STATE},
                    REQUEST_READ_PHONE_STATE);
        } else {
            //已经取得权限
            show(handler, REQUEST_READ_PHONE_STATE,success);
        }
    }
    //拍照和录制视频
    public static void checkCamera(Context context,Handler handler) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA);
        } else {
            //已经取得权限
            show(handler, REQUEST_CAMERA,success);
        }
    }
    //位置
    public static void checkPlace(Context context,Handler handler) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    FINE_LOCATION);
        } else {
            //已经取得权限
            show(handler, FINE_LOCATION,success);
        }
    }
    //通讯录
    public static void checkContacts(Context context,Handler handler) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.GET_ACCOUNTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.GET_ACCOUNTS},
                    CONTACTS);
        } else {
            //已经取得权限
            show(handler,CONTACTS,success);
        }
    }
    //短信功能
    public static void checkSms(Context context,Handler handler) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.RECEIVE_SMS},
                    SMS);
        } else {
            //已经取得权限
            show(handler,SMS,success);
        }
    }
}
