package com.example.asus.xyd_order.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.net.result.LoginResult;
import com.example.asus.xyd_order.net.result.RegisterBean;
import com.example.asus.xyd_order.utils.ActivityFactory;
import com.example.asus.xyd_order.utils.SharedPreferenceUtils;
import com.example.asus.xyd_order.utils.StringUtils;

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

    private TextView tv_register,tv_forget;
    private EditText et_password,et_username;
    private Button btn_login;

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
        iv_back.setOnClickListener(v -> {onBackPressed();});
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_login;
    }


    @Override
    public int getData() throws Exception {
        return 1;
    }

    @Override
    public void initView() {
        //初始化所有控件
        inializeView();
    }

    private void inializeView() {
        tv_register = (TextView) findViewById(R.id.tv_register);
        tv_forget= (TextView) findViewById(R.id.tv_forget);
        et_password = (EditText) findViewById(R.id.et_password);
        et_username= (EditText) findViewById(R.id.et_username);
        btn_login= (Button) findViewById(R.id.btn_login);
    }

    @Override
    public void setEvent() {
        tv_register.setOnClickListener(this);
        tv_forget.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }

    /**
     * 登录
     */
    private void login(String username,String password){
        showDialog();
        Observable<HttpResult<LoginResult>> result= ServiceApi.getInstance().getServiceContract().login(username,password);
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
                        toastShow(e.getMessage());
                        dismissDialog();
                    }

                    @Override
                    public void onNext(LoginResult loginResult) {
                        try {
                            initAlis(loginResult);
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    /**
     * 设置极光推送Alis
     */
    public void initAlis(LoginResult loginResult) throws NoSuchAlgorithmException {
//        String alis=getMD5("helpd_user_"+ali);
//        Log.e("zyh","别名："+loginResult.getUser_id());
        JPushInterface.setAlias(getApplicationContext(), "helpd_user_"+loginResult.getUser_id(), new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                if (i != 0){
                    toastShow("别名错误");
                }else {
                    SharedPreferenceUtils.setParam(context,"apitoken",loginResult.getApitoken());
                    SharedPreferenceUtils.setParam(context,"user_id",loginResult.getUser_id());
                    SharedPreferenceUtils.setParam(context,"user_name",loginResult.getUser_name());
                    SharedPreferenceUtils.setParam(context,"isLogin",true);
                    SharedPreferenceUtils.setParam(context,"avatar",loginResult.getAvatar());
                    ActivityFactory.gotoMain(context);
                    finish();
                }
            }
        });
    }
    public static String getMD5(String val) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(val.getBytes());
        byte[] m = md5.digest();//加密
        return getString(m);
    }
    private static String getString(byte[] b){
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < b.length; i ++){
            sb.append(b[i]);
        }
        return sb.toString();
    }
}