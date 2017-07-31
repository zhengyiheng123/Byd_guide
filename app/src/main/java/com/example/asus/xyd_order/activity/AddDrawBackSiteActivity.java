package com.example.asus.xyd_order.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.base.BaseArrayAdapter;
import com.example.asus.xyd_order.commonview.AddPicLayout;
import com.example.asus.xyd_order.commonview.OnPreviewListener;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.CityListBean;
import com.example.asus.xyd_order.net.result.CountryBean;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.ui.SmartImageveiw;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.leo.photopicker.pick.PhotoPicker;
import me.iwf.photopicker.PhotoPagerActivity;
import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;
import me.leefeng.promptlibrary.PromptDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/3/7.
 */
public class AddDrawBackSiteActivity extends BaseActivity {


    private Spinner country_spinner;

    private List<String> mList=new ArrayList<>();
    private List<CityListBean.RegionsBean> cityList=new ArrayList<>();
    private ArrayAdapter adapter;
    private ImageView iv_stamp,iv_tuishui;
    private TextView tv_select,tv_select_tuishui,tv_back_address;

    private MultipartBody.Builder builder;
    File fileStamp,fileTuishui;

    //城市id
    private String region_id="";
    private EditText et_area,et_address,et_desc;
    private Intent intent;
    private TextView tv_submit;
    private PromptDialog dialog;

    @Override
    public void myOnclick(View view) {
        switch (view.getId()){
            case R.id.tv_select:
                selectPic(iv_stamp);
                break;
            case R.id.tv_select_tuishui:
                selectPic(iv_tuishui);
                break;
            case R.id.tv_submit:
                dialog = new PromptDialog(AddDrawBackSiteActivity.this);
                dialog.showLoading("上传中",false);
                addDraw();
                break;
            case R.id.iv_tuishui:
                intent = new Intent(context,ImageViewActivity.class);
                intent.putExtra("path",fileTuishui.getAbsolutePath());
                startActivity(intent);
                break;
            case R.id.iv_stamp:
                intent = new Intent(context,ImageViewActivity.class);
                intent.putExtra("path",fileStamp.getAbsolutePath());
                startActivity(intent);
                break;
        }
    }

    private void selectPic(ImageView iv) {
        PhotoPicker.selectPic(this, 1, false, 0, 0, new PhotoPicker.PicCallBack() {
            @Override
            public void onPicSelected(String[] path) {
                Glide.with(context).load(path[0]).into(iv);
                if (iv.getId() == R.id.iv_stamp){
                    fileStamp=new File(path[0]);
                    builder.addFormDataPart("stamp_img",fileStamp.getName(),RequestBody.create(MediaType.parse("image/*"), fileStamp));
                }else if (iv.getId() == R.id.iv_tuishui){
                    fileTuishui=new File(path[0]);
                    builder.addFormDataPart("stamp_img",fileTuishui.getName(),RequestBody.create(MediaType.parse("image/*"), fileTuishui));
                }
            }
        });
    }

    @Override
    public void setToolbar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(v -> {onBackPressed();});
        TextView tv_title= (TextView) findViewById(R.id.tv_title);
        tv_title.setText("添加航站楼信息");
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_adddrawback;
    }
    private RequestBody getRequestBody(){
        builder.addFormDataPart("apitoken",apitoken)
                .addFormDataPart("region_id",region_id)
                .addFormDataPart("dp_address",et_address.getText().toString())
                .addFormDataPart("dp_point",et_area.getText().toString())
                .addFormDataPart("dp_desc",et_desc.getText().toString());
        return builder.build();
    }

    @Override
    public int getData() throws Exception {
        builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        getNetData();
        return 0;
    }

    @Override
    public void initView() {
        tv_submit = (TextView) findViewById(R.id.tv_submit);
        tv_submit.setVisibility(View.VISIBLE);
        tv_submit.setText("上传");
        tv_submit.setOnClickListener(this);
        tv_select = (TextView) findViewById(R.id.tv_select);
        tv_select_tuishui= (TextView) findViewById(R.id.tv_select_tuishui);
        tv_back_address= (TextView) findViewById(R.id.tv_back_address);

        et_area = (EditText) findViewById(R.id.et_area);
        et_address= (EditText) findViewById(R.id.et_address);
        et_desc= (EditText) findViewById(R.id.et_desc);

        iv_stamp = (ImageView) findViewById(R.id.iv_stamp);
        iv_tuishui= (ImageView) findViewById(R.id.iv_tuishui);
        country_spinner = (Spinner) findViewById(R.id.country_spinner);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,mList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        country_spinner.setAdapter(adapter);
        country_spinner.setSelection(mList.size()-1,true);
        country_spinner.setPrompt("请选择国家");
    }

    @Override
    public void setEvent() {
        iv_tuishui.setOnClickListener(this);
        iv_stamp.setOnClickListener(this);
        tv_select.setOnClickListener(this);
        tv_select_tuishui.setOnClickListener(this);
        country_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
     * 添加退税点
     */
    private void addDraw(){
        Observable<HttpResult> result= ServiceApi.getInstance().getServiceContract().drawBack(getRequestBody());
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
                        dialog.dismissImmediately();
                    }

                    @Override
                    public void onNext(Object o) {
                        toastShow("添加成功");
                        dialog.dismissImmediately();
                        finish();
                    }
                });
    }

    /**
     * 获取城市列表
     */
    private void getNetData(){
        Observable<HttpResult<CityListBean>> result= ServiceApi.getInstance().getServiceContract().cityList(apitoken);
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
