package com.example.asus.xyd_order.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.asus.xyd_order.base.BaseFragment;

import java.util.List;

/**
 * Created by Administrator on 2016/12/5.
 */
public class DemandPagerAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> mlist;
    private List<String> titleList;
    public DemandPagerAdapter(FragmentManager fm, List<BaseFragment> mList,List<String> titleList) {
        super(fm);
        mlist=mList;
        this.titleList=titleList;
    }

    @Override
    public Fragment getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }

}
