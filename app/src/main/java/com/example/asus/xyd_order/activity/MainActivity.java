package com.example.asus.xyd_order.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.adapter.MyPagerAdapter;
import com.example.asus.xyd_order.app.ExitApplication;
import com.example.asus.xyd_order.base.BaseFragment;
import com.example.asus.xyd_order.fragment.CatchOrdersFragment;
import com.example.asus.xyd_order.fragment.HomeFragment;
import com.example.asus.xyd_order.fragment.MyFragment;
import com.example.asus.xyd_order.fragment.OrdersFragment;
import com.example.asus.xyd_order.fragment.ServiceFragment;
import com.example.asus.xyd_order.ui.NoScrollViewPager;
import com.example.asus.xyd_order.utils.ToastUtils;

import java.util.ArrayList;

/**
 * Created by ASUS on 2017/2/15.
 */
public class MainActivity extends AppCompatActivity {
    public static MainActivity instance;
    private ArrayList<BaseFragment> mlist;
    private RadioGroup rg_bottom;
    private RadioButton rb_home;
    private RadioButton rb_home1;
    private NoScrollViewPager vp_main;

    @Override
    protected void onResume() {
        super.onResume();
        instance=this;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ExitApplication.getInstance().addActivity(this);
        initData();
        initView();
    }
    public void initView() {
        rg_bottom = (RadioGroup) findViewById(R.id.rg_bottom);
        rb_home = (RadioButton) findViewById(R.id.rb_home);
        rb_home1 = rb_home;
        rb_home1.setChecked(true);
        vp_main = (NoScrollViewPager) findViewById(R.id.vp_main);
        vp_main.setAdapter(new MyPagerAdapter(getSupportFragmentManager(),mlist));
        vp_main.setOffscreenPageLimit(3);
        vp_main.setPageTransformer(false,null);
        rg_bottom.setOnCheckedChangeListener(new MyOncheckedListener());
    }
    public void initData() {
        mlist = new ArrayList<>();
        mlist.add(new HomeFragment());
        mlist.add(new CatchOrdersFragment());
        mlist.add(new OrdersFragment());
        mlist.add(new ServiceFragment());
        mlist.add(new MyFragment());
    }
    //radiogroup点击事件
    private class MyOncheckedListener implements RadioGroup.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            switch (i){
                case R.id.rb_home:
                    vp_main.setCurrentItem(0,false);
                    break;
                case R.id.rb_news:
                    vp_main.setCurrentItem(1,false);
                    break;
                case R.id.rb_my:
                    vp_main.setCurrentItem(4,false);
                    break;
                case R.id.rb_enquiry:
                    vp_main.setCurrentItem(2,false);
                    break;
                case R.id.rb_service:
                    vp_main.setCurrentItem(3,false);
                    break;
            }
        }
    }











    private static long exitTime = 0;

    public  void exitApp(Context context) {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            ToastUtils.showShort(context, "再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            ExitApplication.getInstance().exit();
        }
    }

    @Override
    public void onBackPressed() {
        exitApp(getApplicationContext());
    }
}
