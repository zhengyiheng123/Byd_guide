package com.example.asus.xyd_order.fragment;

import android.view.View;
import android.widget.ListView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseArrayAdapter;
import com.example.asus.xyd_order.base.BaseFragment;
import com.example.asus.xyd_order.holder.SingleHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zheng on 2017/2/24.
 */
public class FragmentSingleChoce extends BaseFragment {

    private ListView multiple;
    private List<String> mlist;

    @Override
    public void myOnclick(View view) {

    }

    @Override
    public void initView(View v) {
        multiple = (ListView) v.findViewById(R.id.multiple);
        initAdapter();
    }

    private void initAdapter() {
        multiple.setAdapter(new BaseArrayAdapter<>(context, new BaseArrayAdapter.OnCreateViewHolderListener() {
            @Override
            public Object onCreateViewHolder() {
                return new SingleHolder();
            }
        },mlist));
    }

    @Override
    public int getResource() {
        return R.layout.fragment_singlechoce;
    }

    @Override
    public int initData() throws Exception {
        mlist = new ArrayList<>();
        mlist.add("1");
        mlist.add("1");
        mlist.add("1");
        mlist.add("1");
        mlist.add("1");
        mlist.add("1");
        return 1;
    }

    @Override
    public void setEvent() {

    }

}
