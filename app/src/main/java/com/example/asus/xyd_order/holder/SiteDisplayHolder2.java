package com.example.asus.xyd_order.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseArrayAdapter;
import com.example.asus.xyd_order.base.BaseViewHolder;
import com.example.asus.xyd_order.net.result.BaseTicketRouteBean;
import com.example.asus.xyd_order.ui.MyListView;

/**
 * Created by Zheng on 2017/7/18.
 */

public class SiteDisplayHolder2 extends BaseViewHolder<BaseTicketRouteBean> {

    private TextView tv_ticket_name,tv_count;
    private MyListView lv_item_site;
    private BaseArrayAdapter adapter;

    @Override
    public BaseViewHolder onCreateViewHolder(View v) {
        tv_ticket_name = (TextView) v.findViewById(R.id.tv_ticket_name);
        lv_item_site = (MyListView) v.findViewById(R.id.lv_item_site);
        tv_count= (TextView) v.findViewById(R.id.tv_count);
        return this;
    }

    @Override
    public void onBindViewHolder(Context context, BaseTicketRouteBean s) {
        tv_ticket_name.setText(s.getTicket_name());
        tv_count.setText("共"+s.getAllCount()+"张");
        tv_count.setVisibility(View.GONE);
        adapter = new BaseArrayAdapter(context, new BaseArrayAdapter.OnCreateViewHolderListener() {
            @Override
            public Object onCreateViewHolder() {
                return new NotAddAndRelease_Holder();
            }
        },s.getTickets());
        lv_item_site.setAdapter(adapter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_site_listview2;
    }
}
