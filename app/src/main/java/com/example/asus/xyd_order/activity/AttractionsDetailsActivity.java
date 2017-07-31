package com.example.asus.xyd_order.activity;

import android.content.Intent;
import android.media.Rating;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.base.BaseArrayAdapter;
import com.example.asus.xyd_order.base.BaseViewHolder;
import com.example.asus.xyd_order.dateview.CalendarView;
import com.example.asus.xyd_order.holder.AttractionDetailsHolder;
import com.example.asus.xyd_order.holder.CommentsHolder;
import com.example.asus.xyd_order.net.BaseApi;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.AttractionsBean;
import com.example.asus.xyd_order.net.result.AttractionsTimeBean;
import com.example.asus.xyd_order.net.result.CommentBean;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.net.result.IsCollectBean;
import com.example.asus.xyd_order.ui.MyListView;
import com.example.asus.xyd_order.utils.ActivityFactory;
import com.example.asus.xyd_order.utils.ToastUtils;
import com.recker.flybanner.FlyBanner;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/3/2.
 */
public class AttractionsDetailsActivity extends BaseActivity {

    private MyListView lv_ticketprice,lv_pinglun;
    private List<CommentBean.CommentsBean> mList;
    private FlyBanner flybanner;
    private ArrayList<String> mDatas;
    private CalendarView mCalendarView;
    private TextView txt_select_month,tv_leave_message,tv_message_count,tv_scene_name,tv_address,tv_order_desc,tv_run_time_desc;
    private ImageButton mLastMonthView;
    private ImageButton mNextMonthView;
    private ImageView tv_overview;
    private List<String> kexuan=new ArrayList<>();
    private String scene_id;
    private List<String> imgList=new ArrayList<>();
    private RatingBar ratingbar;
    private BaseArrayAdapter<CommentBean.CommentsBean, BaseViewHolder> commentAdapter;
    private AttractionsBean bean;
    private TextView tv_title;
    private ImageView iv_img;
    private RelativeLayout rl_map;
    private ScrollView scroll;

    @Override
            public void myOnclick(View view) {
        switch (view.getId()){
            case R.id.img_select_last_month:
                mCalendarView.setLastMonth();
                txt_select_month.setText(mCalendarView.getDate());
                break;
            case R.id.img_select_next_month:
                mCalendarView.setNextMonth();
                txt_select_month.setText(mCalendarView.getDate());
                break;
            case R.id.tv_overview:
                ActivityFactory.gotoOverView(context,bean.getScene_id()+"","");
                break;
            case R.id.tv_leave_message:
                ActivityFactory.gotoLeaveMessage(context);
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
                tv_title = (TextView) findViewById(R.id.tv_title);
            iv_back.setOnClickListener(v -> onBackPressed());
                iv_img = (ImageView) findViewById(R.id.iv_img);
                iv_img.setVisibility(View.VISIBLE);
                iv_img.setOnClickListener(this);
            }

            @Override
            protected int getResourceId() {
                return R.layout.activity_attractions_details;
            }


            @Override
            public int getData() throws Exception {
                mDatas = new ArrayList<>();
                scene_id = getIntent().getStringExtra("scene_id");
                getTimes();
                getNetData();

                mList = new ArrayList<>();
                return 1;
            }

            @Override
            public void initView() {
                inilizeView();
                //初始化票价列表listview
                initListView();
                //初始化评论listview
                inilizeComments();
                //初始化日历控件
                //请求日期列表
            }

    /**
     * 初始化日历控件
     */
    private void initCalender() {
        mCalendarView = (CalendarView) findViewById(R.id.calendarView);
        txt_select_month = (TextView) findViewById(R.id.txt_select_month);
        mLastMonthView = (ImageButton) findViewById(R.id.img_select_last_month);
        mNextMonthView = (ImageButton) findViewById(R.id.img_select_next_month);
        mLastMonthView.setOnClickListener(this);
        mNextMonthView.setOnClickListener(this);
        txt_select_month.setText(mCalendarView.getDate());
        // 设置可选日期
        mCalendarView.setOptionalDate(kexuan);
        // 设置已选日期
        mCalendarView.setSelectedDates(mDatas);
        mCalendarView.setClickable(true);
//        mCalendarView.setLastMonth();
        mCalendarView.setOnClickDate(new CalendarView.OnClickListener() {
            @Override
            public void onClickDateListener(int year, int month, int day) {
                List<String> list=mCalendarView.getSelectedDates();
                String monthStr=month+"";
                String dayStr=day+"";
                if (month<10){
                    monthStr="0"+monthStr;
                }
                if (day<10){
                    dayStr="0"+dayStr;
                }
                if (kexuan.contains(year+monthStr+dayStr)){
                    ActivityFactory.gotoOverView(context,bean.getScene_id()+"",year+"-"+monthStr+"-"+dayStr);
                }
//                ToastUtils.show(context,year+"-"+month+"-"+day,0);

            }
        });
    }
    private void inilizeComments() {
        lv_pinglun= (MyListView) findViewById(R.id.lv_pinglun);
        commentAdapter= new BaseArrayAdapter<>(context, new BaseArrayAdapter.OnCreateViewHolderListener() {
            @Override
            public Object onCreateViewHolder() {
                return new CommentsHolder(context);
            }
        },mList);
        lv_pinglun.setAdapter(commentAdapter);
    }

    /**
     * 初始化票价Listview
     */
    private void initListView() {
        lv_ticketprice = (MyListView) findViewById(R.id.lv_ticketprice);
        lv_ticketprice.setAdapter(new BaseArrayAdapter<>(context, new BaseArrayAdapter.OnCreateViewHolderListener() {
            @Override
            public Object onCreateViewHolder() {
                return new AttractionDetailsHolder();
            }
        },mList));
    }

    /**
     * 初始化所有控件
     */
    private void inilizeView() {
        scroll = (ScrollView) findViewById(R.id.scroll);
        rl_map = (RelativeLayout) findViewById(R.id.rl_map);
        flybanner = (FlyBanner) findViewById(R.id.flybanner);
        tv_overview= (ImageView) findViewById(R.id.tv_overview);
        tv_leave_message= (TextView) findViewById(R.id.tv_leave_message);
        tv_scene_name= (TextView) findViewById(R.id.tv_scene_name);
        tv_address= (TextView) findViewById(R.id.tv_address);
        tv_run_time_desc= (TextView) findViewById(R.id.tv_run_time_desc);
        tv_message_count= (TextView) findViewById(R.id.tv_message_count);
        tv_order_desc= (TextView) findViewById(R.id.tv_order_desc);
        ratingbar = (RatingBar) findViewById(R.id.ratingbar);
    }
    @Override
    public void setEvent() {
        tv_overview.setOnClickListener(this);
        tv_leave_message.setOnClickListener(this);
        rl_map.setOnClickListener(this);
    }

    //请求网络数据
    private void getNetData(){
        Observable<HttpResult<AttractionsBean>> result= ServiceApi.getInstance().getServiceContract().attractiosDetails(scene_id,apitoken);
        result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AttractionsBean>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(AttractionsBean attractionsBean) {
                        bean = attractionsBean;
                        if (bean.getIs_collected() == 0){
                            iv_img.setImageResource(R.drawable.ic_collect);
                        }else {
                            iv_img.setImageResource(R.drawable.ic_collected);
                        }
                        tv_scene_name.setText(attractionsBean.getScene_name());
                        tv_title.setText(attractionsBean.getScene_name());
                        tv_address.setText(attractionsBean.getAddress());
                        tv_run_time_desc.setText(attractionsBean.getTime_desc());
                        tv_order_desc.setText(attractionsBean.getIntroduction());
                        String mer_id =attractionsBean.getScene_id()+"";
                        if (attractionsBean.getBanners()!=null && attractionsBean.getBanners().size()>0){
                            imgList.clear();
                            for (int i=0;i<attractionsBean.getBanners().size();i++){
                                imgList.add(BaseApi.getBaseUrl()+attractionsBean.getBanners().get(i));
                            }
                            flybanner.setImagesUrl(imgList);
                        }
                        ratingbar.setRating(attractionsBean.getRank());
                        getComments(mer_id);
                        for (int i=0;i<attractionsBean.getTime().size();i++){
                            tv_run_time_desc.setText(tv_run_time_desc.getText().toString()+attractionsBean.getTime().get(i)+"\n");
                        }
                    }
                });
    }
    /**
     * 获取评论列表信息
     */
    private void getComments(String mer_id){
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
                        scroll.fullScroll(ScrollView.FOCUS_UP);
                        scroll.smoothScrollTo(0,0);
                    }
                });
    }

    /**
     * 表演景区类获取演出时间
     */
    private void getTimes(){
        Observable<HttpResult<AttractionsTimeBean>>result=ServiceApi.getInstance().getServiceContract().attractionsTimesBean(apitoken,scene_id);
        result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AttractionsTimeBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(AttractionsTimeBean attractionsTimeBean) {
                        if (attractionsTimeBean.getDates()!=null && attractionsTimeBean.getDates().size()>0){
                            kexuan.clear();
                            kexuan.addAll(attractionsTimeBean.getDates());
                        }
                        initCalender();
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
