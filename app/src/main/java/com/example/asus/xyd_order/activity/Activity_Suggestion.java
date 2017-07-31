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
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/3/9.
 */
public class Activity_Suggestion extends BaseActivity {
    @Bind(R.id.et_suggestion)
    EditText et_suggestion;

    private TextView tv_submit;

    @Override
    public void myOnclick(View view) {
        switch (view.getId()){
            case R.id.tv_submit:
                if (TextUtils.isEmpty(et_suggestion.getText().toString())){
                    return;
                }
                submitSuggestion(et_suggestion.getText().toString());
                break;
        }
    }

    @Override
    public void setToolbar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(v -> {onBackPressed();});
        TextView tv_title= (TextView) findViewById(R.id.tv_title);
        tv_title.setText("意见反馈");
        tv_submit = (TextView) findViewById(R.id.tv_submit);
        tv_submit.setOnClickListener(this);
        tv_submit.setVisibility(View.VISIBLE);
        tv_submit.setText("提交");
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_suggestion;
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
    }
    /**
     *  提交反馈信息
     */
    private void submitSuggestion(String content){
        Observable<HttpResult> result= ServiceApi.getInstance().getServiceContract().submitSuggestion(apitoken,content);
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
                        toastShow("提交成功");
                        finish();
                    }
                });
    }
}
