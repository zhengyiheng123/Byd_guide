package com.example.asus.xyd_order.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.adapter.DemandPagerAdapter;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.base.BaseFragment;
import com.example.asus.xyd_order.fragment.Fragment_All_Demand;
import com.example.asus.xyd_order.fragment.Fragment_Canceled_Demand;
import com.example.asus.xyd_order.fragment.Fragment_Finished_Demand;
import com.example.asus.xyd_order.fragment.Fragment_Geted_Demand;
import com.example.asus.xyd_order.fragment.Fragment_Waiting_Demand;
import com.example.asus.xyd_order.fragment.GetedFragment_All;
import com.example.asus.xyd_order.fragment.GetedFragment_Canceled;
import com.example.asus.xyd_order.fragment.GetedFragment_Finished;
import com.example.asus.xyd_order.fragment.GetedFragment_Geted;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Zheng on 2017/7/6.
 */

public class Activity_MyOrdersGeted extends BaseActivity {
    //控件
    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.viewpager)
     ViewPager viewpager;

    @Bind(R.id.tablayout)
     TabLayout tablayout;
    //数据
    private List<BaseFragment> fragmentList;
    private List<String> titleList;
    @Override
    public void myOnclick(View view) {

    }

    @Override
    public void setToolbar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(v -> {onBackPressed();});
        tv_title.setText("我接的单");
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_myorders_geted;
    }


    @Override
    public int getData() throws Exception {
        fragmentList = new ArrayList<>();
        fragmentList.add(new GetedFragment_All());
        fragmentList.add(new GetedFragment_Geted());
        fragmentList.add(new GetedFragment_Canceled());
        fragmentList.add(new GetedFragment_Finished());
        titleList = new ArrayList<>();
        titleList.add("全部");
        titleList.add("已接单");
        titleList.add("已取消");
        titleList.add("已完成");
        return 0;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        tablayout = (TabLayout) findViewById(R.id.tablayout);
        tablayout.setTabTextColors(context.getResources().getColor(R.color.text_grey),context.getResources().getColor(R.color.tool_bar_color));
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        tablayout.setTabMode(TabLayout.MODE_FIXED);
        DemandPagerAdapter adapter=new DemandPagerAdapter(getSupportFragmentManager(),fragmentList,titleList);
        viewpager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewpager);
        tablayout.setTabsFromPagerAdapter(adapter);
    }

    @Override
    public void setEvent() {

    }
}
