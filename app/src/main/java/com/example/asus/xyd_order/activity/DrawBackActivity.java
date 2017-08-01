package com.example.asus.xyd_order.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.base.BaseArrayAdapter;
import com.example.asus.xyd_order.controler.CategoryControl;
import com.example.asus.xyd_order.holder.TerminalHolder;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.CityListBean;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.net.result.TuishuiBean;
import com.example.asus.xyd_order.ui.SelectPopWindow;
import com.example.asus.xyd_order.utils.ActivityFactory;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/3/6.
 */
public class DrawBackActivity extends BaseActivity {
    private List<CityListBean.RegionsBean> countryList=new ArrayList<>();
    private ImageView iv_back;
    private TextView tv_submit,tv_month,tv_port_name,tv_details;
    private CategoryControl control;
    private SelectPopWindow popWindow;
    private GridView gd_point;

    //航站楼集合
    private List<TuishuiBean.DrawbackPointBean> pointList=new ArrayList<>();
    private BaseArrayAdapter adapter;

    @Override
    public void myOnclick(View view) {
        switch (view.getId()){
            case R.id.tv_submit:
                ActivityFactory.gotoAddDrawBack(context);
                break;
            case R.id.tv_month:
                popWindow = control.getCountry(countryList);
                popWindow.showAsDropDown(tv_month);
                break;
        }
    }

    @Override
    public void setToolbar() {
        TextView tv_title= (TextView) findViewById(R.id.tv_title);
        tv_title.setText("退税说明");
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(v -> {onBackPressed();});
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_drawback;
    }


    @Override
    public int getData() throws Exception {
        control = new CategoryControl(DrawBackActivity.this);
        control.setCountryItemClick(new CategoryControl.CountryItemClick() {
            @Override
            public void onItemClick(CityListBean.RegionsBean bean) {
//                toastShow(bean.getRegion_name()+", "+bean.getRegion_id());
                popWindow.dismiss();
                tv_month.setText(bean.getRegion_name());
                queryData(bean.getRegion_id()+"");
            }
        });
        getNetData();
        return 0;
    }

    @Override
    public void initView() {
    inializeView();
    }

    private void inializeView() {
        tv_submit = (TextView) findViewById(R.id.tv_submit);
        tv_port_name= (TextView) findViewById(R.id.tv_port_name);
        tv_month= (TextView) findViewById(R.id.tv_month);
        tv_details= (TextView) findViewById(R.id.tv_details);
        tv_submit.setVisibility(View.VISIBLE);
        tv_submit.setText("添加");
        gd_point = (GridView) findViewById(R.id.gd_point);
        adapter = new BaseArrayAdapter<>(context, new BaseArrayAdapter.OnCreateViewHolderListener() {
            @Override
            public Object onCreateViewHolder() {
                return new TerminalHolder();
            }
        },pointList);
        gd_point.setAdapter(adapter);
    }

    @Override
    public void setEvent() {
        tv_submit.setOnClickListener(this);
        tv_month.setOnClickListener(this);
        gd_point.setOnItemClickListener((adapterView, view, i, l) -> ActivityFactory.gotoDrawBackIntroduce(context,pointList.get(i).getDp_id()+""));
//                ActivityFactory.gotoDrawBackIntroduce(context);
    }

    /**
     * 获取网络数据
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
                        if (cityListBean.getRegions()!=null && cityListBean.getRegions().size()>0){
                            return;
                        }
                        queryData(cityListBean.getRegions().get(0).getRegion_id()+"");
                    }
                });
    }

    /**
     * 获取退税信息
     */
    private void queryData(String area_id){
        Observable<HttpResult<TuishuiBean>> result=ServiceApi.getInstance().getServiceContract().tuishui(apitoken,area_id);
        result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TuishuiBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        toastShow(e.getMessage());
                    }

                    @Override
                    public void onNext(TuishuiBean tuishuiBean) {
                        if (!TextUtils.isEmpty(tuishuiBean.getDrawback_name())){
                            tv_port_name.setText(tuishuiBean.getDrawback_name());
                            tv_details.setText(tuishuiBean.getDrawback_desc());
                            if (pointList.size()>0){
                                pointList.clear();
                            }
                            pointList.addAll(tuishuiBean.getDrawback_point());
                            adapter.notifyDataSetChanged();
                        }else {
                            toastShow("无信息");
                            tv_port_name.setText("");
                            tv_details.setText("");
                            if (pointList.size()>0){
                                pointList.clear();
                            }
//                            pointList.addAll(tuishuiBean.getDrawback_point());
                            adapter.notifyDataSetChanged();
                        }

                    }
                });
    }
}
