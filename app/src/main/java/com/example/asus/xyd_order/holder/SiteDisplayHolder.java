package com.example.asus.xyd_order.holder;

import android.content.Context;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseArrayAdapter;
import com.example.asus.xyd_order.base.BaseViewHolder;
import com.example.asus.xyd_order.net.result.BaseTicketBean;
import com.example.asus.xyd_order.net.result.BaseTicketRouteBean;
import com.example.asus.xyd_order.ui.MyListView;

import java.util.List;

/**
 * Created by Zheng on 2017/7/17.
 */

public class SiteDisplayHolder extends BaseViewHolder<BaseTicketRouteBean> {

    private TextView tv_ticket_name;
    private MyListView lv_item_site;
    private BaseArrayAdapter adapter;
    private List<BaseTicketRouteBean.TicketsBean> beanList;

    @Override
    public BaseViewHolder onCreateViewHolder(View v) {
        tv_ticket_name = (TextView) v.findViewById(R.id.tv_ticket_name);
        lv_item_site = (MyListView) v.findViewById(R.id.lv_item_site);
        return this;
    }

    @Override
    public void onBindViewHolder(Context context, BaseTicketRouteBean s) {
        tv_ticket_name.setText(s.getTicket_name());
        beanList = s.getTickets();
        adapter = new BaseArrayAdapter(context, new BaseArrayAdapter.OnCreateViewHolderListener() {
            @Override
            public Object onCreateViewHolder() {
                return new Item_SiteHolder(new Item_SiteHolder.CountTotalCount() {
                    @Override
                    public void countTotal() {
                        //计算总数量
                        int totalCount=0;
                        for (int i=0;i<beanList.size();i++){
                            totalCount=totalCount+beanList.get(i).getNums();
                        }
                        s.setAllCount(totalCount);
                    }
                });
            }
        },beanList);
        lv_item_site.setAdapter(adapter);

    }

    @Override
    public int getLayoutId() {
        return R.layout.item_site_listview;
    }
}
