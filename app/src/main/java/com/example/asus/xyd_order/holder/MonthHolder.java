package com.example.asus.xyd_order.holder;

import android.content.Context;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseArrayAdapter;
import com.example.asus.xyd_order.base.BaseViewHolder;
import com.example.asus.xyd_order.net.result.Calender;

/**
 * Created by Zheng on 2017/7/21.
 */

public class MonthHolder extends BaseViewHolder<Calender.CalendarBean>{

    private TextView tv_title;
    private ListView lv_days;

    @Override
    public BaseViewHolder onCreateViewHolder(View v) {
        tv_title = (TextView) v.findViewById(R.id.tv_title);
        lv_days = (ListView) v.findViewById(R.id.lv_days);
        return this;
    }

    @Override
    public void onBindViewHolder(Context context, Calender.CalendarBean calendarBean) {
        tv_title.setText(calendarBean.getMonth());
        lv_days.setAdapter(new BaseArrayAdapter<>(context, new BaseArrayAdapter.OnCreateViewHolderListener() {
            @Override
            public Object onCreateViewHolder() {
                return new DatsHolder();
            }
        },calendarBean.getNodes()));
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_months;
    }
}
