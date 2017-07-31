package com.example.asus.xyd_order.holder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.activity.Activity_address_list;
import com.example.asus.xyd_order.activity.AddAddressActivity;
import com.example.asus.xyd_order.base.BaseViewHolder;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.AddressBean;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.utils.SharedPreferenceUtils;
import com.example.asus.xyd_order.utils.ToastUtils;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/3/3.
 */
public class Address_Holder extends BaseViewHolder<AddressBean.AddressesBean> implements View.OnClickListener{
    TextView tv_name,tv_phoneNum,tv_address;
    ImageView iv_check;
    LinearLayout ll_edit,ll_delete;
    private Context context;
    AddressBean.AddressesBean bean;
    private String apitoken;;
    @Override
    public BaseViewHolder onCreateViewHolder(View v) {
        tv_name= (TextView) v.findViewById(R.id.tv_name);
        tv_phoneNum= (TextView) v.findViewById(R.id.tv_phoneNum);
        tv_address= (TextView) v.findViewById(R.id.tv_address);
        iv_check= (ImageView) v.findViewById(R.id.iv_check);
        ll_delete= (LinearLayout) v.findViewById(R.id.ll_delete);
        ll_edit= (LinearLayout) v.findViewById(R.id.ll_edit);
        iv_check.setOnClickListener(this);
        ll_delete.setOnClickListener(this);
        ll_edit.setOnClickListener(this);
        return this;
    }

    @Override
    public void onBindViewHolder(Context context, AddressBean.AddressesBean s) {
        this.context=context;
        apitoken= (String) SharedPreferenceUtils.getParam(context,"apitoken","");
        bean=s;
        tv_address.setText(s.getAddress());
        tv_phoneNum.setText(s.getMobile());
        tv_name.setText(s.getUser_name());
        int is_default=s.getIs_default();
        if (is_default == 0){
            iv_check.setAlpha(0f);
        }else if (is_default == 1){
            iv_check.setAlpha(1.0f);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_address;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_check:
//                ToastUtils.show(context,bean.getUa_id()+"ID",0);
                setDefault();
                break;
            case R.id.ll_delete:
                deleteAddress();
                break;
            case R.id.ll_edit:
                Intent intent=new Intent(context, AddAddressActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("address",bean);
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                break;
        }
    }

    /**
     * 设置默认地址
     */
    private void setDefault(){
        Observable<HttpResult> result= ServiceApi.getInstance().getServiceContract().setDefault(apitoken,bean.getUa_id()+"");
        result.map(new ResultFilter())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.show(context,e.getMessage(),0);
                    }

                    @Override
                    public void onNext(Object o) {
                        ToastUtils.show(context,"设置默认地址成功",0);
                        if (iv_check.getAlpha() == 0){
                            iv_check.setAlpha(1.0f);
                        }else {
                            iv_check.setAlpha(0f);
                        }
                        Activity_address_list.instance.getNetData();
                    }
                });

    }

    /**
     * 删除地址
     */
    private void deleteAddress(){
        Observable<HttpResult> result=ServiceApi.getInstance().getServiceContract().deleteAddress(bean.getUa_id()+"",apitoken);
        result.map(new ResultFilter())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.show(context,e.getMessage(),0);
                    }

                    @Override
                    public void onNext(Object o) {
                        Activity_address_list.instance.getNetData();
                    }
                });
    }
}
