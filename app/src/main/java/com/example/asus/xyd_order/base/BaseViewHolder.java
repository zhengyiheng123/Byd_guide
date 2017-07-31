package com.example.asus.xyd_order.base;

import android.content.Context;
import android.view.View;

/**
 * Created by RONMEI on 2015/10/19.
 */
public abstract class BaseViewHolder<T> {

    public abstract BaseViewHolder onCreateViewHolder(View v);
    public abstract void onBindViewHolder(Context context, T t);
    public abstract int getLayoutId();
}
