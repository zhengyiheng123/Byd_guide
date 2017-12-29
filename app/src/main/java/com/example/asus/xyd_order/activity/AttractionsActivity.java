package com.example.asus.xyd_order.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.app.APP;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.base.BaseArrayAdapter;
import com.example.asus.xyd_order.controler.CategoryControl;
import com.example.asus.xyd_order.holder.AttractionsHolder;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.CityListBean;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.net.result.JingdianBean;
import com.example.asus.xyd_order.net.result.RegionsBean;
import com.example.asus.xyd_order.refresh.widget.swipetorefresh.RefreshLayout;
import com.example.asus.xyd_order.ui.MyListView;
import com.example.asus.xyd_order.ui.SelectPopWindow;
import com.example.asus.xyd_order.utils.ActivityFactory;
import com.example.asus.xyd_order.utils.LocationUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/3/2.
 */
public class AttractionsActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,RefreshLayout.OnLoadListener{

    private TextView tv_travel_time;
    private ListView lv_attractions;
    private List<JingdianBean.ScenesBean> mList=new ArrayList<>();
    private BaseArrayAdapter arrayAdapter;
    private RefreshLayout refresh;

    //是否正在刷新
    private boolean isRefreshing;
    //经度
    private String longitude="";
    //维度
    private String latitude="";

    //排序标志 1|价格升序 2|价格降序 3|距离优先
    private String sort_state="3";

    //分页码
    private int page;
    private TextView tv_sort_distance;
    private TextView tv_price_down;
    private String region_id;
    private ImageView iv_img;

    @Override
    public void myOnclick(View view) {
        switch (view.getId()){
            case R.id.iv_img:
                startActivity(new Intent(getApplicationContext(),JingDianActivity.class));
                break;
            case R.id.tv_travel_time:
                sort_state="1";
                onRefresh();
                break;
            case R.id.tv_sort_distance:
                sort_state="3";
                onRefresh();
                break;
            case R.id.tv_price_down:
                sort_state="2";
                onRefresh();
                break;
        }
    }

    @Override
    public void setToolbar() {
        TextView tv_title= (TextView) findViewById(R.id.tv_title);
        tv_title.setText("景点门票");
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(v -> {onBackPressed();});
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_attractions;
    }


    @Override
    public int getData() throws Exception {
        RegionsBean cityBean= APP.getApplication().getCityBean();
        if (cityBean!=null){
            region_id = cityBean.getRegion_id()+"";
        }else {
            region_id="";
        }
        return 0;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void initView() {
        //初始化所有控件
        inilizeView();
        //初始化Listview
        initListView();
        onRefresh();

    }

    /**
     * 景点条目Listview
     */
    private void initListView() {
        lv_attractions = (ListView) findViewById(R.id.lv_orderbytime);
        lv_attractions.setEmptyView(View.inflate(context,R.layout.emptyview,null));
           arrayAdapter= new BaseArrayAdapter<>(context, new BaseArrayAdapter.OnCreateViewHolderListener() {
                @Override
                public Object onCreateViewHolder() {
                    return new AttractionsHolder();
                }
            },mList);
            refresh.setAdapter(arrayAdapter,lv_attractions);
    }

    /**
     * 初始化控件
     */
    private void inilizeView() {
        tv_travel_time = (TextView) findViewById(R.id.tv_travel_time);
        tv_sort_distance = (TextView) findViewById(R.id.tv_sort_distance);
        tv_price_down = (TextView) findViewById(R.id.tv_price_down);
        refresh = (RefreshLayout) findViewById(R.id.refresh);
        iv_img = (ImageView) findViewById(R.id.iv_img);
        iv_img.setVisibility(View.VISIBLE);
        iv_img.setImageResource(R.mipmap.icon_zhongcan_search);
    }

    @Override
    public void setEvent() {
        iv_img.setOnClickListener(this);
        tv_travel_time.setOnClickListener(this);
        refresh.setOnLoadListener(this);
        tv_price_down.setOnClickListener(this);
        refresh.setOnRefreshListener(this);
        tv_sort_distance.setOnClickListener(this);
        lv_attractions.setOnItemClickListener(onItemClickListener);
    }
        AdapterView.OnItemClickListener onItemClickListener=new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    switch (mList.get(i).getSub_cate_id()){
                        case 6:
                            ActivityFactory.gotoAttractionDetails(context,mList.get(i).getScene_id()+"");
                            break;
                        case 5:
                            ActivityFactory.gotoAttractionsNomal(context,mList.get(i).getScene_id());
                            break;
                        case 7:
                            ActivityFactory.gotoAttractionTicket(context,mList.get(i).getScene_id());
                            break;
                    }
            }
};
    @Override
    public void onLoad() {
        page=page+1;
        isRefreshing=false;
        refresh.setLoading(true);
        getNetData(page+"",sort_state);
    }

    @Override
    public void onRefresh() {
        LocationUtils locationUtils=new LocationUtils();
        LocationUtils.LandL location=locationUtils.location(AttractionsActivity.this);
        if (location!=null){
            longitude=location.Longitude+"";
            latitude=location.Latitude+"";
        }
        isRefreshing=true;
        page=0;
        refresh.setRefreshing(true);
        refresh.recoveryLoad();
        getNetData(page+"",sort_state);
            }

    /**
     * 获取网络信息
     */
    private void getNetData(String p,String sort_state){
        Observable<HttpResult<JingdianBean>> result= ServiceApi.getInstance().getServiceContract().jingdianList(apitoken,longitude,latitude,sort_state,p,region_id);
        result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JingdianBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                            toastShow(e.getMessage());
                        refresh.setRefreshing(false);
                    }

                    @Override
                    public void onNext(JingdianBean jingdianBean) {
                        if (jingdianBean.getScenes() == null || jingdianBean.getScenes().size()==0){
                            refresh.onLoadFinish();
                        }else {
                            if (isRefreshing){
                                mList.clear();
                                if (jingdianBean.getScenes().size()<10){
                                    refresh.onLoadFinish();
                                }
                                mList.addAll(jingdianBean.getScenes());
                                arrayAdapter.notifyDataSetChanged();
                            }else {
                                mList.addAll(jingdianBean.getScenes());
                                arrayAdapter.notifyDataSetChanged();
                            }
                        }
                        refresh.setLoading(true);
                        refresh.setRefreshing(false);
                    }
                });
    }
}
