package com.example.asus.xyd_order.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;


import java.util.List;

/**
 * Created by RONMEI on 2015/10/19.
 */

public class BaseArrayAdapter<T, VH extends BaseViewHolder> extends ArrayAdapter {

    private LayoutInflater mInflater;
    private List<T> mList;
    private OnCreateViewHolderListener mListener;

    public interface OnCreateViewHolderListener<VH> {
        VH onCreateViewHolder();
    };
    public BaseArrayAdapter(Context context, OnCreateViewHolderListener listener, List<T> objects) {
        super(context, 0, objects);
        mInflater = LayoutInflater.from(context);
        mListener = listener;
        mList = objects;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VH holder = null;
        if(convertView == null) {
            holder = (VH) mListener.onCreateViewHolder();
            convertView = mInflater.inflate(holder.getLayoutId(), null);
            holder.onCreateViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (VH) convertView.getTag();
        }

        holder.onBindViewHolder(getContext(),mList.get(position));
        return convertView;
    }

    public List<T> getList() {
        return mList;
    }

    public void setList(List<T> mList) {
        this.mList = mList;
    }


}
