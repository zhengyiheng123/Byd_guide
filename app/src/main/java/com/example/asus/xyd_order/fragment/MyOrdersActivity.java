package com.example.asus.xyd_order.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.adapter.DemandPagerAdapter;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.base.BaseFragment;
import com.example.asus.xyd_order.ui.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zheng on 2017/5/8.
 */

public class MyOrdersActivity extends BaseActivity {

    private List<String> titleList;
    private PagerSlidingTabStrip tablayout;
    private ViewPager viewpager;
    private List<BaseFragment> fragmentList;
    public static MyOrdersActivity instance;
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
    protected void onResume() {
        super.onResume();
        instance=this;
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
        tablayout = (PagerSlidingTabStrip) findViewById(R.id.tablayout);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        DemandPagerAdapter adapter=new DemandPagerAdapter(getSupportFragmentManager(),fragmentList,titleList);
        viewpager.setAdapter(adapter);
        viewpager.setOffscreenPageLimit(3);
        tablayout.setViewPager(viewpager);
        setTabsValue();
    }
    private void setTabsValue() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        // 设置Tab底部选中的指示器Indicator的高度
        tablayout.setIndicatorHeight(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2.5f, dm));
        // 设置Tab底部选中的指示器 Indicator的颜色
        tablayout.setIndicatorColorResource(R.color.tool_bar_color);
        //设置指示器Indicatorin是否跟文本一样宽，默认false
        tablayout.setIndicatorinFollowerTv(true);
        //设置小红点提示，item从0开始计算，true为显示，false为隐藏
//        tabs.setMsgToast(2, true);
        //设置红点滑动到当前页面自动消失,默认为true
        tablayout.setMsgToastPager(false);
        //设置Tab标题文字的颜色
        //tabs.setTextColor(R.color.***);
        // 设置Tab标题文字的大小
        tablayout.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, dm));
        // 设置选中的Tab文字的颜色
        tablayout.setSelectedTextColorResource(R.color.tool_bar_color);
        //设置Tab底部分割线的高度
        tablayout.setUnderlineHeight(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1f, dm));
        //设置Tab底部分割线的颜色
        //tabs.setUnderlineColorResource(R.color.colorGray);
        // 设置点击某个Tab时的背景色,设置为0时取消背景色tabs.setTabBackground(0);
//        tabs.setTabBackground(R.drawable.bg_tab);
        tablayout.setTabBackground(0);
        // 设置Tab是自动填充满屏幕的
        tablayout.setShouldExpand(true);
    }
    @Override
    public void setEvent() {

    }
    //设置待付款小红点
    public void setMsgRedPoint(boolean msg){
        tablayout.setMsgToast(2,msg);
    }
}
