package com.example.asus.xyd_order.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.net.BaseApi;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.net.result.TrainSuccessBean;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/8/10.
 */

public class Activity_Train_Success extends BaseActivity {
    @Bind(R.id.iv_back)ImageView iv_back;

    @Bind(R.id.imageView6)ImageView imageView6;

    @Bind(R.id.tv_order_status)TextView tv_order_status;

    @Bind(R.id.tv_order_info)TextView tv_order_info;

    @Bind(R.id.imageView5)ImageView iv_head;

    @Bind(R.id.tv_mer_name)TextView tv_mer_name;

    @Bind(R.id.tv_route_name)TextView tv_route_name;

    @Bind(R.id.tv__address)TextView tv__address;

    @Bind(R.id.tv_phone)TextView tv_phone;

    @Bind(R.id.tv_ord_num)TextView tv_ord_num;

    @Bind(R.id.tv_price)TextView tv_price;

    @Bind(R.id.et_pay_type)TextView et_pay_type;

    @Bind(R.id.et_order_name)TextView et_order_name;


    @Bind(R.id.et_geted_phone)TextView et_geted_phone;

    @Bind(R.id.et_get_address)TextView et_get_address;

    @Bind(R.id.introduce)TextView introduce;
    @Bind(R.id.tv_title)TextView tv_title;
    @Bind(R.id.tv_cancel_order)TextView tv_cancel_order;
    @Bind(R.id.tv_group_num)TextView tv_group_num;

    @Bind(R.id.tv_date)TextView tv_date;
    @Bind(R.id.tv_start)TextView tv_start;
    @Bind(R.id.tv_start_time)TextView tv_start_time;
    @Bind(R.id.tv_spend)TextView tv_spend;
    @Bind(R.id.tv_end_station)TextView tv_end_station;
    @Bind(R.id.tv_end_time)TextView tv_end_time;

    private String ord_id;
    private TrainSuccessBean bean;

    @Override
    public void myOnclick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_cancel_order:
                Intent intent=new Intent(context,CancelActivity.class);
                intent.putExtra("ord_id",bean.getOrd_id()+"");
                intent.putExtra("cancel_type","0");
                startActivity(intent);
                break;
        }
    }

    @Override
    public void setToolbar() {
        tv_title.setText("预定已提交");
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_attraction_train_succed;
    }

    @Override
    public int getData() throws Exception {
        ord_id = getIntent().getStringExtra("ord_id");
        return 0;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getNetData(ord_id);
    }

    @Override
    public void initView() {

    }

    @Override
    public void setEvent() {
        iv_back.setOnClickListener(this);
        tv_cancel_order.setOnClickListener(this);
    }
    //请求网络数据
    private void getNetData(String ord_id){
        showDialog();
        Observable<HttpResult<TrainSuccessBean>> result= ServiceApi.getInstance().getServiceContract().orderTrain(ord_id,apitoken);
        result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TrainSuccessBean>() {
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
                    public void onNext(TrainSuccessBean nomalOrderSuccess) {
                        bean = nomalOrderSuccess;
                        Glide.with(context).load(BaseApi.getBaseUrl()+nomalOrderSuccess.getMerchant().getLogo_path()).into(iv_head);
                        tv_mer_name.setText(nomalOrderSuccess.getMerchant().getMer_name());
                        tv_route_name.setText(nomalOrderSuccess.getMerchant().getRoute_name());
                        tv__address.setText(nomalOrderSuccess.getMerchant().getAddress());
                        tv_phone.setText(nomalOrderSuccess.getMerchant().getMobile());
                        tv_ord_num.setText(nomalOrderSuccess.getOrd_num());
                        int pay_type=nomalOrderSuccess.getPay_type();
                        switch (pay_type){
                            case 1:
                                et_pay_type.setText("到店支付");
                                break;
                            case 2:
                                et_pay_type.setText("在线支付");
                                break;
                            case 3:
                                et_pay_type.setText("微信");
                                break;
                            case 4:
                                et_pay_type.setText("银联");
                                break;
                        }

                        et_order_name.setText(nomalOrderSuccess.getUser_name());
                        tv_price.setText(nomalOrderSuccess.getPrice());
                        et_geted_phone.setText(nomalOrderSuccess.getMobile());
                        et_get_address.setText(nomalOrderSuccess.getAddress());
                        if(nomalOrderSuccess.getIntroduction()!=null){
                            introduce.setText(nomalOrderSuccess.getIntroduction());
                        }else {
                            introduce.setText("无");
                        }
                        tv_date.setText(nomalOrderSuccess.getTicket().getDate());
                        tv_start.setText(nomalOrderSuccess.getTicket().getStart_station());
                        tv_start_time.setText(nomalOrderSuccess.getTicket().getTime_start());
                        tv_spend.setText(nomalOrderSuccess.getTicket().getTime_spend());
                        tv_end_station.setText(nomalOrderSuccess.getTicket().getEnd_station());
                        tv_end_time.setText(nomalOrderSuccess.getTicket().getTime_end());
                        int ord_status=nomalOrderSuccess.getOrd_status();
                        tv_group_num.setText(nomalOrderSuccess.getGroup_num());
                        switch (ord_status){
//                                -2|用户取消 -1|商家取消 0|待接单 1|待付款 2|已付款 3|已消费 4|已评价
                            case -2:
                                tv_order_status.setText("用户取消");
                                tv_order_info.setText("当前订单已被用户取消");
                                break;
                            case -1:
                                tv_order_status.setText("商家取消");
                                tv_order_info.setText("当前订单已被商家取消");
                                break;
                            case 0:
                                tv_order_status.setText("等待商家接单");
                                tv_order_info.setText("预定已提交，等待商家接单");
                                tv_cancel_order.setVisibility(View.VISIBLE);
                                break;
                            case 1:
                                tv_order_status.setText("待付款");
                                tv_order_info.setText("商家已接单，请尽快付款");
                                tv_cancel_order.setVisibility(View.VISIBLE);
                                break;
                            case 2:
                                tv_order_status.setText("已付款");
                                tv_order_info.setText("用户已付款，请准时消费");
                                break;
                            case 3:
                                tv_order_status.setText("已消费");
                                tv_order_info.setText("订单已消费，请对商家的服务做出评价");
                                break;
                            case 4:
                                tv_order_status.setText("已评价");
                                tv_order_info.setText("订单已评价，谢谢使用");
                                break;

                        }
                    }
                });
    }
}
