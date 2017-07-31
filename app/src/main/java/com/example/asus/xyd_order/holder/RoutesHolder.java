package com.example.asus.xyd_order.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseViewHolder;
import com.example.asus.xyd_order.net.result.RouteBean;

/**
 * Created by Zheng on 2017/3/20.
 */
public class RoutesHolder extends BaseViewHolder<RouteBean.RoutesBean> {

    private TextView tv_peer,tv_order,tv_route_name;

    @Override
    public BaseViewHolder onCreateViewHolder(View v) {
        tv_route_name = (TextView) v.findViewById(R.id.tv_route_name);
        tv_peer = (TextView) v.findViewById(R.id.tv_peer);
        tv_order= (TextView) v.findViewById(R.id.tv_route_name);
        return this;
    }

    @Override
    public void onBindViewHolder(Context context, RouteBean.RoutesBean s) {
        tv_route_name.setText(s.getRoute_name());
        tv_peer.setText(s.getPrice()+"/人");
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_routes;
    }
}
