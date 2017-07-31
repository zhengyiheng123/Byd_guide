package com.example.asus.xyd_order.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Zheng on 2017/7/28.
 */

public class ViewpagerAdapter extends PagerAdapter {
    List<String> mImageUrls;
    Context context;
    public ViewpagerAdapter(List<String> mImageUrls, Context context) {
        this.mImageUrls=mImageUrls;
        this.context=context;
    }

    @Override
    public int getCount() {
        return mImageUrls.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView imageView = new ImageView(context);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        Glide.with(context).load(mImageUrls.get(position)).into(imageView);
        container.addView(imageView);

        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
        if (object != null)
            object = null;
    }
}
