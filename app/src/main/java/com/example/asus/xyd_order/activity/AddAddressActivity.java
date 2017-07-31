package com.example.asus.xyd_order.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.entity.Area;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.AddressBean;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/3/3.
 */
public class AddAddressActivity extends BaseActivity {

    @Bind(R.id.et_phone)
     EditText et_phone;

    @Bind(R.id.et_name)
     EditText et_name;

    @Bind(R.id.et_post)
     EditText et_post;

    @Bind(R.id.et_street)
    EditText et_street;

    @Bind(R.id.et_country_city)
    EditText et_country_city;

    @Bind(R.id.rl_check)
    CheckBox rl_check;

    String isdefault;
    private AddressBean.AddressesBean addressesBean;

    @Override
    public void myOnclick(View view) {
        switch (view.getId()) {
            case R.id.tv_submit:
                if (addressesBean ==null){
                if (!TextUtils.isEmpty(et_name.getText().toString()) && !TextUtils.isEmpty(et_phone.getText().toString()) && !TextUtils.isEmpty(et_post.getText().toString()) && !TextUtils.isEmpty(et_country_city.getText().toString())
                        && !TextUtils.isEmpty(et_street.getText().toString())
                        ){
                        boolean ischecked=rl_check.isChecked();
                    if (ischecked){
                        isdefault="1";
                    }else {
                        isdefault ="0";
                    }
                        addAddress(et_name.getText().toString(),et_post.getText().toString(),et_phone.getText().toString(),et_street.getText().toString(),et_country_city.getText().toString(),isdefault);
                }else {
                    toastShow("清完善地址信息");
                }
                }else {
                    if (!TextUtils.isEmpty(et_name.getText().toString()) && !TextUtils.isEmpty(et_phone.getText().toString()) && !TextUtils.isEmpty(et_post.getText().toString()) && !TextUtils.isEmpty(et_country_city.getText().toString())
                            && !TextUtils.isEmpty(et_street.getText().toString())
                            ){
                        boolean ischecked=rl_check.isChecked();
                        if (ischecked){
                            isdefault="1";
                        }else {
                            isdefault ="0";
                        }
                        editAddress(et_name.getText().toString(),et_post.getText().toString(),et_phone.getText().toString(),et_street.getText().toString(),et_country_city.getText().toString(),isdefault);
                    }else {
                        toastShow("清完善地址信息");
                    }
                }


                break;
        }
    }

    @Override
    public void setToolbar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        TextView tv_title= (TextView) findViewById(R.id.tv_title);
        tv_title.setText("收货地址");
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView tv_submit= (TextView) findViewById(R.id.tv_submit);
        tv_submit.setVisibility(View.VISIBLE);
        tv_submit.setOnClickListener(this);
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_add_address;
    }


    @Override
    public int getData() throws Exception {
        return 1;
    }

    @Override
    public void initView() {
        addressesBean = (AddressBean.AddressesBean) getIntent().getSerializableExtra("address");
        if (addressesBean!=null){
            getDataNet();
        }
    }
    @Override
    public void setEvent() {
    }

    /**
     * 添加收货地址
     */
    private void addAddress(String username,String post_code,String mobile,String address,String region_name_list,String is_default){
        Observable<HttpResult> result= ServiceApi.getInstance().getServiceContract().saveAddress(apitoken,username,post_code,mobile,address,region_name_list,is_default);
        result.map(new ResultFilter())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        toastShow(e.getMessage());
                    }

                    @Override
                    public void onNext(Object o) {
                        toastShow("添加地址成功");
                        finish();
                    }
                });
    }

    /**
     * 更新收货地址
     */
    private void editAddress(String username,String post_code,String mobile,String address,String region_name_list,String is_default){
        Observable<HttpResult> result=ServiceApi.getInstance().getServiceContract().updateAddress(addressesBean.getUa_id()+"",apitoken,username,post_code,mobile,address,region_name_list,is_default,"PUT");
        result.map(new ResultFilter())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        toastShow(e.getMessage());
                    }

                    @Override
                    public void onNext(Object o) {
                    toastShow("更新成功");
                        finish();
                    }
                });
    }
    /**
     * 获取地址信息
     */
    private void getDataNet(){
        Observable<HttpResult<AddressBean.AddressesBean>> result=ServiceApi.getInstance().getServiceContract().addressInfo(addressesBean.getUa_id()+"",apitoken);
        result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AddressBean.AddressesBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(AddressBean.AddressesBean addressesBean) {
                        et_name.setText(addressesBean.getUser_name());
                        et_street.setText(addressesBean.getAddress());
                        et_post.setText(addressesBean.getPost_code());
                        et_phone.setText(addressesBean.getMobile());
                        et_country_city.setText(addressesBean.getRegion_name_list());
                        int isDefault=addressesBean.getIs_default();
                        if (isDefault == 1){
                            rl_check.setChecked(true);
                        }else if (isDefault ==0){
                            rl_check.setChecked(false);
                        }
                    }
                });
    }

}
