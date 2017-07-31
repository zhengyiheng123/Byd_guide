package com.example.asus.xyd_order.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.ui.CountDownTimerUtils;

/**
 * Created by Zheng on 2017/3/3.
 */
public class CheckSmsActivity extends BaseActivity {

    private TextView tv_get_code;

    @Override
    public void myOnclick(View view) {
        switch (view.getId()){
            case R.id.tv_get_code:
                CountDownTimerUtils utils=new CountDownTimerUtils(tv_get_code,30000,1000,context.getResources().getColor(R.color.tool_bar_color));
                utils.start();
                break;
        }
    }

    @Override
    public void setToolbar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(v -> {onBackPressed();});
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_check_sms;
    }


    @Override
    public int getData() throws Exception {
        return 1;
    }

    @Override
    public void initView() {
        tv_get_code = (TextView) findViewById(R.id.tv_get_code);

    }

    @Override
    public void setEvent() {
        tv_get_code.setOnClickListener(this);
    }


}
