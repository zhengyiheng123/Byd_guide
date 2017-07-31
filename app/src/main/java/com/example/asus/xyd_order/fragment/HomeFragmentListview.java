package com.example.asus.xyd_order.fragment;

import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseFragmentTemp;
import com.example.asus.xyd_order.refresh.widget.XListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 2017/2/15.
 */
public class HomeFragmentListview extends BaseFragmentTemp implements XListView.IXListViewListener{

    private List<String> dataList;
    private ArrayAdapter<String> mAdapter;
    private XListView lv_listview;
    private Handler handler=new Handler();
    @Override
    public void myOnclick(View view) {

    }

    @Override
    public void initView(View view) {
        lv_listview = (XListView) view.findViewById(R.id.lv_listview);
        mAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,dataList);
        lv_listview.setPullLoadEnable(true);
        lv_listview.setPullRefreshEnable(true);
//        lv_listview.setAutoLoadEnable(true);
        lv_listview.setXListViewListener(this);
        lv_listview.setRefreshTime(getTime());
        lv_listview.setAdapter(mAdapter);

    }

    @Override
    public int getResource() {
        return R.layout.fragment_home;
    }

    @Override
    public void initData() {
        dataList = new ArrayList<>();
        for (int i=0;i<10;i++){
            dataList.add("条目："+i);
        }
    }

    @Override
    public void setTitle(RelativeLayout rl_parent, TextView tv_title, TextView tv_secondTitle, TextView third_title) {

    }

    @Override
    public void setEvent() {

    }

    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dataList.add(0,"新加条目");
                mAdapter.notifyDataSetChanged();
                onLoad();
            }
        },2000);
    }

    @Override
    public void onLoadMore() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<5;i++){
                    dataList.add("后加条目+"+i);
                }
                mAdapter.notifyDataSetChanged();
                onLoad();
            }
        },2000);
    }

    private void onLoad() {
        lv_listview.stopRefresh();
        lv_listview.stopLoadMore();
        lv_listview.setRefreshTime(getTime());
    }
}
