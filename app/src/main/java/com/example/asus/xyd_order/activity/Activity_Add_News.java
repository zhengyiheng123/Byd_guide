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
 * Created by Zheng on 2017/3/13.
 */
public class Activity_Add_News extends BaseActivity {
    @Bind(R.id.et_content)
    EditText et_content;

    @Bind(R.id.tv_submit)
    TextView tv_submit;

    @Override
    public void myOnclick(View view) {
        switch (view.getId()){
            case R.id.tv_submit:
                if(!TextUtils.isEmpty(et_content.getText().toString())){
                    sendMutual(et_content.getText().toString());
                }else {
                    toastShow("请填写内容");
                }
                break;
        }
    }

    @Override
    public void setToolbar() {
        TextView tv_title= (TextView) findViewById(R.id.tv_title);
        tv_title.setText("填写业内互助消息");
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(v -> {onBackPressed();});
        tv_submit.setVisibility(View.VISIBLE);
        tv_submit.setText("发表");
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_add_news;
    }


    @Override
    public int getData() throws Exception {
        return 0;
    }

    @Override
    public void initView() {

    }

    @Override
    public void setEvent() {
        tv_submit.setOnClickListener(this);
    }
    /**
     * 发表互助消息
     */
    private void sendMutual(String context){
        Observable<HttpResult> result= ServiceApi.getInstance().getServiceContract().sendMutualData(apitoken,context);
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
