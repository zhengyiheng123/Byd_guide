package com.example.asus.xyd_order.fragment;

import android.view.View;
import android.widget.ListView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseArrayAdapter;
import com.example.asus.xyd_order.base.BaseFragment;
import com.example.asus.xyd_order.holder.MyOrder_All_Holder;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.net.result.MyOrderBean;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/7/20.
 */

public class MyOrderFragment_All extends BaseFragment {

    private ListView lv_all;

    private List<MyOrderBean.OrdersBean> mList=new ArrayList<>();
    private BaseArrayAdapter adapter;

    @Override
    public void myOnclick(View view) {

    }

    @Override
    public void initView(View v) {
        lv_all = (ListView) v.findViewById(R.id.lv_all);
        adapter = new BaseArrayAdapter(context, new BaseArrayAdapter.OnCreateViewHolderListener() {
            @Override
            public Object onCreateViewHolder() {
                return new MyOrder_All_Holder();
            }
        },mList);
        lv_all.setAdapter(adapter);
    }

    @Override
    public int getResource() {
        return R.layout.fragment_myorders;
    }

    @Override
    public int initData() throws Exception {
        getNetData();
        return 0;
    }

    @Override
    public void setEvent() {

    }
    //获取网络数据
    private void getNetData(){
        Observable<HttpResult<MyOrderBean>> result= ServiceApi.getInstance().getServiceContract().myOrderList(apitoken,"","");
        result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MyOrderBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MyOrderBean myOrderBean) {
                        mList.clear();
                        mList.addAll(myOrderBean.getOrders());
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}
