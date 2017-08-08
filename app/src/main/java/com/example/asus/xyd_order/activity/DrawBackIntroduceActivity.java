package com.example.asus.xyd_order.activity;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.net.BaseApi;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.net.result.TuiShuiDetails;
import com.example.asus.xyd_order.ui.SmartImageveiw;
import com.example.asus.xyd_order.utils.Myutils;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/3/7.
 */
public class DrawBackIntroduceActivity extends BaseActivity {

    private SmartImageveiw iv_gaizhang,iv_draw;
    private String country_id;
    private TextView tv_point_name;
    private TextView tv_details;
    private Intent intent;
    private TuiShuiDetails details;
    private TextView tv_title;

    @Override
    public void myOnclick(View view) {
        switch (view.getId()){
            case R.id.iv_gaizhang:
                intent = new Intent(context,ImageViewActivity.class);
                intent.putExtra("path",BaseApi.getBaseUrl()+details.getStamp_img());
                startActivity(intent);
                break;
            case R.id.iv_draw:
                intent = new Intent(context,ImageViewActivity.class);
                intent.putExtra("path",BaseApi.getBaseUrl()+details.getDrawback_img());
                startActivity(intent);
                break;
        }
    }

    @Override
    public void setToolbar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(v -> {onBackPressed();});
        tv_title = (TextView) findViewById(R.id.tv_title);
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_drawable_introduce;
    }


    @Override
    public int getData() throws Exception {
        country_id = getIntent().getStringExtra("country_id");
        getNetDat();
        return 0;
    }

    @Override
    public void initView() {
        tv_point_name= (TextView) findViewById(R.id.tv_point_name);
        tv_details= (TextView) findViewById(R.id.tv_details);

        iv_gaizhang = (SmartImageveiw) findViewById(R.id.iv_gaizhang);
        ViewGroup.LayoutParams params1=iv_gaizhang.getLayoutParams();
        params1.height= ViewGroup.LayoutParams.WRAP_CONTENT;
        params1.width= Myutils.getScreenWidth(DrawBackIntroduceActivity.this)/2;
        iv_gaizhang.setLayoutParams(params1);
        iv_gaizhang.setRatio(1.8f);

        iv_draw= (SmartImageveiw) findViewById(R.id.iv_draw);
        ViewGroup.LayoutParams params2=iv_gaizhang.getLayoutParams();
        params2.height= ViewGroup.LayoutParams.WRAP_CONTENT;
        params2.width= Myutils.getScreenWidth(DrawBackIntroduceActivity.this)/2;
        iv_draw.setLayoutParams(params1);
        iv_draw.setRatio(1.8f);

    }

    @Override
    public void setEvent() {
        iv_gaizhang.setOnClickListener(this);
        iv_draw.setOnClickListener(this);
    }

    /**
     * 请求数据
     */
    private void getNetDat(){
        Observable<HttpResult<TuiShuiDetails>> result= ServiceApi.getInstance().getServiceContract().tuishuiDetails(country_id,apitoken);
        result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TuiShuiDetails>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        toastShow(e.getMessage());
                    }

                    @Override
                    public void onNext(TuiShuiDetails tuiShuiDetails) {
                        details = tuiShuiDetails;
                        tv_title.setText("退税说明详情");
                        tv_details.setText(tuiShuiDetails.getDp_desc());
                        tv_point_name.setText(tuiShuiDetails.getDp_address()+"  "+tuiShuiDetails.getDp_point());
                        Glide.with(context).load(BaseApi.getBaseUrl()+tuiShuiDetails.getDrawback_img()).into(iv_draw);
                        Glide.with(context).load(BaseApi.getBaseUrl()+tuiShuiDetails.getStamp_img()).into(iv_gaizhang);
                    }
                });
    }
}
