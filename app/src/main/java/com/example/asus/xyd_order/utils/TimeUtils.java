package com.example.asus.xyd_order.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {

    /*
     * 将时间转换为时间戳
     */
    public static String dateToStamp(String ssss) throws ParseException {
        String res;
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
        Date date = simpleDateFormat.parse(ssss);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }

    /*
    * 将时间转换为时间戳
    */
    public static String dateToStampsss(String ssss) throws ParseException {
        String res;
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        Date date = simpleDateFormat.parse(ssss);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }
    /*
* 将时间戳转换为时间
*/
    public static String stampToDateS(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
        long lt = new Long(s);
        Date date = new Date(lt*1000L);
        res = simpleDateFormat.format(date);
        return res;
    }
    /*
* 将时间戳转换为时间
*/
    public static String stampToDateSs(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        long lt = new Long(s);
        Date date = new Date(lt*1000L);
        res = simpleDateFormat.format(date);
        return res;
    }
    /*
   * 将时间戳转换为时间
   */
    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }
}