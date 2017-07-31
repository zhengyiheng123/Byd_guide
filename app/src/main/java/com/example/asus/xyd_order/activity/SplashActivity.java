package com.example.asus.xyd_order.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.utils.ActivityFactory;
import com.example.asus.xyd_order.utils.SharedPreferenceUtils;


public class SplashActivity extends Activity {
    private Handler handler=new Handler();
    private boolean isLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isLogin = (boolean) SharedPreferenceUtils.getParam(this,"isLogin",false);
// 设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 移除标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isLogin){
                    ActivityFactory.gotoMain(SplashActivity.this);
                    finish();
                }else {
                    ActivityFactory.goToLogin(SplashActivity.this);
                }
                finish();
            }
        },0);
    }


}
