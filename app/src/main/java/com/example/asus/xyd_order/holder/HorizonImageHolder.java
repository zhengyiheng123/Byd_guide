package com.example.asus.xyd_order.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseViewHolder;
import com.example.asus.xyd_order.net.BaseApi;

/**
 * Created by Zheng on 2017/8/4.
 */

public class HorizonImageHolder extends BaseViewHolder<String> {

    private ImageView iv_img_hos;

    @Override
    public BaseViewHolder onCreateViewHolder(View v) {
        iv_img_hos = (ImageView) v.findViewById(R.id.iv_img_hos);
        return this;
    }

    @Override
    public void onBindViewHolder(Context context, String s) {
        Glide.with(context).load(s).into(iv_img_hos);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_image;
    }
}
