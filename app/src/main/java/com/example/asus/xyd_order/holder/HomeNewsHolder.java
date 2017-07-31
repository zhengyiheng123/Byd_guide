package com.example.asus.xyd_order.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseViewHolder;
import com.example.asus.xyd_order.entity.NewsEntity;
import com.example.asus.xyd_order.net.BaseApi;
import com.example.asus.xyd_order.net.result.HomePage;
import com.example.asus.xyd_order.ui.CircleImageView;
import com.example.asus.xyd_order.utils.TimeUtils;

/**
 * Created by Zheng on 2017/2/22.
 */
public class HomeNewsHolder extends BaseViewHolder<HomePage.MutualMessageBean> {

    private CircleImageView rl_head;
    private TextView tv_home_name,tv_home_time,tv_home_msg;

    @Override
    public BaseViewHolder onCreateViewHolder(View v) {
        rl_head = (CircleImageView) v.findViewById(R.id.rl_head);
        tv_home_name = (TextView) v.findViewById(R.id.tv_home_name);
        tv_home_time= (TextView) v.findViewById(R.id.tv_home_time);
        tv_home_msg= (TextView) v.findViewById(R.id.tv_home_msg);
        return this;
    }

    @Override
    public void onBindViewHolder(Context context, HomePage.MutualMessageBean newsEntity) {
        Glide.with(context).load(BaseApi.getBaseUrl()+newsEntity.getAvatar()).into(rl_head);
        tv_home_msg.setText(newsEntity.getContent());
        tv_home_name.setText(newsEntity.getUser_name());
        tv_home_time.setText(TimeUtils.stampToDateSs(newsEntity.getAdd_time()+""));
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_home_news;
    }

}
