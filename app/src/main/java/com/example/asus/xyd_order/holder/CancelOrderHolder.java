package com.example.asus.xyd_order.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseViewHolder;

/**
 * Created by Zheng on 2017/2/27.
 */
public class CancelOrderHolder extends BaseViewHolder<String> {

    private TextView lv_reasons;

    @Override
    public BaseViewHolder onCreateViewHolder(View v) {
        lv_reasons = (TextView) v.findViewById(R.id.tv_reason);
        return this;
    }

    @Override
    public void onBindViewHolder(Context context, String s) {
        lv_reasons.setText(s);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_cancel_reason;
    }
}
