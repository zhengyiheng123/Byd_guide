package com.example.asus.xyd_order.net;

/**
 * Created by xiaotongchao on 2016/8/25.
 * Function :
 */
public class BaseApi {
    public static boolean isDebug = true;
//    public static String baseOnlineUrl="http://helpd.cinyida.com/";
    public static String baseOnlineUrl="http://helpd.longxiangshengwu.com/";

    /**
     * 返回baseurl
     * @return
     */

    public static String getBaseUrl(){
     return baseOnlineUrl;
    }
}
