package com.example.asus.xyd_order.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Zheng on 2017/5/8.
 */

public class MyordersHolder extends BaseAdapter {
    private int type;
    private List<String> mList;
    private MyOrderHolder holder=null;
    private Context context;
    public MyordersHolder(int num, List<String> mList,Context context){
        this.type=num;
        this.mList=mList;
        this.context=context;
    }


    private void switchView(MyOrderHolder holder) {
        if (type == 1){
            holder.cancel.setVisibility(View.VISIBLE);
//            holder.modify.setVisibility(View.VISIBLE);
        }else if (type ==5){

        }else if (type==2){
            holder.cancel.setVisibility(View.VISIBLE);
            holder.pay.setVisibility(View.VISIBLE);
        }else if (type==3){
//            holder.republish.setVisibility(View.VISIBLE);
//            holder.store.setVisibility(View.VISIBLE);
        }
        else if (type == 4){
//            holder.write.setVisibility(View.VISIBLE);
//            holder.store.setVisibility(View.VISIBLE);
            holder.comment.setVisibility(View.VISIBLE);
        }
    }

    private void setGone(){
        if (holder !=null){
            holder.republish.setVisibility(View.GONE);
            holder.modify.setVisibility(View.GONE);
            holder.finished.setVisibility(View.GONE);
            holder.store.setVisibility(View.GONE);
            holder.cancel.setVisibility(View.GONE);
            holder.write.setVisibility(View.GONE);
            holder.comment.setVisibility(View.GONE);
            holder.pay.setVisibility(View.GONE);
        }
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            holder=new MyOrderHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.item_myorders,null);
            holder.cancel= (TextView) convertView.findViewById(R.id.cancel);
            holder.store= (TextView) convertView.findViewById(R.id.store);
            holder.finished= (TextView) convertView.findViewById(R.id.finished);
            holder.modify= (TextView) convertView.findViewById(R.id.modify);
            holder.republish= (TextView) convertView.findViewById(R.id.republish);
            holder.write= (TextView) convertView.findViewById(R.id.write);
            holder.comment= (TextView) convertView.findViewById(R.id.comment);
            holder.pay= (TextView) convertView.findViewById(R.id.pay);
            convertView.setTag(holder);
        }else {
            holder= (MyOrderHolder) convertView.getTag();
        }
        setGone();
        switchView(holder);
        return convertView;
    }
}
