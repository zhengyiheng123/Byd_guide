package com.example.asus.xyd_order.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.base.BaseArrayAdapter;
import com.example.asus.xyd_order.holder.ZhongcanHolder;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.CityListBean;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.net.result.RestaurantBean;
import com.example.asus.xyd_order.utils.ActivityFactory;


import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnItemClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/10/23.
 */

public class CantingActivity extends BaseActivity {
    @Bind(R.id.iv_back)ImageView ivBack;
    @Bind(R.id.auto_text)AutoCompleteTextView autoText;
    @Bind(R.id.tv_search)TextView tv_search;
    @Bind(R.id.lv_searchresult)ListView mListView;

    @Bind(R.id.tv_empty)TextView mEmpty;
    private BaseArrayAdapter adapter;
    List<RestaurantBean.RestaurantsBean> mList=new ArrayList<>();

    @OnClick({R.id.iv_back,R.id.tv_search})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_search:
                String text=autoText.getText().toString();
                getZhongcanInfos(text);
                break;
        }
    }
    @OnItemClick(R.id.lv_searchresult)
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
        ActivityFactory.gotoTuancanDetails(context,mList.get(i).getRes_id()+"",mList.get(i).getLogo_path());
    }
    @Override
    public void myOnclick(View view) {
    }

    @Override
    public void setToolbar() {

    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_searchcanting;
    }

    @Override
    public int getData() throws Exception {
        return 0;
    }
    public void getZhongcanInfos(String key_word){
        Observable<HttpResult<RestaurantBean>> result= ServiceApi.getInstance().getServiceContract().find(apitoken,key_word);
        result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RestaurantBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(RestaurantBean bean) {
                        mList.clear();
                        if (bean.getRestaurants().size()>0){
                            mList.addAll(bean.getRestaurants());
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }
    @Override
    public void initView() {
        mListView.setEmptyView(mEmpty);
        adapter = new BaseArrayAdapter(context, new BaseArrayAdapter.OnCreateViewHolderListener() {
            @Override
            public Object onCreateViewHolder() {
                return new ZhongcanHolder();
            }
        },mList);
        mListView.setAdapter(adapter);
    }

    @Override
    public void setEvent() {
    }
}
