package com.example.asus.xyd_order.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseViewHolder;
import com.example.asus.xyd_order.net.result.TuishuiBean;

/**
 * Created by Zheng on 2017/7/12.
 */

public class TerminalHolder extends BaseViewHolder<TuishuiBean.DrawbackPointBean> {

    private TextView tv_name;

    @Override
    public BaseViewHolder onCreateViewHolder(View v) {
        tv_name = (TextView) v.findViewById(R.id.tv_name);
        return this;
    }

    @Override
    public void onBindViewHolder(Context context, TuishuiBean.DrawbackPointBean drawbackPointBean) {
        tv_name.setText(drawbackPointBean.getDp_point());
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_terminal;
    }
}
