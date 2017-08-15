package com.example.asus.xyd_order.holder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.activity.Activity_Attractions_order_Successed;
import com.example.asus.xyd_order.activity.Activity_Train_Success;
import com.example.asus.xyd_order.activity.Activity_write_Opinion;
import com.example.asus.xyd_order.activity.AttractionsOrderSuccessActivity;
import com.example.asus.xyd_order.activity.CancelActivity;
import com.example.asus.xyd_order.activity.ConfirmOrderActivity;
import com.example.asus.xyd_order.activity.FefundsActivity;
import com.example.asus.xyd_order.activity.PayActivity;
import com.example.asus.xyd_order.base.BaseViewHolder;
import com.example.asus.xyd_order.fragment.MyOrderDetails;
import com.example.asus.xyd_order.net.BaseApi;
import com.example.asus.xyd_order.net.result.MyOrderBean;
import com.example.asus.xyd_order.utils.TimeUtils;

/**
 * Created by Zheng on 2017/7/20.
 */

public class MyOrder_All_Holder extends BaseViewHolder<MyOrderBean.OrdersBean> implements View.OnClickListener {

    private TextView tv_type,tv_name,tv_ord_num,tv_peer
            ,cancel,modify,store,finished,refunds,comment,pay,cancel_state,
            tv_routename
            ,tv_tel,tv_address,tv_ord_time;
    private ImageView iv_img;
    private MyOrderBean.OrdersBean bean;
    private LinearLayout rl_item;

    @Override
    public BaseViewHolder onCreateViewHolder(View v) {
        tv_tel= (TextView) v.findViewById(R.id.tv_tel);
        tv_routename= (TextView) v.findViewById(R.id.tv_routename);
        tv_address= (TextView) v.findViewById(R.id.tv_address);
        tv_ord_time= (TextView) v.findViewById(R.id.tv_ord_time);
        tv_type = (TextView) v.findViewById(R.id.tv_type);
//        tv_pay_type= (TextView) v.findViewById(R.id.tv_pay_type);
        iv_img= (ImageView) v.findViewById(R.id.iv_img);
        tv_name= (TextView) v.findViewById(R.id.tv_name);
        tv_ord_num= (TextView) v.findViewById(R.id.tv_ord_num);
        tv_peer= (TextView) v.findViewById(R.id.tv_peer);
        cancel= (TextView) v.findViewById(R.id.cancel);
        modify= (TextView) v.findViewById(R.id.modify);
        store= (TextView) v.findViewById(R.id.store);
        finished= (TextView) v.findViewById(R.id.finished);
        refunds= (TextView) v.findViewById(R.id.refunds);
        comment= (TextView) v.findViewById(R.id.comment);
        pay= (TextView) v.findViewById(R.id.pay);
        cancel_state= (TextView) v.findViewById(R.id.cancel_state);
        rl_item = (LinearLayout) v.findViewById(R.id.rl_item);
        return this;
    }
    Context context;
    @Override
    public void onBindViewHolder(Context context, MyOrderBean.OrdersBean s) {
        rl_item.setOnClickListener(this);
        tv_tel.setText("联系方式："+s.getMobile());
        tv_routename.setText(s.getRoute_name());
        tv_address.setText("消费地点："+s.getAddress());
        tv_ord_time.setText("下单时间："+ TimeUtils.stampToDateSs(s.getAdd_time()+""));
        bean = s;
        this.context=context;
        setGone();
        int ord_type=s.getOrd_type();
        switch (ord_type){
            case 1:
                //餐饮
                tv_type.setText("订单类型：餐饮");
                break;
            case 5:
                //普通景点
                tv_type.setText("订单类型：普通景点");
                break;
            case 6:
                //表演类景点
                tv_type.setText("订单类型：表演类景点");
                break;
            case 7:
                //游船类
                tv_type.setText("订单类型：游船类");
                break;
        }
        Glide.with(context).load(BaseApi.getBaseUrl()+s.getLogo_path()).into(iv_img);
        tv_name.setText(s.getMer_name()+" "+s.getRoute_name());
        tv_peer.setText("总计:"+s.getPrice());
        tv_ord_num.setText("订单号:"+s.getOrd_num());
        int ord_status=s.getOrd_status();
        switch (ord_status){
            case -4://导游申请退款成功
                cancel_state.setText("退款成功");
                cancel_state.setVisibility(View.VISIBLE);
                break;
            case -3://-3|导游申请退款
                cancel_state.setText("退款中");
                cancel_state.setVisibility(View.VISIBLE);
                break;
            case -2:
                //导游取消
                cancel_state.setText("导游取消");
                cancel_state.setVisibility(View.VISIBLE);
                break;
            case -1:
                //商家取消
                cancel_state.setVisibility(View.VISIBLE);
                cancel_state.setText("商家取消");
                break;
            case 0:
                //待接单
                cancel.setVisibility(View.VISIBLE);
                cancel.setOnClickListener(this);
                break;
            case 1:
                //待付款
                cancel.setVisibility(View.VISIBLE);
                pay.setVisibility(View.VISIBLE);
                cancel.setOnClickListener(this);
                pay.setOnClickListener(this);
                break;
            case 2:
                //已付款
                cancel_state.setVisibility(View.VISIBLE);
                cancel_state.setText("已付款");
                refunds.setVisibility(View.VISIBLE);
                refunds.setOnClickListener(this);
                break;
            case 3:
                //已消费
                comment.setVisibility(View.VISIBLE);
                comment.setOnClickListener(this);
                break;
            case 4:
                //已评价
                cancel_state.setVisibility(View.VISIBLE);
                cancel_state.setText("已评价");
                break;
        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.item_myorders2;
    }
    private void setGone(){
        refunds.setVisibility(View.GONE);
            modify.setVisibility(View.GONE);
            finished.setVisibility(View.GONE);
            store.setVisibility(View.GONE);
            cancel.setVisibility(View.GONE);
            comment.setVisibility(View.GONE);
            pay.setVisibility(View.GONE);
            cancel_state.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.cancel:
                intent=new Intent(context, CancelActivity.class);
                intent.putExtra("ord_id",bean.getOrd_id()+"");
                if (bean.getOrd_type()==1){
                    //餐饮
                    intent.putExtra("cancel_type","1");
                }else if (bean.getOrd_type() == 5 || bean.getOrd_type() == 6 || bean.getOrd_type() == 7){
                    //景点
                    intent.putExtra("cancel_type","0");
                }
                context.startActivity(intent);
                break;
            case R.id.pay:
                intent=new Intent(context, PayActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("bean",bean);
                intent.putExtras(bundle);
                context.startActivity(intent);
                break;
            case R.id.comment:
                if (bean.getOrd_type() == 1){
                    //餐饮
                    intent=new Intent(context, Activity_write_Opinion.class);
                    intent.putExtra("ord_id",bean.getOrd_id()+"");
                    intent.putExtra("commentType","1");
                    intent.putExtra("dmd_id",bean.getMer_id()+"");
                    context.startActivity(intent);
                }else if (bean.getOrd_type() == 5 || bean.getOrd_type() == 6 || bean.getOrd_type() == 7){
                    //景点
                    intent=new Intent(context, Activity_write_Opinion.class);
                    intent.putExtra("ord_id",bean.getOrd_id()+"");
                    intent.putExtra("commentType","0");
                    intent.putExtra("dmd_id",bean.getMer_id()+"");
                    context.startActivity(intent);
                }
                break;
            case R.id.rl_item:
                int ord_type=bean.getOrd_type();
                if(ord_type == 1){
                    //餐厅
                    intent=new Intent(context, MyOrderDetails.class);
                    intent.putExtra("ord_status",bean.getOrd_status());
                    intent.putExtra("ord_id",bean.getOrd_id()+"");
                    context.startActivity(intent);
                }else if (ord_type == 5){
                    //普通景点
                    intent=new Intent(context, Activity_Attractions_order_Successed.class);
                    intent.putExtra("ord_id",bean.getOrd_id()+"");
                    context.startActivity(intent);
                }else if (ord_type == 6){
                    //商家景点
                    intent=new Intent(context, AttractionsOrderSuccessActivity.class);
                    intent.putExtra("order_id",bean.getOrd_id()+"");
                    context.startActivity(intent);
                }else if (ord_type == 7){
                    intent=new Intent(context, Activity_Train_Success.class);
                    intent.putExtra("ord_id",bean.getOrd_id()+"");
                    context.startActivity(intent);
                }
                break;
            case R.id.refunds:
                intent=new Intent(context, FefundsActivity.class);
                intent.putExtra("ord_id",bean.getOrd_id()+"");
                intent.putExtra("ord_type",bean.getOrd_type()+"");
                context.startActivity(intent);
                break;
        }
    }
}
