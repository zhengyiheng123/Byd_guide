package com.example.asus.xyd_order.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.asus.xyd_order.base.BaseFragment;

import java.util.List;

/**
 * Created by Zheng on 2017/3/9.
 */
public class RegisterAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> mList;
    public RegisterAdapter(FragmentManager fm, List<BaseFragment> mList) {
        super(fm);
        this.mList=mList;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }
}
