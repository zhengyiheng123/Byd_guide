package com.example.asus.xyd_order.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.adapter.DemandPagerAdapter;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zheng on 2017/5/8.
 */

public class MyOrdersActivity extends BaseActivity {

    private List<String> titleList;
    private TabLayout tablayout;
    private ViewPager viewpager;
    private List<BaseFragment> fragmentList;

    @Override
    public void myOnclick(View view) {

    }

    @Override
    public void setToolbar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(v -> onBackPressed());
        TextView tv_title= (TextView) findViewById(R.id.tv_title);
        tv_title.setText("我的预定");
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_myorders;
    }


    @Override
    public int getData() throws Exception {
        fragmentList = new ArrayList<>();
        fragmentList.add(new Myorder_Fragment(""));
        fragmentList.add(new Myorder_Fragment("0"));
        fragmentList.add(new Myorder_Fragment("1"));
        fragmentList.add(new Myorder_Fragment("-1"));
        fragmentList.add(new Myorder_Fragment("2"));
        titleList = new ArrayList<>();
        titleList.add("全部");
        titleList.add("待接单");
        titleList.add("待付款");
        titleList.add("已取消");
        titleList.add("已完成");
        return 0;
    }

    @Override
    public void initView() {
        initViewpager();
    }
    private void initViewpager() {
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
