package com.example.asus.xyd_order.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseViewHolder;
import com.example.asus.xyd_order.net.result.ActAttractionsBean;

/**
 * Created by Zheng on 2017/3/2.
 */
public class OverViewHolder extends BaseViewHolder<ActAttractionsBean.ShowsBean> {

    private TextView tv_mer_time,tv_mer_name;

    @Override
    public BaseViewHolder onCreateViewHolder(View v) {
        tv_mer_time = (TextView) v.findViewById(R.id.tv_mer_time);
        tv_mer_name= (TextView) v.findViewById(R.id.tv_mer_name);
        return this;
    }

    @Override
    public void onBindViewHolder(Context context, ActAttractionsBean.ShowsBean s) {
        tv_mer_name.setText(s.getRoute_name());
        tv_mer_time.setText("演出时间:"+s.getDate()+" "+s.getTime());
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_overview;
    }
}
