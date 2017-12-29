package com.example.asus.xyd_order.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.example.asus.xyd_order.activity.ZhongCanActivity;
import com.example.asus.xyd_order.utils.common.Permission;
import com.example.asus.xyd_order.utils.common.PermissionManager;
import com.example.asus.xyd_order.utils.common.PermissionResult;

import java.util.List;

import cn.leo.photopicker.pick.PermissionUtil;
import retrofit2.http.POST;

/**
 * Created by Zheng on 2017/7/28.
 */

public class LocationUtils {

    private static LocationManager locationManager;
    private static String locationProvider;
    public class LandL{
        public LandL(Double longitude, Double latitude) {
            Longitude = longitude;
            Latitude = latitude;
        }

        public Double Longitude;
        public Double Latitude;
    }

    public LandL location(Activity activity) {
        boolean isPermission= PermissionUtil.checkPremission(activity,Manifest.permission.ACCESS_FINE_LOCATION);
        if (!isPermission){
            PermissionManager.with(activity).request(new PermissionManager.Callback() {
                @Override
                public void call(PermissionResult result) {

                }
            },Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION);
        }else {
            locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
            List<String> providers = locationManager.getProviders(true);

            if (providers.contains(LocationManager.GPS_PROVIDER)) {
                //如果是GPS
                locationProvider = LocationManager.GPS_PROVIDER;
            } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
                //如果是Network
                locationProvider = LocationManager.NETWORK_PROVIDER;
            } else {
                AlertDialog.Builder builder=new AlertDialog.Builder(activity);
                builder.setCancelable(false);
                builder.setTitle("提示");
                builder.setMessage("请打开定位服务");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        activity.finish();
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent locationIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        activity.startActivity(locationIntent);
                    }
                });
                builder.show();
                return null;
            }
            Location location = locationManager.getLastKnownLocation(locationProvider);
            if (location != null) {
                //不为空,显示地理位置经纬度
                if (location!=null){
                    PositionUtil.Gps gps= PositionUtil.gcj02_To_Bd09(location.getLatitude(),location.getLongitude());
                    return  new LandL(gps.getWgLon(),gps.getWgLat());
                }
                return null;
            }else {
                locationProvider= LocationManager.NETWORK_PROVIDER;
                location=locationManager.getLastKnownLocation(locationProvider);
                if (location!=null){
                    PositionUtil.Gps gps= PositionUtil.gcj02_To_Bd09(location.getLatitude(),location.getLongitude());
                    return  new LandL(gps.getWgLon(),gps.getWgLat());
                }
                return null;
            }
        }
        return null;
    }
}
