package com.example.asus.xyd_order.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.CountryBean;
import com.example.asus.xyd_order.net.result.CreateDemand;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.utils.CompressUtil;
import com.example.asus.xyd_order.utils.SharedPreferenceUtils;
import com.example.asus.xyd_order.utils.TimeUtils;
import com.example.asus.xyd_order.utils.ToastUtils;
import com.nanchen.compresshelper.CompressHelper;

import java.io.File;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import cn.leo.photopicker.crop.CropUtil;
import cn.leo.photopicker.pick.PhotoPicker;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.FilePicker;
import cn.qqtheme.framework.picker.OptionPicker;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/3/13.
 */
public class Activity_ReleaseDemand extends BaseActivity {
    private EditText et_phonenum,et_tuan_num,et_lvxingshe,et_details,et_route_details,et_total_money,et_name,tv_seats;
    private TextView tv_commit,tv_paytype,tv_dmd_area,tv_team_type,tv_service_content,tv_level,tv_start,tv_end,tv_target_country,tv_file;
    private TextView tv_date;
    private RadioGroup rg_service_type;
    private CheckBox cb_check;
    private OptionPicker picker;
    private LinearLayout mLlContainer;
    private MultipartBody.Builder builder;
    private int group_type;
    private int service_type=1;
    private int level_req=0;
    private int dmd_area;
    private int pay_type;
    private List<String> teamList;
    private List<String> serviceList;
    private List<String> levelList;
    private List<String> payList;
    private List<String> areaList;
    private String countryId="";
    private ImageView iv_img_hos;
    private TimePickerView pvTime;
    //开始时间
    private String startTime="";
    //结束时间
    private String endTime="";

    public static String COUNTRY_NAME="Country_Name";
    public static String COUNTRY_ID="country_id";

    @Override
    public void myOnclick(View view) {
        switch (view.getId()){
            case R.id.tv_team_type:
                showOptionPicker(teamList,tv_team_type,1);
                break;
            case R.id.tv_service_content:
                showOptionPicker(serviceList,tv_service_content,2);
                break;
            case R.id.tv_level:
                showOptionPicker(levelList,tv_level,3);
                break;
            case R.id.tv_end:
//                showTime("2");
                pvTime.show(tv_end);
                break;
            case R.id.tv_start:
//                showTime("1");
                pvTime.show(tv_start);
                break;
            case R.id.tv_file:
                selectMore();
                break;
            case R.id.tv_target_country:
//                showOptionPicker(countryS,tv_target_country,4);
                Intent intent=new Intent(context,SelectCountry.class);
                startActivityForResult(intent,0);
                break;
            case R.id.tv_paytype:
//                showOptionPicker(payList,tv_paytype,5);
                break;
            case R.id.tv_dmd_area:
                showOptionPicker(areaList,tv_dmd_area,6);
                break;
            case R.id.tv_commit:
                if (TextUtils.isEmpty(et_tuan_num.getText().toString())){
                    toastShow("团号不能为空");
                    return;
                }
                createDemand();
                break;
            case R.id.iv_img_hos:
                selectMore();
                break;
        }
    }

    /**
     * 选项
     * @param strings
     */
    private void showOptionPicker(List<String> strings,TextView tv,int type){
        if (picker!=null){
            picker =null;
        }
        picker = new OptionPicker(this, strings);
        picker.setCycleDisable(true);
        picker.setLineVisible(true);
        picker.setShadowVisible(false);
        picker.setTextSize(14);

        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                switch (type){
                    case 1:
                        group_type=index+1;
                        tv_team_type.setText(item);
                        break;
                    case 2:
                        service_type =index+1;
                        tv_service_content.setText(item);
                        break;
                    case 3:
                        level_req=index+1;
                        tv_level.setText(item);
                        break;
                    case 5:
                        pay_type=index+1;
                        break;
                    case 6:
                        dmd_area=index+1;
                        tv_dmd_area.setText(item);
                        break;
                }
            }
        });
        picker.show();
    }

    @Override
    public void setToolbar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        TextView tv_title= (TextView) findViewById(R.id.tv_title);
        tv_title.setText("寻车寻导");
        iv_back.setOnClickListener(v -> {onBackPressed();});
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_release_demand;
    }


    @Override
    public int getData() throws Exception {
        builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        teamList = new ArrayList<>();
//        "公务","散拼","自由行","其他"
        teamList.add("公务");
        teamList.add("散拼");
        teamList.add("自由行");
        teamList.add("其他");
        serviceList = new ArrayList<>();
//        "找导游","租车","9座司导","翻译","其他"
        serviceList.add("找导游");
        serviceList.add("租车");
        serviceList.add("9座司导");
        serviceList.add("翻译");
        serviceList.add("其他");
        levelList = new ArrayList<>();
//        "一级","二级","三级","四级","五级"
        levelList.add("无限制");
        levelList.add("一级");
        levelList.add("二级");
        levelList.add("三级");
        levelList.add("四级");
        levelList.add("五级");
        payList = new ArrayList<>();
//        "线下支付","支付宝","微信"
        payList.add("线下支付");
        payList.add("支付宝");
        payList.add("微信");
        areaList = new ArrayList<>();
//        "导游圈","车行圈","全部"
        areaList.add("导游圈");
        areaList.add("车行圈");
        areaList.add("全部");
        return 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
        initTimePicker();
        tv_commit= (TextView) findViewById(R.id.tv_commit);
        //工作种类设置默认值
        tv_service_content= (TextView) findViewById(R.id.tv_service_content);
        tv_service_content.setText(serviceList.get(0));
        //服务人员级别默认值
        tv_level= (TextView) findViewById(R.id.tv_level);
        tv_level.setText("无限制");

        tv_end= (TextView) findViewById(R.id.tv_end);
        //设置默认开始时间
        tv_start= (TextView) findViewById(R.id.tv_start);
        tv_start.setText(TimeUtils.stampToDateSdemand1(TimeUtils.getTime()));
        startTime=TimeUtils.getTime();
        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_file= (TextView) findViewById(R.id.tv_file);
        tv_paytype= (TextView) findViewById(R.id.tv_paytype);
        tv_target_country= (TextView) findViewById(R.id.tv_target_country);
        if (TextUtils.isEmpty((String)SharedPreferenceUtils.getParam(context,COUNTRY_NAME,""))){
            tv_target_country.setText("请选择");
        }else {
            tv_target_country.setText((String)SharedPreferenceUtils.getParam(context,COUNTRY_NAME,""));
        }
        countryId=(String) SharedPreferenceUtils.getParam(context,COUNTRY_ID,"");
        mLlContainer = (LinearLayout) findViewById(R.id.ll_container);
        //服务范围设置默认值
        tv_dmd_area= (TextView) findViewById(R.id.tv_dmd_area);
        tv_dmd_area.setText(areaList.get(2));
        dmd_area=3;

        et_name= (EditText) findViewById(R.id.et_name);
        et_name.setText((String) SharedPreferenceUtils.getParam(context,LoginActivity.USER_NAME,""));
        et_phonenum = (EditText) findViewById(R.id.et_phonenum);
        et_phonenum.setText((String)SharedPreferenceUtils.getParam(context,LoginActivity.USER_MOBILE,""));
        //获取当前时间
        Date date=new Date(System.currentTimeMillis());
        SimpleDateFormat    formatter    =   new    SimpleDateFormat    ("yyyyMMdd");
        String curDate=formatter.format(date);
        et_tuan_num= (EditText) findViewById(R.id.et_tuan_num);
        //设置团号默认值
        et_tuan_num.setText(curDate);
        et_lvxingshe= (EditText) findViewById(R.id.et_lvxingshe);
        //团队类型设置默认值
        tv_team_type = (TextView) findViewById(R.id.tv_team_type);
        tv_team_type.setText(teamList.get(1));
        group_type=2;
        et_details= (EditText) findViewById(R.id.et_details);
        tv_seats= (EditText) findViewById(R.id.tv_seats);
        et_total_money= (EditText) findViewById(R.id.et_total_money);
        et_route_details= (EditText) findViewById(R.id.et_route_details);
        iv_img_hos = (ImageView) findViewById(R.id.iv_img_hos);

        cb_check= (CheckBox) findViewById(R.id.cb_check);
        rg_service_type= (RadioGroup) findViewById(R.id.rg_service_type);
    }


    @Override
    public void setEvent() {
        tv_team_type.setOnClickListener(this);
        tv_service_content.setOnClickListener(this);
        tv_level.setOnClickListener(this);
        tv_end.setOnClickListener(this);
        tv_start.setOnClickListener(this);
        tv_date.setOnClickListener(this);
        tv_file.setOnClickListener(this);
        tv_target_country.setOnClickListener(this);
        tv_paytype.setOnClickListener(this);
        tv_dmd_area.setOnClickListener(this);
        tv_commit.setOnClickListener(this);
        iv_img_hos.setOnClickListener(this);
        rg_service_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (i ==R.id.rb_guide){
                    service_type = 1;
                }else if (i == R.id.rb_cars){
                    service_type = 2;
                }else if (i == R.id.rb_translate){
                    service_type =3;
                }else if (i == R.id.cb_nine){
                    service_type =4;
                }else if(i == R.id.rb_other){
                    service_type = 5;
                }
            }
        });
    }




    /**
     * 选择多张图片
     */
    private void selectMore(){
        PhotoPicker.selectPic(this, 5, false, 0, 0, new PhotoPicker.PicCallBack() {
            @Override
            public void onPicSelected(String[] path) {
                mLlContainer.removeAllViews();
                for (int i = 0; i < path.length; i++) {
                    ImageView iv = new ImageView(Activity_ReleaseDemand.this);
                    File file=new File(path[i]);
//                    Log.e("zyh","oldFile"+file.length()+"");
                    File newFile=new CompressHelper.Builder(context).setQuality(80).build().compressToFile(file);
//                    Log.e("zyh","newFile"+newFile.length()+"");
                    builder.addFormDataPart("img"+i+1, newFile.getName() , RequestBody.create(MediaType.parse("image/*"), newFile));
                    iv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent=new Intent(context,ImageViewActivity.class);
                            intent.putExtra("path",newFile.getAbsolutePath());
                            startActivity(intent);
                        }
                    });
                    mLlContainer.addView(iv);
                    LinearLayout.LayoutParams mParams = (LinearLayout.LayoutParams) iv.getLayoutParams();
                    if (i > 0) {
                        mParams.leftMargin = CropUtil.dip2Px(Activity_ReleaseDemand.this, 8);
                    }
                    mParams.width = CropUtil.dip2Px(Activity_ReleaseDemand.this, 60);
                    mParams.height = CropUtil.dip2Px(Activity_ReleaseDemand.this, 60);
                    iv.setScaleType(ImageView.ScaleType.FIT_START);
                    iv.setLayoutParams(mParams);
                    Glide.with(Activity_ReleaseDemand.this)
                            .load(path[i])
                            .centerCrop()
                            .into(iv);
                }

            }
        });
    }

    private MultipartBody getBody() {
            builder
                    .addFormDataPart("apitoken",apitoken)
                    .addFormDataPart("user_name",et_name.getText().toString())
                    .addFormDataPart("mobile",et_phonenum.getText().toString())
                    .addFormDataPart("group_type",group_type+"")
                    .addFormDataPart("group_num",et_tuan_num.getText().toString())
                    .addFormDataPart("service_type",service_type+"")
                    .addFormDataPart("target_country",countryId)
                    .addFormDataPart("people_number",tv_seats.getText().toString())
                    .addFormDataPart("level_req",level_req+"")
                    .addFormDataPart("dmd_area",dmd_area+"")
                    .addFormDataPart("start_time", startTime)
                    .addFormDataPart("end_time",endTime)
                    .addFormDataPart("price",et_total_money.getText().toString())
//                    .addFormDataPart("pay_type",pay_type+"")
                    .addFormDataPart("company",et_lvxingshe.getText().toString())
                    .addFormDataPart("dmd_desc",et_details.getText().toString())
                    .addFormDataPart("route_desc",et_route_details.getText().toString());
        return builder.build();
    }
    //创建订单
    private void createDemand(){
        if (!cb_check.isChecked()){
            ToastUtils.show(context,"请同意免责声明",0);
            return;
        }
        showDialog();

        Observable<HttpResult<CreateDemand>> result= ServiceApi.getInstance().getServiceContract().createDemand(getBody());
        result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CreateDemand>() {
                    @Override
                    public void onCompleted() {
                        dismissDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissDialog();
                        toastShow(e.getMessage());
                    }

                    @Override
                    public void onNext(CreateDemand createDemand) {
                        toastShow("发布成功");
                        finish();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        countryId ="";
        if (data == null){
            return;
        }
        if (resultCode == 0){
            List<CountryBean.CountriesBean> countriesBeen= (List<CountryBean.CountriesBean>) data.getSerializableExtra("countryList");
            String countryStr="";
            for (int i=0;i<countriesBeen.size();i++){
                if (TextUtils.isEmpty(countryStr)){
                    countryStr=countriesBeen.get(i).getRegion_name();
                }else {
                    countryStr=countryStr+","+countriesBeen.get(i).getRegion_name();
                }

                if (TextUtils.isEmpty(countryId)){
                    countryId = (countriesBeen.get(i).getRegion_id()+"");
                }else {
                    countryId = (countryId+","+countriesBeen.get(i).getRegion_id()+"");
                }
            }
            if (TextUtils.isEmpty(countryStr)){
                tv_target_country.setText("请选择");
            }else {
                tv_target_country.setText(countryStr);
            }

            SharedPreferenceUtils.setParam(context,COUNTRY_NAME,countryStr);
            SharedPreferenceUtils.setParam(context,COUNTRY_ID,countryId);
        }
    }
    //选择时间
    private void initTimePicker() {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(2016, 0, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2020, 11, 28);
        //时间选择器
        pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null

                /*btn_Time.setText(getTime(date));*/
                    TextView tv_time = (TextView) v;
                    tv_time.setText(getDateAndTime(date));
//                tv_timepicker.setText(getDateAndTime(date));
                try {
                    if (tv_time.getId() == R.id.tv_start){
                        startTime = TimeUtils.dateToStampssss(getDateAndTime(date));
                        startTime=startTime.substring(0,10);
                    }else if (tv_time.getId() == R.id.tv_end){
                        endTime = TimeUtils.dateToStampssss(getDateAndTime(date));
                        endTime=endTime.substring(0,10);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        })
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{true, true, true, true, false, false})
                .setLabel("年", "月", "日", "", "", "")
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
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH");
        return format.format(date);
    }
}