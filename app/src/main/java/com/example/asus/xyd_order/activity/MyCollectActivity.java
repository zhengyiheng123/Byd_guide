package com.example.asus.xyd_order.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.adapter.MyCollectMultipleAdapter;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.base.BaseArrayAdapter;
import com.example.asus.xyd_order.holder.CollectHolder;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.Collections;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.refresh.widget.swipetorefresh.RefreshLayout;
import com.example.asus.xyd_order.utils.ActivityFactory;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/3/24.
 */
public class MyCollectActivity extends BaseActivity{

    private ListView lv_collect;
    private List<Collections.CollectionsBean> mList=new ArrayList<>();
    private MyCollectMultipleAdapter adapter;
    private TextView tv_empty;

    @Override
    public void myOnclick(View view) {

    }

    @Override
    public void setToolbar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        TextView title= (TextView) findViewById(R.id.tv_title);
        title.setText("我的收藏");
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_mycollect;
    }


    @Override
    public int getData() throws Exception {
        getNetData();
        return 0;
    }

    @Override
    public void initView() {
        tv_empty = (TextView) findViewById(R.id.tv_empty);
        initRefresh();
    }

    private void initRefresh() {
        lv_collect = (ListView) findViewById(R.id.lv_collect);
        lv_collect.setEmptyView(tv_empty);
        adapter = new MyCollectMultipleAdapter(context,mList,2);
        lv_collect.setAdapter(adapter);
    }

    @Override
    public void setEvent() {
        lv_collect.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (!TextUtils.isEmpty(mList.get(i).getScene_id())){
                    switch (Integer.valueOf(mList.get(i).getSub_cate_id())){
                        case 6:
                            ActivityFactory.gotoAttractionDetails(context,mList.get(i).getScene_id()+"");
                            break;
                        case 5:
                            ActivityFactory.gotoAttractionsNomal(context,Integer.valueOf(mList.get(i).getScene_id()));
                            break;
                        case 7:
                            ActivityFactory.gotoAttractionTicket(context,Integer.valueOf(mList.get(i).getScene_id()));
                            break;
                    }
                }else {
                    ActivityFactory.gotoTuancanDetails(context,mList.get(i).getRes_id()+"",mList.get(i).getLogo_path());
                }

            }
        });
    }



    //获取网络数据

    private void getNetData(){
        Observable<HttpResult<Collections>> result= ServiceApi.getInstance().getServiceContract().collectionsList(apitoken);
        result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Collections>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Collections collections) {
                        if (collections.getCollections()!=null){
                            mList.clear();
                            mList.addAll(collections.getCollections());
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    };
}
