package com.example.asus.xyd_order.holder;

import android.content.Context;
import android.media.Rating;
import android.text.TextUtils;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseViewHolder;
import com.example.asus.xyd_order.net.BaseApi;
import com.example.asus.xyd_order.net.result.CommentRecords;
import com.example.asus.xyd_order.ui.CircleImageView;
import com.example.asus.xyd_order.utils.TimeUtils;

/**
 * Created by Zheng on 2017/3/10.
 */
public class MyCommentsHolder extends BaseViewHolder<CommentRecords.CommentsBean> {

    private CircleImageView iv_head;
    private TextView tv_name,tv_time,tv_content,finish_time,service_content,tv_country;
    private RatingBar rb_stars;

    @Override
    public BaseViewHolder onCreateViewHolder(View v) {
        tv_country = (TextView) v.findViewById(R.id.tv_country);
        iv_head = (CircleImageView) v.findViewById(R.id.iv_head);
        tv_name = (TextView) v.findViewById(R.id.tv_name);
        tv_time= (TextView) v.findViewById(R.id.tv_time);
        rb_stars = (RatingBar) v.findViewById(R.id.rb_stars);
        tv_content= (TextView) v.findViewById(R.id.tv_content);
        finish_time= (TextView) v.findViewById(R.id.finish_time);
        service_content= (TextView) v.findViewById(R.id.service_content);
        tv_country= (TextView) v.findViewById(R.id.tv_country);
        return this;
    }

    @Override
    public void onBindViewHolder(Context context,CommentRecords.CommentsBean s) {
        tv_content.setText(s.getComment());
        tv_name.setText(s.getUser_name());
        int  time=s.getComment_time();
        if (!TextUtils.isEmpty(time+"")){
            tv_time.setText(TimeUtils.stampToDateS(time+""));
        }
        String countrys="";
        for (int i=0;i<s.getCountries().size();i++){
            countrys=countrys+s.getCountries().get(i)+",";
        }
        tv_country.setText(countrys);
        Glide.with(context).load(BaseApi.getBaseUrl()+s.getAvatar()).into(iv_head);
        rb_stars.setRating(s.getRank());
        if (!TextUtils.isEmpty(s.getFinish_time()+"")) {
            finish_time.setText("完成时间："+TimeUtils.stampToDateS(s.getFinish_time()+""));
        }
        int service_type=s.getService_type();
        switch (service_type){
            case 1:
                service_content.setText("服务内容：找导游");
                break;
            case 2:
                service_content.setText("服务内容：租车");
                break;
            case 3:
                service_content.setText("服务内容：9座司导");
                break;
            case 4:
                service_content.setText("服务内容：翻译");
                break;
            case 5:
                service_content.setText("服务内容：其他");
                break;
        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.item_mycomments;
    }
}
