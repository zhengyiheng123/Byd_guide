package com.example.asus.xyd_order.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.NotificationCompat;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.activity.Activity_News;
import com.example.asus.xyd_order.activity.MyOrderNews_Activity;
import com.example.asus.xyd_order.net.result.PushEntity;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Zheng on 2017/7/24.
 */

public class NotiUtils {

    private static Intent intent;

    public static void showXuanGua(Context context, PushEntity entity){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);//创建NotificationCompat.Builder
        if (entity.getOrd_type().equals("-1")){
            //需求订单
            intent = new Intent(context, MyOrderNews_Activity.class);
        }else if (entity.getOrd_type().equals("1")){
            intent = new Intent(context, Activity_News.class);
            //餐饮订单
        }else if (entity.getOrd_type().equals("5")){
            intent = new Intent(context, Activity_News.class);
            //普通景区
        }else if (entity.getOrd_type().equals("6")){
            intent = new Intent(context, Activity_News.class);
            //表演类
        }else if (entity.getOrd_type().equals("7")){
            intent = new Intent(context, Activity_News.class);
            //火车游船类
        }
        PendingIntent pendingIntent1 = PendingIntent.getActivity(context, 0, intent, 0);
        builder.setContentIntent(pendingIntent1);
        //点击跳转的意图
        Notification notification= builder.build();
        notification.iconLevel=1000;
        //通知显示时间
        notification.when=System.currentTimeMillis();

        //设置通知小图标
        builder.setSmallIcon(R.drawable.ic_call_phone);
        //设置通知大图标
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_calender));

        //是否可取消
        builder.setAutoCancel(true);
        //铃声和震动
        builder.setDefaults(Notification.DEFAULT_ALL);

        //标题
        builder.setContentTitle("帮预定");
        //内容
        //上吊式悬浮
        Intent push = new Intent();
        push.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent2 = PendingIntent.getActivity(
                context, 0, push, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentText(entity.getMsg())
                .setFullScreenIntent(pendingIntent2, true);
        int requestCode=(int)System.currentTimeMillis();
        NotificationManager messageNotificatioManager= (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        messageNotificatioManager.notify(requestCode, builder.getNotification());
    }
}
