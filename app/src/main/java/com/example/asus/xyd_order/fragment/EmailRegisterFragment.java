package com.example.asus.xyd_order.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.activity.Activity_Register_confirm;
import com.example.asus.xyd_order.base.BaseFragment;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.CodeBean;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.net.result.RegisterBean;
import com.example.asus.xyd_order.utils.SharedPreferenceUtils;
import com.example.asus.xyd_order.utils.StringUtils;
import com.lucenlee.countdownlibrary.CountdownButton;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/3/9.
 */
public class EmailRegisterFragment extends BaseFragment {

    private CountdownButton countdown;
    private EditText et_email,et_code,et_password,et_repassword;
    private TextView tv_next;
    private RegisterBean registerBean1;
    public static EmailRegisterFragment instance;

    @Override
    public void onResume() {
        super.onResume();
        instance=this;
    }

    @Override
    public void myOnclick(View view) {
        switch (view.getId()){
            case R.id.countdown:
                if (!TextUtils.isEmpty(et_email.getText().toString().trim())){
                    if (StringUtils.isEmail(et_email.getText().toString())){
                        getCode(et_email.getText().toString());
                    }else {
                        toastShow("邮箱格式不正确");
                    }
                }else {
                    toastShow("请输入邮箱账号");
                }
                break;
            case R.id.tv_next:
                if (!TextUtils.isEmpty(et_email.getText().toString()) && !TextUtils.isEmpty(et_password.getText().toString()) && !TextUtils.isEmpty(et_repassword.getText().toString()) && !TextUtils.isEmpty(et_code.getText().toString())){
                    if (!et_repassword.getText().toString().equals(et_password.getText().toString())){
                        toastShow("两次输入密码不一样");
                    }else {
                        if (!StringUtils.isPassword(et_password.getText().toString())){
                            toastShow("密码必须是8-15位数字和字母");
                            return;
                        }
                        register(et_password.getText().toString(),et_email.getText().toString(),Integer.valueOf(et_code.getText().toString()));
                    }
                }else {
                    toastShow("请填写相关信息");
                }
                break;
        }
    }

    @Override
    public void initView(View v) {
        countdown = (CountdownButton) v.findViewById(R.id.countdown);
        countdown.setCount(120);
        et_email = (EditText) v.findViewById(R.id.et_email);
        et_code= (EditText) v.findViewById(R.id.et_code);
        et_password= (EditText) v.findViewById(R.id.et_password);
        et_repassword= (EditText) v.findViewById(R.id.et_repassword);
        tv_next = (TextView) v.findViewById(R.id.tv_next);
    }

    @Override
    public int getResource() {
        return R.layout.fragment_email;
    }

    @Override
    public int initData() throws Exception {
        return 1;
    }

    @Override
    public void setEvent() {
        countdown.setOnClickListener(this);
        tv_next.setOnClickListener(this);
    }

    /**
    *邮箱注册获取验证码
     */
    private void getCode(String email){
        showDialog();
        Observable<HttpResult<CodeBean>> result= ServiceApi.getInstance().getServiceContract().getCode(2,
                "","",email
                );
        result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CodeBean>() {
                    @Override
                    public void onCompleted() {
                        dismissDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissDialog();
                        toastShow(e.getMessage());
                    }

                    @Override
                    public void onNext(CodeBean codeBean) {
                        toastShow("验证码发送成功");
                    }
                });
    }

    /**
     * 邮箱注册
     */
    private void register(String password,String email,int captcha){
        showDialog();
        Observable<HttpResult<RegisterBean>> result=ServiceApi.getInstance().getServiceContract().register(2,"",password,email,captcha,"");
        result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RegisterBean>() {
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
                    public void onNext(RegisterBean registerBean) {
                        registerBean1 = registerBean;
                        SharedPreferenceUtils.setParam(context,"apitoken",registerBean.getApitoken());
                        getActivity().finish();
                        Intent intent=new Intent(getActivity(), Activity_Register_confirm.class);
                        getActivity().startActivity(intent);
                    }
                });
    }
}
