package com.example.asus.xyd_order.activity;

import android.content.Intent;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.base.BaseArrayAdapter;
import com.example.asus.xyd_order.holder.CountryHolder;
import com.example.asus.xyd_order.holder.Item_country_holder;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.CountryBean;
import com.example.asus.xyd_order.net.result.HttpResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/8/7.
 */

public class SelectCountry extends BaseActivity {
    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.gv_country)
    GridView gv_country;
    @Bind(R.id.tv_submit)
    TextView tv_submit;

    public static SelectCountry instance;


    private List<CountryBean.CountriesBean> countriesList=new ArrayList<>();
    private BaseArrayAdapter adapter;

    private List<CountryBean.CountriesBean> dataList=new ArrayList<>();
    @Override
    public void myOnclick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_submit:
                dataList.clear();
                for ( int i =0;i<countriesList.size();i++){
                    if (countriesList.get(i).getState() == 1){
                        dataList.add(countriesList.get(i));
                    }
                }
                Intent intent=new Intent();
                intent.putExtra("countryList", (Serializable) dataList);
                this.setResult(0,intent);
                finish();
                break;
        }
    }

    @Override
    public void setToolbar() {
        tv_title.setText("选择目标国家");
        iv_back.setVisibility(View.GONE);
        tv_submit.setVisibility(View.VISIBLE);
        tv_submit.setText("完成");
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_select_country;
    }

    @Override
    public int getData() throws Exception {
        getCountry();
        return 0;
    }

    @Override
    protected void onResume() {
        super.onResume();
        instance=this;
    }

    @Override
    public void initView() {
        adapter = new BaseArrayAdapter<>(context, new BaseArrayAdapter.OnCreateViewHolderListener() {
            @Override
            public Object onCreateViewHolder() {
                return new Item_country_holder();
            }
        },countriesList);
        gv_country.setAdapter(adapter);
    }

    @Override
    public void setEvent() {
        iv_back.setOnClickListener(this);
        tv_submit.setOnClickListener(this);
    }
    /**
     * 获取国家列表
     */
    private  void getCountry(){

        Observable<HttpResult<CountryBean>> result= ServiceApi.getInstance().getServiceContract().getCountrys(apitoken);
        result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CountryBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CountryBean countryBean) {
                        if (countryBean.getCountries()!=null){
                            countriesList.addAll(countryBean.getCountries());
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }
    public void notifyAdapter(){
        adapter.notifyDataSetChanged();
    }
}
