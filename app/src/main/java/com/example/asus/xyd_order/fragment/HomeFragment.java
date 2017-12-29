package com.example.asus.xyd_order.fragment;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.activity.LoginActivity;
import com.example.asus.xyd_order.activity.SearchActivity;
import com.example.asus.xyd_order.activity.TestWebActivity;
import com.example.asus.xyd_order.app.APP;
import com.example.asus.xyd_order.base.BaseArrayAdapter;
import com.example.asus.xyd_order.base.BaseFragment;
import com.example.asus.xyd_order.dialog.ConfirmDialog;
import com.example.asus.xyd_order.holder.CityHolder;
import com.example.asus.xyd_order.holder.HomeNewsHolder;
import com.example.asus.xyd_order.net.BaseApi;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.CityListBean;
import com.example.asus.xyd_order.net.result.HomePage;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.selectcity.SelectCountryActivity;
import com.example.asus.xyd_order.ui.MyListView;
import com.example.asus.xyd_order.utils.ActivityFactory;
import com.example.asus.xyd_order.utils.Myutils;
import com.example.asus.xyd_order.utils.SharedPreferenceUtils;
import com.example.asus.xyd_order.utils.TimeUtils;
import com.recker.flybanner.FlyBanner;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/2/17.
 */
public class HomeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{


    //初始化控件
    private GridView gv_category;
    private TextView tv_home_place,tv_mer_name,tv_mer_time,tv_mer_message,tv_ord_name,tv_ord_time,tv_ord_message;
    private ImageView tv_send;
    private MyListView lv_home_news;
    private FlyBanner banner;
    private SwipeRefreshLayout refresh;
    private LinearLayout ll_location,my_news;
    private RelativeLayout rl_mer_order,rl_ord,rl_search;


//    private ViewPager id_viewpager;
    private List<String> bannerList =new ArrayList<>();
    private RelativeLayout rl_news;
    private List<HomePage.BannersBean> homeList;
    private BaseArrayAdapter helpAdapter;
    private TextView auto_text;

    @Override
    public void myOnclick(View view) {
    switch (view.getId()){
        case R.id.ll_location:
//            ActivityFactory.gotoLocation(context);
            startActivity(new Intent(getActivity(), SelectCountryActivity.class));
            break;
        case R.id.my_news:

            break;
        case R.id.rl_news:

            break;
        case R.id.auto_text:
//            initListPopwindow(auto_text);
            break;
        case R.id.tv_send:
            ActivityFactory.gotoAddHelpNews(context);
            break;
        case R.id.rl_mer_order:
            //我的预定消息
            ActivityFactory.gotoNews(context);
            break;
        case R.id.rl_ord:
            //我的需求消息
            ActivityFactory.gotoMyOrder_News(context);
            break;
        case R.id.rl_search:
            startActivity(new Intent(getActivity(), SelectCountryActivity.class));
            break;
    }
    }

    @Override
    public void initView(View v) {
        //初始化基本控件
        iniLizeView(v);
        //初始化轮播图
        initBanner(v);
        tv_home_place.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        //初始化Gridview
        initGridView(v);
        //初始化Listview
        initListView(v);
        //初始化业内互助消息ListView
        initNewsListView(v);
        //初始化Viewpager
//        initViewPager(v);
    }
    private void iniLizeView(View v) {
        auto_text = (TextView) v.findViewById(R.id.auto_text);
        tv_send= (ImageView) v.findViewById(R.id.tv_send);
        tv_home_place= (TextView) v.findViewById(R.id.tv_home_place);
        tv_mer_name= (TextView) v.findViewById(R.id.tv_mer_name);
        tv_mer_time= (TextView) v.findViewById(R.id.tv_mer_time);
        tv_mer_message= (TextView) v.findViewById(R.id.tv_mer_message);
        tv_ord_message= (TextView) v.findViewById(R.id.tv_ord_message);
        tv_ord_name= (TextView) v.findViewById(R.id.tv_ord_name);
        tv_ord_time= (TextView) v.findViewById(R.id.tv_ord_time);
        ll_location= (LinearLayout) v.findViewById(R.id.ll_location);
        my_news= (LinearLayout) v.findViewById(R.id.my_news);
        rl_news = (RelativeLayout) v.findViewById(R.id.rl_news);
        rl_mer_order= (RelativeLayout) v.findViewById(R.id.rl_mer_order);
        rl_ord= (RelativeLayout) v.findViewById(R.id.rl_ord);
        rl_search= (RelativeLayout) v.findViewById(R.id.rl_search);
    }

    private void initBanner(View v) {
        banner = (FlyBanner) v.findViewById(R.id.banner_1);
        banner.setPointsIsVisible(true);
        banner.setOnItemClickListener(new FlyBanner.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent=new Intent(getActivity(), TestWebActivity.class);
                intent.putExtra("banner_url",homeList.get(position));
                getActivity().startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (APP.getApplication().getCityBean()!=null){
            auto_text.setText(APP.getApplication().getCityBean().getOriginal_name());
        }else {
            auto_text.setText("离我最近");
        }
        onRefresh();
    }

    /**
     * 业内互助消息listview
     */
    private void initNewsListView(View v){
        lv_home_news = (MyListView) v.findViewById(R.id.lv_home_news);
        helpAdapter = new BaseArrayAdapter<>(getActivity(), new BaseArrayAdapter.OnCreateViewHolderListener() {
            @Override
            public Object onCreateViewHolder() {
                return new HomeNewsHolder();
            }
        },newsEntityList);
        lv_home_news.setAdapter(helpAdapter);

    }

    private void initListView(View v) {
        refresh = (SwipeRefreshLayout) v.findViewById(R.id.refresh);
        refresh.setOnRefreshListener(this);
        refresh.setColorSchemeColors(context.getResources().getColor(R.color.material_blue_600),
                context.getResources().getColor(R.color.tool_bar_color));
        refresh.setProgressBackgroundColorSchemeResource(R.color.background_color);
    }

    /**
     * 加载Gridview
     * @param v
     */
    private void initGridView(View v) {
        gv_category = (GridView) v.findViewById(R.id.gv_category);
        gv_category.setAdapter(new MyGridview());
    }

    @Override
    public int getResource() {
        return R.layout.fragment_home;
    }
//消息实体集合
    List<HomePage.MutualMessageBean> newsEntityList=new ArrayList<>();
    @Override
    public int initData() {
        return 1;

    }

    @Override
    public void setEvent() {
        rl_news.setOnClickListener(this);
        tv_send.setOnClickListener(this);
        ll_location.setOnClickListener(this);
        rl_ord.setOnClickListener(this);
        rl_mer_order.setOnClickListener(this);
        gv_category.setOnItemClickListener(onItemClickListener);
        lv_home_news.setOnItemClickListener((parent, view, position, id) -> {
            ActivityFactory.gotoHelpNews(context);
        });
        my_news.setOnClickListener(this);
        rl_search.setOnClickListener(this);
    }

    @Override
    public void onRefresh() {
        getNetData();
    }

    //Gridview数据适配器
    private class MyGridview extends BaseAdapter {
        @Override
        public int getCount() {
            return 6;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }
        class ViewHolder{
            ImageView iv_home_category;
            TextView tv_home_category;
        }
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            if (view == null){
                holder=new ViewHolder();
                view= LayoutInflater.from(getActivity()).inflate(R.layout.item_home_category,null);
                holder.tv_home_category= (TextView) view.findViewById(R.id.tv_home_category);
                holder.iv_home_category= (ImageView) view.findViewById(R.id.iv_home_category);
                view.setTag(holder);
            }else {
                holder= (ViewHolder) view.getTag();
            }
            if (i==0){
                holder.iv_home_category.setImageResource(R.mipmap.ic_zhongcan);
                holder.tv_home_category.setText("中餐");
            }else if (i==1){
                holder.iv_home_category.setImageResource(R.mipmap.ic_tesecan);
                holder.tv_home_category.setText("特色餐");
            }else if (i==2) {
                holder.iv_home_category.setImageResource(R.mipmap.ic_jingdian);
                holder.tv_home_category.setText("景点娱乐");
            }else if (i==3){
                holder.iv_home_category.setImageResource(R.mipmap.ic_xunche);
                holder.tv_home_category.setText("寻找车导");
            }else if (i==4){
                holder.iv_home_category.setImageResource(R.mipmap.ic_tuishui);
                holder.tv_home_category.setText("退税说明");
            }else if (i==5){
                holder.iv_home_category.setImageResource(R.mipmap.ic_yingji);
                holder.tv_home_category.setText("应急通道");
            }
            return view;
        }
    }

    /**
     * Gridview条目点击事件
     */
    AdapterView.OnItemClickListener onItemClickListener=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            switch (i){
                case 0:
                    ActivityFactory.gotoZhongCan(context,"1");
                    break;
                case 1:
                    ActivityFactory.gotoZhongCan(context,"2");
                    break;
                case 2:
                    ActivityFactory.gotoAttraction(context);
                    break;
                case 3:
                    int state= (int) SharedPreferenceUtils.getParam(getActivity(), LoginActivity.CONFIRM_STATE,0);
                    if (state == 2){
                        ActivityFactory.gotoReleaseDemand(context);
                    }else {
                        ConfirmDialog dialog=new ConfirmDialog(getActivity());
                    }
                    break;
                case 4:
                    ActivityFactory.gotoDrawback(context);
                    break;
                case 5:
                    ActivityFactory.gotoEmergencyActivity(context);
                    break;
            }
        }
    };
    //获取网络数据
    private void getNetData(){
        Observable<HttpResult<HomePage>> result= ServiceApi.getInstance().getServiceContract().homePage(apitoken);
        result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HomePage>() {
                    @Override
                    public void onCompleted() {
                        dismissDialog();
                        refresh.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        toastShow(e.getMessage());
                        dismissDialog();
                        refresh.setRefreshing(false);
                    }

                    @Override
                    public void onNext(HomePage homePage) {
                        if (homePage.getBanners()!=null && homePage.getBanners().size()>0){
                            homeList = homePage.getBanners();
                            bannerList.clear();
                            for (int i=0;i<homePage.getBanners().size();i++){
                                bannerList.add(BaseApi.getBaseUrl()+homePage.getBanners().get(i).getImg_path());
                            }
                            banner.setImagesUrl(bannerList);
                            banner.startAutoPlay();
                        }
                        if (homePage.getMer_notice().getAdd_time()!=0){
                            tv_mer_time.setText(TimeUtils.stampToDateSs(homePage.getMer_notice().getAdd_time()+""));
                            tv_mer_message.setText(homePage.getMer_notice().getMsg());
                            tv_mer_name.setText(homePage.getMer_notice().getTitle());
                        }else {
                            rl_mer_order.setVisibility(View.GONE);
                        }
                        if (homePage.getDmd_notice().getAdd_time() !=0){
                            tv_ord_message.setText(homePage.getDmd_notice().getMsg());
                            tv_ord_time.setText(TimeUtils.stampToDateSs(homePage.getDmd_notice().getAdd_time()+""));
                            tv_ord_name.setText(homePage.getDmd_notice().getTitle());
                        }else {
                            rl_ord.setVisibility(View.GONE);
                        }


                        newsEntityList.clear();
                        newsEntityList.add(homePage.getMutual_message());
//                        if (homePage.getMutual_message().ge)
                        helpAdapter.notifyDataSetChanged();
                    }
                });
    }

}
