package com.example.asus.xyd_order.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.net.BaseApi;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.GuideDetailsBean;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.ui.CircleImageView;
import com.example.asus.xyd_order.utils.TimeUtils;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/3/10.
 */
public class Activity_myguide_details extends BaseActivity {

    @Bind(R.id.tv_insurence)
    TextView tv_insurence;

    @Bind(R.id.iv_head)
    CircleImageView iv_head;

    @Bind(R.id.tv_name)
    TextView tv_name;

    @Bind(R.id.tv_gender)
    TextView tv_gender;

    @Bind(R.id.tv_email)
    TextView tv_email;

    @Bind(R.id.tv_company)
    TextView tv_company;

    @Bind(R.id.tv_live_city)
    TextView tv_live_city;

    @Bind(R.id.tv_car_grand)
    TextView tv_car_grand;

    @Bind(R.id.tv_car_factory)
    TextView tv_car_factory;

    @Bind(R.id.tv_servive_range)
    TextView tv_servive_range;

    @Bind(R.id.tv_career_time)
    TextView tv_career_time;
    private GuideDetailsBean bean1;

    @Override
    public void myOnclick(View view) {
        switch (view.getId()){
            case R.id.iv_head:
                Intent intent=new Intent(context,ImageViewActivity.class);
                intent.putExtra("path",BaseApi.getBaseUrl()+bean1.getAvatar());
                startActivity(intent);
                break;
        }
    }

    @Override
    public void setToolbar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(v -> {onBackPressed();});
        TextView tv_title= (TextView) findViewById(R.id.tv_title);
        tv_title.setText("导游详细资料");
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_myguide_details;
    }


    @Override
    public int getData() throws Exception {
        String user_id=getIntent().getStringExtra("user_id");
        getNetData(user_id);
        return 0;
    }

    @Override
    public void initView() {

    }

    @Override
    public void setEvent() {
        iv_head.setOnClickListener(this);
    }

    /**
     * 请求网络数据
     */
    private void getNetData(String user_id){
        Observable<HttpResult<GuideDetailsBean>> result= ServiceApi.getInstance().getServiceContract().guideDetails(user_id+"",apitoken);
            result.map(new ResultFilter<>())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<GuideDetailsBean>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            toastShow(e.getMessage());
                        }

                        @Override
                        public void onNext(GuideDetailsBean bean) {
                            bean1 = bean;
                            processData(bean);
                        }
                    });
    }

    private void processData(GuideDetailsBean bean) {
        Picasso.with(context).load(BaseApi.getBaseUrl()+bean.getAvatar()).into(iv_head);
        tv_name.setText(bean.getUser_name());
        if (!TextUtils.isEmpty(bean.getCar_birth())){
            tv_car_factory.setText(bean.getCar_birth());
        }else {
            tv_car_factory.setText("未设置");
        }
        if (!TextUtils.isEmpty(bean.getAttend_time())){
            tv_career_time.setText(bean.getAttend_time());
        }else {
            tv_career_time.setText("未设置");
        }
        tv_car_grand.setText(bean.getCar_brand());
        tv_company.setText(bean.getCompany());
        tv_email.setText(bean.getEmail());
        String gender=bean.getGender();
        if (gender.equals("1")){
            tv_gender.setText("男");
        }else if (gender.equals("0")){
            tv_gender.setText("女");
        }else {
            tv_gender.setText("性别：未设置");
        }
        String is_insured=bean.getIs_car_insured();
        if (is_insured.equals("0")){
            tv_insurence.setText("未上车辆保险");
        }else if (is_insured.equals("1")){
            tv_insurence.setText("已上车辆保险");
        }else {
            tv_insurence.setText("未设置");
        }
        tv_live_city.setText(bean.getLive_city());
    }
}
