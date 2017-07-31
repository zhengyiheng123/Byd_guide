package com.example.asus.xyd_order.activity;

import android.view.View;
import android.widget.ImageView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;

/**
 * Created by Zheng on 2017/3/10.
 */
public class Activity_Account_Details extends BaseActivity {
    @Override
    public void myOnclick(View view) {

    }

    @Override
    public void setToolbar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(v->{onBackPressed();});
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_account_details;
    }


    @Override
    public int getData() throws Exception {
        return 0;
    }

    @Override
    public void initView() {

    }

    @Override
    public void setEvent() {

    }
}
