package com.example.asus.xyd_order.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.base.BaseArrayAdapter;
import com.example.asus.xyd_order.holder.HistoryHolder;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.History_Mode;
import com.example.asus.xyd_order.net.result.HttpResult;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/6/26.
 */

public class History_Activity extends BaseActivity {
    public static History_Activity instance;
    private ListView lv_history;

    private List<History_Mode.SamplesBean> mlist=new ArrayList<>();
    private BaseArrayAdapter adapter;

    @Override
    public void myOnclick(View view) {

    }

    @Override
    public void setToolbar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        TextView tv_title= (TextView) findViewById(R.id.tv_title);
        tv_title.setText("历史样本");
        iv_back.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_history;
    }


    @Override
    public int getData() throws Exception {
        return 0;
    }

    @Override
    public void initView() {
        lv_history = (ListView) findViewById(R.id.lv_history);
        TextView tv_empty= (TextView) findViewById(R.id.tv_empty);
        lv_history.setEmptyView(tv_empty);
        adapter = new BaseArrayAdapter(context, new BaseArrayAdapter.OnCreateViewHolderListener() {
            @Override
            public Object onCreateViewHolder() {
                return new HistoryHolder();
            }
        },mlist);
        lv_history.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        instance=this;
        getNetData();
    }

    @Override
    public void setEvent() {

    }
    /**
     * 获取网络数据
     */
    public void getNetData(){
        Observable<HttpResult<History_Mode>> result= ServiceApi.getInstance().getServiceContract().historyList(apitoken);
        result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<History_Mode>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        toastShow(e.getMessage());
                    }

                    @Override
                    public void onNext(History_Mode history_mode) {
                        mlist.clear();
                        mlist.addAll(history_mode.getSamples());
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}
