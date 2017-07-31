package com.example.asus.xyd_order.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import com.example.asus.xyd_order.base.BaseFragment;

import java.util.List;

/**
 * Created by Administrator on 2016/12/5.
 */
public class MyPagerAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> mlist;
    public MyPagerAdapter(FragmentManager fm, List<BaseFragment> mList) {
        super(fm);
        mlist=mList;
    }

    @Override
    public Fragment getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

}
