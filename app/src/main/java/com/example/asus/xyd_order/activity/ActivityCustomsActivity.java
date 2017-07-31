package com.example.asus.xyd_order.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;

/**
 * Created by Zheng on 2017/3/7.
 */
public class ActivityCustomsActivity extends BaseActivity {

    private TextView tv_submit;

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
        return R.layout.activity_customs;
    }


    @Override
    public int getData() throws Exception {
        return 1;
    }

    @Override
    public void initView() {
        tv_submit = (TextView) findViewById(R.id.tv_submit);
        tv_submit.setVisibility(View.VISIBLE);
        tv_submit.setText("添加");
    }

    @Override
    public void setEvent() {

    }
}
