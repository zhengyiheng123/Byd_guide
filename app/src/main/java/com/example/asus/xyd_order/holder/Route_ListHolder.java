package com.example.asus.xyd_order.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseViewHolder;
import com.example.asus.xyd_order.net.BaseApi;
import com.example.asus.xyd_order.net.result.MyOrderBean;
import com.example.asus.xyd_order.utils.TimeUtils;

/**
 * Created by Zheng on 2017/5/5.
 */

public class Route_ListHolder extends BaseViewHolder<MyOrderBean.OrdersBean> {

    private TextView tv_name,tv_ordertime,tv_startoff;
    private ImageView iv_img;

    @Override
    public BaseViewHolder onCreateViewHolder(View v) {
        iv_img = (ImageView) v.findViewById(R.id.iv_img);
        tv_name = (TextView) v.findViewById(R.id.tv_name);
        tv_ordertime= (TextView) v.findViewById(R.id.tv_ordertime);
        tv_startoff= (TextView) v.findViewById(R.id.tv_startoff);
        return this;
    }

    @Override
    public void onBindViewHolder(Context context, MyOrderBean.OrdersBean s) {
        Glide.with(context).load(BaseApi.getBaseUrl()+s.getLogo_path()).dontAnimate().into(iv_img);
        tv_name.setText(s.getMer_name()+" "+s.getRoute_name());
        tv_ordertime.setText("预定日:"+ TimeUtils.stampToDateS(s.getAdd_time()+""));
        tv_startoff.setText("预约日:"+TimeUtils.stampToDateS(s.getBook_time()+""));
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_route_list;
    }
}
