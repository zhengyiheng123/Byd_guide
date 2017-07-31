package com.example.asus.xyd_order.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseViewHolder;
import com.example.asus.xyd_order.net.result.RestaurantDetailsBean;

/**
 * Created by Zheng on 2017/2/27.
 */
public class TuancanHolder extends BaseViewHolder<RestaurantDetailsBean.SingleMealBean> {

    private TextView tv_price,tv_num,tv_meal_name;

    @Override
    public BaseViewHolder onCreateViewHolder(View v) {
        tv_price = (TextView) v.findViewById(R.id.tv_price);
        tv_num= (TextView) v.findViewById(R.id.tv_num);
        tv_meal_name= (TextView) v.findViewById(R.id.tv_meal_name);
        return this;
    }

    @Override
    public void onBindViewHolder(Context context, RestaurantDetailsBean.SingleMealBean s) {
        tv_price.setText(s.getMeal_price()+"元");
        tv_meal_name.setText(s.getMeal_name());
        tv_num.setText(s.getNums()+"份");
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_multiple_details_child;
    }
}
