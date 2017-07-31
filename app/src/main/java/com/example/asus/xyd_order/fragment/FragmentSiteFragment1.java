package com.example.asus.xyd_order.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseArrayAdapter;
import com.example.asus.xyd_order.holder.AttractionDetailsHolder;
import com.example.asus.xyd_order.ui.MyListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zheng on 2017/3/2.
 */
@SuppressLint("ValidFragment")
public class FragmentSiteFragment1 extends Fragment {
    String mark;
    private MyListView multiple;
    private final List<String> mList1;
    private final List<String> mList2;

    public FragmentSiteFragment1(String mark){
        this.mark=mark;
        mList1 = new ArrayList<>();
        mList1.add("1");
        mList1.add("1");
        mList1.add("1");
        mList1.add("1");
        mList1.add("1");
        mList1.add("1");
        mList1.add("1");
        mList1.add("1");
        mList1.add("1");
        mList2 = new ArrayList<>();
        mList2.add("1");
        mList2.add("1");
        mList2.add("1");
        mList2.add("1");
        mList2.add("1");
        mList2.add("1");
        mList2.add("1");
        mList2.add("1");
        mList2.add("1");
        mList2.add("1");
        mList2.add("1");
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_mutiplechoce,null);
        initListView(view);
        return view;
    }

    private void initListView(View v) {
        multiple = (MyListView) v.findViewById(R.id.multiple);
        if (mark.equals("1")){
            multiple.setAdapter(new BaseArrayAdapter<>(getContext(), new BaseArrayAdapter.OnCreateViewHolderListener() {
                @Override
                public Object onCreateViewHolder() {
                    return new AttractionDetailsHolder();
                }
            },mList1));
        }else {
            multiple.setAdapter(new BaseArrayAdapter<>(getContext(), new BaseArrayAdapter.OnCreateViewHolderListener() {
                @Override
                public Object onCreateViewHolder() {
                    return new AttractionDetailsHolder();
                }
            },mList2));
        }

    }
}
