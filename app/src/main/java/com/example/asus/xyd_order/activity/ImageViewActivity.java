package com.example.asus.xyd_order.activity;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.ui.SmartImageveiw;

/**
 * Created by Zheng on 2017/7/12.
 */

public class ImageViewActivity extends BaseActivity{

    private ImageView iv_preview;
    private String path;
    private ImageView iv_back;

    @Override
    public void myOnclick(View view) {
    }

    @Override
    public void setToolbar() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(view -> onBackPressed());
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_image_view;
    }


    @Override
    public int getData() throws Exception {
        path = getIntent().getStringExtra("path");

        return 0;
    }

    @Override
    public void initView() {
        iv_preview = (ImageView) findViewById(R.id.iv_preview);
        Glide.with(context).load(path).into(iv_preview);
    }

    @Override
    public void setEvent() {

    }
}
