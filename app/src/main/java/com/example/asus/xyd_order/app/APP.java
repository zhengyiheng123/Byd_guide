package com.example.asus.xyd_order.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.example.asus.xyd_order.utils.NetworkState;
import com.example.asus.xyd_order.utils.ToastUtils;

import java.util.LinkedList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by ASUS on 2017/2/15.
 */
public class APP extends Application {
    private static Context ctx;
    public static Context getCtx() {
        return ctx;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);
        if (ctx ==null) {
            ctx = getApplicationContext();
        }
        boolean isNetAvailable= NetworkState.isNetworkAvailable(getApplicationContext());
        if (!isNetAvailable){
            ToastUtils.show(getApplicationContext(),"当前网络连接不可用",0);
        }
    }
}
