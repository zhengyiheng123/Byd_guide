package com.example.asus.xyd_order.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.net.result.LoginResult;
import com.example.asus.xyd_order.utils.ActivityFactory;
import com.example.asus.xyd_order.utils.SharedPreferenceUtils;
import com.example.asus.xyd_order.utils.ToastUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/3/9.
 */
public class LoginActivity extends BaseActivity {

    public static final String CONFIRM_STATE = "CONFIRM_STATE";
    private TextView tv_register,tv_forget;
    private EditText et_password,et_username;
    private Button btn_login;
    private String username;
    private String password;
    private CheckBox cb_remeber;
    public static String USER_NAME="user_name";
    public static String USER_MOBILE="user_phone";
    public static String USER_EMAIL="user_email";
    public static String USERNAME="username";
    public static String PASSWORD="password";
    //记住密码
    private boolean isRemember;

    @Override
    public void myOnclick(View view) {
        switch (view.getId()){
            case R.id.tv_register:
                startActivityForResult(new Intent(context,RegisterActivity.class),1);
                break;
            case R.id.tv_forget:
                ActivityFactory.gotoForgetPassword(context);
                break;
            case R.id.btn_login:
                if (!TextUtils.isEmpty(et_password.getText().toString()) && !TextUtils.isEmpty(et_username.getText().toString())){
                    login(et_username.getText().toString(),et_password.getText().toString());
                }else {
                    toastShow("请输入用户名密码");
                }
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void setToolbar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setVisibility(View.GONE);
        iv_back.setOnClickListener(v -> {onBackPressed();});
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_login;
    }


    @Override
    public int getData() throws Exception {
        username = (String) SharedPreferenceUtils.getParam(context,USERNAME,"");
        password = (String) SharedPreferenceUtils.getParam(context,PASSWORD,"");
        return 1;
    }

    @Override
    public void initView() {
        //初始化所有控件
        inializeView();
    }

    private void inializeView() {
        cb_remeber = (CheckBox) findViewById(R.id.cb_remeber);
        tv_register = (TextView) findViewById(R.id.tv_register);
        tv_forget= (TextView) findViewById(R.id.tv_forget);
        et_password = (EditText) findViewById(R.id.et_password);
        et_password.setText(password);
        et_username= (EditText) findViewById(R.id.et_username);
        et_username.setText(username);
        btn_login= (Button) findViewById(R.id.btn_login);
    }

    @Override
    public void setEvent() {
        tv_register.setOnClickListener(this);
        tv_forget.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        cb_remeber.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isRemember=b;
            }
        });
    }

    /**
     * 登录
     */
    private void login(String username,String password){
        showDialog();
        Observable<HttpResult<LoginResult>> result= ServiceApi.getInstance().getServiceContract().login(username,password,"android");
        result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LoginResult>() {
                    @Override
                    public void onCompleted() {
                        dismissDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showShort(context,e.getMessage());
                        dismissDialog();
                    }

                    @Override
                    public void onNext(LoginResult loginResult) {
                        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, "helpd_user_"+loginResult.getUser_id()));
                        SharedPreferenceUtils.setParam(context,CONFIRM_STATE,loginResult.getState());
                        SharedPreferenceUtils.setParam(context,USERNAME,et_username.getText().toString());
                        SharedPreferenceUtils.setParam(context,USER_NAME,loginResult.getUser_name());
                        SharedPreferenceUtils.setParam(context,USER_MOBILE,loginResult.getMobile());
                        SharedPreferenceUtils.setParam(context,USER_EMAIL,loginResult.getEmail());
                        SharedPreferenceUtils.setParam(context,"apitoken",loginResult.getApitoken());
                        SharedPreferenceUtils.setParam(context,"user_id",loginResult.getUser_id());
                        SharedPreferenceUtils.setParam(context,"user_name",loginResult.getUser_name());
                        SharedPreferenceUtils.setParam(context,"isLogin",true);
                        SharedPreferenceUtils.setParam(context,"avatar",loginResult.getAvatar());
                        if (isRemember){
                            SharedPreferenceUtils.setParam(context,PASSWORD,et_password.getText().toString());
                        }else {
                            SharedPreferenceUtils.setParam(context,PASSWORD,"");
                        }
                        ActivityFactory.gotoMain(context);
                    }
                });

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
                    ToastUtils.showShort(context,"登录成功");
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
}
