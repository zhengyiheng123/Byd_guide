package com.example.asus.xyd_order.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.base.BaseArrayAdapter;
import com.example.asus.xyd_order.controler.CategoryControl;
import com.example.asus.xyd_order.holder.ConsulateHolder;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.CityListBean;
import com.example.asus.xyd_order.net.result.EmbassyBean;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.selectcity.SelectCityActivity;
import com.example.asus.xyd_order.ui.SelectPopWindow;
import com.example.asus.xyd_order.utils.ActivityFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/3/7.
 */
public class EmergencyActivity extends BaseActivity  {

    private List<CityListBean.RegionsBean> countryList=new ArrayList<>();

    private ListView lv_sonsulte;
    private List<EmbassyBean.EmbassiesBean> mList=new ArrayList<>();
    private TextView tv_cancel_order,tv_procedure,tv_notice;
    private TextView tv_month;
    private SelectPopWindow popWindow;
    private BaseArrayAdapter adapter;
    private View view;

    @Override
    public void myOnclick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.tv_cancel_order:
                ActivityFactory.gotoCustoms(context);
                break;
            case R.id.tv_month:
                intent=new Intent(getApplicationContext(),SelectCityActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable(SelectCityActivity.CITY_LIST, (Serializable) countryList);
                intent.putExtras(bundle);
                startActivityForResult(intent,0);
                break;
            case R.id.tv_submit:
                intent=new Intent(getApplicationContext(),HospitalActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void setToolbar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        TextView tv_title= (TextView) findViewById(R.id.tv_title);
        tv_title.setText("应急通道");
        iv_back.setOnClickListener(v -> {
            onBackPressed();
        });
        TextView tv_submit= (TextView) findViewById(R.id.tv_submit);
        tv_submit.setVisibility(View.VISIBLE);
        tv_submit.setText("医院");
        tv_submit.setOnClickListener(this);
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_emergency;
    }


    @Override
    public int getData() throws Exception {
        getNetData();
        return 1;
    }

    @Override
    public void initView() {
        initListView();
    }

    private void initListView() {
        tv_month = (TextView) findViewById(R.id.tv_month);
        lv_sonsulte = (ListView) findViewById(R.id.lv_sonsulte);
        adapter = new BaseArrayAdapter<>(context, new BaseArrayAdapter.OnCreateViewHolderListener() {
            @Override
            public Object onCreateViewHolder() {
                return new ConsulateHolder();
            }
        },mList);
        lv_sonsulte.setAdapter(adapter);
        view = LayoutInflater.from(context).inflate(R.layout.include_consulate,null);
        tv_cancel_order = (TextView) view.findViewById(R.id.tv_cancel_order);
        tv_procedure= (TextView) view.findViewById(R.id.tv_procedure);
        tv_notice= (TextView) view.findViewById(R.id.tv_notice);
    }

    @Override
    public void setEvent() {
        tv_cancel_order.setOnClickListener(this);
        tv_month.setOnClickListener(this);
    }


    /**
     * 获取网络数据
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
                        if (countryList.size()>0){
                            countryList.clear();
                        }
                        if (cityListBean.getRegions()!=null){
                            countryList.addAll(cityListBean.getRegions());
                            tv_month.setText(cityListBean.getRegions().get(0).getRegion_name());
                            queryEmbassy(cityListBean.getRegions().get(0).getRegion_id()+"");
                        }
//                        queryData("7");
//                        queryData(cityListBean.getRegions().get(0).getRegion_id()+"");
                    }
                });
    }
/**
 * 查询大使馆信息
 *
 */
private void queryEmbassy(String region_id){
    showDialog();
    Observable<HttpResult<EmbassyBean>> result=ServiceApi.getInstance().getServiceContract().embassy(apitoken,region_id);
    result.map(new ResultFilter<>())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<EmbassyBean>() {
                @Override
                public void onCompleted() {
                    dismissDialog();
                }

                @Override
                public void onError(Throwable e) {
                    dismissDialog();
                    toastShow(e.getMessage());
                }

                @Override
                public void onNext(EmbassyBean embassyBean) {
                    mList.clear();
                    if (embassyBean.getEmbassies().size()>0){
                        mList.addAll(embassyBean.getEmbassies());
                        adapter.notifyDataSetChanged();
                    }
                    tv_procedure.setText(embassyBean.getPassport_loss().getProcedure());
                    tv_notice.setText(embassyBean.getPassport_loss().getNotice());
                    lv_sonsulte.removeFooterView(view);
                    lv_sonsulte.addFooterView(view);
                }
            });
}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == 1){
            if (data == null){
                return;
            }
            String regionsBean= data.getStringExtra(SelectCityActivity.COUNTRY_INFO);
            tv_month.setText(regionsBean);
            for (int i=0;i<countryList.size();i++){
                if (countryList.get(i).getRegion_name().equals(regionsBean)){
                    queryEmbassy(countryList.get(i).getRegion_id()+"");
                }
            }
        }
    }
}
