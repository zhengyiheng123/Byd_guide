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

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/7/5.
 */

public class Activity_SaveToSample extends BaseActivity {

    private TextView tv_submit;
    private EditText et_name;
    private String dmd_id;

    @Override
    public void myOnclick(View view) {
            switch (view.getId()){
                case R.id.tv_submit:
                    if (!TextUtils.isEmpty(et_name.getText().toString())){
                        saveSample(et_name.getText().toString());
                    }else {
                        toastShow("请输入名字");
                    }
                    break;
            }
    }

    @Override
    public void setToolbar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        TextView tv_title= (TextView) findViewById(R.id.tv_title);
        tv_title.setText("存为需求订单");
        iv_back.setOnClickListener(v -> onBackPressed());
        tv_submit = (TextView) findViewById(R.id.tv_submit);
        tv_submit.setOnClickListener(this);
        tv_submit.setVisibility(View.VISIBLE);
        tv_submit.setText("保存");
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_savetosample;
    }


    @Override
    public int getData() throws Exception {
        return 0;
    }

    @Override
    public void initView() {
        dmd_id = getIntent().getStringExtra("dmd_id");
        et_name = (EditText) findViewById(R.id.et_name);
    }

    @Override
    public void setEvent() {

    }
    /**
     * 保存需求订单
     */
    private void saveSample(String name){
        Observable<HttpResult> result= ServiceApi.getInstance().getServiceContract().saveToSample(apitoken,dmd_id,name);
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
                        toastShow("保存成功");
                        finish();
                    }
                });
    }
}
