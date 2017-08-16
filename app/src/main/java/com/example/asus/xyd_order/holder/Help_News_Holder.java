package com.example.asus.xyd_order.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseViewHolder;
import com.example.asus.xyd_order.net.BaseApi;
import com.example.asus.xyd_order.net.result.MutualBean;
import com.example.asus.xyd_order.ui.CircleImageView;
import com.example.asus.xyd_order.utils.TimeUtils;

/**
 * Created by Zheng on 2017/3/13.
 */
public class Help_News_Holder extends BaseViewHolder<MutualBean.MutualMessagesBean> {

    private CircleImageView iv_head;
    private TextView tv_name;
    private TextView tv_time;
    private TextView tv_content;

    @Override
    public BaseViewHolder onCreateViewHolder(View v) {
        iv_head = (CircleImageView) v.findViewById(R.id.iv_head);
        tv_name = (TextView) v.findViewById(R.id.tv_name);
        tv_time = (TextView) v.findViewById(R.id.tv_time);
        tv_content = (TextView) v.findViewById(R.id.tv_content);
        return this;
    }

    @Override
    public void onBindViewHolder(Context context, MutualBean.MutualMessagesBean s) {
        Glide.with(context).load(BaseApi.getBaseUrl()+s.getAvatar()).error(R.mipmap.icon_head).into(iv_head);
        tv_name.setText(s.getUser_name());
        tv_content.setText(s.getContent());
        tv_time.setText(TimeUtils.stampToDateSs(s.getAdd_time()+""));
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_help_news;
    }
}
