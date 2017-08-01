package com.example.asus.xyd_order.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.net.BaseApi;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.HospitaiDetails;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.utils.common.PermissionManager;
import com.example.asus.xyd_order.utils.common.PermissionResult;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/8/1.
 */

public class Activity_HospitalDetails extends BaseActivity {

    private String hospital_id;

    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.tv_prience)
    TextView tv_prience;
    @Bind(R.id.tv_desc)
    TextView tv_desc;
    @Bind(R.id.tv_location)
    TextView tv_location;
    @Bind(R.id.tv_tel)
    TextView tv_tel;
    @Bind(R.id.tv_h_name)
    TextView tv_h_name;
    @Bind(R.id.tv_name)
    TextView tv_name;
    @Bind(R.id.iv_head)
    ImageView iv_head;
    @Bind(R.id.rl_call)
    RelativeLayout rl_call;
    private HospitaiDetails hospitaiDetails;

    @Override
    public void myOnclick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
            onBackPressed();
            break;
            case R.id.rl_call:
                if (!TextUtils.isEmpty(hospitaiDetails.getHos_phone())){
                    PermissionManager.with(Activity_HospitalDetails.this).request(new PermissionManager.Callback() {
                        @Override
                        public void call(PermissionResult result) {
                            if (result.isSuccessful()){
                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                Uri data = Uri.parse("tel:" + hospitaiDetails.getHos_phone());
                                intent.setData(data);
                                context.startActivity(intent);
                            }else {
                                toastShow("没有拨打电话权限");
                            }

                        }
                    }, Manifest.permission.CALL_PHONE);
                }
                break;
        }
    }

    @Override
    public void setToolbar() {
        iv_back.setOnClickListener(this);
        tv_title.setText("医院详情");
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_hospitaldetails;
    }

    @Override
    public int getData() throws Exception {
        hospital_id = getIntent().getStringExtra("hospital_id");
        getNetData();
        return 0;
    }

    @Override
    public void initView() {

    }

    @Override
    public void setEvent() {
        rl_call.setOnClickListener(this);

    }
    /**
     * 获取网络数据
     */
    private void getNetData(){
        Observable<HttpResult<HospitaiDetails>> result= ServiceApi.getInstance().getServiceContract().hospitalDetails(hospital_id,apitoken);
        result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HospitaiDetails>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HospitaiDetails bean) {
                        hospitaiDetails = bean;
                        tv_title.setText(bean.getHos_name());
                        tv_h_name.setText(bean.getHos_type());
                        Glide.with(context).load(BaseApi.getBaseUrl()+bean.getImg_path()).into(iv_head);
                        tv_tel.setText(bean.getHos_phone());
                        tv_location.setText(bean.getHos_address());
                        tv_desc.setText(bean.getTime());
                        tv_prience.setText(bean.getExperience());
                    }
                });
    }
}
