package com.example.asus.xyd_order.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.base.BaseArrayAdapter;
import com.example.asus.xyd_order.holder.MonthHolder;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.Calender;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.utils.ActivityFactory;
import com.example.asus.xyd_order.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/5/5.
 */

public class Journy_Activity extends BaseActivity {

    private List<Calender.CalendarBean> mList=new ArrayList<>();
    private ListView lv_route;
    private BaseArrayAdapter adapter;

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
        return R.layout.activity_journy;
    }


    @Override
    public int getData() throws Exception {
        getNetData();
        return 0;
    }

    @Override
    public void initView() {
        TextView tv_empty= (TextView) findViewById(R.id.tv_empty);
        lv_route = (ListView) findViewById(R.id.lv_route);
        lv_route.setEmptyView(tv_empty);
        lv_route.setEmptyView(View.inflate(context,R.layout.emptyview,null));
        adapter = new BaseArrayAdapter(context, new BaseArrayAdapter.OnCreateViewHolderListener() {
            @Override
            public Object onCreateViewHolder() {
                return new MonthHolder();
            }
        },mList);
        lv_route.setAdapter(adapter);
    }

    @Override
    public void setEvent() {
    }

    /**
     * 获取网络数据
     */
    private void getNetData(){
        showDialog();
        Observable<HttpResult<Calender>> result= ServiceApi.getInstance().getServiceContract().calenderList(apitoken);
        result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Calender>() {
                    @Override
                    public void onCompleted() {
                        dismissDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        toastShow(e.getMessage());
                    }

                    @Override
                    public void onNext(Calender calender) {
                        mList.clear();
                        mList.addAll(calender.getCalendar());
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}
