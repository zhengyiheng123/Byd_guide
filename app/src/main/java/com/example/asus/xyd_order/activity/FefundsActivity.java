package com.example.asus.xyd_order.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.HttpResult;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/8/11.
 */

public class FefundsActivity extends BaseActivity {
    @Bind(R.id.iv_back)ImageView iv_back;
    @Bind(R.id.tv_title)TextView tv_title;
    @Bind(R.id.btn_refund)TextView btn_refund;
    @Bind(R.id.et_desc)EditText et_desc;
    private String ord_id;
    private String ord_type;

    @Override
    public void myOnclick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.btn_refund:
                if (!TextUtils.isEmpty(et_desc.getText().toString())){
                    if (ord_type.equals("1")){
                        refund(et_desc.getText().toString());
                    }else if (ord_type.equals("5")|| ord_type.equals("6") || ord_type.equals("7")){
                        refundjingdian(et_desc.getText().toString());
                    }

                }else {
                    toastShow("请填写理由");
                }
                break;
        }
    }

    @Override
    public void setToolbar() {
        tv_title.setText("退款");
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_refunds;
    }

    @Override
    public int getData() throws Exception {
        //订单id
        ord_id = getIntent().getStringExtra("ord_id");
        //订单类型
        ord_type =getIntent().getStringExtra("ord_type");
        return 0;
    }

    @Override
    public void initView() {

    }

    @Override
    public void setEvent() {
        iv_back.setOnClickListener(this);
        btn_refund.setOnClickListener(this);
    }
    //退款餐厅
    private void refund(String message){
        Observable<HttpResult> result= ServiceApi.getInstance().getServiceContract().refund(apitoken,ord_id,message);
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
                        toastShow("退款申请已提交");
                        finish();
                    }
                });
    }
    //退款景点
    private void refundjingdian(String message){
        Observable<HttpResult> result= ServiceApi.getInstance().getServiceContract().refundJingdian(apitoken,ord_id,message);
        result.map(new ResultFilter())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Object o) {

                    }
                });
    }
}
