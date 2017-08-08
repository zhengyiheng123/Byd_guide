package com.example.asus.xyd_order.activity;

import android.Manifest;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.app.APP;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.base.BaseArrayAdapter;
import com.example.asus.xyd_order.controler.CategoryControl;
import com.example.asus.xyd_order.holder.ZhongcanHolder;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.CategoryBean;
import com.example.asus.xyd_order.net.result.CityListBean;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.net.result.RestaurantBean;
import com.example.asus.xyd_order.refresh.widget.swipetorefresh.RefreshLayout;
import com.example.asus.xyd_order.ui.MyListView;
import com.example.asus.xyd_order.ui.SelectPopWindow;
import com.example.asus.xyd_order.utils.ActivityFactory;
import com.example.asus.xyd_order.utils.LocationUtils;
import com.example.asus.xyd_order.utils.common.PermissionManager;
import com.example.asus.xyd_order.utils.common.PermissionResult;

import java.util.ArrayList;
import java.util.List;

import cn.leo.photopicker.pick.PermissionUtil;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/2/23.
 */
public class ZhongCanActivity extends BaseActivity implements RefreshLayout.OnLoadListener,SwipeRefreshLayout.OnRefreshListener{

    private ImageView iv_back;
    private MyListView lv_zhongcan;
    public RefreshLayout refresh;
    private TextView tv_allcategory,auto_sort,tv_shaixuan;
    private RelativeLayout rl_shaixuan,rl_auto,rl_all;
    //检索列表数据
    List<CategoryBean.SubCategoriesBean> cateList=new ArrayList<>();

    public static ZhongCanActivity instance;

    List<RestaurantBean.RestaurantsBean> zhongcanList=new ArrayList<>();
    private BaseArrayAdapter adapter;
    private SelectPopWindow popWindow;
    private ListView lv_pop_allcategory;
    private CategoryControl control;
    private MultipartBody.Builder builder;
    //页码
    public int page =0;
    //经度
    private String longitude="";
    //维度
    private String latitude="";
    // 餐厅分类
    private String cate_id;
    //排序条件   1|距离优先 2|好评优先 3|人均最低 4| 可停大巴
    private  String autoParam ="1";
    //筛选条件  1|大巴停车场,2|停车场,3|大巴临时上长下客

    //经营特色
    private String sub_cate_id;
    private String park = "";
    //人均价格下限
    private String price_start ="";
    //人均价格上限
    private String price_end ="";
    //座位数下限
    private String seat_start ="";
    //座位数上限
    private String seat_end ="";

    //正在刷新
    public boolean isrefreshing;
//    private TextView tv_empty;

    @Override
    public void myOnclick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_shaixuan:
                cancelBg();
                setBg(tv_shaixuan,rl_shaixuan);
                control.getShaixuan().showPop(tv_shaixuan,0,0);
//                startActivity(new Intent( getApplicationContext(),FilterActivity.class));
                break;
            case R.id.tv_allcategory:
                cancelBg();
                setBg(tv_allcategory,rl_all);
                control.getAllCategoryPop().showAsDropDown(tv_allcategory,0,0);
                break;
            case R.id.auto_sort:
                cancelBg();
                setBg(auto_sort,rl_auto);
                control.getAutoPop().showAsDropDown(auto_sort,0,0);
                break;
        }
    }

    @Override
    public void setToolbar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        TextView tv_title= (TextView) findViewById(R.id.tv_title);
        if (!TextUtils.isEmpty(cate_id)){
            if (cate_id.equals("1")){
                tv_title.setText("中餐");
            }else if (cate_id.equals("2")){
                tv_title.setText("特色餐");
            }
        }
        iv_back.setOnClickListener(v -> {onBackPressed();});
    }

    private void initAllCategoryPop() {
        popWindow=null;
    }

    private void setBg(TextView tv, RelativeLayout rl) {
        tv.setTextColor(getResources().getColor(R.color.tool_bar_color));
        rl.setBackgroundResource(R.drawable.shape_line);
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_zhongcan;
    }


    @Override
    public int getData() throws Exception {
        builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        cate_id= getIntent().getStringExtra("cate_id");
        CityListBean.RegionsBean cityBean=APP.getApplication().getCityBean();
        getCateData();
        return 0;
    }

    @Override
    public void initView() {

        //分类选择控制器
        control=new CategoryControl(ZhongCanActivity.this);
        //初始化控件
        iv_back = (ImageView) findViewById(R.id.iv_back);
        refresh = (RefreshLayout) findViewById(R.id.refresh);
        lv_zhongcan = (MyListView) findViewById(R.id.lv_zhongcan);
//        lv_zhongcan.setEmptyView(View.inflate(context,R.layout.emptyview,null));
        tv_allcategory = (TextView) findViewById(R.id.tv_allcategory);
        tv_shaixuan= (TextView) findViewById(R.id.tv_shaixuan);
        auto_sort= (TextView) findViewById(R.id.auto_sort);
//        tv_empty = (TextView) findViewById(R.id.tv_empty);
        rl_shaixuan = (RelativeLayout) findViewById(R.id.rl_shaixuan);
        rl_all= (RelativeLayout) findViewById(R.id.rl_all);
        rl_auto= (RelativeLayout) findViewById(R.id.rl_auto);

        //设置全部分类为选中状态
        setBg(tv_allcategory,rl_all);
        //初始化下方listview
        initMainListview();

    }

    private void initMainListview() {
        adapter = new BaseArrayAdapter(context, new BaseArrayAdapter.OnCreateViewHolderListener() {
            @Override
            public Object onCreateViewHolder() {
                return new ZhongcanHolder();
            }
        },zhongcanList);
        refresh.setAdapter(adapter,lv_zhongcan);
    }

    @Override
    public void setEvent() {
        iv_back.setOnClickListener(this);
        auto_sort.setOnClickListener(this);
        tv_shaixuan.setOnClickListener(this);
        tv_allcategory.setOnClickListener(this);
        refresh.setOnLoadListener(this);
        refresh.setOnRefreshListener(this);
        lv_zhongcan.setOnItemClickListener(onItemClickListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        instance=this;
        onRefresh();
    }
    private void cancelBg(){
        tv_allcategory.setTextColor(getResources().getColor(R.color.material_grey_700));
        tv_shaixuan.setTextColor(getResources().getColor(R.color.material_grey_700));
        auto_sort.setTextColor(getResources().getColor(R.color.material_grey_700));
        rl_auto.setBackgroundResource(R.color.white);
        rl_shaixuan.setBackgroundResource(R.color.white);
        rl_all.setBackgroundResource(R.color.white);
    }

    @Override
    public void onLoad() {
        isrefreshing=false;
        refresh.setLoading(true);
        page=page+1;
        getNetData(control.getAutoParam(),control.getSort_State(),control.getSub_cate_id(),control.getMaxPrice(),control.getMinPrice());
    }

    @Override
    public void onRefresh() {
        LocationUtils locationUtils=new LocationUtils();
        LocationUtils.LandL location=locationUtils.location(ZhongCanActivity.this);
        if (location!=null){
            longitude=location.Longitude+"";
            latitude=location.Latitude+"";
        }
        isrefreshing=true;
        refresh.recoveryLoad();
        page=0;
        park=control.getSort_State();
        autoParam=control.getAutoParam();
        sub_cate_id=control.getSub_cate_id();
        price_start=control.getMinPrice();
        price_end=control.getMaxPrice();
        seat_start="";
        seat_end="";
        getNetData(autoParam,park,sub_cate_id,price_start,price_end);
    }
    AdapterView.OnItemClickListener onItemClickListener=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i<zhongcanList.size()){
                    ActivityFactory.gotoTuancanDetails(context,zhongcanList.get(i).getRes_id()+"",zhongcanList.get(i).getLogo_path());
                }
        }
    };
    /**
     * 获取网络数据
     */
    public void getNetData(String autoParam,String sort_state,String sub,String price_start,String price_end){
        showDialog();
        Observable<HttpResult<RestaurantBean>> result= ServiceApi.getInstance().getServiceContract().restaurantList(apitoken,page,
                longitude,latitude,cate_id,autoParam,sort_state,price_start,price_end,seat_start,seat_end,sub);
                result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RestaurantBean>() {
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
                    public void onNext(RestaurantBean bean) {
                        if (bean.getRestaurants()==null || bean.getRestaurants().size() == 0){
                            refresh.setLoading(true);
                            refresh.setRefreshing(false);
                            refresh.onLoadFinish();
                        }
                        if (isrefreshing){
                            zhongcanList.clear();
                            zhongcanList.addAll(bean.getRestaurants());
                            adapter.notifyDataSetChanged();
                        }else {
                            zhongcanList.addAll(bean.getRestaurants());
                            adapter.notifyDataSetChanged();
                        }
                        refresh.setLoading(true);
                        refresh.setRefreshing(false);
//                        toastShow("刷新成功");
                    }

                });
    }
    /**
     * 获取检索条目列表
     */
    public void getCateData(){
        Observable<HttpResult<CategoryBean>> result=ServiceApi.getInstance().getServiceContract().categoryBean(apitoken,cate_id);
        result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CategoryBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CategoryBean categoryBean) {
                        if (cateList.size()>0){
                            cateList.clear();
                        }
                        cateList.addAll(categoryBean.getSub_categories());
                        control.setList(cateList);
                    }
                });
    }
}
