package com.example.asus.xyd_order.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.activity.Activity_Geted_Orders_Details;
import com.example.asus.xyd_order.base.BaseArrayAdapter;
import com.example.asus.xyd_order.base.BaseFragment;
import com.example.asus.xyd_order.holder.OrdersGetedHolder;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.net.result.MyDemandBean;
import com.example.asus.xyd_order.refresh.widget.swipetorefresh.RefreshLayout;
import com.example.asus.xyd_order.utils.SharedPreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/7/6.
 */

public class GetedFragment_All extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    ListView lv_common;

    private List<MyDemandBean.DemandsBean> mList=new ArrayList<>();
    private BaseArrayAdapter adapter;

    public static GetedFragment_All instance;
    private RefreshLayout refresh;

    @Override
    public void myOnclick(View view) {

    }

    @Override
    public void onResume() {
        super.onResume();
        instance=this;
        onRefresh();
    }

    @Override
    public void initView(View view) {
        refresh = (RefreshLayout) view.findViewById(R.id.refresh);
        lv_common= (ListView) view.findViewById(R.id.lv_common);
        TextView tv_empty= (TextView) view.findViewById(R.id.tv_empty);
        lv_common.setEmptyView(tv_empty);
        adapter = new BaseArrayAdapter(context, new BaseArrayAdapter.OnCreateViewHolderListener() {
            @Override
            public Object onCreateViewHolder() {
                return new OrdersGetedHolder(getActivity());
            }
        },mList);
        refresh.setAdapter(adapter,lv_common);
        refresh.setOnRefreshListener(this);
    }

    @Override
    public int getResource() {
        return R.layout.fragment_geted_all;
    }

    @Override
    public int initData() throws Exception {
        return 0;
    }

    @Override
    public void setEvent() {
        lv_common.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getActivity(), Activity_Geted_Orders_Details.class);
                intent.putExtra("dmd_id",mList.get(i).getDmd_id()+"");
                intent.putExtra("status",mList.get(i).getOrd_status()+"");
                startActivity(intent);
            }
        });
    }


    /**
     * 获取网络数据
     */
    public void getNetData(){
        showDialog();
        String apitoken= (String) SharedPreferenceUtils.getParam(context,"apitoken","");
        Observable<HttpResult<MyDemandBean>> result= ServiceApi.getInstance().getServiceContract().getMyDemanOther("",apitoken);
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
                        toastShow(e.getMessage());
                        dismissDialog();
                        refresh.setRefreshing(false);
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
    public void onRefresh() {
        getNetData();
    }
}
