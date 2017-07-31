package com.example.asus.xyd_order.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseViewHolder;
import com.example.asus.xyd_order.net.result.BaseTicketBean;
import com.example.asus.xyd_order.net.result.NomalOrderSuccess;

/**
 * Created by Zheng on 2017/7/17.
 */

public class TicketItemHolder extends BaseViewHolder<BaseTicketBean> {

    private TextView tv_ticket_name,tv_count;

    @Override
    public BaseViewHolder onCreateViewHolder(View v) {
        tv_ticket_name = (TextView) v.findViewById(R.id.tv_ticket_name);
        tv_count= (TextView) v.findViewById(R.id.tv_count);
        return this;
    }

    @Override
    public void onBindViewHolder(Context context, BaseTicketBean nomalOrderSuccess) {
        tv_ticket_name.setText(nomalOrderSuccess.getTicket_type());
        tv_count.setText(nomalOrderSuccess.getNums()+"张");
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_nomal_ticket;
    }
}
