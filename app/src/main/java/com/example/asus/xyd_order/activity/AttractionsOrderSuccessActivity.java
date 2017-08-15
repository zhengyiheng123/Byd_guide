package com.example.asus.xyd_order.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.base.BaseArrayAdapter;
import com.example.asus.xyd_order.holder.SiteDisplayHolder2;
import com.example.asus.xyd_order.net.BaseApi;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.AttractionsOrderSuccess;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.ui.MyListView;
import com.example.asus.xyd_order.ui.SmartImageveiw;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/7/18.
 */

public class AttractionsOrderSuccessActivity extends BaseActivity {

    private String order_id;
    @Bind(R.id.imageView6)
    ImageView imageView6;
    @Bind(R.id.imageView5)
    SmartImageveiw imageView5;

    @Bind(R.id.tv_order_status)
    TextView tv_order_status;
    @Bind(R.id.tv_order_info)
    TextView tv_order_info;

    @Bind(R.id.tv_mer_name)
    TextView tv_mer_name;

    @Bind(R.id.tv_route_name)
    TextView tv_route_name;

    @Bind(R.id.tv_address)
    TextView tv_address;

    @Bind(R.id.tv_time)
    TextView tv_time;

    @Bind(R.id.tv_phone)
    TextView tv_phone;

    @Bind(R.id.tv_ord_num)
    TextView tv_ord_num;

    @Bind(R.id.tv_price)
    TextView tv_price;

    @Bind(R.id.tv_cancel_order)
    TextView tv_cancel_order;

    @Bind(R.id.et_pay_type)
    EditText et_pay_type;

    @Bind(R.id.et_order_name)
    EditText et_order_name;

    @Bind(R.id.et_geted_phone)
    EditText et_geted_phone;

    @Bind(R.id.et_get_address)
    EditText et_get_address;

    @Bind(R.id.mylistview_group)
    MyListView mylistview_group;

    @Bind(R.id.mylistview_nomal)
    MyListView mylistview_nomal;

    @Bind(R.id.tv_group_num)
    TextView tv_group_num;
    private AttractionsOrderSuccess bean;

    @Override
    public void myOnclick(View view) {
        switch (view.getId()){
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
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(view -> onBackPressed());
        TextView tv_title= (TextView) findViewById(R.id.tv_title);
        tv_title.setText("预订已提交");
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_attractionsordersuccess;
    }

    @Override
    public int getData() throws Exception {
        order_id = getIntent().getStringExtra("order_id");
        getNetData();
        return 0;
    }

    @Override
    public void initView() {
        ScrollView scroll= (ScrollView) findViewById(R.id.scroll);
        scroll.smoothScrollTo(0,0);
    }

    private void initListView() {
        if (bean.getGroup_ticket().size()>0){
            mylistview_group.setAdapter(new BaseArrayAdapter<>(context, new BaseArrayAdapter.OnCreateViewHolderListener() {
                @Override
                public Object onCreateViewHolder() {
                    return new SiteDisplayHolder2();
                }
            },bean.getGroup_ticket()));
        }

            if (bean.getNormal_ticket().size()>0){
                mylistview_nomal.setAdapter(new BaseArrayAdapter<>(context, new BaseArrayAdapter.OnCreateViewHolderListener() {
                    @Override
                    public Object onCreateViewHolder() {
                        return new SiteDisplayHolder2();
                    }
                },bean.getNormal_ticket()));
            }

    }

    @Override
    public void setEvent() {
        tv_cancel_order.setOnClickListener(this);
    }

    //获取网络数据
    private void getNetData(){
        Observable<HttpResult<AttractionsOrderSuccess>> result= ServiceApi.getInstance().getServiceContract().orderSuccess(order_id,apitoken);
        result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AttractionsOrderSuccess>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(AttractionsOrderSuccess s) {
                        bean = s;
                        Glide.with(context).load(BaseApi.getBaseUrl()+s.getMerchant().getLogo_path()).into(imageView5);
                        tv_address.setText(s.getMerchant().getAddress());
                        if (s.getMerchant().getDate_time()!=null){
                            tv_time.setText(s.getMerchant().getDate_time());
                        }
                        tv_mer_name.setText(s.getMerchant().getMer_name());
                        tv_ord_num.setText(s.getOrd_num());
                        tv_phone.setText(s.getMerchant().getMobile());
                        tv_price.setText(s.getPrice());
                        tv_route_name.setText(s.getMerchant().getRoute_name());

                        et_order_name.setText(s.getUser_name());
                        et_geted_phone.setText(s.getMobile());
                        et_get_address.setText(s.getAddress());

                        int pay_type=s.getPay_type();
                        switch (pay_type){
                            case 1:
                                et_pay_type.setText("到店支付");
                                break;
                            case 2:
                                et_pay_type.setText("支付宝");
                                break;
                            case 3:
                                et_pay_type.setText("微信");
                                break;
                            case 4:
                                et_pay_type.setText("银联");
                                break;
                        }
                        int ord_status=s.getOrd_status();
                        tv_group_num.setText(s.getGroup_num());
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
                                break;
                            case 1:
                                tv_order_status.setText("待付款");
                                tv_order_info.setText("商家已接单，请尽快付款");
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
                                tv_order_status.setText("已评价接单");
                                tv_order_info.setText("订单已评价，谢谢使用");
                                break;

                        }
                        initListView();
                    }
                });
    }
}
