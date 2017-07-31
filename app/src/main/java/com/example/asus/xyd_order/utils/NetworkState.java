package com.example.asus.xyd_order.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

public class NetworkState {

	
	public static final String NetType_null="null";
	public static final String NetType_wifi="wifi";
	public static final String NetType_2g="2g";
	public static final String NetType_3g="3g";
	public static final String NetType_4g="4g";
	
	
	
	/**
     * 检查当前网络是否可用
     * 
     * @param context
     * @return
     */
    
    public static boolean isNetworkAvailable(Context context)
    {
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        
        if (connectivityManager == null)
        {
            return false;
        }
        else
        {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
            
            if (networkInfo != null && networkInfo.length > 0)
            {
                for (int i = 0; i < networkInfo.length; i++)
                {
                    System.out.println(i + "===状态===" + networkInfo[i].getState());
                    System.out.println(i + "===类型===" + networkInfo[i].getTypeName());
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
	
    
    /**
     * 检查当前网络哪一种网络
     * 
     * @param context
     * @return
     */
    public static String getCurrentNetType(Context context) {
    	String type = "";
    	ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    	NetworkInfo info = cm.getActiveNetworkInfo();
    	if (info == null) {
    	type =NetType_null;
    	} else if (info.getType() == ConnectivityManager.TYPE_WIFI) {
    	type = NetType_wifi;
    	} else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
    	int subType = info.getSubtype();
    	if (subType == TelephonyManager.NETWORK_TYPE_CDMA || subType == TelephonyManager.NETWORK_TYPE_GPRS
    	|| subType == TelephonyManager.NETWORK_TYPE_EDGE) {
    	type = NetType_2g;
    	} else if (subType == TelephonyManager.NETWORK_TYPE_UMTS || subType == TelephonyManager.NETWORK_TYPE_HSDPA
    	|| subType == TelephonyManager.NETWORK_TYPE_EVDO_A || subType == TelephonyManager.NETWORK_TYPE_EVDO_0
    	|| subType == TelephonyManager.NETWORK_TYPE_EVDO_B) {
    	type =NetType_3g;
    	} else if (subType == TelephonyManager.NETWORK_TYPE_LTE) {// LTE是3g到4g的过渡，是3.9G的全球标准
    	type =NetType_4g;
    	}
    	}
    	return type;
    	}
    
    
//    相关类型列表：
//
//    1、NETWORK_TYPE_1xRTT： 常量值：7 网络类型：1xRTT
//
//    2、NETWORK_TYPE_CDMA ： 常量值：4 网络类型： CDMA （电信2g）
//
//    3、NETWORK_TYPE_EDGE： 常量值：2 网络类型：EDGE（移动2g）
//
//    4、NETWORK_TYPE_EHRPD： 常量值：14 网络类型：eHRPD
//
//    5、NETWORK_TYPE_EVDO_0： 常量值：5 网络类型：EVDO 版本0.（电信3g）
//
//    6、NETWORK_TYPE_EVDO_A： 常量值：6 网络类型：EVDO 版本A （电信3g）
//
//    7、NETWORK_TYPE_EVDO_B： 常量值：12 网络类型：EVDO 版本B（电信3g）
//
//    8、NETWORK_TYPE_GPRS： 常量值：1 网络类型：GPRS （联通2g）
//
//    9、NETWORK_TYPE_HSDPA： 常量值：8 网络类型：HSDPA（联通3g）
//
//    10、NETWORK_TYPE_HSPA： 常量值：10 网络类型：HSPA
//
//    11、NETWORK_TYPE_HSPAP： 常量值：15 网络类型：HSPA+
//
//    12、NETWORK_TYPE_HSUPA： 常量值：9 网络类型：HSUPA
//
//    13、NETWORK_TYPE_IDEN： 常量值：11 网络类型：iDen
//
//    14、NETWORK_TYPE_LTE： 常量值：13 网络类型：LTE(3g到4g的一个过渡，称为准4g)
//
//    15、NETWORK_TYPE_UMTS： 常量值：3 网络类型：UMTS（联通3g）
//
//    16、NETWORK_TYPE_UNKNOWN：常量值：0 网络类型：未知
    
    /**
     * 获取手机ip
     * 
     * @param context
     * @return
     */
    public static String getIp(Activity activity){
    	Context context = activity.getApplicationContext();
    	//获取wifi服务
    	WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    	//判断wifi是否开启
    	if (!wifiManager.isWifiEnabled()) {
    	wifiManager.setWifiEnabled(true);
    	}
    	WifiInfo wifiInfo = wifiManager.getConnectionInfo();
    	int ipAddress = wifiInfo.getIpAddress();
    	String ip = intToIp(ipAddress);
    	return ip;
    	}

    	private static String intToIp(int i) {

    	return (i & 0xFF ) + "." +
    	((i >> 8 ) & 0xFF) + "." +
    	((i >> 16 ) & 0xFF) + "." +
    	( i >> 24 & 0xFF) ;
    	}
	public static void openSetting(Context context) {
//        Intent intent = new Intent("/");
//        ComponentName cm = new ComponentName("com.android.settings",
//                "com.android.settings.WirelessSettings");
//        intent.setComponent(cm);
//        intent.setAction("android.intent.action.VIEW");
//        activity.startActivityForResult(intent, 0);
		if (android.os.Build.VERSION.SDK_INT > 10) {
			//3.0以上打开设置界面，也可以直接用ACTION_WIRELESS_SETTINGS打开到wifi界面
			context.startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
		} else {
			context.startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
		}
	}
    	
    	
}
