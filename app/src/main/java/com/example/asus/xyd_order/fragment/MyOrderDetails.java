package com.example.asus.xyd_order.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.activity.Activity_write_Opinion;
import com.example.asus.xyd_order.activity.CancelActivity;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.base.BaseArrayAdapter;
import com.example.asus.xyd_order.holder.OrderDetails_Single_Holder;
import com.example.asus.xyd_order.net.BaseApi;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.net.result.MyOrderBean;
import com.example.asus.xyd_order.net.result.Order_Info_Bean;
import com.example.asus.xyd_order.utils.ActivityFactory;
import com.example.asus.xyd_order.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/5/8.
 */

public class MyOrderDetails extends BaseActivity {
    @Bind(R.id.tv_comment)
    TextView tv_comment;
    @Bind(R.id.iv_img)
    ImageView iv_img;
    @Bind(R.id.tv_res_name)
    TextView tv_res_name;
    @Bind(R.id.child)
    TextView child;
    @Bind(R.id.tv_address)
    TextView tv_address;
    @Bind(R.id.tv_mobile)
    TextView tv_mobile;
    @Bind(R.id.tv_group_num)
    TextView tv_group_num;
    @Bind(R.id.tv_ord_num)
    TextView tv_ord_num;
    @Bind(R.id.tv_eat_sum)
    TextView tv_eat_sum;
    @Bind(R.id.tv_all_money)
    TextView tv_all_money;
    @Bind(R.id.tv_order_time)
    TextView tv_order_time;
    @Bind(R.id.arrive_time)
    TextView arrive_time;
    @Bind(R.id.tv_paytype)
    TextView tv_paytype;
    @Bind(R.id.tv_leave_message)
    TextView tv_leave_message;
    @Bind(R.id.tv_username)
    TextView tv_username;
    @Bind(R.id.tv_order_phone)
    TextView tv_order_phone;
    @Bind(R.id.tv_ord_status)
    TextView tv_ord_status;
    @Bind(R.id.iv_back)
    ImageView iv_back;

    @Bind(R.id.tv_cancel)
    TextView tv_cancel;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.mylistview_single)
    ListView mylistview_single;
    private String ord_id;
    //数据
    List<Order_Info_Bean.SingleMealBean> singleMealBeen=new ArrayList<>();
    private BaseArrayAdapter adapter;
    private int ord_status;
    private Order_Info_Bean bean1;

    @Override
    public void myOnclick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.tv_comment:
                if (ord_status==0 || ord_status == 1){
                    //取消订单
                    intent=new Intent(context, CancelActivity.class);
                    intent.putExtra("ord_id",bean1.getOrd_id()+"");
                    intent.putExtra("cancel_type","1");
                    startActivity(intent);
                }else if (ord_status == 3){
                    //评论
                    intent=new Intent(context, Activity_write_Opinion.class);
                    intent.putExtra("ord_id",bean1.getOrd_id());
                    intent.putExtra("commentType","1");
                    intent.putExtra("dmd_id",bean1.getMer_id()+"");
                    context.startActivity(intent);
                }
                break;
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getNetData();
    }

    @Override
    public void setToolbar() {
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_myorder_details;
    }


    @Override
    public int getData() throws Exception {
        ord_id = getIntent().getStringExtra("ord_id");
        ord_status = getIntent().getIntExtra("ord_status",5);
        if (ord_status==0 || ord_status == 1){
            tv_comment.setText("取消订单");
        }
        else if (ord_status == -2 || ord_status == -1 || ord_status == 2 || ord_status == 4){
            tv_comment.setVisibility(View.GONE);
        }
        else if (ord_status == 3){
            tv_comment.setText("评价");
        }
        return 0;
    }

    @Override
    public void initView() {
        tv_title.setText("详情");
        adapter = new BaseArrayAdapter(context, new BaseArrayAdapter.OnCreateViewHolderListener() {
            @Override
            public Object onCreateViewHolder() {
                return new OrderDetails_Single_Holder();
            }
        },singleMealBeen);
        mylistview_single.setAdapter(adapter);
    }

    @Override
    public void setEvent() {
        tv_comment.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }

    /**
     * 获取网络数据
     */
    private void getNetData(){
        Observable<HttpResult<Order_Info_Bean>> result= ServiceApi.getInstance().getServiceContract().orderInfo(ord_id,apitoken);
        result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Order_Info_Bean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        toastShow(e.getMessage());
                    }

                    @Override
                    public void onNext(Order_Info_Bean bean) {
                        bean1 = bean;
                        Glide.with(context).load(BaseApi.getBaseUrl()+bean.getMerchant().getLogo_path()).into(iv_img);
                        tv_res_name.setText(bean.getMerchant().getMer_name());
                        if (bean.getMeal_type()!=null){
                            if (bean.getMeal_type().equals("1")){
                                //团餐
                                child.setText(bean.getGroup_meal().getMeal_name()+" "+bean.getGroup_meal().getMeal_price()+"/人");
                            }else if (bean.getMeal_type().equals("2")){
                                //单点
                                child.setText("单点");
                                singleMealBeen.clear();
                                singleMealBeen.addAll(bean.getSingle_meal());
                                adapter.notifyDataSetChanged();
                            }
                            tv_address.setText(bean.getMerchant().getAddress());
                            tv_mobile.setText(bean.getMerchant().getMobile());
                            tv_group_num.setText(bean.getGroup_num());
                            tv_ord_num.setText(bean.getOrd_num()+"");
                            tv_eat_sum.setText(bean.getSeat_cost()+"人");
                            tv_all_money.setText(bean.getPrice());
                            tv_order_time.setText(TimeUtils.stampToDateSs(bean.getAdd_time()+""));
                            arrive_time.setText(TimeUtils.stampToDateSs(bean.getBook_time()+""));
                            int paytype=bean.getPay_type();
                            switch (paytype){
                                case 1:
                                    //到店支付
                                    tv_paytype.setText("到店支付");
                                    break;
                                case 2:
                                    tv_paytype.setText("支付宝");
                                    //支付宝
                                    break;
                                case 3:
                                    tv_paytype.setText("微信");
                                    //微信
                                    break;
                                case 4:
                                    tv_paytype.setText("银联");
                                    //银联
                                    break;
                            }
                            tv_leave_message.setText(bean.getMessage());
                            tv_username.setText(bean.getUser_name());
                            tv_order_phone.setText(bean.getMobile());
                            int ord_status=bean.getOrd_status();
                            switch (ord_status){
                                case -2:
                                    //用户取消
                                    tv_ord_status.setText("用户取消");
                                    tv_cancel.setVisibility(View.VISIBLE);
                                    tv_cancel.setText("用户取消");
                                    break;
                                case -1:
                                    tv_ord_status.setText("商家取消");
                                    tv_cancel.setVisibility(View.VISIBLE);
                                    tv_cancel.setText("商家取消");
                                    break;
                                case 0:
                                    tv_ord_status.setText("待接单");
                                    break;
                                case 1:
                                    tv_ord_status.setText("待付款");
                                    break;
                                case 2:
                                    tv_ord_status.setText("用户已付款");
                                    break;
                                case 3:
                                    tv_ord_status.setText("未评价");
                                    break;
                                case 4:
                                    tv_ord_status.setText("已评价");
                                    break;
                            }
                        }
                    }
                });
    }
}
