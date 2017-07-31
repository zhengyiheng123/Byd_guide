package com.example.asus.xyd_order.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseViewHolder;

/**
 * Created by Zheng on 2017/2/24.
 */
public class CommentsImgHolder extends BaseViewHolder<Integer> {

    private ImageView img_list_item;

    @Override
    public BaseViewHolder onCreateViewHolder(View v) {
        img_list_item = (ImageView) v.findViewById(R.id.img_list_item);
        return this;
    }

    @Override
    public void onBindViewHolder(Context context, Integer integer) {
        img_list_item.setImageResource(integer);
    }

    @Override
    public int getLayoutId() {
        return R.layout.horizontal_list_item;
    }
}
