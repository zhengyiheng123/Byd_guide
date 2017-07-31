package com.example.asus.xyd_order.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseViewHolder;
import com.example.asus.xyd_order.entity.ZhongcanItemEntity;
import com.example.asus.xyd_order.net.BaseApi;
import com.example.asus.xyd_order.net.result.RestaurantBean;
import com.example.asus.xyd_order.ui.SmartImageveiw;

/**
 * Created by Zheng on 2017/2/23.
 */
public class ZhongcanHolder extends BaseViewHolder<RestaurantBean.RestaurantsBean> {

    private TextView tv_name,tv_distance,tv_range,tv_peer;
    private SmartImageveiw iv_img;
    private RatingBar rb_stars;

    @Override
    public BaseViewHolder onCreateViewHolder(View v) {
        iv_img = (SmartImageveiw) v.findViewById(R.id.iv_img);
        iv_img.setRatio(1.5f);
        tv_name = (TextView) v.findViewById(R.id.tv_name);
        tv_distance= (TextView) v.findViewById(R.id.tv_distance);
        rb_stars= (RatingBar) v.findViewById(R.id.rb_stars);
        tv_range= (TextView) v.findViewById(R.id.tv_range);
        tv_peer= (TextView) v.findViewById(R.id.tv_peer);
        return this;
    }

    @Override
    public void onBindViewHolder(Context context, RestaurantBean.RestaurantsBean s) {
        Glide.with(context).load(BaseApi.getBaseUrl()+s.getLogo_path()).into(iv_img);
        tv_name.setText(s.getRes_name());
        tv_distance.setText("距您"+s.getDistance()+"米");
        switch (s.getMeal_type()){
            case 1:
                tv_range.setText("营业范围：团餐");
                break;
            case 2:
                tv_range.setText("营业范围：单点");
                break;
            case 3:
                tv_range.setText("营业范围：团餐+单点");
                break;
        }
        tv_peer.setText("人均消费："+s.getAvg_cost()+"人");
        rb_stars.setRating(s.getRank());
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_zhongcan;
    }
}
