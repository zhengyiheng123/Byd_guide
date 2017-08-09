package com.example.asus.xyd_order.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseViewHolder;
import com.example.asus.xyd_order.net.result.QueryData;

/**
 * Created by Zheng on 2017/5/5.
 */

public class Ticket_List_Holder extends BaseViewHolder<QueryData.TicketsBean> {
    TextView tv_price,tv_end_time,tv_spend,tv_start,tv_start_time,tv_train_name,tv_end_station;
    @Override
    public BaseViewHolder onCreateViewHolder(View v) {
        tv_price= (TextView) v.findViewById(R.id.tv_price);
        tv_end_time= (TextView) v.findViewById(R.id.tv_end_time);
        tv_spend= (TextView) v.findViewById(R.id.tv_spend);
        tv_start= (TextView) v.findViewById(R.id.tv_start);
        tv_start_time= (TextView) v.findViewById(R.id.tv_start_time);
//        tv_train_name= (TextView) v.findViewById(R.id.tv_train_name);
        tv_end_station= (TextView) v.findViewById(R.id.tv_end_station);
        return this;
    }

    @Override
    public void onBindViewHolder(Context context, QueryData.TicketsBean s) {
        tv_price.setText(s.getGroup_price()+"起");
        tv_end_station.setText(s.getEnd_station());
        tv_end_time.setText(s.getTime_end()+"到达");
        tv_spend.setText(s.getTime_spend());
        tv_start_time.setText(s.getTime_start()+"出发");
        tv_start.setText(s.getStart_station());
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_ticket;
    }


}
