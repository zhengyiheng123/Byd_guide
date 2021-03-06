package com.example.asus.xyd_order.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.activity.Activity_Edit_Release;
import com.example.asus.xyd_order.adapter.AllOrderAdapter;
import com.example.asus.xyd_order.base.BaseFragment;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.net.result.MyDemandBean;
import com.example.asus.xyd_order.refresh.widget.swipetorefresh.RefreshLayout;
import com.example.asus.xyd_order.utils.SharedPreferenceUtils;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/3/13.
 */
public class Fragment_Canceled_Demand extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private ListView lv_all;
    private ArrayList<MyDemandBean.DemandsBean> mList;
    private AllOrderAdapter adapter;
    private RefreshLayout refresh;

    /**
     * 获取网络数据
     */
    private void getNetData(){
        showDialog();
        String apitoken= (String) SharedPreferenceUtils.getParam(context,"apitoken","");
        Observable<HttpResult<MyDemandBean>> result= ServiceApi.getInstance().getServiceContract().getMyDeman("-1",apitoken);
        result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MyDemandBean>() {
                    @Override
                    public void onCompleted() {
dismissDialog();
                        refresh.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        refresh.setRefreshing(false);
dismissDialog();
                    }

                    @Override
                    public void onNext(MyDemandBean myDemandBean) {
                        mList.clear();
                        mList.addAll(myDemandBean.getDemands());
                        adapter.notifyDataSetChanged();
                    }
                });
    }
    @Override
    public void myOnclick(View view) {

    }

    @Override
    public void initView(View view) {
        initListView(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        onRefresh();
    }

    private void initListView(View v) {
        refresh = (RefreshLayout) v.findViewById(R.id.refresh);
        lv_all = (ListView) v.findViewById(R.id.lv_cancel);
        TextView tv_empty= (TextView) v.findViewById(R.id.tv_empty);
        lv_all.setEmptyView(tv_empty);
        adapter = new AllOrderAdapter(mList,getActivity());
        refresh.setAdapter(adapter,lv_all);
        refresh.setOnRefreshListener(this);
    }
    @Override
    public int getResource() {
        return R.layout.fragment_canceled_demand;
    }

    @Override
    public int initData() throws Exception {
        mList = new ArrayList<>();
        return 0;
    }

    @Override
    public void setEvent() {
        lv_all.setOnItemClickListener((parent, view, position, id) -> {Intent intent=new Intent(context, Activity_Edit_Release.class);
            intent.putExtra("dmd_id",mList.get(position).getDmd_id()+"");
            intent.putExtra("ord_status",mList.get(position).getOrd_status()+"");
            context.startActivity(intent);});
    }

    @Override
    public void onRefresh() {
        getNetData();
    }
}
