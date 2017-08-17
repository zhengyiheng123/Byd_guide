package com.example.asus.xyd_order.activity;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.utils.ActivityFactory;
import com.example.asus.xyd_order.utils.CacheUtil;
import com.example.asus.xyd_order.utils.SharedPreferenceUtils;

/**
 * Created by Zheng on 2017/3/9.
 */
public class Activity_Setting extends BaseActivity {

    private RelativeLayout setting_message,rl_about,rl_clearcache;
    private TextView tv_title;
    private TextView tv_logout,tv_cache;
    private String cache;

    @Override
    public void myOnclick(View view) {
        switch (view.getId()){
            case R.id.setting_message:
                ActivityFactory.gotoMessageSetting(context);
                break;
            case R.id.rl_about:
                ActivityFactory.gotoAboutUs(context);
                break;
            case R.id.tv_logout:
                String username= (String) SharedPreferenceUtils.getParam(context,"username","");
                String password= (String) SharedPreferenceUtils.getParam(context,"password","");
                SharedPreferenceUtils.clear(context);
                SharedPreferenceUtils.setParam(context,"username",username);
                SharedPreferenceUtils.setParam(context,"password",password);
                ActivityFactory.goToLogin(context);
                finish();
                MainActivity.instance.finish();
                break;
            case R.id.rl_clearcache:
                CacheUtil.clearAllCache(context);
                toastShow("缓存已清理");
                tv_cache.setText(0+"k");
                break;
        }
    }

    @Override
    public void setToolbar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(v -> {onBackPressed();});
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("设置");
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_setting;
    }


    @Override
    public int getData() throws Exception {
        cache = CacheUtil.getTotalCacheSize(context);
        return 0;
    }

    @Override
    public void initView() {
        rl_clearcache= (RelativeLayout) findViewById(R.id.rl_clearcache);
        setting_message = (RelativeLayout) findViewById(R.id.setting_message);
        rl_about= (RelativeLayout) findViewById(R.id.rl_about);
        tv_logout = (TextView) findViewById(R.id.tv_logout);
        tv_cache= (TextView) findViewById(R.id.tv_cache);
        tv_cache.setText(cache);
    }

    @Override
    public void setEvent() {
        setting_message.setOnClickListener(this);
        rl_about.setOnClickListener(this);
        tv_logout.setOnClickListener(this);
        rl_clearcache.setOnClickListener(this);
    }
}
