package com.example.asus.xyd_order.holder;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.activity.Activity_Route_List;
import com.example.asus.xyd_order.base.BaseViewHolder;
import com.example.asus.xyd_order.net.result.Calender;
import com.example.asus.xyd_order.utils.Myutils;
import com.example.asus.xyd_order.utils.StringUtils;

/**
 * Created by Zheng on 2017/7/21.
 */

public class DatsHolder extends BaseViewHolder<Calender.CalendarBean.NodesBean> implements View.OnClickListener{

    private TextView tv_days,tv_count;
    private RelativeLayout rl_item;
    private Context context;
    private Calender.CalendarBean.NodesBean bean;

    @Override
    public BaseViewHolder onCreateViewHolder(View v) {
        tv_days = (TextView) v.findViewById(R.id.tv_days);
        tv_count= (TextView) v.findViewById(R.id.tv_count);
        rl_item = (RelativeLayout) v.findViewById(R.id.rl_item);
        return this;
    }

    @Override
    public void onBindViewHolder(Context context, Calender.CalendarBean.NodesBean nodesBean) {
        this.context=context;
        bean=nodesBean;
        tv_days.setText(nodesBean.getDate());
        String str=nodesBean.getCount()+"项行程";
        tv_count.setText(str);
        StringUtils.setTextColorOfPart(context,tv_count,R.color.material_grey_700,str.length()-3,str.length());
        rl_item.setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_journy_content;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_item:
                Intent intent=new Intent(context, Activity_Route_List.class);
                intent.putExtra("stamp",bean.getStamp());
                context.startActivity(intent);
                break;
        }
    }
}
