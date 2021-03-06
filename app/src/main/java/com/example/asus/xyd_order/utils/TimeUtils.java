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
    /*将字符串转为时间戳*/
    public static long getStringToDate(String time) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try{
            date = sf.parse(time);
        } catch(ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
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
    * 将时间转换为时间戳
    */
    public static String dateToStampssss(String ssss) throws ParseException {
        String res;
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH");
        Date date = simpleDateFormat.parse(ssss);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }
    /*
* 将时间转换为时间戳
*/
    public static String dateToStampsssss(String ssss) throws ParseException {
        String res;
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
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
    public static String stampToDateSdemand(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH");
        long lt = new Long(s);
        Date date = new Date(lt*1000L);
        res = simpleDateFormat.format(date);
        return res;
    }
    /*
* 将时间戳转换为时间
*/
    public static String stampToDateSdemand1(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH");
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
    public static String getTime(){

        long time=System.currentTimeMillis()/1000;//获取系统时间的10位的时间戳

        String  str=String.valueOf(time);

        return str;

    }
}