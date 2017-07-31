package com.example.asus.xyd_order.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.base.BaseArrayAdapter;
import com.example.asus.xyd_order.holder.Route_ListHolder;
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
 * Created by Zheng on 2017/5/5.
 */

public class Activity_Route_List extends BaseActivity {

    private ListView lv_route_list;
    private List<MyOrderBean.OrdersBean> mlist=new ArrayList<>();
    private BaseArrayAdapter adapter;
    private int stamp;

    @Override
    public void myOnclick(View view) {

    }

    @Override
    public void setToolbar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(v -> onBackPressed());
        TextView tv_title= (TextView) findViewById(R.id.tv_title);
        tv_title.setText("行程日历");
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_route_list;
    }


    @Override
    public int getData() throws Exception {
        stamp = getIntent().getIntExtra("stamp",0);
            getNetData();
        return 0;
    }

    @Override
    public void initView() {
        lv_route_list = (ListView) findViewById(R.id.lv_route_list);
        lv_route_list.setEmptyView(View.inflate(context,R.layout.emptyview,null));
        adapter = new BaseArrayAdapter(context, new BaseArrayAdapter.OnCreateViewHolderListener() {
            @Override
            public Object onCreateViewHolder() {
                return new Route_ListHolder();
            }
        },mlist);
        lv_route_list.setAdapter(adapter);
    }

    @Override
    public void setEvent() {

    }
    /**
     * 请求网络数据
     */
    private void getNetData(){
        showDialog();
        Observable<HttpResult<MyOrderBean>> result= ServiceApi.getInstance().getServiceContract().myOrderList(apitoken,"",stamp+"");
        result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MyOrderBean>() {
                    @Override
                    public void onCompleted() {
                        dismissDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        toastShow(e.getMessage());
                        dismissDialog();
                    }

                    @Override
                    public void onNext(MyOrderBean myOrderBean) {
                        mlist.clear();
                        mlist.addAll(myOrderBean.getOrders());
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}
