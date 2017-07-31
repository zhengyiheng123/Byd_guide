package com.example.asus.xyd_order.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseFragment;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.CodeBean;
import com.example.asus.xyd_order.net.result.ForgetPassword;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.utils.StringUtils;
import com.lucenlee.countdownlibrary.CountdownButton;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/3/9.
 */
public class EmailFindPasswordFragment extends BaseFragment {
    private EditText et_email,et_password;
    private TextView tv_country_code;
    private TextView et_code;
    private CountdownButton tv_getcode;
    private TextView tv_next;

    @Override
    public void myOnclick(View view) {
        switch (view.getId()){
            case R.id.tv_getcode:
                String mail=et_email.getText().toString();
                    if (!TextUtils.isEmpty(mail)){
                        tv_getcode.startDown();
                        getCode(mail);
                }else {
                    toastShow("请输入邮箱号码");
                }
                break;
            case R.id.tv_next:
                if (!TextUtils.isEmpty(et_code.getText().toString()) &&
                        !TextUtils.isEmpty(et_password.getText().toString()) && !TextUtils.isEmpty(et_email.getText().toString())){
                    findPassword(et_email.getText().toString(),et_password.getText().toString(),et_code.getText().toString());
                }else {
                    toastShow("请完善信息");
                }
                break;
        }
    }

    @Override
    public void initView(View v) {
        et_email = (EditText) v.findViewById(R.id.et_email);
        tv_country_code = (TextView) v.findViewById(R.id.tv_country_code);
        et_code = (TextView) v.findViewById(R.id.et_code);
        tv_getcode= (CountdownButton) v.findViewById(R.id.tv_getcode);
        et_password= (EditText) v.findViewById(R.id.et_password);
        tv_next = (TextView) v.findViewById(R.id.tv_next);
    }

    @Override
    public int getResource() {
        return R.layout.fragment_email_find;
    }

    @Override
    public int initData() throws Exception {
        return 1;
    }

    @Override
    public void setEvent() {
        tv_getcode.setOnClickListener(this);
        tv_next.setOnClickListener(this);
    }


    /**
     * 获取验证码
     */
    private void getCode(String mail){
        showDialog();
        Observable<HttpResult<CodeBean>> result= ServiceApi.getInstance().getServiceContract().getCode(2,"","",mail);
        result.map(new ResultFilter())
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
                    public void onNext(CodeBean httpResult) {
                        toastShow("验证码发送成功"+httpResult.getCaptcha());
                    }
                });
    }
    private void findPassword(String email,String password,String code){
        showDialog();
        Observable<HttpResult<ForgetPassword>> result=ServiceApi.getInstance().getServiceContract()
                .findPassword(2,"","",email,password,code);
        result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ForgetPassword>() {
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
                    public void onNext(ForgetPassword forgetPassword) {
                        getActivity().finish();
                        toastShow("修改成功");
                    }
                });
    }
}
