package com.example.asus.xyd_order.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseArrayAdapter;
import com.example.asus.xyd_order.base.BaseFragment;
import com.example.asus.xyd_order.holder.CatchViewHolder;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.net.result.TakingOrderBean;
import com.example.asus.xyd_order.refresh.widget.XScrollView;
import com.example.asus.xyd_order.refresh.widget.swipetorefresh.RefreshLayout;
import com.example.asus.xyd_order.utils.ActivityFactory;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ASUS on 2017/2/15.
 */
public class CatchOrdersFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    private ArrayList<TakingOrderBean.DemandsBean> dataList=new ArrayList<>();
    private BaseArrayAdapter arrayAdapter;
    private ListView lv_catchorders;
    private RefreshLayout refreshLayout;

    public static CatchOrdersFragment instance;

    //正在刷新
    private boolean isRefreshing;
    private TextView tv_empty;

    @Override
    public void myOnclick(View view) {
        ImageView iv_back= (ImageView) view.findViewById(R.id.iv_back);
        iv_back.setVisibility(View.GONE);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser)
        {
            refreshLayout.setRefreshing(true);
            onRefresh();
        }
    }

    @Override
    public void initView(View v) {
        ImageView iv_back= (ImageView) v.findViewById(R.id.iv_back);
        iv_back.setVisibility(View.GONE);
        TextView tv_title= (TextView) v.findViewById(R.id.tv_title);
        tv_title.setText("去接单");
        initListView(v);
    }

    private void initListView(View v) {
        lv_catchorders = (ListView) v.findViewById(R.id.lv_catchorders);
        tv_empty = (TextView) v.findViewById(R.id.tv_empty);
        refreshLayout = (RefreshLayout) v.findViewById(R.id.refresh);
        refreshLayout.setColorSchemeColors(context.getResources().getColor(R.color.material_blue_600),
                context.getResources().getColor(R.color.tool_bar_color));
        refreshLayout.setProgressBackgroundColorSchemeResource(R.color.background_color);
        arrayAdapter = new BaseArrayAdapter(getContext(), new BaseArrayAdapter.OnCreateViewHolderListener() {
            @Override
            public Object onCreateViewHolder() {
                return new CatchViewHolder(getActivity());
            }
        }, dataList);
        refreshLayout.setAdapter(arrayAdapter,lv_catchorders);
    }

    @Override
    public int getResource() {
        return R.layout.fragment_catchorders;
    }

    @Override
    public int initData() {
        return 0;
    }


    @Override
    public void setEvent() {
        lv_catchorders.setOnItemClickListener((parent, view, position, id) -> {
                ActivityFactory.gotoCatchOrders(context,dataList.get(position).getDmd_id()+"");
        });
//        refreshLayout.setOnLoadListener(this);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setLoading(false);
    }


    @Override
    public void onResume() {
        super.onResume();
        instance=this;
        onRefresh();
    }

    /**
     * 请求网络数据
     */
    public void getNetData(){
        Observable<HttpResult<TakingOrderBean>> result= ServiceApi.getInstance().getServiceContract().takingOrderList(apitoken);
        result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TakingOrderBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        toastShow(e.getMessage());
                    }

                    @Override
                    public void onNext(TakingOrderBean takingOrderBean) {
                        if (isRefreshing) {
                            dataList.clear();
                            dataList.addAll(takingOrderBean.getDemands());
                            arrayAdapter.notifyDataSetChanged();
                            refreshLayout.setRefreshing(false);
                            if (takingOrderBean.getDemands().size()<=0){
                                tv_empty.setVisibility(View.VISIBLE);
                            }else {
                                tv_empty.setVisibility(View.GONE);
                            }
                        }
                    }
                });
    }

    @Override
    public void onRefresh() {
        isRefreshing=true;
        getNetData();
    }
}
