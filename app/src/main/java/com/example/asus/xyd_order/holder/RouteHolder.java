package com.example.asus.xyd_order.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseViewHolder;
import com.example.asus.xyd_order.net.result.RouteResult;

/**
 * Created by Zheng on 2017/8/9.
 */

public class RouteHolder extends BaseViewHolder<RouteResult.RoutesBean> {

    private TextView tv_station;

    @Override
    public BaseViewHolder onCreateViewHolder(View v) {
        tv_station = (TextView) v.findViewById(android.R.id.text1);
        return this;
    }

    @Override
    public void onBindViewHolder(Context context, RouteResult.RoutesBean routesBean) {
        tv_station.setTextColor(context.getResources().getColor(R.color.material_grey_700));
        tv_station.setText(routesBean.getRoute_name());
    }

    @Override
    public int getLayoutId() {
        return android.R.layout.simple_list_item_1;
    }
}
