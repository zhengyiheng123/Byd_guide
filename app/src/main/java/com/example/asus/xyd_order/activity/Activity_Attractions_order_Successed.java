package com.example.asus.xyd_order.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.adapter.ExpandableAttractListviewAdapter;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.base.BaseArrayAdapter;
import com.example.asus.xyd_order.controler.CategoryControl;
import com.example.asus.xyd_order.dialog.CommonDialog;
import com.example.asus.xyd_order.holder.TicketItemHolder;
import com.example.asus.xyd_order.net.BaseApi;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.BaseTicketBean;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.net.result.NomalOrderSuccess;
import com.example.asus.xyd_order.ui.MyExpandListView;
import com.example.asus.xyd_order.ui.MyListView;
import com.example.asus.xyd_order.ui.SmartImageveiw;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/3/3.
 */
public class Activity_Attractions_order_Successed extends BaseActivity {

    private TextView tv_cancel_order,tv_nomal_count
            ,tv_group_count,tv_mer_name,tv_time
            ,tv_route_name,tv_phone,tv_address,tv_ord_num,introduce,tv_price,tv_order_status,tv_order_info;
    private MyListView mylistview_group,mylistview_nomal;
    private String ord_id;

    private List<BaseTicketBean> groupList=new ArrayList<>();
    private List<BaseTicketBean> nomalList=new ArrayList<>();
    private BaseArrayAdapter adapterGroup,adapterNomal;
    private SmartImageveiw iv_head;
    private EditText et_order_name,et_get_name,et_geted_phone,et_leave_message,et_get_address,et_pay_type;
    private NomalOrderSuccess bean;

    public static Activity_Attractions_order_Successed instance;

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
        iv_back.setOnClickListener(v -> {onBackPressed();});
        TextView tv_title= (TextView) findViewById(R.id.tv_title);
        tv_title.setText("预定已提交");
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_attraction_succed;
    }

    @Override
    protected void onResume() {
        super.onResume();
        instance=this;
    }

    @Override
    public int getData() throws Exception {
        ord_id = getIntent().getStringExtra("ord_id");
        getNetData();
        return 0;
    }

    @Override
    public void initView() {
        tv_cancel_order = (TextView) findViewById(R.id.tv_cancel_order);
        tv_group_count= (TextView) findViewById(R.id.tv_group_count);
        tv_nomal_count= (TextView) findViewById(R.id.tv_nomal_count);
        tv_mer_name= (TextView) findViewById(R.id.tv_mer_name);
        tv_route_name= (TextView) findViewById(R.id.tv_route_name);
        tv_time= (TextView) findViewById(R.id.tv_time);
        tv_price= (TextView) findViewById(R.id.tv_price);
        tv_order_status= (TextView) findViewById(R.id.tv_order_status);
        tv_order_info= (TextView) findViewById(R.id.tv_order_info);
        ScrollView scroll= (ScrollView) findViewById(R.id.scroll);
        scroll.smoothScrollTo(0,0);
        iv_head = (SmartImageveiw) findViewById(R.id.imageView5);
        tv_phone= (TextView) findViewById(R.id.tv_phone);
        tv_address= (TextView) findViewById(R.id.tv__address);
        tv_ord_num= (TextView) findViewById(R.id.tv_ord_num);
        introduce= (TextView) findViewById(R.id.introduce);
        et_order_name = (EditText) findViewById(R.id.et_order_name);
        et_get_name= (EditText) findViewById(R.id.et_get_name);
        et_geted_phone= (EditText) findViewById(R.id.et_geted_phone);
        et_leave_message= (EditText) findViewById(R.id.et_leave_message);
        et_get_address= (EditText) findViewById(R.id.et_get_address);
        et_pay_type= (EditText) findViewById(R.id.et_pay_type);
        iv_head.setRatio(1.7f);
        initListView();
    }
    //初始化Listview
    private void initListView() {
        mylistview_group = (MyListView) findViewById(R.id.mylistview_group);
        mylistview_nomal= (MyListView) findViewById(R.id.mylistview_nomal);
        adapterGroup = new BaseArrayAdapter(context, new BaseArrayAdapter.OnCreateViewHolderListener() {
            @Override
            public Object onCreateViewHolder() {
                return new TicketItemHolder();
            }
        },groupList);
        adapterNomal=new BaseArrayAdapter(context, new BaseArrayAdapter.OnCreateViewHolderListener() {
            @Override
            public Object onCreateViewHolder() {
                return new TicketItemHolder();
            }
        },nomalList);
        mylistview_nomal.setAdapter(adapterNomal);
        mylistview_group.setAdapter(adapterGroup);
    }

    @Override
    public void setEvent() {
        tv_cancel_order.setOnClickListener(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    /**
     * 获取网络数据
     */
    private void getNetData(){
        Observable<HttpResult<NomalOrderSuccess>> result= ServiceApi.getInstance().getServiceContract().nomalOrderSuccess(ord_id,apitoken);
        result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<NomalOrderSuccess>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(NomalOrderSuccess nomalOrderSuccess) {
                        bean = nomalOrderSuccess;
                        Glide.with(context).load(BaseApi.getBaseUrl()+nomalOrderSuccess.getMerchant().getLogo_path()).into(iv_head);
                        tv_mer_name.setText(nomalOrderSuccess.getMerchant().getMer_name());
                        tv_route_name.setText(nomalOrderSuccess.getMerchant().getRoute_name());
                        tv_address.setText(nomalOrderSuccess.getMerchant().getAddress());
                        tv_phone.setText(nomalOrderSuccess.getMerchant().getMobile());
                        tv_ord_num.setText(nomalOrderSuccess.getOrd_num());
                        int pay_type=nomalOrderSuccess.getPay_type();
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

                        et_order_name.setText(nomalOrderSuccess.getUser_name());
                        tv_price.setText(nomalOrderSuccess.getPrice());

                        et_geted_phone.setText(nomalOrderSuccess.getMobile());
                        if (!TextUtils.isEmpty(nomalOrderSuccess.getAddress())){
                            et_get_address.setText(nomalOrderSuccess.getAddress());
                        }
                        introduce.setText(nomalOrderSuccess.getIntroduction());
                        tv_nomal_count.setText(nomalOrderSuccess.getNormal_ticket().size()+"张");
                        tv_group_count.setText(nomalOrderSuccess.getGroup_ticket().size()+"张");
                        groupList.clear();
                        nomalList.clear();
                        groupList.addAll(nomalOrderSuccess.getGroup_ticket());
                        nomalList.addAll(nomalOrderSuccess.getNormal_ticket());
                        adapterGroup.notifyDataSetChanged();
                        adapterNomal.notifyDataSetChanged();
                        int ord_status=nomalOrderSuccess.getOrd_status();
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
