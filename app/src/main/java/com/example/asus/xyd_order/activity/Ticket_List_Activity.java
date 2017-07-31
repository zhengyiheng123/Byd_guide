package com.example.asus.xyd_order.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.base.BaseArrayAdapter;
import com.example.asus.xyd_order.holder.Ticket_List_Holder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zheng on 2017/5/5.
 */

public class Ticket_List_Activity extends BaseActivity {

    private ListView lv_ticket_list;
    private List<String> mList;

    @Override
    public void myOnclick(View view) {

    }

    @Override
    public void setToolbar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(v -> onBackPressed());
        TextView tv_title= (TextView) findViewById(R.id.tv_title);
        tv_title.setText("车次查询");
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_ticket_list;
    }


    @Override
    public int getData() throws Exception {
        mList = new ArrayList<>();
        mList.add("");
        mList.add("");
        mList.add("");
        mList.add("");
        mList.add("");
        return 0;
    }

    @Override
    public void initView() {
        lv_ticket_list = (ListView) findViewById(R.id.lv_ticket_list);
        BaseArrayAdapter adapter=new BaseArrayAdapter(context, new BaseArrayAdapter.OnCreateViewHolderListener() {
            @Override
            public Object onCreateViewHolder() {
                return new Ticket_List_Holder();
            }
        },mList);
            lv_ticket_list.setAdapter(adapter);
    }

    @Override
    public void setEvent() {

    }
}
