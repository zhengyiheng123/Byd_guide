package com.example.asus.xyd_order.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.example.asus.xyd_order.net.result.CityListBean;
import com.example.asus.xyd_order.net.result.PushEntity;
import com.example.asus.xyd_order.net.result.RegionsBean;
import com.example.asus.xyd_order.utils.NetworkState;
import com.example.asus.xyd_order.utils.SharedPreferenceUtils;
import com.example.asus.xyd_order.utils.ToastUtils;

import java.util.LinkedList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by ASUS on 2017/2/15.
 */
public class APP extends Application {
    private static APP myApplication = null;
    public static String REGION_NAME="region_name";
    public static String REGION_ID="region_id";
    public static APP getApplication() {
        return myApplication;
    }

    private static Context ctx;

    public static Context getCtx() {
        return ctx;
    }

    public RegionsBean cityBean;

    public RegionsBean getCityBean() {
        String region_name= (String) SharedPreferenceUtils.getParam(getApplicationContext(),REGION_NAME,"");
        int region_id= (int) SharedPreferenceUtils.getParam(getApplicationContext(),REGION_ID,-1);
        if (cityBean==null){
            cityBean=new RegionsBean();
        }
        cityBean.setOriginal_name(region_name);
        cityBean.setRegion_id(region_id);
        if (region_id == -1){
            return null;
        }else {
            return cityBean;
        }
    }

    public  void setCityBean(RegionsBean cityBean) {
        if (cityBean==null){
            SharedPreferenceUtils.setParam(getApplicationContext(),REGION_NAME,"");
            SharedPreferenceUtils.setParam(getApplicationContext(),REGION_ID,-1);
        }else {
            SharedPreferenceUtils.setParam(getApplicationContext(),REGION_NAME,cityBean.getOriginal_name());
            SharedPreferenceUtils.setParam(getApplicationContext(),REGION_ID,cityBean.getRegion_id());
        }

    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
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
