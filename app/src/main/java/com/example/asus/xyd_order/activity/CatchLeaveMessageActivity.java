package com.example.asus.xyd_order.activity;

import android.view.View;
import android.widget.ImageView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;

/**
 * Created by Zheng on 2017/3/9.
 */
public class CatchLeaveMessageActivity extends BaseActivity {
    @Override
    public void myOnclick(View view) {

    }

    @Override
    public void setToolbar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(v -> {onBackPressed();});
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_catch_message;
    }


    @Override
    public int getData() throws Exception {
        String ord_id= getIntent().getStringExtra("ord_id");
        return 1;
    }

    @Override
    public void initView() {

    }

    @Override
    public void setEvent() {

    }
}
