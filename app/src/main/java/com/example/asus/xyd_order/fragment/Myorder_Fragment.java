package com.example.asus.xyd_order.fragment;

import android.annotation.SuppressLint;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseArrayAdapter;
import com.example.asus.xyd_order.base.BaseFragment;
import com.example.asus.xyd_order.holder.MyOrderHolder;
import com.example.asus.xyd_order.holder.MyOrder_All_Holder;
import com.example.asus.xyd_order.holder.MyordersHolder;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.net.result.MyOrderBean;
import com.example.asus.xyd_order.refresh.widget.swipetorefresh.RefreshLayout;
import com.example.asus.xyd_order.utils.ActivityFactory;
import com.example.asus.xyd_order.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/5/8.
 */

@SuppressLint("ValidFragment")
public class Myorder_Fragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    private String num;
    private RefreshLayout refresh;

    public Myorder_Fragment(String num){
        this.num=num;
    }
    private ListView lv_all;

    private List<MyOrderBean.OrdersBean> mList=new ArrayList<>();
    private BaseArrayAdapter adapter;

    @Override
    public void myOnclick(View view) {

    }
    @Override
    public void onResume() {
        super.onResume();
        onRefresh();
    }

    @Override
    public void initView(View view) {
        lv_all = (ListView) view.findViewById(R.id.lv_all);
        refresh = (RefreshLayout) view.findViewById(R.id.refresh);
        TextView tv_empty= (TextView) view.findViewById(R.id.tv_empty);
        lv_all.setEmptyView(tv_empty);
        adapter = new BaseArrayAdapter(context, new BaseArrayAdapter.OnCreateViewHolderListener() {
            @Override
            public Object onCreateViewHolder() {
                return new MyOrder_All_Holder();
            }
        },mList);
        refresh.setAdapter(adapter,lv_all);
        refresh.setOnRefreshListener(this);
    }

    @Override
    public int getResource() {
        return R.layout.fragment_myorders;
    }

    @Override
    public int initData() throws Exception {
        return 0;
    }

    @Override
    public void setEvent() {
        lv_all.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
    }
    //获取网络数据
    private void getNetData(){
        showDialog();
        Observable<HttpResult<MyOrderBean>> result= ServiceApi.getInstance().getServiceContract().myOrderList(apitoken,num,"");
        result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MyOrderBean>() {
                    @Override
                    public void onCompleted() {
                        dismissDialog();
                        refresh.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        toastShow(e.getMessage());
                        dismissDialog();
                        refresh.setRefreshing(false);
                    }

                    @Override
                    public void onNext(MyOrderBean myOrderBean) {
                        mList.clear();
                        mList.addAll(myOrderBean.getOrders());
                        adapter.notifyDataSetChanged();
                        if (num.equals("1")){
                            if (mList.size()>0){
                                MyOrdersActivity.instance.setMsgRedPoint(true);
                            }else {
                                MyOrdersActivity.instance.setMsgRedPoint(false);
                            }
                        }
                    }
                });
    }

    @Override
    public void onRefresh() {
        getNetData();
    }
}
