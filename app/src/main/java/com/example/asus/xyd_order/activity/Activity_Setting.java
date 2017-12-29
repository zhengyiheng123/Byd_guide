package com.example.asus.xyd_order.activity;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.utils.ActivityFactory;
import com.example.asus.xyd_order.utils.CacheUtil;
import com.example.asus.xyd_order.utils.SharedPreferenceUtils;
import com.example.asus.xyd_order.utils.ToastUtils;

import java.security.NoSuchAlgorithmException;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

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
                mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, ""));
                String username= (String) SharedPreferenceUtils.getParam(context,LoginActivity.USERNAME,"");
                String password= (String) SharedPreferenceUtils.getParam(context,LoginActivity.PASSWORD,"");
                SharedPreferenceUtils.clear(context);
                SharedPreferenceUtils.setParam(context,LoginActivity.USERNAME,username);
                SharedPreferenceUtils.setParam(context,LoginActivity.PASSWORD,password);
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

    private static final int MSG_SET_ALIAS = 1001;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    Log.d("Login", "Set alias in handler.");
                    // 调用 JPush 接口来设置别名。
                    JPushInterface.setAlias(context,(String) msg.obj,mAliasCallback);
                    break;
                default:
                    Log.i("Login", "Unhandled msg - " + msg.what);
            }
        }
    };
    //设置别名
    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    Log.i("Login", logs);
                    ToastUtils.showShort(context,"退出成功");
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.i("Login", logs);
                    // 延迟 60 秒来调用 Handler 设置别名
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
                    Log.i("Login", logs);
            }
        }
    };
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
