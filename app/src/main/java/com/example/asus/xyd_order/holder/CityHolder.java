package com.example.asus.xyd_order.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseViewHolder;
import com.example.asus.xyd_order.net.result.CityListBean;

/**
 * Created by Zheng on 2017/5/2.
 */

public class CityHolder extends BaseViewHolder<CityListBean.RegionsBean> {

    private TextView lv_city;

    @Override
    public BaseViewHolder onCreateViewHolder(View v) {
        lv_city = (TextView) v.findViewById(R.id.tv_city);
        return this;
    }

    @Override
    public void onBindViewHolder(Context context, CityListBean.RegionsBean s) {
        lv_city.setText(s.getRegion_name());
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_city;
    }
}
