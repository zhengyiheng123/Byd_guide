package com.example.asus.xyd_order.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.adapter.MyPagerAdapter;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.base.BaseArrayAdapter;
import com.example.asus.xyd_order.base.BaseFragment;
import com.example.asus.xyd_order.holder.CommentsHolder;
import com.example.asus.xyd_order.holder.MultipleHolder;
import com.example.asus.xyd_order.net.BaseApi;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.CommentBean;
import com.example.asus.xyd_order.net.result.CommentRecords;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.net.result.IsCollectBean;
import com.example.asus.xyd_order.net.result.RestaurantDetailsBean;
import com.example.asus.xyd_order.ui.ChildViewPager;
import com.example.asus.xyd_order.ui.MyListView;
import com.example.asus.xyd_order.ui.NoScrollViewPager;
import com.example.asus.xyd_order.utils.ActivityFactory;
import com.example.asus.xyd_order.utils.Myutils;
import com.example.asus.xyd_order.utils.ToastUtils;
import com.recker.flybanner.FlyBanner;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/2/23.
 */
public class ZhongcanDetailsActivity extends BaseActivity {
    //初始化控件
    private FlyBanner banner_1;
    private ArrayList<String> bannerList = new ArrayList<>();
    private ScrollView sc_zhongcan;
    private TextView tv_change_dianmode;
    private ListView lv_pinglun;
    private List<RestaurantDetailsBean.GroupMealBean> mList = new ArrayList<>();
    //评论集合
    private List<CommentBean.CommentsBean> commentList=new ArrayList<>();
    private ImageView iv_back;
    private MyListView multiple;
    private TextView btn_order;
    private String res_id;
    private ImageView iv_cai;
    private RelativeLayout rl_order;
    LinearLayout rl_park;
    private TextView tv_zuowei,tv_address,tv_cancel_condition,tv_restaurant_name,tv_park,tv_time;
    private RatingBar ratingbar;
    private BaseArrayAdapter adapter;
    private BaseArrayAdapter commentAdapter;
    private RestaurantDetailsBean bean1;
    private String logo;
    private ImageView iv_img;
    private TextView tv_title;
    private RelativeLayout rl_map;
    private RestaurantDetailsBean restaurantDetailsBean;

    //数据集合
    @Override
    public void myOnclick(View view) {
        switch (view.getId()){
            case R.id.tv_change_dianmode:
                ActivityFactory.gotoSingleChoice(context,bean1.getSingle_meal(),bean1.getRes_name(),logo,bean1.getRes_id());
                break;
            case R.id.btn_order:
//                ActivityFactory.gotoOrder(context,"multi");
                break;
            case R.id.iv_img:
                collect();
                break;
            case R.id.rl_map:
                Intent intent=new Intent(context,MapWebViewActivity.class);
                intent.putExtra("map_url",restaurantDetailsBean.getMap_url());
                startActivity(intent);
                break;

        }
    }

    @Override
    public void setToolbar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_back.setOnClickListener(v -> {onBackPressed();});
        iv_img = (ImageView) findViewById(R.id.iv_img);
        iv_img.setVisibility(View.VISIBLE);
        iv_img.setOnClickListener(this);
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_tuancandetails;
    }


    @Override
    public int getData() throws Exception {
        res_id = getIntent().getStringExtra("res_id");
        logo = getIntent().getStringExtra("logo");
        getComment();
        return 0;
    }

    @Override
    public void initView() {
        banner_1 = (FlyBanner) findViewById(R.id.banner_1);
        sc_zhongcan = (ScrollView) findViewById(R.id.sc_zhongcan);

        iv_cai = (ImageView) findViewById(R.id.iv_cai);
        rl_map = (RelativeLayout) findViewById(R.id.rl_map);

        ratingbar = (RatingBar) findViewById(R.id.ratingbar);
        tv_zuowei = (TextView) findViewById(R.id.tv_zuowei);
        tv_address= (TextView) findViewById(R.id.tv_address);
        tv_cancel_condition= (TextView) findViewById(R.id.tv_cancel_condition);
        tv_restaurant_name= (TextView) findViewById(R.id.tv_restaurant_name);
        tv_time= (TextView) findViewById(R.id.tv_time);

        rl_order = (RelativeLayout) findViewById(R.id.rl_order);
        rl_park= (LinearLayout) findViewById(R.id.rl_park);
        tv_change_dianmode = (TextView) findViewById(R.id.tv_change_dianmode);
        lv_pinglun = (ListView) findViewById(R.id.lv_pinglun);
        initFlybanner();
        initViewPager();
        initComments();
        sc_zhongcan.fullScroll(ScrollView.FOCUS_UP);

        btn_order = (TextView) findViewById(R.id.btn_order);
    }

    private void initComments() {
        commentAdapter = new BaseArrayAdapter(context, new BaseArrayAdapter.OnCreateViewHolderListener() {
            @Override
            public Object onCreateViewHolder() {
                return new CommentsHolder(context);
            }
        },commentList);
        lv_pinglun.setAdapter(commentAdapter);
    }

    private void initFlybanner() {
        banner_1.stopAutoPlay();
    }

    private void initViewPager() {
        multiple = (MyListView) findViewById(R.id.multiple);
        initAdapter();
    }

    private void initAdapter() {
        adapter = new BaseArrayAdapter<>(context, new BaseArrayAdapter.OnCreateViewHolderListener() {
            @Override
            public Object onCreateViewHolder() {
                return new MultipleHolder();
            }
        },mList);
        multiple.setAdapter(adapter);
    }

    @Override
    public void setEvent() {
        btn_order.setOnClickListener(this);
        tv_change_dianmode.setOnClickListener(this);
        multiple.setOnItemClickListener(onItemClickListener);
        rl_map.setOnClickListener(this);
    }
    AdapterView.OnItemClickListener onItemClickListener=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            ActivityFactory.goToMultipleDetails(context,bean1.getGroup_meal().get(i),bean1.getRes_name(),bean1.getRes_id()+"");
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        getNetData();
    }

    /**
     *  获取网络数据
     */
    private void getNetData(){
        Observable<HttpResult<RestaurantDetailsBean>> result= ServiceApi.getInstance().getServiceContract().restaurantDetails(res_id,apitoken);
        result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RestaurantDetailsBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(RestaurantDetailsBean bean) {
                        restaurantDetailsBean = bean;
                        String pic=bean.getPic_meal();
                        if (bean.getIs_collected() == 0){
                            iv_img.setImageResource(R.drawable.ic_collect);
                        }else {
                            iv_img.setImageResource(R.drawable.ic_collected);
                        }
                        if (!TextUtils.isEmpty(pic)){
                            iv_cai.setVisibility(View.VISIBLE);
                            rl_order.setVisibility(View.GONE);
                            sc_zhongcan.setVisibility(View.GONE);
                            Glide.with(context).load(BaseApi.getBaseUrl()+bean.getPic_meal()).into(iv_cai);
                        }else {
                            sc_zhongcan.setVisibility(View.VISIBLE);
                        mList.clear();
                        mList.addAll(bean.getGroup_meal());
                        adapter.notifyDataSetChanged();
                        processData(bean);
                        }
                    }
                });
    }

    /**
     * 处理网络返回数据
     * @param bean
     */
    private void processData(RestaurantDetailsBean bean) {
        tv_title.setText(bean.getRes_name());
        bean1 = bean;
        if (bannerList.size()>0){
            bannerList.clear();
        }
        for (int i=0;i<bean.getBanners().size();i++){
            bannerList.add(BaseApi.baseOnlineUrl+bean.getBanners().get(i));
        }
        if (bannerList.size()>0){
            banner_1.setImagesUrl(bannerList);
        }
        List<String> parks=bean.getPark();
        rl_park.removeAllViews();
        for (int i=0;i<parks.size();i++){
//            parkbuffer.append(parks.get(i)+"  ");
            TextView tv_park=new TextView(context);
            tv_park.setTextColor(getResources().getColor(R.color.material_grey_700));
            tv_park.setTextSize(14);
            tv_park.setText(parks.get(i));
            tv_park.setPadding(Myutils.Dp2Px(context,8),0,0,0);
            Drawable rightDrawable = getResources().getDrawable(R.drawable.ic_yes);
            rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
            tv_park.setCompoundDrawables(null, null, rightDrawable, null);
            tv_park.setCompoundDrawablePadding(Myutils.Dp2Px(context,4));
            tv_park.setGravity(Gravity.CENTER);
            rl_park.addView(tv_park);
        }
//        tv_park.setText(parkbuffer);

        tv_restaurant_name.setText(bean.getRes_name());
        tv_zuowei.setText(bean.getSeat_num()+"");
        tv_address.setText(bean.getAddress());
        ratingbar.setRating(bean.getRank());

        StringBuffer stringBuffer=new StringBuffer();
        for (int i=0;i<bean.getTime().size();i++){
            stringBuffer.append(bean.getTime().get(i)+"\n");
        }
        tv_time.setText(stringBuffer);
        int cancel=bean.getCancel_condition();
        if (cancel == 0){
            tv_cancel_condition.setText("不可取消");
        }else {
            tv_cancel_condition.setText("距离预约时间"+cancel+"小时前可以取消");
        }
    }
    /**
     * 获取评论列表
     */
    private void getComment(){
        Observable<HttpResult<CommentBean>> result=ServiceApi.getInstance().getServiceContract().commentList(apitoken,res_id);
        result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CommentBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CommentBean commentBean) {
                        if (commentList.size()>0){
                            commentList.clear();
                        }
                        commentList.addAll(commentBean.getComments());
                        commentAdapter.notifyDataSetChanged();
                    }
                });
    }

    /**
     * 收藏
     */
    private void collect(){
        showDialog();
        Observable<HttpResult<IsCollectBean>> result=ServiceApi.getInstance().getServiceContract().collect(apitoken,res_id);
        result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<IsCollectBean>() {
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
                    public void onNext(IsCollectBean isCollectBean) {
                        int isCollect=isCollectBean.getIs_collected();
                        if (isCollect == 1){
                            iv_img.setImageResource(R.drawable.ic_collected);
                        }else if (isCollect == 0){
                            iv_img.setImageResource(R.drawable.ic_collect);
                        }
                    }
                });
    }
}
