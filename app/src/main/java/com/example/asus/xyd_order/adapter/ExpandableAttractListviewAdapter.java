package com.example.asus.xyd_order.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.activity.Activity_Order_Nomal;
import com.example.asus.xyd_order.activity.SiteDisplayActivity;
import com.example.asus.xyd_order.net.result.BaseTicketRouteBean;
import com.example.asus.xyd_order.net.result.RouteDetails;

import java.util.List;

/**
 * Created by Zheng on 2017/2/27.
 */
public class ExpandableAttractListviewAdapter extends BaseExpandableListAdapter {
    List<BaseTicketRouteBean> grouplist;
    Context context;

    public ExpandableAttractListviewAdapter(List<BaseTicketRouteBean> groupList, Context context) {
        this.grouplist=groupList;
        this.context=context;
    }

    @Override
    public int getGroupCount() {
        return grouplist.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return grouplist.get(i).getTickets().size();
    }

    @Override
    public Object getGroup(int i) {
        return grouplist.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return grouplist.get(i).getTickets().get(i1);
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
            view= LayoutInflater.from(context).inflate(R.layout.item_group_attract,null);
//            holder.tv_count= (TextView) view.findViewById(R.id.tv_count);
            holder.tv_group_name= (TextView) view.findViewById(R.id.tv_group_name);
            view.setTag(holder);
        }else {
            view=convertView;
            holder= (GroupViewHolder) view.getTag();
        }
        holder.tv_group_name.setText(grouplist.get(i).getTicket_name());
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view=null;
        ChildViewHolder holder = null;
        if (convertView != null){
            view=convertView;
            holder = (ChildViewHolder) view.getTag();
        }else {
            view=LayoutInflater.from(context).inflate(R.layout.item_child_attract,null);
            holder =new ChildViewHolder();
            holder.tv_left= (TextView) view.findViewById(R.id.tv_left);
            holder.iv_add= (ImageView) view.findViewById(R.id.iv_add);
            holder.iv_release= (ImageView) view.findViewById(R.id.iv_release);
            holder.tv_age= (TextView) view.findViewById(R.id.tv_age);
            holder.tv_count= (TextView) view.findViewById(R.id.tv_count);
            holder.tv_num= (TextView) view.findViewById(R.id.tv_num);
            TextView tv_num=holder.tv_num;
            holder.iv_release.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int num=Integer.valueOf(tv_num.getText().toString());
                    if (num>0){
                        num=num-1;
                        grouplist.get(groupPosition).getTickets().get(childPosition).setNums(num);
                        tv_num.setText(num+"");
                    }else {
                        return;
                    }
                    SiteDisplayActivity.instance.updateTitle();
                }
            });
            holder.iv_add.setOnClickListener(new View.OnClickListener() {
                @Override
                    public void onClick(View view) {
                    int num=Integer.valueOf(tv_num.getText().toString());
                    num=num+1;
                    tv_num.setText(num+"");
                    grouplist.get(groupPosition).getTickets().get(childPosition).setNums(num);
                    SiteDisplayActivity.instance.updateTitle();
                }
            });
            view.setTag(holder);
        }
        holder.tv_left.setText("剩余:"+grouplist.get(groupPosition).getTickets().get(childPosition).getTicket_nums());
        holder.tv_age.setText(grouplist.get(groupPosition).getTickets().get(childPosition).getTicket_type()+":"+grouplist.get(groupPosition).getTickets().get(childPosition).getPrice());
        holder.tv_num.setText(grouplist.get(groupPosition).getTickets().get(childPosition).getNums()+"");
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }


    class GroupViewHolder {
        TextView tv_group_name,tv_count;
    }
    class ChildViewHolder {
        TextView tv_age,tv_count,tv_left,tv_num;
        ImageView iv_release,iv_add;
    }
}
