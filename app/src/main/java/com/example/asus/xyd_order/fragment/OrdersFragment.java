package com.example.asus.xyd_order.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.activity.Activity_MyOrdersGeted;
import com.example.asus.xyd_order.activity.History_Activity;
import com.example.asus.xyd_order.base.BaseFragment;
import com.example.asus.xyd_order.utils.ActivityFactory;

/**
 * Created by ASUS on 2017/2/15.
 */
public class OrdersFragment extends BaseFragment {

    private TextView tv_my_demand,tv_myorder,tv_history_copy,tv_mygeted_orders;
    private Intent intent;

    @Override
    public void myOnclick(View view) {
        switch (view.getId()){
            case R.id.tv_my_demand:
                ActivityFactory.gotoMyDemand(context);
                break;
//            case R.id.tv_to_mycollect:
//                ActivityFactory.gotoMyCollect(context);
//                break;
            case R.id.tv_myorder:
                ActivityFactory.gotoMyOrder(context);
                break;
            case R.id.tv_history_copy:
                intent = new Intent(getActivity(), History_Activity.class);
                startActivity(intent);
                break;
            case R.id.tv_mygeted_orders:
                intent=new Intent(getActivity(), Activity_MyOrdersGeted.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void initView(View view) {
        //初始化所有布局
        inializeView(view);
        ImageView iv_back= (ImageView) view.findViewById(R.id.iv_back);
        iv_back.setVisibility(View.GONE);
        TextView tv_title= (TextView) view.findViewById(R.id.tv_title);
        tv_title.setText("订单");
    }

    private void inializeView(View v) {
        tv_my_demand = (TextView) v.findViewById(R.id.tv_my_demand);
        tv_myorder= (TextView) v.findViewById(R.id.tv_myorder);
        tv_history_copy= (TextView) v.findViewById(R.id.tv_history_copy);
        tv_mygeted_orders= (TextView) v.findViewById(R.id.tv_mygeted_orders);
//        tv_to_mycollect= (TextView) v.findViewById(R.id.tv_to_mycollect);
    }

    @Override
    public int getResource() {
        return R.layout.fragment_orders;
    }

    @Override
    public int initData() {
        return STATE_NO_NETWORK;
    }


    @Override
    public void setEvent() {
        tv_myorder.setOnClickListener(this);
        tv_my_demand.setOnClickListener(this);
        tv_history_copy.setOnClickListener(this);
        tv_mygeted_orders.setOnClickListener(this);
    }

}
