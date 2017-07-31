package com.example.asus.xyd_order.holder;

import android.content.Context;
import android.view.View;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseViewHolder;

/**
 * Created by Zheng on 2017/3/24.
 */
public class CollectHolder extends BaseViewHolder {
    @Override
    public BaseViewHolder onCreateViewHolder(View v) {
        return this;
    }

    @Override
    public void onBindViewHolder(Context context, Object o) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.item_mycollect;
    }
}
