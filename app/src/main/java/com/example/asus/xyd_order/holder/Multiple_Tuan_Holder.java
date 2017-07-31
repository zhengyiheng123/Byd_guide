package com.example.asus.xyd_order.holder;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseViewHolder;
import com.example.asus.xyd_order.net.result.TuancanBean;

/**
 * Created by Zheng on 2017/7/10.
 */

public class Multiple_Tuan_Holder   extends BaseViewHolder<TuancanBean.PriceListBean> {

    private TextView tv_meal_name,tv_meal_details;
    private CheckBox cb_check;

    @Override
    public BaseViewHolder onCreateViewHolder(View v) {
        tv_meal_name = (TextView) v.findViewById(R.id.tv_meal_name);
        tv_meal_details= (TextView) v.findViewById(R.id.tv_meal_details);
        cb_check= (CheckBox) v.findViewById(R.id.cb_check);
        return this;
    }

    @Override
    public void onBindViewHolder(Context context, TuancanBean.PriceListBean s) {
        tv_meal_name.setText(s.getMeal_name()+"  "+s.getMeal_price()+"/人");
        tv_meal_details.setText(s.getMeal_detail());
        if (s.isChecked()){
            cb_check.setChecked(true);
        }else {
            cb_check.setChecked(false);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_multiple_can;
    }

}
