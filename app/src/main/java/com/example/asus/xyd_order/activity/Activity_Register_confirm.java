package com.example.asus.xyd_order.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.TextUtils;
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

import com.bigkoo.pickerview.TimePickerView;
import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.ConfirmUserInfo;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.utils.SharedPreferenceUtils;
import com.example.asus.xyd_order.utils.SoftInputUtil;
import com.example.asus.xyd_order.utils.TimeUtils;
import com.example.asus.xyd_order.utils.ToastUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.DateTimePicker;
import me.leefeng.promptlibrary.PromptDialog;
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
    private EditText et_realname,live_city,et_passport,et_phonenum,et_email,
            et_belong_company,service_type_desc;
    private CheckBox cb_only_guide,cb_nine,cb_daba,cb_translate,cb_check;
    private RadioGroup rg_sex,rg_have_car;
    private MultipartBody.Builder builder;

    private String find_guide;
    private String have_daba;
    private String nine_sidao;
    private String translate;
    //性别
    private String sex="";
    //从业日期
    private String attend_time="";
    private LinearLayout ll_ishave_car;
    private CheckBox cb_insurence;
    private TextView tv_factory_time;
    private EditText et_grand;
    //是否有车辆保险
    private String isInsurence="";
    private TimePickerView pvTime;

    //邮箱key值
    public static String EMAIL="email";
    //手机号key值
    public static String MOBILE_NUM="phone";
    private String email;
    private PromptDialog dialog;
    private String phoneNum;

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
//                showDatePicker(tv_career_time);
                initTimePicker(new boolean[]{true,false,false,false,false,false},"年","","","","","");
                pvTime.show(tv_career_time);
                break;
            case R.id.tv_birth:
//                showDatePicker(tv_birth);
                SoftInputUtil.hideSoftInput(context,tv_birth);
                initTimePicker(new boolean[]{true,true,true,false,false,false},"年","月","日","","","");
                pvTime.show(tv_birth);
                break;
            case R.id.tv_next:
                if (TextUtils.isEmpty(et_realname.getText().toString())){
                    ToastUtils.show(context,"请输入真实姓名",0);
                    return;
                }
                if (TextUtils.isEmpty(et_phonenum.getText().toString())){
                    ToastUtils.show(context,"请输入手机号码",0);
                    return;
                }
                if (TextUtils.isEmpty(et_email.getText().toString())){
                    ToastUtils.show(context,"请输入邮箱",0);
                    return;
                }
                if (attend_time.equals("")){
                    ToastUtils.show(context,"请选择从业开始时间",0);
                    return;
                }
                if (TextUtils.isEmpty(et_belong_company.getText().toString())){
                    ToastUtils.show(context,"请输入所属旅行社",0);
                    return;
                }
                if (!cb_check.isChecked()){
                    ToastUtils.show(context,"请同意免责声明",0);
                }
                confirmInfo();
                break;
            case R.id.tv_factory_time:
//                selectCarDate();
                SoftInputUtil.hideSoftInput(context,tv_factory_time);
                pvTime.show(tv_factory_time);
                break;
        }
    }

    @Override
    public void setToolbar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        TextView tv_title= (TextView) findViewById(R.id.tv_title);
        tv_title.setText("认证");
        iv_back.setOnClickListener(v -> {onBackPressed();});
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_register_confirm;
    }


    @Override
    public int getData() throws Exception {
        builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
//        Bundle bundle=getIntent().getExtras();
//
//        if (bundle!=null){
//            email = bundle.getString(EMAIL);
//            phoneNum = bundle.getString(MOBILE_NUM);
//        }
        return 0;
    }

    @Override
    public void initView() {
        //初始化所有布局
        iniaLizeView();
        dialog = new PromptDialog(Activity_Register_confirm.this);
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
        et_phonenum.setText((String) SharedPreferenceUtils.getParam(context,LoginActivity.USER_MOBILE,""));
        et_email= (EditText) findViewById(R.id.et_email);
        et_email.setText((String)SharedPreferenceUtils.getParam(context,LoginActivity.USER_EMAIL,""));

        et_belong_company= (EditText) findViewById(R.id.et_belong_company);
        service_type_desc= (EditText) findViewById(R.id.service_type_desc);
        tv_factory_time = (TextView) findViewById(R.id.tv_factory_time);
        et_grand = (EditText) findViewById(R.id.et_grand);

        cb_insurence = (CheckBox) findViewById(R.id.cb_insurence);
        cb_daba= (CheckBox) findViewById(R.id.cb_daba);
        cb_nine= (CheckBox) findViewById(R.id.cb_nine);
        cb_check= (CheckBox) findViewById(R.id.cb_check);
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
        dialog.showLoading("正在提交，请稍后",false);
        Observable<HttpResult<ConfirmUserInfo>> result=
                ServiceApi.getInstance().getServiceContract().confirmUserinfo(getrequestBody());
        result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ConfirmUserInfo>() {
                    @Override
                    public void onCompleted() {
                        dialog.dismissImmediately();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dialog.dismissImmediately();
                        toastShow(e.getMessage());
                    }

                    @Override
                    public void onNext(ConfirmUserInfo confirmUserInfo) {
                        SharedPreferenceUtils.setParam(context,LoginActivity.CONFIRM_STATE,confirmUserInfo.getState());
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
        String service_type="";
        if(!TextUtils.isEmpty(find_guide)){
            service_type=service_type+find_guide+",";
        }
        if (!TextUtils.isEmpty(translate)){
            service_type=service_type+translate+",";
        }
        if (!TextUtils.isEmpty(nine_sidao)){
            service_type=service_type+nine_sidao+",";
        }
        if (!TextUtils.isEmpty(have_daba)){
            service_type=service_type+have_daba+",";
        }
        builder.addFormDataPart("apitoken",apitoken);
        builder.addFormDataPart("user_name",et_realname.getText().toString());
        builder.addFormDataPart("gender",sex);
        builder.addFormDataPart("birth",tv_birth.getText().toString());
        builder.addFormDataPart("mobile",et_phonenum.getText().toString());
        builder.addFormDataPart("email",et_email.getText().toString());
        builder.addFormDataPart("company",et_belong_company.getText().toString());
        builder.addFormDataPart("attend_time",attend_time);
        builder.addFormDataPart("service_type",service_type);
        builder.addFormDataPart("service_type_desc",service_type_desc.getText().toString());
        builder.addFormDataPart("live_city",live_city.getText().toString());
        builder.addFormDataPart("passport_num",et_passport.getText().toString());
        builder.addFormDataPart("car_brand",et_grand.getText().toString());
        builder.addFormDataPart("car_birth",tv_factory_time.getText().toString());
        builder.addFormDataPart("is_car_insured",isInsurence);
        return builder.build();
    }

    //选择时间
    private void initTimePicker(boolean[] flags,String years,String month ,String day,String hours,String minutes,String seconds) {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(1970, 0, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2030, 11, 28);
        //时间选择器
        pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null

                /*btn_Time.setText(getTime(date));*/
                TextView tv_time = (TextView) v;
                tv_time.setText(getDateAndTime(date));
//                tv_timepicker.setText(getDateAndTime(date));
                    if (tv_time.getId() == R.id.tv_career_time){
                        attend_time = TimeUtils.getStringToDate(getDateAndTime(date))+"";
                        attend_time = attend_time.substring(0,10);
                    }else if (tv_time.getId() == R.id.tv_birth){
                }
            }
        })
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(flags)
                .setLabel(years, month, day, hours, minutes, seconds)
                .isCenterLabel(false)
                .setDividerColor(Color.DKGRAY)
                .setContentSize(14)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                .setDecorView(null)
                .build();
    }
    private String getDateAndTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
}
