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
        },1500);
    }


}
