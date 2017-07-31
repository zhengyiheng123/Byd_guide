package com.example.asus.xyd_order.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseViewHolder;
import com.example.asus.xyd_order.entity.HomeCategory;
import com.example.asus.xyd_order.entity.MyorderEntity;
import com.example.asus.xyd_order.net.result.NoticeBean;
import com.example.asus.xyd_order.utils.TimeUtils;

/**
 * Created by Zheng on 2017/2/16.
 */
public class Category_Home_Holder extends BaseViewHolder<NoticeBean.NoticesBean> {
    private Context context;
    private ImageView imageView1;
    private TextView tv_time,tv_content,tv_title;

    public Category_Home_Holder(Context context){
        this.context=context;
    }
    @Override
    public BaseViewHolder onCreateViewHolder(View v) {
        imageView1 = (ImageView) v.findViewById(R.id.imageView1);
        tv_time = (TextView) v.findViewById(R.id.tv_time);
        tv_title= (TextView) v.findViewById(R.id.tv_title);
        tv_content= (TextView) v.findViewById(R.id.tv_content);
        return this;
    }

    @Override
    public void onBindViewHolder(Context context, NoticeBean.NoticesBean homeCategory) {
            imageView1.setImageResource(R.drawable.icon_blue_lingdang);
        tv_content.setText(homeCategory.getMsg());
        tv_title.setText(homeCategory.getTitle());
        tv_time.setText(TimeUtils.stampToDateSs(homeCategory.getAdd_time()+""));

    }

    @Override
    public int getLayoutId() {
        return R.layout.item_home_myorder_content;
    }

}
