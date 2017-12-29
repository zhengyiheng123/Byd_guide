package com.example.asus.xyd_order.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseViewHolder;
import com.example.asus.xyd_order.net.result.RegionsBean;

/**
 * Created by Zheng on 2017/12/29.
 */

public class AutoTextHolder extends BaseViewHolder<RegionsBean> {

    private TextView text1;

    @Override
    public BaseViewHolder onCreateViewHolder(View v) {
        text1 = (TextView) v.findViewById(R.id.text1);
        return this;
    }

    @Override
    public void onBindViewHolder(Context context, RegionsBean regionsBean) {
        text1.setText(regionsBean.getOriginal_name());
    }

    @Override
    public int getLayoutId() {

        return R.layout.simpe_text;
    }
}
