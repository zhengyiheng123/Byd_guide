package com.example.asus.xyd_order.fragment;

import android.content.Intent;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.areacode.AreaCodeActivity;
import com.example.asus.xyd_order.base.BaseFragment;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.CodeBean;
import com.example.asus.xyd_order.net.result.ForgetPassword;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.utils.StringUtils;
import com.example.asus.xyd_order.utils.TextCodeUtils;
import com.lucenlee.countdownlibrary.CountdownButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.w3c.dom.Text;

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
public class MobileFindPasswordFragment extends BaseFragment {

    private EditText et_phonenum,et_password,et_code;
    private TextView tv_country_code,tv_next;
    private CountdownButton tv_getcode;
    private List<String> countryList=new ArrayList<>();
    private String countryCode;

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
    public void myOnclick(View view) {
        switch (view.getId()){
            case R.id.tv_country_code:
//                if (countryList.size()>0){
//                    showDialog(countryList);
//                }
                startActivityForResult(new Intent(getActivity(), AreaCodeActivity.class),0);
                break;
            case R.id.tv_getcode:
                String phonenum=et_phonenum.getText().toString();
                if (!TextUtils.isEmpty(phonenum)){
                    if (StringUtils.isNumber(tv_country_code.getText().toString())){
                        tv_getcode.startDown();
                        getCode(phonenum);
                    }else {
                        toastShow("请选择国际区号");
                    }

                }else {
                    toastShow("请输入手机号码");
                }
                break;
            case R.id.tv_next:
                if (!TextUtils.isEmpty(et_code.getText().toString()) &&
                        !TextUtils.isEmpty(et_password.getText().toString()) && !TextUtils.isEmpty(et_phonenum.getText().toString())){
                    findPassword(et_phonenum.getText().toString(),tv_country_code.getText().toString(),et_password.getText().toString(),et_code.getText().toString());
                }else {
                    toastShow("请完善信息");
                }
                break;
        }
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
    public void initView(View v) {
        initAsset();
        et_phonenum = (EditText) v.findViewById(R.id.et_phonenum);
        tv_country_code = (TextView) v.findViewById(R.id.tv_country_code);
        et_code = (EditText) v.findViewById(R.id.et_code);
        tv_getcode= (CountdownButton) v.findViewById(R.id.tv_getcode);
        tv_getcode.setCount(120);
        et_password= (EditText) v.findViewById(R.id.et_password);
        tv_next= (TextView) v.findViewById(R.id.tv_next);
    }

    @Override
    public int getResource() {
        return R.layout.fragment_mobile_find;
    }

    @Override
    public int initData() throws Exception {
        return 1;
    }

    @Override
    public void setEvent() {
        tv_country_code.setOnClickListener(this);
        tv_getcode.setOnClickListener(this);
        tv_next.setOnClickListener(this);
    }


    /**
     * 获取验证码
     */
    private void getCode(String mobile){
        showDialog();
        Observable<HttpResult<CodeBean>> result= ServiceApi.getInstance().getServiceContract().getCode(1,mobile,countryCode,"");
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
    private void findPassword(String mobile,String area_code,String password,String code){
        showDialog();
        Observable<HttpResult<ForgetPassword>> result=ServiceApi.getInstance().getServiceContract()
                .findPassword(1,mobile,area_code,"",password,code);
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode ==1 && requestCode ==0){
            tv_country_code.setText(data.getStringExtra("areacode"));
        }
    }
}
