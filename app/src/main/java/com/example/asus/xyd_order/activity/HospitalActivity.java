package com.example.asus.xyd_order.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.base.BaseArrayAdapter;
import com.example.asus.xyd_order.controler.CategoryControl;
import com.example.asus.xyd_order.holder.HospitalItem;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.CityListBean;
import com.example.asus.xyd_order.net.result.HospitaiDetails;
import com.example.asus.xyd_order.net.result.HospitalBean;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.ui.SelectPopWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/7/31.
 */

public class HospitalActivity extends BaseActivity {
    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.lv_hospital)
    ListView lv_hospital;
    @Bind(R.id.tv_empty)
    TextView tv_empty;

    private CategoryControl control;
    private SelectPopWindow popWindow;

    private List<HospitalBean.HospitalsBean> mList=new ArrayList<>() ;
    private BaseArrayAdapter adapter;
    private TextView tv_month;

    private List<CityListBean.RegionsBean> countryList=new ArrayList<>();
    @Override
    public void myOnclick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_month:
                popWindow = control.getCountry(countryList);
                popWindow.showAsDropDown(tv_month);
                break;
        }
    }

    @Override
    public void setToolbar() {
        tv_title.setText("医院");
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_hospital;
    }

    @Override
    public int getData() throws Exception {
        control = new CategoryControl(HospitalActivity.this);
        control.setCountryItemClick(new CategoryControl.CountryItemClick() {
            @Override
            public void onItemClick(CityListBean.RegionsBean bean) {
//                toastShow(bean.getRegion_name()+", "+bean.getRegion_id());
                popWindow.dismiss();
                tv_month.setText(bean.getRegion_name());
                getNetData(bean.getRegion_id()+"");
            }
        });
        getNetData();
        return 0;
    }

    @Override
    public void initView() {
        tv_month = (TextView) findViewById(R.id.tv_month);
        lv_hospital= (ListView) findViewById(R.id.lv_hospital);
        lv_hospital.setEmptyView(tv_empty);
        adapter = new BaseArrayAdapter(context, new BaseArrayAdapter.OnCreateViewHolderListener() {
            @Override
            public Object onCreateViewHolder() {
                return new HospitalItem();
            }
        },mList);
        lv_hospital.setAdapter(adapter);
    }

    @Override
    public void setEvent() {
        iv_back.setOnClickListener(this);
        tv_month.setOnClickListener(this);
        lv_hospital.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(context, Activity_HospitalDetails.class);
                intent.putExtra("hospital_id",mList.get(i).getHos_id()+"");
                startActivity(intent);
            }
        });
    }
    /**
     * 获取网络数据
     */
    private void getNetData(String regionId){
        showDialog();
        Observable<HttpResult<HospitalBean>> result= ServiceApi.getInstance().getServiceContract().hospitalList(apitoken,regionId);
        result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HospitalBean>() {
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
                    public void onNext(HospitalBean hospitalBean) {
                        if (hospitalBean.getHospitals()!=null){
                            mList.clear();
                            mList.addAll(hospitalBean.getHospitals());
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    /**
     * 获取国家列表
     */
    private void getNetData(){
        Observable<HttpResult<CityListBean>> result= ServiceApi.getInstance().getServiceContract().countyrList(apitoken);
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
                        countryList.addAll(cityListBean.getRegions());

                        tv_month.setText(cityListBean.getRegions().get(0).getRegion_name());
//                        queryData("7");
                        if (cityListBean.getRegions()==null && cityListBean.getRegions().size()==0){
                            return;
                        }else {
                            getNetData(cityListBean.getRegions().get(0).getRegion_id()+"");
                        }
                    }
                });
    }
}
