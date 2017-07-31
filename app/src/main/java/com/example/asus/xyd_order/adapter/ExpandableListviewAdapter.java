package com.example.asus.xyd_order.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.asus.xyd_order.R;

import java.util.List;

/**
 * Created by Zheng on 2017/2/27.
 */
public class ExpandableListviewAdapter extends BaseExpandableListAdapter {
    List<String> grouplist;
    List<String> childList;
    Context context;
    public ExpandableListviewAdapter(List<String> groupList, List<String> childList, Context context) {
        this.grouplist=groupList;
        this.childList=childList;
        this.context=context;
    }

    @Override
    public int getGroupCount() {
        return grouplist.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return childList.size();
    }

    @Override
    public Object getGroup(int i) {
        return null;
    }

    @Override
    public Object getChild(int i, int i1) {
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View convertView, ViewGroup viewGroup) {
        View view=null;
        GroupViewHolder holder;
        if (convertView ==null){
            holder=new GroupViewHolder();
            view= LayoutInflater.from(context).inflate(R.layout.item_multiple_details_group,null);
            view.setTag(holder);
        }else {
            view=convertView;
            holder= (GroupViewHolder) view.getTag();
        }
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view=null;
        ChildViewHolder holder=null;
        if (convertView != null){
            view=convertView;
            holder= (ChildViewHolder) view.getTag();
        }else {
            view=LayoutInflater.from(context).inflate(R.layout.item_multiple_details_child,null);
            holder=new ChildViewHolder();
            view.setTag(holder);
        }
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    class GroupViewHolder {
        TextView textView4;
    }
    class ChildViewHolder {
        TextView textView4;
    }
}
