package com.example.asus.xyd_order.fragment;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseArrayAdapter;
import com.example.asus.xyd_order.base.BaseFragment;
import com.example.asus.xyd_order.base.BaseViewHolder;
import com.example.asus.xyd_order.holder.AttractionsHolder;
import com.example.asus.xyd_order.holder.SearchAttractionsHolder;
import com.example.asus.xyd_order.holder.SearchZhongcanHolder;
import com.example.asus.xyd_order.net.result.SerchResult;
import com.example.asus.xyd_order.ui.MyListView;
import com.example.asus.xyd_order.utils.ActivityFactory;

import java.util.List;

/**
 * Created by Zheng on 2017/8/8.
 */
@SuppressLint("ValidFragment")
public class AttractionsFragment extends BaseFragment {

    private MyListView lv_attractions;
    List<SerchResult.ScenesBean> seceeList;
    private BaseArrayAdapter<SerchResult.ScenesBean, BaseViewHolder> arrayAdapter;
    private TextView tv_empty;

    public AttractionsFragment(List<SerchResult.ScenesBean> seceeList) {
        this.seceeList=seceeList;
    }

    @Override
    public void myOnclick(View view) {

    }

    @Override
    public void initView(View view) {
        tv_empty = (TextView) view.findViewById(R.id.tv_empty);
        lv_attractions = (MyListView) view.findViewById(R.id.lv_attractions);
        lv_attractions.setEmptyView(tv_empty);
        arrayAdapter = new BaseArrayAdapter<>(context, new BaseArrayAdapter.OnCreateViewHolderListener() {
            @Override
            public Object onCreateViewHolder() {
                return new SearchAttractionsHolder();
            }
        },seceeList);
        lv_attractions.setAdapter(arrayAdapter);
    }

    @Override
    public int getResource() {
        return R.layout.fragment_attractions;
    }

    @Override
    public int initData() throws Exception {
        return 0;
    }

    @Override
    public void setEvent() {
        lv_attractions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (seceeList.get(i).getSub_cate_id()){
                    case 6:
                        ActivityFactory.gotoAttractionDetails(context,seceeList.get(i).getScene_id()+"");
                        break;
                    case 5:
                        ActivityFactory.gotoAttractionsNomal(context,seceeList.get(i).getScene_id());
                        break;
                    case 7:
                        ActivityFactory.gotoAttractionTicket(context,seceeList.get(i).getScene_id());
                        break;
                }
            }
        });
    }
}
