package com.example.asus.xyd_order.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.utils.ActivityFactory;
import com.example.asus.xyd_order.utils.StringUtils;
import com.example.asus.xyd_order.utils.ToastUtils;

import java.util.List;

/**
 * Created by Zheng on 2017/5/5.
 */

public class Journey_Adapter extends BaseAdapter {
    private Context context;
    private List<String> mList;
    private int position;
    public Journey_Adapter(Context context, List<String> mlist){
        this.context=context;
        this.mList=mlist;
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
    public int getItemViewType(int position) {
        if (position == 0||position == 5 || position == 8){
            return 0;
        }else {
            return 1;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        this.position=position;
        ViewholderContent content=null;
        ViewholderTitle title=null;
        if (getItemViewType(position) == 0){
            if (convertView == null){
                title = new ViewholderTitle();
                convertView= LayoutInflater.from(context).inflate(R.layout.item_journy_title,null);
                convertView.setTag(title);
            }else {
                title= (ViewholderTitle) convertView.getTag();
            }
        }else {
            if (convertView == null){
                content = new ViewholderContent();
                convertView= LayoutInflater.from(context).inflate(R.layout.item_journy_content,null);
                content.tv_num= (TextView) convertView.findViewById(R.id.tv_num);
                convertView.setTag(content);
            }else {
                content= (ViewholderContent) convertView.getTag();
            }
            convertView.setOnClickListener(onClickListener);
            content.tv_num.setText("8项行程");
            StringUtils.setTextColorOfPart(context,content.tv_num,R.color.material_red_400,0,1);
        }
        return convertView;
    }

    class ViewholderTitle{

    }
    class ViewholderContent{
        public TextView tv_num;
    }

    private View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ActivityFactory.gotoJouney_Route(context);
        }
    };
}
