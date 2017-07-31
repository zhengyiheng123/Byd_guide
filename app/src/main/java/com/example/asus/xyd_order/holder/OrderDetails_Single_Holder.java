package com.example.asus.xyd_order.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseViewHolder;
import com.example.asus.xyd_order.net.result.Order_Info_Bean;

/**
 * Created by Zheng on 2017/7/21.
 */

public class OrderDetails_Single_Holder extends BaseViewHolder<Order_Info_Bean.SingleMealBean> {

    private TextView tv_meal_name;
    private TextView tv_num;
    private TextView tv_price;

    @Override
    public BaseViewHolder onCreateViewHolder(View v) {
        tv_meal_name = (TextView) v.findViewById(R.id.tv_meal_name);
        tv_num = (TextView) v.findViewById(R.id.tv_num);
        tv_price = (TextView) v.findViewById(R.id.tv_price);
        return this;
    }

    @Override
    public void onBindViewHolder(Context context, Order_Info_Bean.SingleMealBean singleMealBean) {
        tv_meal_name.setText(singleMealBean.getMeal_name());
        tv_num.setText(singleMealBean.getNums()+"");
        tv_price.setText(singleMealBean.getMeal_price()+"");
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_multiple_details_child;
    }
}
