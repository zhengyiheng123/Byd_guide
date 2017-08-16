package com.example.asus.xyd_order.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseViewHolder;
import com.example.asus.xyd_order.net.BaseApi;
import com.example.asus.xyd_order.net.result.Guide_Circle_Bean;
import com.example.asus.xyd_order.ui.CircleImageView;

/**
 * Created by Zheng on 2017/3/10.
 */
public class MyCircleHolder extends BaseViewHolder<Guide_Circle_Bean.UsersBean> {

    private CircleImageView iv_head;
    private TextView tv_name;

    @Override
    public BaseViewHolder onCreateViewHolder(View v) {
        iv_head = (CircleImageView) v.findViewById(R.id.iv_head);
        tv_name = (TextView) v.findViewById(R.id.tv_name);
        return this;
    }

    @Override
    public void onBindViewHolder(Context context, Guide_Circle_Bean.UsersBean s) {
        Glide.with(context).load(BaseApi.getBaseUrl()+s.getAvatar()).error(R.mipmap.icon_head).into(iv_head);
        tv_name.setText(s.getUser_name());
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_mycircle_holder;
    }
}
