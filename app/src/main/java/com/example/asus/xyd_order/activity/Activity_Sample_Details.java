package com.example.asus.xyd_order.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.net.BaseApi;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.CountryBean;
import com.example.asus.xyd_order.net.result.CreateDemand;
import com.example.asus.xyd_order.net.result.His_SampleBean;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.utils.SharedPreferenceUtils;
import com.example.asus.xyd_order.utils.TimeUtils;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.leo.photopicker.crop.CropUtil;
import cn.leo.photopicker.pick.PhotoPicker;
import cn.qqtheme.framework.picker.OptionPicker;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/7/5.
 */

public class Activity_Sample_Details extends BaseActivity {
    private List<String> teamList;
    private List<String> serviceList;
    private List<String> levelList;
    private List<String> payList;
    private List<String> areaList;

    private String dmd_id;
    private EditText et_phonenum,et_tuan_num,et_lvxingshe,et_details,et_route_details,et_total_money,et_name;
    private TextView tv_team_type,tv_file,tv_service_content,tv_target_country,tv_level,tv_start,tv_end,tv_paytype,tv_dmd_area,tv_publish;
    private LinearLayout mLlContainer;
    private OptionPicker picker;
    private String token;
    private int group_type;
    private int service_type;
    private int level_req;
    private int dmd_area;
    private int pay_type;

    private MultipartBody.Builder builder;

    private String countryId="";
    private String ds_id;
    private ImageView iv_img_hos;
    private TimePickerView pvTime;
    private String startTime;
    private String endTime;

    @Override
    public void myOnclick(View view) {
        switch (view.getId()){
            case R.id.tv_file:
                selectMore();
                break;
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
            case R.id.tv_target_country:
//                showOptionPicker(countryS,tv_target_country,4);
                Intent intent=new Intent(context,SelectCountry.class);
                startActivityForResult(intent,0);
                break;
            case R.id.tv_paytype:
                showOptionPicker(payList,tv_paytype,5);
                break;
            case R.id.tv_dmd_area:
                showOptionPicker(areaList,tv_dmd_area,6);
                break;
            case R.id.tv_publish:
                publish();
                break;
            case R.id.iv_img_hos:
                selectMore();
                break;
        }
    }

    @Override
    public void setToolbar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        TextView tv_title= (TextView) findViewById(R.id.tv_title);
        tv_title.setText("需求样本");
        iv_back.setOnClickListener(v -> onBackPressed());
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_sample_details;
    }


    @Override
    public int getData() throws Exception {
        ds_id = getIntent().getStringExtra("ds_id");
        builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        token = (String) SharedPreferenceUtils.getParam(context,"apitoken","");
        getDemandDetails(token);
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
    public void initView() {
        inialize();
        initTimePicker();
    }
    private MultipartBody getBody() {
            builder
                    .addFormDataPart("apitoken",token)
                    .addFormDataPart("user_name",et_name.getText().toString())
                    .addFormDataPart("mobile",et_phonenum.getText().toString())
                    .addFormDataPart("group_type",group_type+"")
                    .addFormDataPart("group_num",et_tuan_num.getText().toString())
                    .addFormDataPart("service_type",service_type+"")
                    .addFormDataPart("target_country",countryId)
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
    private void inialize() {
        et_name= (EditText) findViewById(R.id.et_name);
        et_phonenum = (EditText) findViewById(R.id.et_phonenum);
        et_tuan_num= (EditText) findViewById(R.id.et_tuan_num);
        et_lvxingshe= (EditText) findViewById(R.id.et_lvxingshe);
        tv_team_type = (TextView) findViewById(R.id.tv_team_type);
        et_details= (EditText) findViewById(R.id.et_details);
        et_total_money= (EditText) findViewById(R.id.et_total_money);
        et_route_details= (EditText) findViewById(R.id.et_route_details);
        tv_service_content= (TextView) findViewById(R.id.tv_service_content);
        tv_target_country= (TextView) findViewById(R.id.tv_target_country);
        tv_level= (TextView) findViewById(R.id.tv_level);
        tv_start= (TextView) findViewById(R.id.tv_start);
        tv_end= (TextView) findViewById(R.id.tv_end);
        tv_file= (TextView) findViewById(R.id.tv_file);
        mLlContainer = (LinearLayout) findViewById(R.id.ll_container);
        tv_paytype= (TextView) findViewById(R.id.tv_paytype);
        tv_dmd_area= (TextView) findViewById(R.id.tv_dmd_area);
        tv_publish= (TextView) findViewById(R.id.tv_publish);

        iv_img_hos = (ImageView) findViewById(R.id.iv_img_hos);
    }

    /**
     * 请求网络数据
     */
    private void getDemandDetails(String token){
        showDialog();
        Observable<HttpResult<His_SampleBean>> result= ServiceApi.getInstance().getServiceContract().sampleDetails(ds_id,token);
        result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<His_SampleBean>() {
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
                    public void onNext(His_SampleBean bean) {
                        et_name.setText(bean.getDs_name());
                        et_details.setText(bean.getDmd_desc());
                        et_lvxingshe.setText(bean.getCompany());
                        et_phonenum.setText(bean.getMobile());
                        et_route_details.setText("\t\t\t"+bean.getRoute_desc());
                        et_total_money.setText(bean.getPrice());
                        et_tuan_num.setText(bean.getGroup_num());
                        int dmd_area1=bean.getDmd_area();
                        dmd_area=dmd_area1;
                        //目标国家
                        List<His_SampleBean.CountryListBean> countriesBeen=bean.getCountry_list();
                        String countryStr="";
                        for (int i=0;i<countriesBeen.size();i++){
                            if (TextUtils.isEmpty(countryStr)){
                                countryStr=countriesBeen.get(i).getRegion_name();
                            }else {
                                countryStr=countryStr+","+countriesBeen.get(i).getRegion_name();
                            }
                            tv_target_country.setText(countryStr);
                            if (TextUtils.isEmpty(countryId)){
                                countryId = (countriesBeen.get(i).getRegion_id()+"");
                            }else {
                                countryId = (countryId+","+countriesBeen.get(i).getRegion_id()+"");
                            }
                        }
                        switch (dmd_area1){
                            case 1:
                                tv_dmd_area.setText("导游圈");
                                break;
                            case 2:
                                tv_dmd_area.setText("车行圈");
                                break;
                            case 3:
                                tv_dmd_area.setText("全部");
                                break;
                        }

                        int paytype=bean.getPay_type();
                        pay_type=paytype;
                        switch (pay_type){
                            case 1:
                                tv_paytype.setText("线下支付");
                                break;
                            case 2:
                                tv_paytype.setText("支付宝");
                                break;
                            case 3:
                                tv_paytype.setText("微信");
                                break;
                        }
                        int group_type1=bean.getGroup_type();
                        group_type=group_type1;
                        switch (group_type1){
                            case 1:
                                tv_team_type.setText("公务");
                                break;
                            case 2:
                                tv_team_type.setText("散拼");
                                break;
                            case 3:
                                tv_team_type.setText("自由行");
                                break;
                            case 4:
                                tv_team_type.setText("其他");
                                break;
                        }
                        int servicetype=bean.getService_type();
                        service_type=servicetype;
                        switch (servicetype){
                            case 1:
                                service_type=1;
                                tv_service_content.setText("找导游");
                                break;
                            case 2:
                                service_type=2;
                                tv_service_content.setText("租车");
                                break;
                            case 3:
                                service_type=3;
                                tv_service_content.setText("9座司导");
                                break;
                            case 4:
                                service_type=4;
                                tv_service_content.setText("翻译");
                                break;
                            case 5:
                                service_type=5;
                                tv_service_content.setText("其他");
                                break;
                        }
                        int level_req1=bean.getLevel_req();
                        level_req=level_req1;
                        switch (level_req1){
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
                        tv_start.setText(TimeUtils.stampToDateS(bean.getStart_time()+""));
                        tv_end.setText(TimeUtils.stampToDateS(bean.getEnd_time()+""));
                        if (TextUtils.isEmpty(bean.getRoute_img_path())){
                            return;
                        }
                        String[] path=bean.getRoute_img_path().split(",");
                        mLlContainer.removeAllViews();
                        for (int i = 0; i < path.length; i++) {
                            final int ii=i;
                            ImageView iv = new ImageView(Activity_Sample_Details.this);
                            iv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent=new Intent(context,ImageViewActivity.class);
                                    intent.putExtra("path",BaseApi.getBaseUrl()+path[ii]);
                                    startActivity(intent);
                                }
                            });
                            mLlContainer.addView(iv);
                            LinearLayout.LayoutParams mParams = (LinearLayout.LayoutParams) iv.getLayoutParams();
                            if (i > 0) {
                                mParams.leftMargin = CropUtil.dip2Px(Activity_Sample_Details.this, 8);
                            }
                            mParams.width = CropUtil.dip2Px(Activity_Sample_Details.this, 60);
                            mParams.height = CropUtil.dip2Px(Activity_Sample_Details.this, 60);
                            iv.setLayoutParams(mParams);
                            Glide.with(Activity_Sample_Details.this)
                                    .load(BaseApi.getBaseUrl()+path[i])
                                    .centerCrop()
                                    .into(iv);
                        }
                    }
                });
    }
    @Override
    public void setEvent() {
        tv_file.setOnClickListener(this);
        tv_end.setOnClickListener(this);
        tv_start.setOnClickListener(this);
        tv_team_type.setOnClickListener(this);
        tv_service_content.setOnClickListener(this);
        tv_target_country.setOnClickListener(this);
        tv_level.setOnClickListener(this);
        tv_paytype.setOnClickListener(this);
        tv_dmd_area.setOnClickListener(this);
        tv_publish.setOnClickListener(this);
        iv_img_hos.setOnClickListener(this);
    }

    /**
     * 选择多张图片
     */
    private void selectMore(){
        PhotoPicker.selectPic(this, 5, true, 0, 0, new PhotoPicker.PicCallBack() {
            @Override
            public void onPicSelected(String[] path) {
                mLlContainer.removeAllViews();
                for (int i = 0; i < path.length; i++) {
                    ImageView iv = new ImageView(Activity_Sample_Details.this);
                    File file=new File(path[i]);
                    iv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent=new Intent(context,ImageViewActivity.class);
                            intent.putExtra("path",file.getAbsoluteFile());
                            startActivity(intent);
                        }
                    });
                    builder.addFormDataPart("img"+i+1, file.getName() , RequestBody.create(MediaType.parse("image/*"), file));

                    mLlContainer.addView(iv);
                    LinearLayout.LayoutParams mParams = (LinearLayout.LayoutParams) iv.getLayoutParams();
                    if (i > 0) {
                        mParams.leftMargin = CropUtil.dip2Px(Activity_Sample_Details.this, 8);
                    }
                    mParams.width = CropUtil.dip2Px(Activity_Sample_Details.this, 60);
                    mParams.height = CropUtil.dip2Px(Activity_Sample_Details.this, 60);
                    iv.setLayoutParams(mParams);
                    Glide.with(Activity_Sample_Details.this)
                            .load(path[i])
                            .centerCrop()
                            .into(iv);
                }

            }
        });
    }

    /**
     * 选项
     * @param strings
     */
    private void showOptionPicker(List<String> strings,TextView tv,int type){
        if (picker !=null){
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
                        break;
                    case 2:
                        service_type =index+1;
                        break;
                    case 3:
                        level_req=index+1;
                        break;
                    case 5:
                        pay_type=index+1;
                        break;
                    case 6:
                        dmd_area=index+1;
                        break;
                }
            }
        });
        picker.show();
    }

    //更新数据
    private void publish() {
        showDialog();
        Observable<HttpResult<CreateDemand>> result=ServiceApi.getInstance().getServiceContract().createDemand(getBody());
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
                        toastShow(e.getMessage());
                        dismissDialog();
                    }

                    @Override
                    public void onNext(CreateDemand demandUpdate) {
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
                tv_target_country.setText(countryStr);
                if (TextUtils.isEmpty(countryId)){
                    countryId = (countriesBeen.get(i).getRegion_id()+"");
                }else {
                    countryId = (countryId+","+countriesBeen.get(i).getRegion_id()+"");
                }
            }
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
                        startTime = startTime.substring(0,10);
                    }else if (tv_time.getId() == R.id.tv_end){
                        endTime = TimeUtils.dateToStampssss(getDateAndTime(date));
                        endTime = endTime.substring(0,10);
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
