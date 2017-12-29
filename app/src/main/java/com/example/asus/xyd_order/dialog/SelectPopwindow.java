package com.example.asus.xyd_order.dialog;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseArrayAdapter;
import com.example.asus.xyd_order.holder.AutoTextHolder;
import com.example.asus.xyd_order.net.result.RegionsBean;
import com.example.asus.xyd_order.ui.SelectPopWindow;

import java.util.List;

/**
 * Created by Zheng on 2017/12/29.
 */

public class SelectPopwindow extends PopupWindow {
    private Window window;
    private final View view;
    private final ListView lv_regions;

    public SelectPopwindow(Activity activity, List<RegionsBean> regionsBeen,OnItemClick onItemClick){
        this.window=activity.getWindow();
        LayoutInflater inflater=LayoutInflater.from(activity);
        view = inflater.inflate(R.layout.pop_select_city,null,false);
        lv_regions = (ListView) view.findViewById(R.id.lv_regions);
        BaseArrayAdapter adapter=new BaseArrayAdapter(activity, new BaseArrayAdapter.OnCreateViewHolderListener() {
            @Override
            public Object onCreateViewHolder() {
                return new AutoTextHolder();
            }
        },regionsBeen);
        lv_regions.setAdapter(adapter);
        lv_regions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                onItemClick.onItemClick(i);
            }
        });
        this.setContentView(view);
        //设置SelectPicPopupWindow的View
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setAnimationStyle(R.style.PopupWindowAnimation);

    }
    public void showPop(View view, int xoff, int yoff){
        showAsDropDown(view,xoff,yoff);
    }
    private OnItemClick onItemClick;
    public interface OnItemClick{
        void onItemClick(int position);
    }
}
