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
 * Created by Zheng on 2017/7/28.
 */

public class SendMessageActivity extends BaseActivity {

    private EditText et_message;
    private String ord_id;

    @Override
    public void myOnclick(View view) {
        switch (view.getId()){
            case R.id.tv_submit:
                if (!TextUtils.isEmpty(et_message.getText().toString())){
                    getNetData(et_message.getText().toString());
                }else {
                    toastShow("请输入内容");
                }
                break;
        }
    }

    @Override
    public void setToolbar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(view -> onBackPressed());
        TextView tv_title= (TextView) findViewById(R.id.tv_title);
        tv_title.setText("发送消息");
        TextView tv_submit= (TextView) findViewById(R.id.tv_submit);
        tv_submit.setOnClickListener(this);
        tv_submit.setVisibility(View.VISIBLE);
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_send_message;
    }

    @Override
    public int getData() throws Exception {
        ord_id = getIntent().getStringExtra("ord_id");
        return 0;
    }

    @Override
    public void initView() {
        et_message = (EditText) findViewById(R.id.et_message);

    }

    @Override
    public void setEvent() {

    }
    /**
     * 发送消息
     */
    private void getNetData(String msg){
        showDialog();
        Observable<HttpResult> result= ServiceApi.getInstance().getServiceContract().msg(apitoken,msg,ord_id);
        result.map(new ResultFilter())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber() {
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
                    public void onNext(Object o) {
                        toastShow("发送成功");
                        finish();
                    }
                });
    }
}
