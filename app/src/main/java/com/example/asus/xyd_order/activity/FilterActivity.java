package com.example.asus.xyd_order.activity;

import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.adapter.FilterAdapter;
import com.example.asus.xyd_order.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Zheng on 2017/7/10.
 */

public class FilterActivity extends BaseActivity {
    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.tv_submit)
    TextView tv_submit;

    @Bind(R.id.gd_park)
    GridView gd_park;

    //数据源
    private List<String > mList=new ArrayList<>();

    @Override
    public void myOnclick(View view) {

    }

    @Override
    public void setToolbar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(v -> {onBackPressed();});
        tv_title.setText("筛选");
        tv_submit.setVisibility(View.VISIBLE);
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_filter;
    }


    @Override
    public int getData() throws Exception {
        mList.add("大巴停车场");
        mList.add("停车场");
        mList.add("大巴临时上下客");
        return 0;
    }

    @Override
    public void initView() {
        gd_park.setAdapter(new FilterAdapter(mList,getApplicationContext()));
    }

    @Override
    public void setEvent() {

    }
}
