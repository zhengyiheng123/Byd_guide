package com.example.asus.xyd_order.activity;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.base.BaseArrayAdapter;
import com.example.asus.xyd_order.controler.CategoryControl;
import com.example.asus.xyd_order.holder.OverViewHolder;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.ActAttractionsBean;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.net.result.YearAndMonth;
import com.example.asus.xyd_order.refresh.widget.swipetorefresh.RefreshLayout;
import com.example.asus.xyd_order.ui.SelectPopWindow;
import com.example.asus.xyd_order.utils.ActivityFactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/3/2.
 */
public class OverViewActivity extends BaseActivity {

    private ListView lv_overview;
    private RefreshLayout refresh;
    private List<YearAndMonth> mList;

    public static OverViewActivity instance;

    private List<ActAttractionsBean.ShowsBean> actionList=new ArrayList<>();
    private CategoryControl control;
    private TextView tv_month;
    private String mer_id;
    private BaseArrayAdapter adapter;
    private String date;

    @Override
    public void myOnclick(View view) {
        switch (view.getId()){
            case R.id.tv_month:
                SelectPopWindow popWindow=control.getMonth(mList);
                popWindow.showPop(tv_month,0,0);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        instance=this;
    }

    @Override
    public void setToolbar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(v -> {onBackPressed();});
        TextView tv_title= (TextView) findViewById(R.id.tv_title);
        tv_title.setText("演出节目总览");
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_overview;
    }


    @Override
    public int getData() throws Exception {
        mer_id = getIntent().getStringExtra("mer_id");
        date = getIntent().getStringExtra("date");
//        mer_id="18";
        if (!TextUtils.isEmpty(date)){
            getNetData("",date);
        }else {
            getNetData("","");
        }
        Calendar calendar=Calendar.getInstance();
        int month=calendar.get(Calendar.MONTH);
        int year=calendar.get(Calendar.YEAR);
        mList = new ArrayList<>();
        for (int i=month+1;i<=12;i++){
            month=month+1;
            mList.add(new YearAndMonth(year+"",month+""));
        }
        return 1;
    }

    @Override
    public void initView() {
        inilizeView();
        inilizeListView();
        control = new CategoryControl(OverViewActivity.this);
    }
    //初始化listview
    private void inilizeListView() {
        lv_overview = (ListView) findViewById(R.id.lv_overvew);
//        refresh = (RefreshLayout) findViewById(R.id.refresh);
        adapter = new BaseArrayAdapter(context, new BaseArrayAdapter.OnCreateViewHolderListener() {
            @Override
            public Object onCreateViewHolder() {
                return new OverViewHolder();
            }
        },actionList);
//        refresh.setAdapter(adapter,lv_overview);
        lv_overview.setAdapter(adapter);
    }
    //初始化布局
    private void inilizeView() {
        tv_month = (TextView) findViewById(R.id.tv_month);
        if (TextUtils.isEmpty(date)){
            tv_month.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setEvent() {
        tv_month.setOnClickListener(this);
        lv_overview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ActivityFactory.gotoSiteDisplay(context,actionList.get(i).getRoute_id()+"");
            }
        });
    }

    //请求网络数据
    public void getNetData(String month,String date){
        Observable<HttpResult<ActAttractionsBean>> result= ServiceApi.getInstance().getServiceContract().actAttraction(apitoken,mer_id,date,month);
        result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ActAttractionsBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ActAttractionsBean actAttractionsBean) {
                        if (actAttractionsBean.getShows()!=null){
                            actionList.clear();
                            actionList.addAll(actAttractionsBean.getShows());
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }
}
