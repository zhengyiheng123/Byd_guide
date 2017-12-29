package com.example.asus.xyd_order.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.dialog.CommonDialog;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.HttpResult;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/7/17.
 */

public class CancelActivity extends BaseActivity {

    private EditText et_cancel_condition;
    private String ord_id;
    private TextView tv_submit;
    private String cancelType;

    @Override
    public void myOnclick(View view) {
        switch (view.getId()){
            case R.id.tv_submit:
                if (!TextUtils.isEmpty(et_cancel_condition.getText().toString())){
                    AlertDialog.Builder builder=new AlertDialog.Builder(CancelActivity.this);
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (cancelType.equals("0")){
                                cancel(et_cancel_condition.getText().toString());
                            }else if (cancelType.equals("1")){
                                cancelZhongcan(et_cancel_condition.getText().toString());
                            }
                            dialogInterface.dismiss();
                        }
                    });
                    builder.setTitle("是否取消？");
                    builder.show();
                }else {
                    toastShow("请填写取消原因");
                }
                break;
        }
    }

    @Override
    public void setToolbar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(view -> onBackPressed());
        TextView tv_title= (TextView) findViewById(R.id.tv_title);
        tv_title.setText("取消订单");
        et_cancel_condition = (EditText) findViewById(R.id.et_cancel_condition);
        tv_submit = (TextView) findViewById(R.id.tv_submit);
        tv_submit.setVisibility(View.VISIBLE);
        tv_submit.setOnClickListener(this);
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_cancel;
    }

    @Override
    public int getData() throws Exception {
        ord_id = getIntent().getStringExtra("ord_id");
        cancelType = getIntent().getStringExtra("cancel_type");
        return 0;
    }

    @Override
    public void initView() {
        et_cancel_condition = (EditText) findViewById(R.id.et_cancel_condition);
    }

    @Override
    public void setEvent() {

    }
    /**
     * 取消景点
     */
    private void cancel(String cancel_reson){
        Observable<HttpResult> result= ServiceApi.getInstance().getServiceContract().cancelNomal(ord_id,apitoken,cancel_reson);
        result.map(new ResultFilter())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError (Throwable e) {
                        toastShow(e.getMessage());
                    }

                    @Override
                    public void onNext(Object o) {
                        toastShow("取消成功");
                        finish();
//                        Activity_Attractions_order_Successed.instance.finish();
                    }
                });
    }

    /**
     * 取消中餐
     */
    private void cancelZhongcan(String content){
        Observable<HttpResult> result= ServiceApi.getInstance().getServiceContract().cancelZhongcan(apitoken,ord_id,content);
        result.map(new ResultFilter())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError (Throwable e) {
                        toastShow(e.getMessage());
                    }

                    @Override
                    public void onNext(Object o) {
                        toastShow("取消成功");
                        finish();
//                        Activity_Attractions_order_Successed.instance.finish();
                    }
                });
    }
}
