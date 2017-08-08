package com.example.asus.xyd_order.fragment;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseArrayAdapter;
import com.example.asus.xyd_order.base.BaseFragment;
import com.example.asus.xyd_order.holder.CatchViewHolder;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.net.result.ServiceBean;
import com.example.asus.xyd_order.refresh.widget.swipetorefresh.RefreshLayout;
import com.example.asus.xyd_order.utils.common.PermissionManager;
import com.example.asus.xyd_order.utils.common.PermissionResult;

import java.util.ArrayList;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ASUS on 2017/2/15.
 */
public class ServiceFragment extends BaseFragment {
    TextView tv_phone;
    TextView tv_address;
    TextView tv_runtime;
    TextView tv_email;

    RelativeLayout rl_call;

    private ServiceBean ss;

    @Override
    public void myOnclick(View view) {
        switch (view.getId()){
            case R.id.rl_call:
                PermissionManager.with(getActivity()).request(new PermissionManager.Callback() {
                    @Override
                    public void call(PermissionResult result) {
                        if (result.isSuccessful()){
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            Uri data = Uri.parse("tel:" + ss.getMobile());
                            intent.setData(data);
                            getActivity().startActivity(intent);
                        }else {
                            toastShow("没有拨打电话权限");
                        }

                    }
                }, Manifest.permission.CALL_PHONE);
                break;
        }
    }

    @Override
    public void initView(View v) {
        ImageView iv_back= (ImageView) v.findViewById(R.id.iv_back);
        iv_back.setVisibility(View.GONE);
        TextView tv_title= (TextView) v.findViewById(R.id.tv_title);
        tv_title.setText("客服");

        tv_phone= (TextView) v.findViewById(R.id.tv_phone);
        tv_address= (TextView) v.findViewById(R.id.tv_address);
        tv_runtime= (TextView) v.findViewById(R.id.tv_runtime);
        tv_email= (TextView) v.findViewById(R.id.tv_email);
        rl_call= (RelativeLayout) v.findViewById(R.id.rl_call);
    }

    @Override
    public int getResource() {
        return R.layout.fragment_service;
    }

    @Override
    public int initData() {
        getNetData();
        return STATE_CONTENT;
    }


    @Override
    public void setEvent() {
        rl_call.setOnClickListener(this);
    }

/**
 * 请求网络数据
 */
private void getNetData(){
    Observable<HttpResult<ServiceBean>> result= ServiceApi.getInstance().getServiceContract().service(apitoken);
    result.map(new ResultFilter<>())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<ServiceBean>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(ServiceBean s) {
                    ss = s;
                    tv_address.setText(s.getAddress());
                    tv_email.setText(s.getEmail());
                    tv_phone.setText(s.getMobile());
                    tv_runtime.setText(s.getTime());
                }
            });
}


}
