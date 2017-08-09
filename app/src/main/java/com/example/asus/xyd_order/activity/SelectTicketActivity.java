package com.example.asus.xyd_order.activity;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.AddressBean;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.net.result.QueryData;
import com.example.asus.xyd_order.net.result.SerchResult;
import com.example.asus.xyd_order.net.result.ZhongcanOrderSuccessBean;
import com.example.asus.xyd_order.utils.TimeUtils;

import butterknife.Bind;
import cn.qqtheme.framework.picker.OptionPicker;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/8/9.
 */

public class SelectTicketActivity extends BaseActivity {
    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.tv_start)
    TextView tv_start;
    @Bind(R.id.tv_start_time)
    TextView tv_start_time;

    @Bind(R.id.tv_spend)
    TextView tv_spend;

    @Bind(R.id.rg_get_ways)
    RadioGroup rg_get_ways;
    @Bind(R.id.tv_order)
    TextView tv_order;

    @Bind(R.id.tv_end_station)
    TextView tv_end_station;
    @Bind(R.id.tv_end_time)
    TextView tv_end_time;
    @Bind(R.id.ll_user_info)
    LinearLayout ll_user_info;
    @Bind(R.id.ll_address)
    LinearLayout ll_address;
    @Bind(R.id.tv_nomal_price)
    TextView tv_nomal_price;
    @Bind(R.id.tv_group_price)
    TextView tv_group_price;
    @Bind(R.id.tv_num)
    EditText tv_num;
    @Bind(R.id.tv_notice)
    TextView tv_notice;

    @Bind(R.id.tv_name)
    TextView tv_name;
    @Bind(R.id.tv_phoneNum)
    TextView tv_phoneNum;
    @Bind(R.id.tv_address)
    TextView tv_address;
    @Bind(R.id.et_user_name)
    EditText et_user_name;
    @Bind(R.id.et_group_num)
    EditText et_group_num;
    @Bind(R.id.tv_paytype)
    TextView tv_paytype;
    @Bind(R.id.et_user_phone)
    EditText et_user_phone;
    @Bind(R.id.iv_add)
    ImageView iv_add;

    @Bind(R.id.tv_total_price)
    TextView tv_total_price;

    @Bind(R.id.iv_release)
    ImageView iv_release;


    @Bind(R.id.tv_date)
    TextView tv_date;
    private QueryData.TicketsBean data;
    private String group_start;
    private String date;

    private String numString;
    private AddressBean.AddressesBean addressesBean;
    //地址id
    private String ua_id;
    //快递方式
    private String post_type;
    private OptionPicker picker;
    //支付方式
    private String pay_type;
    private String scene_id;

    //票类型
    private String ticket_type;
    private String route_id;

    @Override
    public void myOnclick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_add:
                int num;
                numString = tv_num.getText().toString();
                if (!android.text.TextUtils.isEmpty(numString)){
                    num= Integer.valueOf(numString);
                    tv_num.setText((num+1)+"");
                }else {
                    tv_num.setText("0");
                }
                if (Integer.valueOf(tv_num.getText().toString())<Integer.valueOf(group_start)){
                    tv_total_price.setText((Integer.valueOf(tv_num.getText().toString())*data.getNormal_price())+"");
                }else if (Integer.valueOf(tv_num.getText().toString())>=Integer.valueOf(group_start)){
                    tv_total_price.setText((Integer.valueOf(tv_num.getText().toString())*data.getGroup_price())+"");
                }

                break;
            case R.id.iv_release:
                numString=tv_num.getText().toString();
                if (!android.text.TextUtils.isEmpty(numString)){
                    num= Integer.valueOf(numString);
                    if (num>0){
                        tv_num.setText(""+(num-1));
                    }else {
                        return;
                    }
                }else {
                    return;
                }
                if (Integer.valueOf(tv_num.getText().toString())<Integer.valueOf(group_start)){
                    tv_total_price.setText((Integer.valueOf(tv_num.getText().toString())*data.getNormal_price())+"");
                }else if (Integer.valueOf(tv_num.getText().toString())>=Integer.valueOf(group_start)){
                    tv_total_price.setText((Integer.valueOf(tv_num.getText().toString())*data.getGroup_price())+"");
                }
                break;

            case R.id.tv_paytype:
                picker = new OptionPicker(SelectTicketActivity.this,new String[]{"到店支付","在线支付"});
                picker.setCycleDisable(true);
                picker.setLineVisible(true);
                picker.setShadowVisible(false);
                picker.setTextSize(14);
                picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                    @Override
                    public void onOptionPicked(int index, String item) {
                        switch (index){
                            case 0:
                                pay_type="1";
                                break;
                            case 1:
                                pay_type="2";
                                break;
                        }
                        tv_paytype.setText(item);
                    }
                });
                picker.show();
                break;
            case R.id.tv_order:
                int nums=Integer.valueOf(tv_num.getText().toString());
                if (nums==0){
                    toastShow("至少选择一张票");
                    return;
                }
            if (TextUtils.isEmpty(et_group_num.getText().toString())){
                toastShow("团号不能为空");
                return;
            }
            if (TextUtils.isEmpty(pay_type)){
                toastShow("请选择支付方式");
                return;
            }
                if (post_type.equals("1")){
                    if (addressesBean==null){
                        toastShow("请选择收货地址");
                        return;
                    }
                }
                if (post_type .equals("2")){
                    if (TextUtils.isEmpty(et_user_name.getText().toString())){
                        toastShow("请填写收货人姓名");
                        return;
                    }
                    if (TextUtils.isEmpty(et_user_phone.getText().toString())){
                        toastShow("请填写收货人联系电话");
                        return;
                    }
                }

                if (nums>=20){
                    ticket_type="1";
                }else if (nums<20){
                    ticket_type="2";
                }
                String group_num=et_group_num.getText().toString();
                String price=tv_total_price.getText().toString();
                order(group_num,pay_type,post_type,scene_id,route_id,date,price,ticket_type,data.getStart_station(),data.getEnd_station(),data.getTime_start(),data.getTime_end(),
                        data.getTime_spend(),nums+"",ua_id,et_user_name.getText().toString(),et_user_phone.getText().toString());
                break;
        }
    }

    @Override
    public void setToolbar() {
        iv_back.setOnClickListener(this);
        tv_title.setText("预定");
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_select_ticket;
    }

    @Override
    public int getData() throws Exception {
        data = (QueryData.TicketsBean) getIntent().getSerializableExtra("ticket");
        group_start = getIntent().getStringExtra("group_start");
        date = getIntent().getStringExtra("date");
        scene_id = getIntent().getStringExtra("scene_id");
        route_id = getIntent().getStringExtra("route_id");
        return 0;
    }

    @Override
    public void initView() {
        tv_date.setText("日期："+TimeUtils.stampToDateS(date));
        tv_start.setText(data.getStart_station());
        tv_start_time.setText("出发："+data.getTime_start());
        tv_spend.setText(data.getTime_spend());
        tv_end_station.setText(data.getEnd_station());
        tv_end_time.setText("到达："+data.getTime_end());
        tv_nomal_price.setText(data.getNormal_price()+"");
        tv_group_price.setText(data.getGroup_price()+"");
        tv_notice.setText("说明：购买数量大于"+group_start+"张才可享受团体票价格");
    }

    @Override
    public void setEvent() {
        iv_add.setOnClickListener(this);
        iv_release.setOnClickListener(this);
        tv_order.setOnClickListener(this);
        rg_get_ways.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i){
                    case R.id.rb_sent:
                        Intent intent=new Intent(context,Activity_address_list.class);
                        intent.putExtra("choose","1");
                        startActivityForResult(intent,0);
                        break;
                    case R.id.rb_getbyself:
                        post_type="2";
                        ll_user_info.setVisibility(View.VISIBLE);
                        ll_address.setVisibility(View.GONE);
                        break;
                }
            }
        });
        tv_paytype.setOnClickListener(this);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 0:
                if (resultCode == 1){
                    addressesBean = (AddressBean.AddressesBean) data.getSerializableExtra("address");
                    ll_address.setVisibility(View.VISIBLE);
                    ll_user_info.setVisibility(View.GONE);
                    tv_name.setText(addressesBean.getUser_name());
                    tv_phoneNum.setText(addressesBean.getMobile());
                    tv_address.setText(addressesBean.getAddress());
                    ua_id = addressesBean.getUa_id()+"";
                    post_type ="1";
                    rg_get_ways.check(R.id.rb_sent);
                }else {
                    rg_get_ways.check(R.id.rb_getbyself);
                }
                break;
        }
    }
    //提交数据
    private void order(String group_num,String pay_type,String post_type,String mer_id,String route_id,String date,String price,String ticket_type
    ,String start_station,String end_station,String time_start,String time_end,String time_spend,String nums,String ua_id,String user_name,String mobile){
        showDialog();
        Observable<HttpResult<ZhongcanOrderSuccessBean>> result= ServiceApi.getInstance().getServiceContract().saveOrder(apitoken,group_num,pay_type,post_type,mer_id,route_id,date
        ,price,ticket_type,start_station,end_station,time_start,time_end,time_spend,nums,ua_id,user_name,mobile
        );
        result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ZhongcanOrderSuccessBean>() {
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
                    public void onNext(ZhongcanOrderSuccessBean zhongcanOrderSuccessBean) {

                    }
                });
    }
}
