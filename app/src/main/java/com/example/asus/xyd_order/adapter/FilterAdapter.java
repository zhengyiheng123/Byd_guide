package com.example.asus.xyd_order.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.asus.xyd_order.R;

import java.util.List;

/**
 * Created by Zheng on 2017/7/10.
 */

public class FilterAdapter extends BaseAdapter {
    List<String> mList;
    Context context;
    public FilterAdapter(List<String> mList, Context context){
        this.mList=mList;
        this.context=context;
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View vie= LayoutInflater.from(context).inflate(R.layout.item_filter,null);
        TextView tv_item= (TextView) vie.findViewById(R.id.tv_item);
        tv_item.setText(mList.get(i));
        return vie;
    }
}
