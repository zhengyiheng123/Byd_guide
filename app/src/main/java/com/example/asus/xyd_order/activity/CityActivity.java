package com.example.asus.xyd_order.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.app.APP;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.base.BaseArrayAdapter;
import com.example.asus.xyd_order.holder.CityHolder;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.CityListBean;
import com.example.asus.xyd_order.net.result.HttpResult;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/5/2.
 */

public class CityActivity extends BaseActivity {

    private ListView lv_city;
    private List<CityListBean.RegionsBean> cityList;
    private BaseArrayAdapter adapter;
    private View view;

    @Override
    public void myOnclick(View view) {
        switch (view.getId()){
            case R.id.tv_city:
                APP.getApplication().setCityBean(null);
                finish();
                break;
        }
    }

    @Override
    public void setToolbar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(v -> onBackPressed());
        TextView tv_title= (TextView) findViewById(R.id.tv_title);
        tv_title.setText("城市选择");
    }

    @Override
    protected int getResourceId() {
        return R.layout.axctivity_city;
    }


    @Override
    public int getData() throws Exception {
        cityList = new ArrayList<>();
        getNetData();
        return 0;
    }

    @Override
    public void initView() {
        lv_city = (ListView) findViewById(R.id.lv_city);
        view = LayoutInflater.from(context).inflate(R.layout.item_city,null);
        TextView tv_city= (TextView) view.findViewById(R.id.tv_city);
        tv_city.setText("不限");
        tv_city.setOnClickListener(this);
        lv_city.addHeaderView(view);
        adapter = new BaseArrayAdapter<>(context, new BaseArrayAdapter.OnCreateViewHolderListener() {
            @Override
            public Object onCreateViewHolder() {
                return new CityHolder();
            }
        },cityList);
        lv_city.setAdapter(adapter);
    }
    private CityListBean.RegionsBean cityBean;
    @Override
    public void setEvent() {
        lv_city.setOnItemClickListener((adapterView, view1, i, l) -> {
            if (i==0){
                return;
            }
            cityBean=cityList.get(i-1);
            APP.getApplication().setCityBean(cityBean);
            finish();
        });
    }
    /**
     * 获取城市列表
     */
    private void getNetData(){
        Observable<HttpResult<CityListBean>> result= ServiceApi.getInstance().getServiceContract().cityList(apitoken);
        result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CityListBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CityListBean cityListBean) {
                        if (cityListBean.getRegions()!=null){
                            cityList.clear();
                            cityList.addAll(cityListBean.getRegions());
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}
