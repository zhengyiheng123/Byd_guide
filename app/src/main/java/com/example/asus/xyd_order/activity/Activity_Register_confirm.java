package com.example.asus.xyd_order.activity;

import android.support.annotation.IdRes;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.ConfirmUserInfo;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.utils.SharedPreferenceUtils;
import com.example.asus.xyd_order.utils.TimeUtils;

import java.text.ParseException;

import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.DateTimePicker;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/3/9.
 */
public class Activity_Register_confirm extends BaseActivity {

    private RadioButton rb_have;
    private TextView tv_career_time,tv_birth,tv_next;
    private DatePicker datePicker;
    private EditText et_realname,live_city,et_passport,et_phonenum,et_email,et_belong_company,service_type_desc;
    private CheckBox cb_only_guide,cb_nine,cb_daba,cb_translate;
    private RadioGroup rg_sex,rg_have_car;
    private String apitoken;
    private MultipartBody.Builder builder;

    private String find_guide;
    private String have_daba;
    private String nine_sidao;
    private String translate;
    //性别
    private String sex;
    //从业日期
    private String attend_time;
    private LinearLayout ll_ishave_car;
    private CheckBox cb_insurence;
    private TextView tv_factory_time;
    private EditText et_grand;
    //是否有车辆保险
    private String isInsurence="";

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (datePicker !=null){
            datePicker=null;
        }
    }

    @Override
    public void myOnclick(View view) {
        switch (view.getId()){
            case R.id.tv_career_time:
                showDatePicker(tv_career_time);
                break;
            case R.id.tv_birth:
                showDatePicker(tv_birth);
                break;
            case R.id.tv_next:
                confirmInfo();
                break;
            case R.id.tv_factory_time:
                selectCarDate();
                break;
        }
    }
    //选择车辆出厂日期
    private void selectCarDate(){
        DatePicker datePicke=new DatePicker(Activity_Register_confirm.this, DatePicker.YEAR_MONTH_DAY);
        datePicke.setRange(1930,2025);
        datePicke.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month,String day) {
                tv_factory_time.setText(year+"-"+month+"-"+day);
            }
        });
        datePicke.show();
    }
private void showDatePicker(TextView tv){
    datePicker = new DatePicker(Activity_Register_confirm.this, DatePicker.YEAR_MONTH_DAY);
    datePicker.setRange(1970,2025);
    datePicker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
        @Override
        public void onDatePicked(String year, String month,String day) {
            if (tv.getId()== R.id.tv_career_time){
                String time=year+"."+month+"."+day;
                try {
                    tv_career_time.setText(time);
                    attend_time=TimeUtils.dateToStamp(time);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }else if (tv.getId() == R.id.tv_birth){
                tv_birth.setText(year+"-"+month+"-"+day);
            }
        }
    });
    datePicker.show();
}

    @Override
    public void setToolbar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(v -> {onBackPressed();});
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_register_confirm;
    }


    @Override
    public int getData() throws Exception {
        return 0;
    }

    @Override
    public void initView() {
        //初始化所有布局
        apitoken = getIntent().getStringExtra("apitoken");
        builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        iniaLizeView();
    }

    private void iniaLizeView() {
        rb_have = (RadioButton) findViewById(R.id.rb_have);
        tv_career_time = (TextView) findViewById(R.id.tv_career_time);
        tv_birth= (TextView) findViewById(R.id.tv_birth);
        tv_next= (TextView) findViewById(R.id.tv_next);

        et_realname = (EditText) findViewById(R.id.et_realname);
        et_passport= (EditText) findViewById(R.id.et_passport);
        live_city= (EditText) findViewById(R.id.live_city);
        et_phonenum= (EditText) findViewById(R.id.et_phonenum);
        et_email= (EditText) findViewById(R.id.et_email);
        et_belong_company= (EditText) findViewById(R.id.et_belong_company);
        service_type_desc= (EditText) findViewById(R.id.service_type_desc);
        tv_factory_time = (TextView) findViewById(R.id.tv_factory_time);
        et_grand = (EditText) findViewById(R.id.et_grand);

        cb_insurence = (CheckBox) findViewById(R.id.cb_insurence);
        cb_daba= (CheckBox) findViewById(R.id.cb_daba);
        cb_nine= (CheckBox) findViewById(R.id.cb_nine);
        cb_only_guide= (CheckBox) findViewById(R.id.cb_only_guide);
        cb_translate= (CheckBox) findViewById(R.id.cb_translate);

        rg_sex = (RadioGroup) findViewById(R.id.rg_sex);
        rg_have_car= (RadioGroup) findViewById(R.id.rg_have_car);
        ll_ishave_car = (LinearLayout) findViewById(R.id.ll_ishave_car);
    }

    @Override
    public void setEvent() {
        tv_career_time.setOnClickListener(this);
        rb_have.setOnClickListener(this);
        tv_birth.setOnClickListener(this);
        tv_next.setOnClickListener(this);
        ll_ishave_car.setOnClickListener(this);
        rg_have_car.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                    if (R.id.rb_have == i){
                        ll_ishave_car.setVisibility(View.VISIBLE);
                    }else {
                        ll_ishave_car.setVisibility(View.GONE);
                    }
            }
        });
        rg_sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (R.id.rb_male == i){
                    sex="1";
                }else if (R.id.rb_female == i){
                    sex="0";
                }
            }
        });

        tv_factory_time.setOnClickListener(this);
        cb_insurence.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    isInsurence="1";
                }else {
                    isInsurence="0";
                }
            }
        });
    }

    /**
     * 注册信息认证
     */
    private void confirmInfo(){
        Observable<HttpResult<ConfirmUserInfo>> result=
                ServiceApi.getInstance().getServiceContract().confirmUserinfo(getrequestBody());
        result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ConfirmUserInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        toastShow(e.getMessage());
                    }

                    @Override
                    public void onNext(ConfirmUserInfo confirmUserInfo) {
                        toastShow("注册完成");
                        finish();
                    }
                });
    }

    private RequestBody getrequestBody(){
        if (cb_only_guide.isChecked()){
            find_guide="1";
        }else {
            find_guide="";
        }

        if (cb_translate.isChecked()){
            translate="4";
        }else {
            translate="";
        }

        if (cb_nine.isChecked()){
            nine_sidao="3";
        }else {
            nine_sidao="";
        }

        if (cb_daba.isChecked()){
            have_daba="2";
        }else {
            have_daba="";
        }
        builder.addFormDataPart("apitoken",apitoken);
        builder.addFormDataPart("user_name",et_realname.getText().toString());
        builder.addFormDataPart("gender",sex);
        builder.addFormDataPart("birth",tv_birth.getText().toString());
        builder.addFormDataPart("mobile",et_phonenum.getText().toString());
        builder.addFormDataPart("email",et_email.getText().toString());
        builder.addFormDataPart("company",et_belong_company.getText().toString());
        builder.addFormDataPart("attend_time",attend_time.substring(0,10));
        builder.addFormDataPart("service_type",find_guide+","+have_daba+","+nine_sidao+","+translate);
//        Log.e("zyh",have_daba+","+find_guide+","+nine_sidao+","+translate);
        builder.addFormDataPart("service_type_desc",service_type_desc.getText().toString());
        builder.addFormDataPart("live_city",live_city.getText().toString());
        builder.addFormDataPart("passport_num",et_passport.getText().toString());
        builder.addFormDataPart("car_brand",et_grand.getText().toString());
        builder.addFormDataPart("car_birth",tv_factory_time.getText().toString());
        builder.addFormDataPart("is_car_insured",isInsurence);
        return builder.build();
    }

    public class SelectInfo{
        public SelectInfo(String tv_factory_time, String et_grand, String insurence) {
            this.tv_factory_time = tv_factory_time;
            this.et_grand = et_grand;
            this.insurence = insurence;
        }

        private String tv_factory_time;
        private String et_grand;
        private String insurence;

        public String getTv_factory_time() {
            return tv_factory_time;
        }

        public String getEt_grand() {
            return et_grand;
        }

        public String isInsurence() {
            return insurence;
        }

        public void setTv_factory_time(String tv_factory_time) {
            this.tv_factory_time = tv_factory_time;
        }

        public void setEt_grand(String et_grand) {
            this.et_grand = et_grand;
        }

        public void setInsurence(String insurence) {
            this.insurence = insurence;
        }
    }

}
