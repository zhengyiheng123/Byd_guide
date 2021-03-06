package com.example.asus.xyd_order.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseViewHolder;
import com.example.asus.xyd_order.net.result.CityListBean;
import com.example.asus.xyd_order.net.result.RegionsBean;

/**
 * Created by Zheng on 2017/5/2.
 */

public class CityHistoryHolder extends BaseViewHolder<RegionsBean> {

    private TextView lv_city;

    @Override
    public BaseViewHolder onCreateViewHolder(View v) {
        lv_city = (TextView) v.findViewById(android.R.id.text1);
        return this;
    }

    @Override
    public void onBindViewHolder(Context context, RegionsBean s) {
        lv_city.setText(s.getRegion_name());
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_history_item;
    }
}
