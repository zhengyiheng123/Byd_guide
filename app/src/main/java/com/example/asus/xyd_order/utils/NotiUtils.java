package com.example.asus.xyd_order.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.activity.Activity_Attractions_order_Successed;
import com.example.asus.xyd_order.activity.Activity_Edit_Release;
import com.example.asus.xyd_order.activity.Activity_News;
import com.example.asus.xyd_order.activity.AttractionDetailsNomal;
import com.example.asus.xyd_order.activity.AttractionsOrderSuccessActivity;
import com.example.asus.xyd_order.activity.ConfirmOrderActivity;
import com.example.asus.xyd_order.activity.MyOrderNews_Activity;
import com.example.asus.xyd_order.app.APP;
import com.example.asus.xyd_order.eventbus.PushData;
import com.example.asus.xyd_order.net.result.PushEntity;

import org.greenrobot.eventbus.EventBus;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Zheng on 2017/7/24.
 */

public class NotiUtils {

    private static Intent intent;
    public static String PUSH_DATA="push_data";
    public static String ORDER_ID="ord_id";
    public static String SCENE_ID="scene_id";


    public static void showXuanGua(Context context, PushEntity entity){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);//创建NotificationCompat.Builder
        if (entity.getOrd_type().equals("-1")){
            //需求订单
            intent=new Intent(context, Activity_Edit_Release.class);
            Bundle bundle=new Bundle();
            bundle.putSerializable(PUSH_DATA,entity);
            intent.putExtras(bundle);
            intent.setAction(""+System.currentTimeMillis());
//            Log.e("zyh","订单号："+entity.getOrd_id()+" ,订单状态："+entity.getOrd_status());
            EventBus.getDefault().post(new PushData(entity));
        }else if (entity.getOrd_type().equals("1")){
            //餐饮订单
            intent = new Intent(context, ConfirmOrderActivity.class);
            intent.putExtra(ORDER_ID,entity.getOrd_id());
            intent.setAction(""+System.currentTimeMillis());
        }else if (entity.getOrd_type().equals("5")){
            //普通景区
            intent = new Intent(context, Activity_Attractions_order_Successed.class);
            intent.putExtra(ORDER_ID,entity.getOrd_id());
            intent.setAction(""+System.currentTimeMillis());
        }else if (entity.getOrd_type().equals("6")){
            //表演类
            intent = new Intent(context, AttractionsOrderSuccessActivity.class);
            intent.putExtra(ORDER_ID,entity.getOrd_id());
            intent.setAction(""+System.currentTimeMillis());
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
