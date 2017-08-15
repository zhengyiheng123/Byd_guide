package com.example.asus.xyd_order.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.base.BaseArrayAdapter;
import com.example.asus.xyd_order.holder.AttractionDetailsHolder;
import com.example.asus.xyd_order.holder.CommentsHolder;
import com.example.asus.xyd_order.net.BaseApi;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.CommentBean;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.net.result.IsCollectBean;
import com.example.asus.xyd_order.net.result.JingDianDetails;
import com.example.asus.xyd_order.net.result.Name;
import com.example.asus.xyd_order.ui.MyListView;
import com.example.asus.xyd_order.utils.ActivityFactory;
import com.recker.flybanner.FlyBanner;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/3/20.
 */
public class AttractionDetailsNomal extends BaseActivity {

    private MyListView myListView;
    private List<CommentBean.CommentsBean> mList;
    private MyListView lv_pinglun;
    private TextView tv_title,tv_order,tv_message_count,tv_name,tv_distance,tv_address,tv_time_desc,tv_introduce;
    private String scene_id;
    private FlyBanner flybanner;
    private RatingBar ratingbar;

    private String mer_id="";
    private BaseArrayAdapter commentAdapter;
    private JingDianDetails bean;
    private ImageView iv_img;
    private RelativeLayout rl_map;
    private ScrollView scroll;

    @Override
    public void myOnclick(View view) {
        switch (view.getId()){
            case R.id.tv_order:
                if (bean.getIs_bookable() == 1){
                    ActivityFactory.selectRoutes(context,mer_id);
                }else {
                    toastShow("当前景点为窗口售票");
                }
                break;
            case R.id.iv_img:
                collect();
                break;
            case R.id.rl_map:
                Intent intent=new Intent(context,MapWebViewActivity.class);
                intent.putExtra("map_url",bean.getMap_url());
                startActivity(intent);
                break;
        }
    }

    @Override
    public void setToolbar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(v -> onBackPressed());
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_img = (ImageView) findViewById(R.id.iv_img);
        iv_img.setVisibility(View.VISIBLE);
        iv_img.setOnClickListener(this);
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_attraction_nomal;
    }


    @Override
    public int getData() throws Exception {
        mList = new ArrayList<>();
        scene_id=getIntent().getStringExtra("scene_id");
        getNetData();
        return 0;
    }

    @Override
    public void initView() {
        initAllView();
        inilizeComments();
    }
    //初始化基础控件
    private void initAllView() {
        scroll = (ScrollView) findViewById(R.id.scroll);
        scroll.fullScroll(ScrollView.FOCUS_UP);
        tv_order= (TextView) findViewById(R.id.tv_order);
        rl_map = (RelativeLayout) findViewById(R.id.rl_map);
        tv_message_count= (TextView) findViewById(R.id.tv_message_count);
        ratingbar = (RatingBar) findViewById(R.id.ratingbar);
        flybanner = (FlyBanner) findViewById(R.id.flybanner);
        tv_name= (TextView) findViewById(R.id.tv_name);
        tv_distance= (TextView) findViewById(R.id.tv_distance);
        tv_address= (TextView) findViewById(R.id.tv_address);
        tv_time_desc= (TextView) findViewById(R.id.tv_time_desc);
        tv_introduce= (TextView) findViewById(R.id.tv_introduce);
    }

    //初始化评论
        private void inilizeComments() {
            lv_pinglun = (MyListView) findViewById(R.id.lv_pinglun);
            commentAdapter = new BaseArrayAdapter<>(context, new BaseArrayAdapter.OnCreateViewHolderListener() {
                @Override
                public Object onCreateViewHolder() {
                    return new CommentsHolder(context);
                }
            },mList);
            lv_pinglun.setAdapter(commentAdapter);
        }


    @Override
    public void setEvent() {
        tv_order.setOnClickListener(this);
        rl_map.setOnClickListener(this);
    }

    /**
     * 请求网络数据
     */
    private void getNetData(){
        Observable<HttpResult<JingDianDetails>> result= ServiceApi.getInstance().getServiceContract().jingdianDetails(scene_id,apitoken);
        result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JingDianDetails>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(JingDianDetails jingDianDetails) {
                        bean = jingDianDetails;
                        if (bean.getIs_collected() == 0){
                            iv_img.setImageResource(R.drawable.ic_collect);
                        }else {
                            iv_img.setImageResource(R.drawable.ic_collected);
                        }
                        List<String> bannerList=new ArrayList<String>();
                        if (jingDianDetails.getBanners().size()>0){
                            bannerList.clear();
                            for (int i=0;i<jingDianDetails.getBanners().size();i++){
                                bannerList.add(BaseApi.getBaseUrl()+jingDianDetails.getBanners().get(i));
                            }
                            flybanner.setImagesUrl(bannerList);
                        }
                        flybanner.startAutoPlay();
                        mer_id=jingDianDetails.getScene_id()+"";
                        tv_title.setText(jingDianDetails.getScene_name());
                        tv_name.setText(jingDianDetails.getScene_name());
                        tv_address.setText(jingDianDetails.getAddress());
                        tv_introduce.setText(jingDianDetails.getIntroduction());
                        tv_time_desc.setText(jingDianDetails.getTime_desc());
                        ratingbar.setRating(jingDianDetails.getRank());
                        if (jingDianDetails.getIs_bookable()== 1){
                            tv_order.setVisibility(View.VISIBLE);
                        }else {
                            tv_order.setVisibility(View.GONE);
                        }
                        getComments();
                    }
                });
    }

    /**
     * 获取评论列表信息
     */
    private void getComments(){
        Observable<HttpResult<CommentBean>> result=ServiceApi.getInstance().getServiceContract().commentJingquList(apitoken,mer_id);
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
                        if (mList.size()>0){
                            mList.clear();
                        }
                        tv_message_count.setText("同行留言("+commentBean.getComments().size()+")");
                        mList.addAll(commentBean.getComments());
                        commentAdapter.notifyDataSetChanged();
                        scroll.smoothScrollTo(0,0);
                    }
                });
    }
    /**
     * 收藏
     */
    private void collect(){
        showDialog();
        Observable<HttpResult<IsCollectBean>> result=ServiceApi.getInstance().getServiceContract().collect(apitoken,scene_id);
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
