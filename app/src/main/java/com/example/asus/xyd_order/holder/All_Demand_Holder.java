package com.example.asus.xyd_order.holder;

import android.content.Context;
import android.view.View;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseViewHolder;

/**
 * Created by Zheng on 2017/3/13.
 */
public class All_Demand_Holder extends BaseViewHolder<String> {
    @Override
    public BaseViewHolder onCreateViewHolder(View v) {
        return this;
    }

    @Override
    public void onBindViewHolder(Context context, String s) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.item_all_demand;
    }

}
