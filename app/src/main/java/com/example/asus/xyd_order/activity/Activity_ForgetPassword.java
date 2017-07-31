package com.example.asus.xyd_order.activity;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.adapter.RegisterAdapter;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.base.BaseFragment;
import com.example.asus.xyd_order.fragment.EmailFindPasswordFragment;
import com.example.asus.xyd_order.fragment.EmailRegisterFragment;
import com.example.asus.xyd_order.fragment.MobileFindPasswordFragment;
import com.example.asus.xyd_order.fragment.MobileRegisterFragment;
import com.example.asus.xyd_order.ui.ChildViewPager;

import java.util.ArrayList;

/**
 * Created by Zheng on 2017/3/9.
 */
public class Activity_ForgetPassword extends BaseActivity {

    private ArrayList<BaseFragment> mlist;
    private ChildViewPager mViewPager;
    private TextView tv_mombile;
    private TextView tv_email;
    private View line_mobile;
    private View line_email;
    private RelativeLayout rl_mail;
    private RelativeLayout rl_mobile;

    @Override
    public void myOnclick(View view) {
        switch (view.getId()){
            case R.id.rl_mobile:
                tv_mombile.setTextColor(getResources().getColor(R.color.tool_bar_color));
                line_mobile.setBackgroundColor(getResources().getColor(R.color.tool_bar_color));
                tv_email.setTextColor(getResources().getColor(R.color.material_grey_800));
                line_email.setBackgroundColor(getResources().getColor(R.color.white));
                mViewPager.setCurrentItem(0);
                break;
            case R.id.rl_mail:
                tv_mombile.setTextColor(getResources().getColor(R.color.material_grey_800));
                line_mobile.setBackgroundColor(getResources().getColor(R.color.white));
                tv_email.setTextColor(getResources().getColor(R.color.tool_bar_color));
                line_email.setBackgroundColor(getResources().getColor(R.color.tool_bar_color));
                mViewPager.setCurrentItem(1);
                break;
        }
    }

    @Override
    public void setToolbar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(v -> {onBackPressed();});
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_forget_password;
    }


    @Override
    public int getData() throws Exception {
        return 0;
    }

    @Override
    public void initView() {
        iniaLizeView();
        initViewPager();
    }

    @Override
    public void setEvent() {
        rl_mail.setOnClickListener(this);
        rl_mobile.setOnClickListener(this);
    }

    private void iniaLizeView() {
        tv_mombile = (TextView) findViewById(R.id.tv_mombile);
        tv_email = (TextView) findViewById(R.id.tv_email);
        line_mobile = findViewById(R.id.line_mobile);
        line_email = findViewById(R.id.line_email);
        rl_mail = (RelativeLayout) findViewById(R.id.rl_mail);
        rl_mobile = (RelativeLayout) findViewById(R.id.rl_mobile);

    }
    private void initViewPager() {
        mlist = new ArrayList<>();
        mViewPager = (ChildViewPager) findViewById(R.id.register_viewpager);
        mlist.add(new MobileFindPasswordFragment());
        mlist.add(new EmailFindPasswordFragment());
        mViewPager.setAdapter(new RegisterAdapter(getSupportFragmentManager(),mlist));
    }
}
