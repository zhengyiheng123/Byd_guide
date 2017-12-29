package com.example.asus.xyd_order.activity;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.net.BaseApi;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.CityListBean;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.net.result.RegionsBean;
import com.nanchen.compresshelper.CompressHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.leo.photopicker.pick.PhotoPicker;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/8/1.
 */

public class Activity_AddHospital extends BaseActivity {
    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.tv_submit)
    TextView tv_submit;

    @Bind(R.id.et_hos_name)
    EditText et_hos_name;
    @Bind(R.id.et_hos_address)
    EditText et_hos_address;
    @Bind(R.id.et_hos_phone)
    EditText et_hos_phone;
    @Bind(R.id.et_time_desc)
    EditText et_time_desc;


    @Bind(R.id.et_pass_experence)
    EditText et_pass_experence;
    @Bind(R.id.spinner)
    Spinner spinner;
    @Bind(R.id.rg_hospital_type)
    RadioGroup rg_hospital_type;

    @Bind(R.id.iv_select)
    ImageView iv_select;
    @Bind(R.id.iv_img_hos)
    ImageView iv_img_hos;

    public String hospitalType ="综合大医院";//医院类型


    private MultipartBody.Builder builder;
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> adapter1;

    private List<String> mList=new ArrayList<>();
    private List<RegionsBean> cityList=new ArrayList<>();
    //国家id
    private String region_id;
    private String pat;

    @Override
    public void myOnclick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_select:
                getPic();
                break;
            case R.id.tv_submit:
                addHos();
                break;
            case R.id.iv_img_hos:
                Intent intent=new Intent(context,ImageViewActivity.class);
                intent.putExtra("path", pat);
                startActivity(intent);
                break;

        }
    }
//选取图片
    private void getPic(){
        PhotoPicker.selectPic(Activity_AddHospital.this, 1, false, 0, 0, new PhotoPicker.PicCallBack() {
            @Override
            public void onPicSelected(String[] path) {
                pat = path[0];
                File file=new File(path[0]);
                File newFile= CompressHelper.getDefault(context).compressToFile(file);
                Glide.with(context).load(path[0]).into(iv_img_hos);
                builder.addFormDataPart("img",newFile.getName(),RequestBody.create(MediaType.parse("image/*"), newFile));
            }
        });
    }
    @Override
    public void setToolbar() {
        tv_title.setText("添加医院经验");
        tv_submit.setText("上传");
        tv_submit.setVisibility(View.VISIBLE);
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_add_hospipal;
    }

    @Override
    public int getData() throws Exception {
        getNetData();
        builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        return 0;
    }

    @Override
    public void initView() {
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,mList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(mList.size()-1,true);
        spinner.setPrompt("请选择国家");
    }


    @Override
    public void setEvent() {
        iv_back.setOnClickListener(this);
        tv_submit.setOnClickListener(this);
        iv_select.setOnClickListener(this);
        iv_img_hos.setOnClickListener(this);
        rg_hospital_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (i == R.id.rb_zonghe){
                    hospitalType="综合大医院";
                }else if (i == R.id.rb_shequ){
                    hospitalType="社区诊所";
                }
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                region_id=cityList.get(i).getRegion_id()+"";
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    /**
     * 添加医院信息
     */
    private void addHos(){
        showDialog();
        Observable<HttpResult> result= ServiceApi.getInstance().getServiceContract().addHospital(getBody());
        result.map(new ResultFilter())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber() {
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
                    public void onNext(Object o) {
                        toastShow("添加成功");
                        finish();
                    }
                });
    }
    private RequestBody getBody(){
        builder.addFormDataPart("apitoken",apitoken);
        builder.addFormDataPart("region_id",region_id);
        builder.addFormDataPart("hos_name",et_hos_name.getText().toString());
        builder.addFormDataPart("hos_address",et_hos_address.getText().toString());
        builder.addFormDataPart("hos_phone",et_hos_phone.getText().toString());
        builder.addFormDataPart("time",et_time_desc.getText().toString());
        builder.addFormDataPart("hos_type",hospitalType);
        builder.addFormDataPart("experience",et_pass_experence.getText().toString());
        return builder.build();
    }
    /**
     * 获取城市列表
     */
    private void getNetData(){
        Observable<HttpResult<CityListBean>> result= ServiceApi.getInstance().getServiceContract().countyrList(apitoken);
        result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CityListBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CityListBean cityListBean) {
                        if (cityList.size()>0){
                            cityList.clear();
                        }
                        cityList=cityListBean.getRegions();
                        if (mList.size()>0){
                            mList.clear();
                        }
                        for (int i=0;i<cityListBean.getRegions().size();i++){
                            mList.add(cityListBean.getRegions().get(i).getRegion_name());

                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}
