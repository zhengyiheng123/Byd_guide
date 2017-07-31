package com.example.asus.xyd_order.holder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.adapter.GallaryAdapter;
import com.example.asus.xyd_order.adapter.HorizontalListViewAdapter;
import com.example.asus.xyd_order.adapter.SpaceItemDecoration;
import com.example.asus.xyd_order.base.BaseArrayAdapter;
import com.example.asus.xyd_order.base.BaseViewHolder;
import com.example.asus.xyd_order.net.BaseApi;
import com.example.asus.xyd_order.net.result.CommentBean;
import com.example.asus.xyd_order.ui.CircleImageView;
import com.example.asus.xyd_order.ui.HorizontalListView;
import com.example.asus.xyd_order.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Zheng on 2017/2/24.
 */
public class CommentsHolder extends BaseViewHolder<CommentBean.CommentsBean> {

    private Context context;
    private CircleImageView iv_head;
    private TextView tv_comment,tv_time;
    private RatingBar rb_stars;

    public CommentsHolder(Context context) {
        this.context=context;

    }

    @Override
    public BaseViewHolder onCreateViewHolder(View v) {
        iv_head = (CircleImageView) v.findViewById(R.id.iv_head);
        tv_comment= (TextView) v.findViewById(R.id.tv_comment);
        tv_time= (TextView) v.findViewById(R.id.tv_time);
        rb_stars = (RatingBar) v.findViewById(R.id.rb_stars);
        return this;
    }

    @Override
    public void onBindViewHolder(Context context, CommentBean.CommentsBean s) {
        Glide.with(context).load(BaseApi.getBaseUrl()+s.getAvatar()).into(iv_head);
        tv_comment.setText(s.getComment());
        if (!TextUtils.isEmpty(s.getAdd_time()+"")){
            tv_time.setText(TimeUtils.stampToDateSs(s.getAdd_time()+""));
        }
        rb_stars.setRating(s.getRank());
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_comments;
    }
}
