package com.example.asus.xyd_order.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.net.BaseApi;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.HeadBean;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.net.result.LoginResult;
import com.example.asus.xyd_order.net.result.UserInfoResult;
import com.example.asus.xyd_order.ui.CircleImageView;
import com.example.asus.xyd_order.ui.PictureSelectPopWindow;
import com.example.asus.xyd_order.ui.SelectPopWindow;
import com.example.asus.xyd_order.utils.SharedPreferenceUtils;
import com.example.asus.xyd_order.utils.TimeUtils;
import com.example.asus.xyd_order.utils.ToastUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import cn.leo.photopicker.crop.CropUtil;
import cn.leo.photopicker.pick.PhotoPicker;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.picker.SinglePicker;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/3/10.
 */
public class Activity_UserInfo extends BaseActivity {

    private RelativeLayout rl_change_head;
    private OptionPicker picker;
    private CircleImageView iv_head;
    private TextView tv_camera,tv_picture,tv_submit;
    private View selectView;
    private List<String> ages;
    private MultipartBody.Builder builder;
    private MultipartBody.Builder builder1;
    private String apitoken;
    //业务范围
    private String find_guide;
    private String have_daba;
    private String nine_sidao;
    private String translate;
    //性别
    private String sex;
    //是否有车辆保险
    private String isInsurence="";

    //从业日期
    private String attend_time;

    //控件依赖
    @Bind(R.id.et_realname)
     EditText et_realname;

    @Bind(R.id.et_email)
     EditText et_email;

    @Bind(R.id.et_belong_company)
    EditText et_belong_company;

    @Bind(R.id.live_city)
    EditText live_city;

    @Bind(R.id.et_passport)
    EditText et_passport;

    @Bind(R.id.et_grand)
    EditText et_grand;

    @Bind(R.id.service_type_desc)
    EditText service_type_desc;

    @Bind(R.id.rg_sex)
     RadioGroup rg_sex;

    @Bind(R.id.tv_factory_time)
     TextView tv_factory_time;

    @Bind(R.id.et_phonenum)
     TextView et_phonenum;

    @Bind(R.id.tv_career_time)
    TextView tv_career_time;

    @Bind(R.id.tv_birth)
     TextView tv_birth;

    @Bind(R.id.cb_only_guide)
     CheckBox cb_only_guide;

    @Bind(R.id.cb_nine)
    CheckBox cb_nine;

    @Bind(R.id.cb_daba)
    CheckBox cb_daba;

    @Bind(R.id.cb_translate)
    CheckBox cb_translate;

    @Bind(R.id.cb_insurence)
     CheckBox cb_insurence;

    private DatePicker datePicker;

    @Override
    public void myOnclick(View view) {
    switch (view.getId()){
        case R.id.rl_change_head:
            selectMore();
            break;
        case R.id.tv_submit:
            //提交
            update();
            break;
    }
    }
    @OnClick({R.id.tv_birth,R.id.tv_career_time,R.id.tv_factory_time,})
    public void textOnclik(TextView button){
        switch (button.getId()){
            case R.id.tv_birth:
                showDatePicker(tv_birth);
                break;
            case R.id.tv_career_time:
                showDatePicker(tv_career_time);
                break;
            case R.id.tv_factory_time:
                selectCarDate();
                break;
        }
    }
    @Override
    public void setToolbar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        TextView tv_title= (TextView) findViewById(R.id.tv_title);
        tv_title.setText("资料");
        iv_back.setOnClickListener(v -> {onBackPressed();});
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_userinfo;
    }


    @Override
    public int getData() throws Exception {
        ages = new ArrayList<>();
        for (int i=0;i<101;i++){
            ages.add(i+"");
        }
        apitoken = (String) SharedPreferenceUtils.getParam(context,"apitoken","");
        getuserInfo();
        return 0;
    }

    @Override
    public void initView() {
        builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder1=new MultipartBody.Builder().setType(MultipartBody.FORM);
        selectView = inflater.inflate(R.layout.pop_picture,null,false);
        tv_camera = (TextView) selectView.findViewById(R.id.tv_camera);
        tv_picture= (TextView) selectView.findViewById(R.id.tv_picture);
        rl_change_head = (RelativeLayout) findViewById(R.id.rl_change_head);
        iv_head = (CircleImageView) findViewById(R.id.iv_head);
        tv_submit= (TextView) findViewById(R.id.tv_submit);
        tv_submit.setVisibility(View.VISIBLE);
        tv_submit.setOnClickListener(this);

    }

    @Override
    public void setEvent() {
        tv_camera.setOnClickListener(this);
        tv_picture.setOnClickListener(this);
        rl_change_head.setOnClickListener(this);
        tv_submit.setOnClickListener(this);
//        rg_sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
//                if (R.id.rb_male == i){
//                    sex="1";
//                }else if (R.id.rb_female == i){
//                    sex="0";
//                }
//            }
//        });
    }

    /**
     * 选择多张图片
     */
    private void selectMore(){
        PhotoPicker.selectPic(this, 1, true, 600, 600, new PhotoPicker.PicCallBack() {
            @Override
            public void onPicSelected(String[] path) {
                File file=new File(path[0]);
                builder1.addFormDataPart("apitoken",apitoken);
                builder1.addFormDataPart("avatar", file.getName() , RequestBody.create(MediaType.parse("image/*"), file));
                changeHead(builder1.build());
            }
        });
    }
    /**
     * 修改图片
     */
    private void changeHead(MultipartBody body){
        Observable<HttpResult<HeadBean>> result= ServiceApi.getInstance().getServiceContract().changeHead(body);
        result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HeadBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        toastShow(e.getMessage());
                    }

                    @Override
                    public void onNext(HeadBean headBean) {
                        toastShow("头像修改成功");
                        Glide.with(getApplicationContext()).load(BaseApi.getBaseUrl()+headBean.getAvatar()).into(iv_head);
                    }
                });
    }
    private RequestBody getrequestBody()  {
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
        int checked_Id=rg_sex.getCheckedRadioButtonId();
        if (checked_Id == R.id.rb_male){
            sex="1";
        }else if (checked_Id == R.id.rb_female){
            sex ="0";
        }
        builder.addFormDataPart("gender",sex);
        builder.addFormDataPart("birth",tv_birth.getText().toString());
        builder.addFormDataPart("mobile",et_phonenum.getText().toString());
        builder.addFormDataPart("email",et_email.getText().toString());
        builder.addFormDataPart("company",et_belong_company.getText().toString());
        try {
            builder.addFormDataPart("attend_time",TimeUtils.dateToStamp(tv_career_time.getText().toString()).substring(0,10));
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        builder.addFormDataPart("service_type",find_guide+","+have_daba+","+nine_sidao+","+translate);
        Log.e("zyh",have_daba+","+find_guide+","+nine_sidao+","+translate);
//        builder.addFormDataPart("service_type_desc",service_type_desc.getText().toString());
        builder.addFormDataPart("live_city",live_city.getText().toString());
        builder.addFormDataPart("passport_num",et_passport.getText().toString());
        builder.addFormDataPart("car_brand",et_grand.getText().toString());
        builder.addFormDataPart("car_birth",tv_factory_time.getText().toString());
        builder.addFormDataPart("_method","PUT");
        boolean is_insurence=cb_insurence.isChecked();
        if (is_insurence) {
            isInsurence="1";
        }else {
            isInsurence="0";
        }
        builder.addFormDataPart("is_car_insured",isInsurence);
        return builder.build();
    }
    /**
     * 更新信息
     */
    private void update(){
        Observable<HttpResult<LoginResult>> result=ServiceApi.getInstance().getServiceContract().updateUserInfo(user_id+"",getrequestBody());
        result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LoginResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        toastShow(e.getMessage());
                    }

                    @Override
                    public void onNext(LoginResult loginResult) {
                        toastShow("修改完成");
                        finish();
                        SharedPreferenceUtils.setParam(context,"apitoken",loginResult.getApitoken());
                        SharedPreferenceUtils.setParam(context,"user_id",loginResult.getUser_id());
                        SharedPreferenceUtils.setParam(context,"user_name",loginResult.getUser_name());
                        SharedPreferenceUtils.setParam(context,"isLogin",true);
                    }
                });

    }
    private void showDatePicker(TextView tv){
        datePicker = new DatePicker(Activity_UserInfo.this, DatePicker.YEAR_MONTH_DAY);
        datePicker.setRange(1970,2025);
        datePicker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month,String day) {
                if (tv.getId()== R.id.tv_career_time){
                    String time=year+"."+month+"."+day;
                        tv_career_time.setText(time);

                }else if (tv.getId() == R.id.tv_birth){
                    tv_birth.setText(year+"-"+month+"-"+day);
                }
            }
        });
        datePicker.show();
    }
    //选择车辆出厂日期
    private void selectCarDate(){
        DatePicker datePicke=new DatePicker(Activity_UserInfo.this, DatePicker.YEAR_MONTH_DAY);
        datePicke.setRange(1970,2025);
        datePicke.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month,String day) {
                tv_factory_time.setText(year+"-"+month+"-"+day);
            }
        });
        datePicke.show();
    }
/**
 * 获取用户信息
 */
private void getuserInfo(){
    int user_id= (int) SharedPreferenceUtils.getParam(context,"user_id",0);
    Observable<HttpResult<UserInfoResult>> result=ServiceApi.getInstance().getServiceContract().getUserInfo(user_id+"",apitoken);
    result.map(new ResultFilter<>())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<UserInfoResult>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    toastShow(e.getMessage());
                }

                @Override
                public void onNext(UserInfoResult userInfoResult) {
                    processData(userInfoResult);
                }
            });
}
    //处理返回数据
    private void processData(UserInfoResult info) {
        et_realname.setText(info.getUser_name());
        et_belong_company.setText(info.getCompany());
        et_email.setText(info.getEmail());
        et_grand.setText(info.getCar_brand());
        et_passport.setText(info.getPassport_num());
        et_phonenum.setText(info.getMobile());
        live_city.setText(info.getLive_city());
        int gender= info.getGender();
        switch (gender){
            case 0:
                rg_sex.check(R.id.rb_female);
                break;
            case 1:
                rg_sex.check(R.id.rb_male);
                break;
        }
        tv_career_time.setText(TimeUtils.stampToDateS(info.getAttend_time()+""));
        tv_birth.setText(info.getBirth());
        tv_factory_time.setText(info.getCar_birth());
        int is_insured=info.getIs_car_insured();
        if (is_insured == 1){
            cb_insurence.setChecked(true);
        }else {
            cb_insurence.setChecked(false);
        }
    }


}
