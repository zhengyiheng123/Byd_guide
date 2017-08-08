package com.example.asus.xyd_order.fragment;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseArrayAdapter;
import com.example.asus.xyd_order.base.BaseFragment;
import com.example.asus.xyd_order.holder.SearchZhongcanHolder;
import com.example.asus.xyd_order.holder.ZhongcanHolder;
import com.example.asus.xyd_order.net.result.SerchResult;
import com.example.asus.xyd_order.ui.MyListView;
import com.example.asus.xyd_order.utils.ActivityFactory;

import java.util.List;

/**
 * Created by Zheng on 2017/8/8.
 */

@SuppressLint("ValidFragment")
public class RestaurantFragment extends BaseFragment {

    private MyListView lv_zhongcan;
    private BaseArrayAdapter adapter;
    List<SerchResult.RestaurantsBean> mList;
    private TextView tv_empty;

    public RestaurantFragment(List<SerchResult.RestaurantsBean> mList) {
        this.mList=mList;
    }

    @Override
    public void myOnclick(View view) {

    }

    @Override
    public void initView(View view) {
        tv_empty = (TextView) view.findViewById(R.id.tv_empty);
        lv_zhongcan = (MyListView) view.findViewById(R.id.lv_zhongcan);
        lv_zhongcan.setEmptyView(tv_empty);
        adapter = new BaseArrayAdapter(context, new BaseArrayAdapter.OnCreateViewHolderListener() {
            @Override
            public Object onCreateViewHolder() {
                return new SearchZhongcanHolder();
            }
        },mList);
        lv_zhongcan.setAdapter(adapter);
    }

    @Override
    public int getResource() {
        return R.layout.fragment_restaurant;
    }

    @Override
    public int initData() throws Exception {
        return 0;
    }

    @Override
    public void setEvent() {
        lv_zhongcan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ActivityFactory.gotoTuancanDetails(context,mList.get(i).getRes_id()+"",mList.get(i).getLogo_path());
            }
        });
    }

}
