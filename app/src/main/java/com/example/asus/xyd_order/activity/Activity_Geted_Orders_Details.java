package com.example.asus.xyd_order.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.dialog.ContactDialog;
import com.example.asus.xyd_order.fragment.GetedFragment_All;
import com.example.asus.xyd_order.net.BaseApi;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.Demand_Details_Bean;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.utils.TimeUtils;
import com.example.asus.xyd_order.utils.ToastUtils;

import butterknife.Bind;
import cn.leo.photopicker.crop.CropUtil;
import me.leefeng.promptlibrary.PromptDialog;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/7/6.
 */

public class Activity_Geted_Orders_Details extends BaseActivity {
    @Bind(R.id.tv_tuanhao)
    TextView tv_tuanhao;

    @Bind(R.id.ll_process)
    LinearLayout ll_process;

    @Bind(R.id.ll_container)
    LinearLayout ll_container;
    @Bind(R.id.tv_country)
    TextView tv_country;

    @Bind(R.id.tv_service_type)
    TextView tv_service_type;

    @Bind(R.id.tv_car_type)
    TextView tv_car_type;

    @Bind(R.id.tv_level)
    TextView tv_level;

    @Bind(R.id.tv_publish_man)
    TextView tv_publish_man;

    @Bind(R.id.tv_company)
    TextView tv_company;

    @Bind(R.id.tv_time)
    TextView tv_time;

    @Bind(R.id.tv_details)
    TextView tv_details;

    @Bind(R.id.tv_travel_details)
    TextView tv_travel_details;

    @Bind(R.id.tv_all_money)
    TextView tv_all_money;

    @Bind(R.id.tv_pay_type)
    TextView tv_pay_type;

    @Bind(R.id.tv_connect)
    TextView tv_connect;

    @Bind(R.id.tv_cancel)
    TextView tv_cancel;
    @Bind(R.id.tv_ord_state)
    TextView tv_ord_state;

    @Bind(R.id.tv_finish)
    TextView tv_finish;

    private String dmd_id;
    private String status;
    private ContactDialog dialog;
    private Demand_Details_Bean details_bean;

    @Override
    public void myOnclick(View view) {
        switch (view.getId()){
            case R.id.tv_finish:
                android.app.AlertDialog.Builder builder1=new android.app.AlertDialog.Builder(Activity_Geted_Orders_Details.this);
                builder1.setMessage("确认完成？");
                builder1.setNegativeButton("取消", (dialog, which) -> {dialog.dismiss();});
                builder1.setPositiveButton("确定",(dialog, which) -> finishedDemand());
                builder1.show();
                break;
            case R.id.tv_cancel:
                AlertDialog.Builder builder=new AlertDialog.Builder(Activity_Geted_Orders_Details.this);
                builder.setTitle("确定取消吗");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        cancel();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
                break;
            case R.id.tv_connect:
                dialog = new ContactDialog(Activity_Geted_Orders_Details.this, R.style.MyDialog,details_bean.getMobile(),"",details_bean.getDmd_id()+"");
                dialog.show();
                break;
        }
    }

    @Override
    public void setToolbar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(view -> onBackPressed());
        TextView tv_title= (TextView) findViewById(R.id.tv_title);
        tv_title.setText("我接的单详情");
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_geted_orders_details;
    }


    @Override
    public int getData() throws Exception {
        dmd_id = getIntent().getStringExtra("dmd_id");
        status = getIntent().getStringExtra("status");
        //订单状态 -2|发布者取消 -1|接受者取消 0|待接单 1|已被接单 2|已完成 3|已评价
        switch (Integer.parseInt(status)){
            case -2:
                tv_ord_state.setText("订单状态：发布者取消");
                ll_process.setVisibility(View.GONE);
                break;
            case -1:
                tv_ord_state.setText("订单状态：我已取消");
                ll_process.setVisibility(View.GONE);
                break;
            case 0:
                tv_ord_state.setText("订单状态：待接单");
                ll_process.setVisibility(View.VISIBLE);
                break;
            case 1:
                tv_ord_state.setText("订单状态：已接单");
                ll_process.setVisibility(View.VISIBLE);
                break;
            case 2:
                tv_ord_state.setText("订单状态：已完成");
                ll_process.setVisibility(View.GONE);
                break;
            case 3:
                tv_ord_state.setText("订单状态：已评价");
                ll_process.setVisibility(View.GONE);
                break;
        }
        getNetData();
        return 0;
    }

    @Override
    public void initView() {

    }

    @Override
    public void setEvent() {
        tv_cancel.setOnClickListener(this);
        tv_finish.setOnClickListener(this);
        tv_connect.setOnClickListener(this);
    }

    /**
     * 获取网络数据
     */
    private void getNetData(){
        Observable<HttpResult<Demand_Details_Bean>> result= ServiceApi.getInstance().getServiceContract().getOrderDetails(dmd_id,apitoken);
        result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Demand_Details_Bean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Demand_Details_Bean bean) {
                        details_bean = bean;
                        tv_tuanhao.setText("团号："+bean.getGroup_num());
                        tv_all_money.setText("总报价："+bean.getPrice());
                        tv_company.setText("所属旅行社："+bean.getCompany());
                        tv_details.setText(bean.getDmd_desc());
                        tv_publish_man.setText("发布人："+bean.getUser_name());
                        tv_time.setText("开始/结束:"+ TimeUtils.stampToDateS(bean.getStart_time()+"")+"——"+TimeUtils.stampToDateS(bean.getEnd_time()+""));
                        StringBuffer countrys = new StringBuffer("目标国家：");
                        for (int i=0;i<bean.getCountries().size();i++){
                            countrys=countrys.append(bean.getCountries().get(i)+",");
                        }
                            tv_country.setText(countrys);
                        int level=bean.getLevel_req();
                        switch (level){
                            case 1:
                                tv_level.setText("一级");
                                break;
                            case 2:
                                tv_level.setText("二级");
                                break;
                            case 3:
                                tv_level.setText("三级");
                                break;
                            case 4:
                                tv_level.setText("四级");
                                break;
                            case 5:
                                tv_level.setText("五级");
                                break;
                        }
                        //团队性质 1|公务 2|散拼 3|自由行 4|其他
                        int lvxi=bean.getGroup_type();
                        switch (lvxi){
                            case 1:
                                tv_service_type.setText("公务");
                                break;
                            case 2:
                                tv_service_type.setText("散拼");
                                break;
                            case 3:
                                tv_service_type.setText("自由行");
                                break;
                            case 4:
                                tv_service_type.setText("其他");
                                break;
                        }
                        int sittype=bean.getService_type();
                        //服务内容 1|找导游 2|租车 3|9座司导 4|翻译 5|其他
                        switch (sittype){
                            case 1:
                                tv_car_type.setText("找导游");
                                break;
                            case 2:
                                tv_car_type.setText("租车");
                                break;
                            case 3:
                                tv_car_type.setText("9座司导");
                                break;
                            case 4:
                                tv_car_type.setText("翻译");
                                break;
                            case 5:
                                tv_car_type.setText("其他");
                                break;
                        }
                        int paytype=bean.getPay_type();
                        switch (paytype){
                            case 1:
                                tv_pay_type.setText("支付方式：线下支付");
                                break;
                            case 2:
                                tv_pay_type.setText("支付方式：支付宝");
                                break;
                            case 3:
                                tv_pay_type.setText("支付方式：微信");
                                break;
                        }
                        String[] path=bean.getRoute_img_path().split(",");
                        ll_container.removeAllViews();
                        for (int i = 0; i < path.length; i++) {
                            ImageView iv = new ImageView(Activity_Geted_Orders_Details.this);

                            ll_container.addView(iv);
                            LinearLayout.LayoutParams mParams = (LinearLayout.LayoutParams) iv.getLayoutParams();
                            if (i > 0) {
                                mParams.leftMargin = CropUtil.dip2Px(Activity_Geted_Orders_Details.this, 8);
                            }
                            mParams.width = CropUtil.dip2Px(Activity_Geted_Orders_Details.this, 60);
                            mParams.height = CropUtil.dip2Px(Activity_Geted_Orders_Details.this, 60);
                            iv.setLayoutParams(mParams);
                            Glide.with(Activity_Geted_Orders_Details.this)
                                    .load(BaseApi.getBaseUrl()+path[i])
                                    .centerCrop()
                                    .into(iv);
                        }
                    }
                });
    }

    /**
     * 接单者取消订单
     */
    private void cancel(){
        Observable<HttpResult> result= ServiceApi.getInstance().getServiceContract().getedCancel(apitoken,dmd_id);
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
                        ToastUtils.show(context,"取消成功",0);
                        finish();
                    }
                });
    }

    private void finishedDemand() {
        Observable<HttpResult> result=ServiceApi.getInstance().getServiceContract().finishDemand(apitoken,dmd_id+"");
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
                        ToastUtils.show(context,"订单已确认完成",0);
                        finish();
                    }
                });
    }
}
