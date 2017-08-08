package com.example.asus.xyd_order.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseViewHolder;
import com.example.asus.xyd_order.net.BaseApi;
import com.example.asus.xyd_order.net.result.JingdianBean;
import com.example.asus.xyd_order.net.result.SerchResult;
import com.example.asus.xyd_order.ui.SmartImageveiw;

/**
 * Created by Zheng on 2017/3/2.
 */
public class SearchAttractionsHolder extends BaseViewHolder<SerchResult.ScenesBean> {

    private SmartImageveiw iv_img;
    private TextView tv_name,tv_range,tv_peer,tv_distance_of_you;

    @Override
    public BaseViewHolder onCreateViewHolder(View v) {
        iv_img = (SmartImageveiw) v.findViewById(R.id.iv_img);
        iv_img.setRatio(1.5f);
        tv_name = (TextView) v.findViewById(R.id.tv_name);
        tv_range= (TextView) v.findViewById(R.id.tv_range);
        tv_peer= (TextView) v.findViewById(R.id.tv_peer);
        tv_distance_of_you= (TextView) v.findViewById(R.id.tv_distance_of_you);
        return this;
    }

    @Override
    public void onBindViewHolder(Context context, SerchResult.ScenesBean s) {
        Glide.with(context).load(BaseApi.getBaseUrl()+s.getLogo_path()).into(iv_img);
        tv_name.setText(s.getScene_name());
        tv_peer.setText("门票："+s.getAvg_cost()+"/人起");
//        tv_distance_of_you.setText(s.getDistance()+"米");
        if (s.getIs_bookable() == 0){
            tv_range.setText("窗口售票，无需购买");
        }else {
            tv_range.setText("");
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_attractions;
    }
}
