package com.example.asus.xyd_order.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.dialog.CatchSeccessDialog;
import com.example.asus.xyd_order.dialog.ContactDialog;
import com.example.asus.xyd_order.net.BaseApi;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.Demand_Details_Bean;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.utils.TimeUtils;

import butterknife.Bind;
import cn.leo.photopicker.crop.CropUtil;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/3/8.
 */
public class CatchOrdersActivity extends BaseActivity {

    @Bind(R.id.tv_tuanhao)
    TextView tv_tuanhao;

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
    @Bind(R.id.tv_contact)
    TextView tv_contact;
    private ContactDialog dialog;

    @Bind(R.id.tv_catch_order)
    TextView tv_catch_order;
    private CatchSeccessDialog seccessDialog;
    private String dmd_id;

    public static CatchOrdersActivity instance;
    private TextView tv_title;
    private Demand_Details_Bean details_bean;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog!=null||seccessDialog!=null)
        {
            seccessDialog=null;
            dialog=null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        instance=this;
        getNetData();
    }

    @Override
    public void myOnclick(View view) {
        switch (view.getId()){
            case R.id.tv_contact:
                dialog = new ContactDialog(CatchOrdersActivity.this, R.style.MyDialog,details_bean.getMobile(),"",details_bean.getDmd_id()+"");
                dialog.show();
                break;
            case R.id.tv_catch_order:
                taking();
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
        return R.layout.activity_catch_orders;
    }


    @Override
    public int getData() throws Exception {
        dmd_id = getIntent().getStringExtra("dmd_id");
        getNetData();
        return 0;
    }

    @Override
    public void initView() {
        //联系方式

    }

    @Override
    public void setEvent() {
        tv_contact.setOnClickListener(this);
        tv_catch_order.setOnClickListener(this);
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
                        toastShow(e.getMessage());
                    }

                    @Override
                    public void onNext(Demand_Details_Bean bean) {
                        details_bean = bean;
                        tv_title.setText("详情");
                        tv_tuanhao.setText("团号："+bean.getGroup_num());
                        tv_all_money.setText("总报价："+bean.getPrice());
                        tv_company.setText("所属旅行社："+bean.getCompany());
                        tv_details.setText(bean.getDmd_desc());
                        tv_publish_man.setText("发布人："+bean.getUser_name());
                        tv_time.setText("开始/结束:"+ TimeUtils.stampToDateS(bean.getStart_time()+"")+"——"+TimeUtils.stampToDateS(bean.getEnd_time()+""));
                        StringBuffer countrys = new StringBuffer("目标国家：");
                        for (int i=0;i<bean.getCountries().size();i++){
                            countrys=countrys.append(bean.getCountries().get(i)+" ");
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
                        for (int i = 0; i < path.length; i++) {
                            String url=BaseApi.getBaseUrl()+path[i];
                            ImageView iv = new ImageView(CatchOrdersActivity.this);
                            iv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent=new Intent(context,ImageViewActivity.class);
                                    intent.putExtra("path",url);
                                    startActivity(intent);
                                }
                            });
                            ll_container.addView(iv);
                            LinearLayout.LayoutParams mParams = (LinearLayout.LayoutParams) iv.getLayoutParams();
                            if (i > 0) {
                                mParams.leftMargin = 5;
                            }
                            mParams.width = CropUtil.dip2Px(CatchOrdersActivity.this, 200);
                            mParams.height = CropUtil.dip2Px(CatchOrdersActivity.this, 150);
                            iv.setLayoutParams(mParams);
                            Glide.with(CatchOrdersActivity.this)
                                    .load(BaseApi.getBaseUrl()+path[i])
                                    .centerCrop()
                                    .into(iv);
                        }
                    }
                });
    }

    /**
     * 接单
     */
    private void taking(){
        Observable<HttpResult> result=ServiceApi.getInstance().getServiceContract().taking(apitoken,dmd_id);
        result.map(new ResultFilter())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (seccessDialog == null) {
                            seccessDialog = new CatchSeccessDialog(CatchOrdersActivity.this, R.style.MyDialog, R.mipmap.ic_catch_fail);
                            seccessDialog.show();
                        }else {
                            seccessDialog.show();
                        }
                    }

                    @Override
                    public void onNext(Object o) {
                        if (seccessDialog == null) {
                            seccessDialog = new CatchSeccessDialog(CatchOrdersActivity.this, R.style.MyDialog, R.mipmap.ic_catch_successed);
                            seccessDialog.show();
                        }else {
                            seccessDialog.show();
                        }
                    }
                });
    }
}
