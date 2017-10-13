package com.example.asus.xyd_order.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.net.result.Name;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/7/4.
 */

public class Activity_write_Opinion extends BaseActivity {
    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.tv_submit)
    TextView tv_submit;

    private String dmd_id;

    @Bind(R.id.ratingbar)
    RatingBar ratingbar;

    @Bind(R.id.tv_count)
    TextView tv_count;

    @Bind(R.id.tv_add_friend)
    TextView tv_add_friend;

    @Bind(R.id.et_comment)
    EditText et_comment;
    private String to_user_id;
    private String commentType;
    private String ord_id;

    @Override
    public void myOnclick(View view) {
        switch (view.getId()){
            case R.id.tv_submit:
                if (TextUtils.isEmpty(et_comment.getText().toString())){
                    toastShow("请填写意见");
                    return;
                }
                if (commentType.equals("1"))
                {
                    //餐饮
                    commentRestaurant(ratingbar.getRating()+"",et_comment.getText().toString());

                }else if (commentType.equals("0")){
                    //景点
                    commentJingdian(ratingbar.getRating()+"",et_comment.getText().toString());
                }
                else {
                    //其他
                    comment(ratingbar.getRating()+"",et_comment.getText().toString());
                }
                break;
            case R.id.tv_add_friend:
                addFriend();
                break;
        }
    }

    /**
     * 添加至导游圈
     */
    private void addFriend(){
        Observable<HttpResult> result=ServiceApi.getInstance().getServiceContract().addtoFriend(apitoken,to_user_id);
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
                        toastShow("添加成功");
                    }
                });
    }
    @Override
    public void setToolbar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(v -> {onBackPressed();});
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("填写意见单");
        tv_submit = (TextView) findViewById(R.id.tv_submit);
        tv_submit.setVisibility(View.VISIBLE);
        tv_submit.setText("发表");
    }


    @Override
    protected int getResourceId() {
        return R.layout.activity_publish_comment;
    }

    @Override
    public int getData() throws Exception {
        dmd_id = getIntent().getStringExtra("dmd_id");
        to_user_id = getIntent().getStringExtra("to_user_id");
        ord_id = getIntent().getStringExtra("ord_id");
        //commentType 评论类型1：餐饮，0：景点
        commentType = getIntent().getStringExtra("commentType");
        if (!TextUtils.isEmpty(to_user_id)){
            getName();
        }
        return 0;
    }

    /**
     * 获取发单人
     */
    private void getName(){
        Observable<HttpResult<Name>> result=ServiceApi.getInstance().getServiceContract().getName(apitoken,dmd_id);
        result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Name>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Name name) {
                        tv_add_friend.setText("将"+name.getUser_name()+"添加到导游圈");
                    }
                });
    }
    @Override
    public void initView() {
        ButterKnife.bind(this);
        if(!TextUtils.isEmpty(to_user_id)){
            tv_add_friend.setVisibility(View.VISIBLE);
        }else {
            tv_add_friend.setVisibility(View.GONE);
        }
    }
    @Override
    public void setEvent() {
        tv_submit.setOnClickListener(this);
        tv_add_friend.setOnClickListener(this);
        ratingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                tv_count.setText(v+"分");
            }
        });
    }
    /**
     * 发表意见单  普通
     */
    private void comment(String rank,String comment) {
        Observable<HttpResult> result = ServiceApi.getInstance().getServiceContract().comment(apitoken, dmd_id, rank, comment);
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
                        toastShow("发表成功");
                        finish();
                    }
                });
    }

        /**
         * 发表意见单  景点
         */
    private void commentJingdian(String rank,String comment) {
        Observable<HttpResult> result = ServiceApi.getInstance().getServiceContract().commentJingdian(apitoken, dmd_id, rank, comment,ord_id);
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
                        toastShow("发表成功");
                        finish();
                    }
                });
    }
        /**
         * 发表意见单  餐厅
         */
    private void commentRestaurant(String rank,String comment){
        Observable<HttpResult> result= ServiceApi.getInstance().getServiceContract().commentRestaurant(apitoken,dmd_id,rank,comment,ord_id);
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
                        toastShow("发表成功");
                        finish();
                    }
                });
    }
}
