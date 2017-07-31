package com.example.asus.xyd_order.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.dialog.CommonDialog;
import com.example.asus.xyd_order.net.BaseApi;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.net.result.Order_Info_Bean;
import com.example.asus.xyd_order.utils.TimeUtils;

import java.text.ParseException;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/2/27.
 */
public class ConfirmOrderActivity extends BaseActivity {
    @Bind(R.id.tv_cancel_order)
    TextView tv_cancel_order;

    @Bind(R.id.tv_order_status)
    TextView tv_order_status;

    @Bind(R.id.tv_order_info)
    TextView tv_order_info;

    @Bind(R.id.rl_single)
    RelativeLayout rl_single;
    @Bind(R.id.tv_ord_num)
    TextView tv_ord_num;

    @Bind(R.id.tv_people_count)
    TextView tv_people_count;

    @Bind(R.id.tv_res_name)
    TextView tv_res_name;

    @Bind(R.id.tv_book_time)
    TextView tv_book_time;

    @Bind(R.id.tv_name)
    TextView tv_name;

    @Bind(R.id.tv_phone)
    TextView tv_phone;

    @Bind(R.id.tv_message)
    TextView tv_message;

    @Bind(R.id.imageView5)
    ImageView imageView5;

    @Bind(R.id.tv_paytype)
    TextView tv_paytype;

    @Bind(R.id.res_info)
    TextView res_info;

    @Bind(R.id.tv_total_money)
    TextView tv_total_money;

    @Bind(R.id.tv_group_num)
    TextView tv_group_num;

    @Bind(R.id.iv_cai_img)
    ImageView iv_cai_img;
    private CommonDialog commonDialog;

    @Bind(R.id.iv_back)
    ImageView iv_bacl;

    @Bind(R.id.tv_title)
    TextView tv_title;
    private String ord_id;


    @Override
    public void myOnclick(View view) {
        switch (view.getId()){
            case R.id.tv_cancel_order:
                commonDialog = new CommonDialog(ConfirmOrderActivity.this, R.style.MyDialog);
                commonDialog.show();
                break;
        }
    }

    @Override
    public void setToolbar() {
        tv_title.setText("预定已提交");
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_confirm_order;
    }


    @Override
    public int getData() throws Exception {
        ord_id = getIntent().getStringExtra("ord_id");
        getNetData();
        return 0;
    }

    @Override
    public void initView() {

    }

    @Override
    public void setEvent() {
        tv_cancel_order.setOnClickListener(this);
        iv_bacl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (commonDialog!=null){
            commonDialog=null;
        }
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
                    public void onNext(Order_Info_Bean order_info_bean) {
                        if(order_info_bean.getMeal_type().equals("2")){
                            rl_single.setVisibility(View.GONE);
                        }
//                        if (order_info_bean.getMeal_type() .equals("1") ){
                            //团餐
                            tv_res_name.setText(order_info_bean.getMerchant().getMer_name());
                            res_info.setText(order_info_bean.getGroup_meal().getMeal_name()+"  "+order_info_bean.getGroup_meal().getMeal_price()+"/人");
                            Glide.with(context).load(BaseApi.getBaseUrl()+order_info_bean.getGroup_meal().getImg_path()).into(iv_cai_img);
                            tv_group_num.setText(order_info_bean.getGroup_num());
                            tv_ord_num.setText(order_info_bean.getOrd_num());
                            tv_people_count.setText(order_info_bean.getSeat_cost()+"");
                            tv_total_money.setText(order_info_bean.getPrice());
                            tv_message.setText(order_info_bean.getMessage());
                            tv_name.setText(order_info_bean.getUser_name());
                            tv_phone.setText(order_info_bean.getMobile());

//                            支付方式: 1|到店支付 2|支付宝 3|微信 4|银联
                            int paytype=order_info_bean.getPay_type();
                            switch (paytype){
                                case 1:
                                    tv_paytype.setText("到店支付");
                                    break;
                                case 2:
                                    tv_paytype.setText("支付宝");
                                    break;
                                case 3:
                                    tv_paytype.setText("微信");
                                    break;
                                case 4:
                                    tv_paytype.setText("银联");
                                    break;

                            }
                            tv_book_time.setText(TimeUtils.stampToDateSs(String.valueOf(order_info_bean.getBook_time())));
                            int ord_status=order_info_bean.getOrd_status();
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
                                    tv_order_status.setText("待评价");
                                    tv_order_info.setText("订单已消费，请对商家的服务做出评价");
                                    break;
                                case 4:
                                    tv_order_status.setText("已评价");
                                    tv_order_info.setText("订单已评价，谢谢使用");
                                    break;

                            }
                        }

//                    }
                });
    }
}
