package com.example.asus.xyd_order.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseViewHolder;
import com.example.asus.xyd_order.net.result.CityListBean;
import com.example.asus.xyd_order.net.result.RegionsBean;

/**
 * Created by Zheng on 2017/3/3.
 */
public class CountryHolder extends BaseViewHolder<RegionsBean> {

    private TextView tv_month;

    @Override
    public BaseViewHolder onCreateViewHolder(View v) {
        tv_month = (TextView) v.findViewById(R.id.tv_month);
        return this;
    }

    @Override
    public void onBindViewHolder(Context context, RegionsBean s) {
        tv_month.setText(s.getRegion_name());
    }

    @Override
    public int getLayoutId() {
        return R.layout.header_overview;
    }
}
