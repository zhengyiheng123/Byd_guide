package com.example.asus.xyd_order.jpush_receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;

import com.example.asus.xyd_order.activity.Activity_Attractions_order_Successed;
import com.example.asus.xyd_order.activity.Activity_Edit_Release;
import com.example.asus.xyd_order.activity.Activity_News;
import com.example.asus.xyd_order.activity.AttractionsOrderSuccessActivity;
import com.example.asus.xyd_order.activity.ConfirmOrderActivity;
import com.example.asus.xyd_order.eventbus.PushData;
import com.example.asus.xyd_order.fragment.MyOrdersActivity;
import com.example.asus.xyd_order.net.result.PushEntity;
import com.example.asus.xyd_order.utils.NotiUtils;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
	private static final String TAG = "JIGUANG-Example";
	private PushEntity entity;
	public static String PUSH_DATA="push_data";
	public static String ORDER_ID="ord_id";
	public static String SCENE_ID="scene_id";
	public static String ORD_STATUS="ord_status";
	@Override
	public void onReceive(Context context, Intent intent) {
		try {
			Bundle bundle = intent.getExtras();
			if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
				//接收到通知
			 } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
				//点击了通知
				Gson gson=new Gson();
				entity = gson.fromJson(bundle.getString(JPushInterface.EXTRA_EXTRA),PushEntity.class);
				if (entity.getOrd_type().equals("-1")){
					//需求订单
					intent=new Intent(context, Activity_Edit_Release.class);
					intent.putExtra("dmd_id",entity.getOrd_id());
					intent.putExtra(ORD_STATUS,entity.getOrd_status());
					EventBus.getDefault().post(new PushData(entity));
				}else if (entity.getOrd_type().equals("1")){
					//餐饮订单
					intent = new Intent(context, MyOrdersActivity.class);
//					intent.putExtra(ORDER_ID,entity.getOrd_id());
				}else if (entity.getOrd_type().equals("5")){
					//普通景区
					intent = new Intent(context, MyOrdersActivity.class);
//					intent.putExtra(ORDER_ID,entity.getOrd_id());
				}else if (entity.getOrd_type().equals("6")){
					//表演类
					intent = new Intent(context, MyOrdersActivity.class);
//					intent.putExtra(ORDER_ID,entity.getOrd_id());
				}
					intent.setAction(""+System.currentTimeMillis());
				  	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
				   	context.startActivity(intent);
				     //打开自定义的Activity
//				 Intent i = new Intent(context, TestActivity.class);
//				 i.putExtras(bundle);
//				 //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				 i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
//				 context.startActivity(i);
			 }
		} catch (Exception e){
			System.out.print(e);
		}
	}
}
