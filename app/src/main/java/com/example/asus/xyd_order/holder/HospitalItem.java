package com.example.asus.xyd_order.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseViewHolder;
import com.example.asus.xyd_order.net.BaseApi;
import com.example.asus.xyd_order.net.result.HospitalBean;

/**
 * Created by Zheng on 2017/7/31.
 */

public class HospitalItem extends BaseViewHolder<HospitalBean.HospitalsBean>{

    private TextView tv_h_name,tv_tel,tv_name,tv_location;
    private ImageView iv_head;

    @Override
    public BaseViewHolder onCreateViewHolder(View v) {
        iv_head = (ImageView) v.findViewById(R.id.iv_head);
        tv_name = (TextView) v.findViewById(R.id.tv_name);
        tv_h_name= (TextView) v.findViewById(R.id.tv_h_name);
        tv_tel= (TextView) v.findViewById(R.id.tv_tel);
        tv_location= (TextView) v.findViewById(R.id.tv_location);
        return this;
    }

    @Override
    public void onBindViewHolder(Context context, HospitalBean.HospitalsBean hospitalsBean) {
        Glide.with(context).load(BaseApi.getBaseUrl()+hospitalsBean.getImg_path());
        tv_h_name.setText(hospitalsBean.getHos_type());
        tv_location.setText(hospitalsBean.getHos_address());
        tv_name.setText(hospitalsBean.getHos_name());
        tv_tel.setText(hospitalsBean.getHos_phone());
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_hospital;
    }
}
