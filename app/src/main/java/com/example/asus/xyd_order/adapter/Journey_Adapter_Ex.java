package com.example.asus.xyd_order.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.utils.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by Zheng on 2017/5/8.
 */

public class Journey_Adapter_Ex extends BaseExpandableListAdapter {
        List<String> groupList;
    Map<Integer,List<String>> childMap;
    Context context;
    public Journey_Adapter_Ex(List<String> groupList, Map<Integer,List<String>> childMap, Context context){
        this.groupList=groupList;
        this.childMap=childMap;
        this.context=context;
    }

    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childMap.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        GroupHolder groupHolder=null;
        if (convertView == null){
            groupHolder=new GroupHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_journy_title,null);
            groupHolder.tv_title= (TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(groupHolder);
        }else {
            groupHolder= (GroupHolder) convertView.getTag();
        }
        groupHolder.tv_title.setText(groupList.get(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder childHolder=null;
        if (convertView == null){
            childHolder=new ChildHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_journy_content,null);
            childHolder.tv_content= (TextView) convertView.findViewById(R.id.tv_num);
            convertView.setTag(childHolder);
        }else {
            childHolder= (ChildHolder) convertView.getTag();
        }
        childHolder.tv_content.setText(childMap.get(groupPosition).get(childPosition).toString());
        StringUtils.setTextColorOfPart(context,childHolder.tv_content,R.color.material_red_700,0,1);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class GroupHolder{
        TextView tv_title;
    }
    class ChildHolder{
        TextView tv_content;
    }
}
