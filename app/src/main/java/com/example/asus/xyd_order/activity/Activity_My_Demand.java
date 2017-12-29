package com.example.asus.xyd_order.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.adapter.DemandPagerAdapter;
import com.example.asus.xyd_order.adapter.MyPagerAdapter;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.base.BaseFragment;
import com.example.asus.xyd_order.fragment.Fragment_All_Demand;
import com.example.asus.xyd_order.fragment.Fragment_Canceled_Demand;
import com.example.asus.xyd_order.fragment.Fragment_Finished_Demand;
import com.example.asus.xyd_order.fragment.Fragment_Geted_Demand;
import com.example.asus.xyd_order.fragment.Fragment_Waiting_Demand;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.net.result.MyDemandBean;
import com.example.asus.xyd_order.utils.SharedPreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/3/13.
 */
public class Activity_My_Demand extends BaseActivity {

    private TextView tv_title,tv_submit;
    private ViewPager viewpager;
    private TabLayout tablayout;
    private List<BaseFragment> fragmentList;
    private List<String> titleList;

    @Override
    public void myOnclick(View view) {

    }

    @Override
    public void setToolbar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(v -> {onBackPressed();});
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("我发布的需求");
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_my_demand;
    }


    @Override
    public int getData() throws Exception {
        fragmentList = new ArrayList<>();
        fragmentList.add(new Fragment_All_Demand());
        fragmentList.add(new Fragment_Waiting_Demand());
        fragmentList.add(new Fragment_Geted_Demand());
        fragmentList.add(new Fragment_Canceled_Demand());
        fragmentList.add(new Fragment_Finished_Demand());
        titleList = new ArrayList<>();
        titleList.add("全部");
        titleList.add("待接单");
        titleList.add("被接单");
        titleList.add("已取消");
        titleList.add("已完成");
        return 0;
    }

    @Override
    public void initView() {
        //初始化tablayout+viewpager
        initViewpager();
    }

    private void initViewpager() {
        tablayout = (TabLayout) findViewById(R.id.tablayout);
        tablayout.setTabTextColors(context.getResources().getColor(R.color.text_grey),context.getResources().getColor(R.color.tool_bar_color));
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        viewpager.setCurrentItem(1);
        viewpager.setOffscreenPageLimit(1);
        tablayout.setTabMode(TabLayout.MODE_FIXED);
        DemandPagerAdapter adapter=new DemandPagerAdapter(getSupportFragmentManager(),fragmentList,titleList);
        viewpager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewpager);
        viewpager.setCurrentItem(1);
        tablayout.setTabsFromPagerAdapter(adapter);
    }

    @Override
    public void setEvent() {

    }


}
