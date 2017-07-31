package com.example.asus.xyd_order.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseViewHolder;
import com.example.asus.xyd_order.net.result.NoticeBean;
import com.example.asus.xyd_order.utils.TimeUtils;

/**
 * Created by Zheng on 2017/4/20.
 */

public class MyOrderNews_Holder extends BaseViewHolder<NoticeBean.NoticesBean> {

    private TextView tv_time,tv_title,tv_content;

    @Override
    public BaseViewHolder onCreateViewHolder(View v) {
        tv_time = (TextView) v.findViewById(R.id.tv_time);
        tv_title= (TextView) v.findViewById(R.id.tv_title);
        tv_content= (TextView) v.findViewById(R.id.tv_content);
        return this;
    }

    @Override
    public void onBindViewHolder(Context context, NoticeBean.NoticesBean s) {
        tv_time.setText(TimeUtils.stampToDateSs(s.getAdd_time()+""));
        tv_content.setText(s.getMsg());
        tv_title.setText(s.getTitle());

    }

    @Override
    public int getLayoutId() {
        return R.layout.item_myordernews;
    }
}
