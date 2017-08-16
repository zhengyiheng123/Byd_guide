package com.example.asus.xyd_order.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.activity.DetailsActivity;
import com.example.asus.xyd_order.activity.PhotoActivity;
import com.example.asus.xyd_order.base.BaseFragment;
import com.example.asus.xyd_order.net.BaseApi;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.HeadBean;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.ui.CircleImageView;
import com.example.asus.xyd_order.utils.ActivityFactory;
import com.example.asus.xyd_order.utils.SharedPreferenceUtils;
import com.example.asus.xyd_order.utils.ToastUtils;
import com.example.asus.xyd_order.utils.httpUtils.CustomWaitDialogUtil;
import com.example.asus.xyd_order.utils.httpUtils.HttpDialogUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;

import cn.leo.photopicker.pick.PhotoPicker;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ASUS on 2017/2/15.
 */
public class MyFragment extends BaseFragment {
    private TextView tv_login,tv_my_guide_circle,tv_opinion,tv_account_journey;
    private RelativeLayout rl_setting,rl_suggestion
            ,rl_my_collect,rl_userinfo,rl_address_manager;
    private CircleImageView iv_head;
    private MultipartBody.Builder builder;
    private String apitoken;

    //image

    @Override
    public void myOnclick(View view) {
        switch (view.getId()){
            case R.id.tv_login:
//                ActivityFactory.goToLogin(context);
                break;
            case R.id.rl_setting:
                ActivityFactory.gotoSetting(context);
                break;
            case R.id.rl_suggestion:
                ActivityFactory.gotoSuggestion(context);
                break;
            case R.id.tv_opinion:
                ActivityFactory.gotoOpinion(context);
                break;
            case R.id.iv_head:
//                ActivityFactory.gotoUserInfo(context);
                selectMore();
                break;
            case R.id.tv_account_journey:
                ActivityFactory.gotoJourny(context);
                break;
            case R.id.rl_userinfo:
                ActivityFactory.gotoUserInfo(context);
                break;
            case R.id.tv_my_guide_circle:
                ActivityFactory.gotoMyGuideCircle(context);
                break;
            case R.id.rl_address_manager:
                ActivityFactory.gotoAddAddressList(context);
                break;
            case R.id.rl_my_collect:
                ActivityFactory.gotoMyCollect(context);
                break;
        }
    }

    @Override
    public void initView(View v) {
        apitoken = (String) SharedPreferenceUtils.getParam(context,"apitoken","");
        builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        inializeView(v);
    }

    @Override
    public void onResume() {
        super.onResume();
        String user_name= (String) SharedPreferenceUtils.getParam(context,"user_name","");
        tv_login.setText(user_name);
        String avatar= (String) SharedPreferenceUtils.getParam(context,"avatar","");
        Glide.with(context).load(BaseApi.getBaseUrl()+avatar).placeholder(R.drawable.ic_head).into(iv_head);
    }

    private void inializeView(View v) {
        tv_login = (TextView) v.findViewById(R.id.tv_login);
        tv_my_guide_circle= (TextView) v.findViewById(R.id.tv_my_guide_circle);
        tv_opinion= (TextView) v.findViewById(R.id.tv_opinion);
        rl_setting = (RelativeLayout) v.findViewById(R.id.rl_setting);
        rl_suggestion= (RelativeLayout) v.findViewById(R.id.rl_suggestion);
        rl_userinfo= (RelativeLayout) v.findViewById(R.id.rl_userinfo);
        iv_head = (CircleImageView) v.findViewById(R.id.iv_head);
        rl_address_manager= (RelativeLayout) v.findViewById(R.id.rl_address_manager);
        tv_account_journey= (TextView) v.findViewById(R.id.tv_account_journey);
        rl_my_collect= (RelativeLayout) v.findViewById(R.id.rl_my_collect);

    }

    @Override
    public int getResource() {
        return R.layout.fragment_my;
    }

    @Override
    public int initData() {
        return STATE_CONTENT;
    }


    @Override
    public void setEvent() {
        tv_login.setOnClickListener(this);
        rl_setting.setOnClickListener(this);
        rl_suggestion.setOnClickListener(this);
        tv_my_guide_circle.setOnClickListener(this);
        tv_opinion.setOnClickListener(this);
        iv_head.setOnClickListener(this);
        tv_account_journey.setOnClickListener(this);
        rl_userinfo.setOnClickListener(this);
        rl_address_manager.setOnClickListener(this);
        rl_my_collect.setOnClickListener(this);
    }

    /**
     * 选择多张图片
     */
    private void selectMore(){
        PhotoPicker.selectPic(getActivity(), 1, true, 600, 600, new PhotoPicker.PicCallBack() {
            @Override
            public void onPicSelected(String[] path) {
                File file=new File(path[0]);
                builder.addFormDataPart("apitoken", apitoken);
                builder.addFormDataPart("avatar", file.getName() , RequestBody.create(MediaType.parse("image/*"), file));
                changeHead(builder.build());
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
                        SharedPreferenceUtils.setParam(context,"avatar",headBean.getAvatar());
                        Glide.with(getActivity()).load(BaseApi.getBaseUrl()+headBean.getAvatar()).dontAnimate().error(R.drawable.ic_head).into(iv_head);
                    }
                });
    }
}
