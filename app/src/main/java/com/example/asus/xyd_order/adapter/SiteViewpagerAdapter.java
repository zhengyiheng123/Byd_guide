package com.example.asus.xyd_order.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Zheng on 2017/3/2.
 */
public class SiteViewpagerAdapter extends FragmentPagerAdapter {
    List<Fragment> mList;
    public SiteViewpagerAdapter(FragmentManager fm, List<Fragment> mList) {
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
