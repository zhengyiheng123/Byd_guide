package com.example.asus.xyd_order.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.asus.xyd_order.R;

import java.util.List;

/**
 * Created by ASUS on 2016/9/12.
 */
public class GallaryAdapter extends RecyclerView.Adapter<GallaryAdapter.ViewHolder> implements View.OnClickListener {
    private Context mContetx;
    private List<Integer> mList;
    private ImageView img;
    private TextView title;
    private String name;

    public GallaryAdapter(Context context, List<Integer> list) {
        mContetx=context;
        mList=list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContetx).inflate(R.layout.item_galary,parent,false);
        view.setOnClickListener(this);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        img.setImageResource(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onClick(View v) {
        if (mListener != null){
            mListener.onItemClick(v,(int)v.getTag());
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View v) {
            super(v);
            img = (ImageView) v.findViewById(R.id.img);
        }
    }
    public OnRecycleItemClickListener mListener;
    public static interface OnRecycleItemClickListener{
        void onItemClick(View view, int position);
    }
    public void setOnItemClickListener(OnRecycleItemClickListener listener){
        mListener=listener;
    }
}
