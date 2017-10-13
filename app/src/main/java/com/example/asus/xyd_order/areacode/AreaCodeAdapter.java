package com.example.asus.xyd_order.areacode;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asus.xyd_order.R;

import java.util.List;

/**
 * Created by yeshu on 2016/11/25.
 * adapter of area code list
 */

public class AreaCodeAdapter extends RecyclerView.Adapter<AreaCodeAdapter.ViewHolder> implements View.OnClickListener{
    private List<AreaCode> mAreaCodeList;
    public int position;

    public AreaCodeAdapter(List<AreaCode> areaCodeList) {
        mAreaCodeList = areaCodeList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        parent.setOnClickListener(this);
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_area_code, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        this.position=position;
        AreaCode areaCode = mAreaCodeList.get(position);
        holder.textArea.setText(areaCode.getArea());
        holder.textCode.setText(areaCode.getCode());
        holder.itemView.setTag(areaCode);
    }

    @Override
    public int getItemCount() {
        return mAreaCodeList.size();
    }

    @Override
    public void onClick(View view) {
        if (itemClickInterface!=null)
        {
            itemClickInterface.OnItemClick((AreaCode) view.getTag());
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textArea;
        TextView textCode;

        public ViewHolder(View itemView) {
            super(itemView);
            textArea = (TextView) itemView.findViewById(R.id.text_area);
            textCode = (TextView) itemView.findViewById(R.id.text_code);
        }
    }
    ItemClickInterface itemClickInterface;
    public void setOnitemClick(ItemClickInterface clickInterface){
        itemClickInterface=clickInterface;
    }
    interface ItemClickInterface{
        void OnItemClick(AreaCode areaCode);
    }
}
