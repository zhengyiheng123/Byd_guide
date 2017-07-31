package com.example.asus.xyd_order.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseViewHolder;
import com.example.asus.xyd_order.net.result.BaseTicketRouteBean;

/**
 * Created by Zheng on 2017/7/18.
 */

public class NotAddAndRelease_Holder extends BaseViewHolder<BaseTicketRouteBean.TicketsBean> {

    private TextView tv_count,tv_ticket_name;

    @Override
    public BaseViewHolder onCreateViewHolder(View v) {
        tv_count = (TextView) v.findViewById(R.id.tv_count);
        tv_ticket_name= (TextView) v.findViewById(R.id.tv_ticket_name);
        return this;
    }

    @Override
    public void onBindViewHolder(Context context, BaseTicketRouteBean.TicketsBean ticketsBean) {
        tv_count.setText(ticketsBean.getNums()+"张");
        tv_ticket_name.setText(ticketsBean.getTicket_type());
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_not_add;
    }
}
