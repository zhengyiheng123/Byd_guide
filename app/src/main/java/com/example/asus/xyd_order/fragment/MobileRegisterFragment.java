package com.example.asus.xyd_order.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.text.TextUtils;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.activity.Activity_Register_confirm;
import com.example.asus.xyd_order.activity.LoginActivity;
import com.example.asus.xyd_order.activity.RegisterActivity;
import com.example.asus.xyd_order.activity.SelectRoutes;
import com.example.asus.xyd_order.base.BaseFragment;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.CodeBean;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.net.result.RegisterBean;
import com.example.asus.xyd_order.utils.StringUtils;
import com.example.asus.xyd_order.utils.TextCodeUtils;
import com.example.asus.xyd_order.utils.ToastUtils;
import com.lucenlee.countdownlibrary.CountdownButton;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.picker.OptionPicker;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/3/9.
 */
@SuppressLint("ValidFragment")
public class MobileRegisterFragment extends BaseFragment {

    private TextView tv_country_code;
    private CountdownButton counddown;
    public String country;
    private List<String> countryList=new ArrayList<>();
    private EditText et_phonenum,et_code,et_password,et_repassword;
    private String countryCode;
    private TextView tv_next;

    @Override
    public void myOnclick(View view) {
        switch (view.getId()){
            case R.id.tv_country_code:
                if (countryList.size()>0){
                    showDialog(countryList);
                }
                break;
            case R.id.tv_next:
                if (!(TextUtils.isEmpty(et_phonenum.getText().toString()) || TextUtils.isEmpty(et_code.getText().toString())
                        ||TextUtils.isEmpty(et_password.getText().toString()) || TextUtils.isEmpty(et_repassword.getText().toString()))){
                    if (!et_password.getText().toString().equals(et_repassword.getText().toString())){
                        toastShow("两次输入密码不一致");
                    }else {
                        if (!StringUtils.isPassword(et_password.getText().toString())){
                            toastShow("密码必须是8-15位数字和字母");
                                    return;
                        }
                        String mobile=et_phonenum.getText().toString().trim();
                        String code=et_code.getText().toString().trim();
                        String password=et_password.getText().toString().trim();
                        register(mobile,code,password);
                    }
                }else {
                    toastShow("相关信息不能为空");
                }
                break;
            case R.id.countdown:
                String phonenum=et_phonenum.getText().toString();
                if (!TextUtils.isEmpty(phonenum)){
                    if (StringUtils.isNumber(tv_country_code.getText().toString())){
                        counddown.startDown();
                        getCode(phonenum);
                    }else {
                        toastShow("请选择国际区号");
                    }

                }else {
                    toastShow("请输入手机号码");
                }

                break;
        }
    }
    private void  showDialog(List<String> mlist){
        OptionPicker optionPicker=new OptionPicker(getActivity(),mlist);
        optionPicker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                String[] items = item.split("\\+");
                countryCode = items[1];
                tv_country_code.setText(countryCode);
            }
        });
        optionPicker.show();
    }
    @Override
    public void initView(View v) {
        initAsset();
        tv_country_code = (TextView) v.findViewById(R.id.tv_country_code);
        tv_next = (TextView) v.findViewById(R.id.tv_next);
        counddown= (CountdownButton) v.findViewById(R.id.countdown);
        counddown.setCount(60);
        et_phonenum = (EditText) v.findViewById(R.id.et_phonenum);
        et_code= (EditText) v.findViewById(R.id.et_code);
        et_password= (EditText) v.findViewById(R.id.et_password);
        et_repassword= (EditText) v.findViewById(R.id.et_password);
    }

    private void   initAsset() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                 String country1 = TextCodeUtils.getTextFromAssets(getActivity(),"county_code.json");
                if (!android.text.TextUtils.isEmpty(country1)) {
                    try {
                        JSONArray jsonArray = new JSONArray(country1);
                        for (int i=0;i<jsonArray.length();i++){
                            JSONArray array=jsonArray.getJSONArray(i);
                            for (int y=0;y<array.length();y++){
                                JSONArray array1=array.getJSONArray(y);
                                String country_name=array1.getString(0);
                                String code=array1.getString(1);
                                countryList.add(country_name+" "+code);
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public int getResource() {
        return R.layout.fragment_mobile;
    }

    @Override
    public int initData() throws Exception {
        return 1;
    }

    @Override
    public void setEvent() {
        tv_country_code.setOnClickListener(this);
        counddown.setOnClickListener(this);
        tv_next.setOnClickListener(this);
    }


    /**
     * 注册
     */
    private void register(String mobile,String code,String password){
        showDialog();
        Observable<HttpResult<RegisterBean>> result= ServiceApi.getInstance().getServiceContract().register(1, mobile, password,"",Integer.valueOf(code),countryCode);
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
                        getActivity().finish();
                        startActivity(new Intent(getActivity(), Activity_Register_confirm.class));
                    }
                });
    }

    /**
     * 获取验证码
     */
    private void getCode(String mobile){
        showDialog();
        Observable<HttpResult<CodeBean>> result=ServiceApi.getInstance().getServiceContract().getCode(1,mobile,countryCode,"");
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
}
