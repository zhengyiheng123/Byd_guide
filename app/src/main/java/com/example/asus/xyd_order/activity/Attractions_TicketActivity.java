package com.example.asus.xyd_order.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.base.BaseArrayAdapter;
import com.example.asus.xyd_order.holder.CommentsHolder;
import com.example.asus.xyd_order.holder.RouteHolder;
import com.example.asus.xyd_order.holder.StationHolder;
import com.example.asus.xyd_order.net.BaseApi;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.CommentBean;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.net.result.IsCollectBean;
import com.example.asus.xyd_order.net.result.JingDianDetails;
import com.example.asus.xyd_order.net.result.RouteResult;
import com.example.asus.xyd_order.ui.MyListView;
import com.example.asus.xyd_order.utils.ActivityFactory;
import com.example.asus.xyd_order.utils.TimeUtils;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.recker.flybanner.FlyBanner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/5/5.
 */

public class Attractions_TicketActivity extends BaseActivity {
    @Bind(R.id.tv_query)
    TextView tv_query;

    private String scene_id;

    @Bind(R.id.tv_jingdian_name)
    TextView tv_jingdian_name;

    @Bind(R.id.tv_address)
    TextView tv_address;

    @Bind(R.id.tv_time)
    TextView tv_time;

    @Bind(R.id.tv_order)
    TextView tv_order;

    @Bind(R.id.tv_order_desc)
    TextView tv_order_desc;
    @Bind(R.id.flybanner)
    FlyBanner flybanner;

    @Bind(R.id.rl_address)
    RelativeLayout rl_address;
    @Bind(R.id.spinner_1)
    TextView spinner_1;
    @Bind(R.id.spinner_2)
    TextView spinner_2;
    @Bind(R.id.spinner_routes)
    TextView spinner_routes;

    @Bind(R.id.tv_show_time)
    TextView tv_show_time;
    @Bind(R.id.ratingbar)
    RatingBar ratingbar;
    @Bind(R.id.lv_pinglun)
    MyListView lv_pinglun;
    @Bind(R.id.tv_select_time)
    TextView tv_select_time;
    private ImageView iv_img;
    private JingDianDetails bean;
    //路线集合
    public List<RouteResult.RoutesBean> routeList=new ArrayList<>();
    //站点集合
    private List<RouteResult.StationsBean> stationList=new ArrayList<>();
    //评论集合
    private List<CommentBean.CommentsBean> commentList=new ArrayList<>();
    //起点id
    private String start_station_id="";
    //终点id
    private String end_station_id="";
    //路线id
    private String route_id = "";
    private BaseArrayAdapter routeAdapter,stationAdapter;
    private TimePickerView pvTime;
    private String queryStamp;
    private BaseArrayAdapter commentAdapter;

    @Override
    public void myOnclick(View view) {
        switch (view.getId()){
            case R.id.tv_query:
                if (TextUtils.isEmpty(route_id)){
                    toastShow("请选择路线");
                    return;
                }
                if (TextUtils.isEmpty(start_station_id)){
                    toastShow("请选择起点");
                    return;
                }
                if (TextUtils.isEmpty(end_station_id)){
                    toastShow("请选择终点");
                    return;
                }
                if (TextUtils.isEmpty(queryStamp)){
                    toastShow("请选择时间");
                    return;
                }
                ActivityFactory.gotoTickList(context,route_id,start_station_id,end_station_id,queryStamp,scene_id);
                break;
            case R.id.iv_img:
                collect();
                break;
            case R.id.rl_address:
                Intent intent=new Intent(context,MapWebViewActivity.class);
                intent.putExtra("map_url",bean.getMap_url());
                startActivity(intent);
                break;
            case R.id.spinner_routes:
                showRouteDialog();
                break;
            case R.id.spinner_1:
                if (TextUtils.isEmpty(route_id)){
                    toastShow("请选择路线");
                    return;
                }
                showStation1Dialog();
                break;
            case R.id.spinner_2:
                if (TextUtils.isEmpty(route_id)){
                    toastShow("请选择路线");
                    return;
                }
                showStation2Dialog();
                break;
            case R.id.tv_select_time:
                pvTime.show();
                break;
        }
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
    //站点选择弹出框
    private void showStation1Dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择起点");
        builder.setAdapter(stationAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                start_station_id=stationList.get(i).getRs_id()+"";
                spinner_1.setText(stationList.get(i).getStation_name());
            }
        });
        builder.show();
    }
    //站点选择弹出框
    private void showStation2Dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择终点");

        builder.setAdapter(stationAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                end_station_id=stationList.get(i).getRs_id()+"";
                spinner_2.setText(stationList.get(i).getStation_name());
            }
        });
        builder.show();
    }
    //路线选择弹出框
    private void showRouteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择路线");
        builder.setAdapter(routeAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                spinner_1.setText("起点");
                spinner_2.setText("终点");
                route_id=routeList.get(i).getRoute_id()+"";
                spinner_routes.setText(routeList.get(i).getRoute_name());
                getStateList(route_id);
            }
        });
        builder.show();
    }

    @Override
    public void setToolbar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(view -> onBackPressed());
        TextView tv_title= (TextView) findViewById(R.id.tv_title);
        tv_title.setText("火车游船");
        iv_img = (ImageView) findViewById(R.id.iv_img);
        iv_img.setVisibility(View.VISIBLE);
        iv_img.setOnClickListener(this);
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_attraction_ticket;
    }


    @Override
    public int getData() throws Exception {
//        scene_id = getIntent().getStringExtra("scene_id");
        scene_id="19";
        getNetData();
        getComment();
        return 0;
    }

    @Override
    public void initView() {
        initTimePicker();
        initComments();
    }

    @Override
    public void setEvent() {
        tv_query.setOnClickListener(this);
        rl_address.setOnClickListener(this);
        spinner_routes.setOnClickListener(this);
        spinner_1.setOnClickListener(this);
        spinner_2.setOnClickListener(this);
        tv_select_time.setOnClickListener(this);
    }
    /**
     * 获取网络数据
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
                        toastShow(e.getMessage());
                    }

                    @Override
                    public void onNext(JingDianDetails s) {
                        bean = s;
                        getRouteList();
                        if (s.getIs_collected() == 0){
                            iv_img.setImageResource(R.drawable.ic_collect);
                        }else {
                            iv_img.setImageResource(R.drawable.ic_collected);
                        }
                        if (s.getBanners()!=null && s.getBanners().size()>0){
                            List<String> imgList=new ArrayList<String>();
                            for (int i=0;i<s.getBanners().size();i++){
                                imgList.add(BaseApi.baseOnlineUrl+s.getBanners().get(i));
                            }
                            flybanner.setImagesUrl(imgList);
                        }
                        tv_address.setText(s.getAddress());
                        tv_jingdian_name.setText(s.getScene_name());
                        tv_order_desc.setText(s.getIntroduction());
                        tv_time.setText(s.getTime_desc());
                        ratingbar.setRating(s.getRank());
                        if (s.getIs_bookable() == 1){
                            tv_order.setVisibility(View.GONE);
                        }else {
                            tv_order.setVisibility(View.GONE);
                        }
                    }
                });
    }

    //获取路线列表
    private void getRouteList(){
        showDialog();
        Observable<HttpResult<RouteResult>> result=ServiceApi.getInstance().getServiceContract().station_list(apitoken,scene_id,"");
        result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RouteResult>() {
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
                    public void onNext(RouteResult stationResult) {
                        routeList.clear();
                        routeList.addAll(stationResult.getRoutes());
                        routeAdapter = new BaseArrayAdapter(context, new BaseArrayAdapter.OnCreateViewHolderListener() {
                            @Override
                            public Object onCreateViewHolder() {
                                return new RouteHolder();
                            }
                        },routeList);
                    }
                });
    }
    //获取站点列表
    private void getStateList(String route_id){
        showDialog();
        Observable<HttpResult<RouteResult>> result=ServiceApi.getInstance().getServiceContract().station_list(apitoken,scene_id,route_id);
        result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RouteResult>() {
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
                    public void onNext(RouteResult stationResult) {
                        stationList.clear();
                        stationList.addAll(stationResult.getStations());
                        stationAdapter = new BaseArrayAdapter(context, new BaseArrayAdapter.OnCreateViewHolderListener() {
                            @Override
                            public Object onCreateViewHolder() {
                                return new StationHolder();
                            }
                        },stationList);
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
    //选择时间
        private void initTimePicker() {
            //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
            //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
            Calendar selectedDate = Calendar.getInstance();
            Calendar startDate = Calendar.getInstance();
            startDate.set(2017, 0, 23);
            Calendar endDate = Calendar.getInstance();
            endDate.set(2020, 11, 28);
            //时间选择器
            pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {//选中事件回调
                    // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null

                /*btn_Time.setText(getTime(date));*/
//                    Button btn = (Button) v;
//                    btn.setText(getDateAndTime(date));
                    tv_show_time.setText(getDateAndTime(date));
                    try {
                        queryStamp = TimeUtils.dateToStampssss(getDateAndTime(date));
                        queryStamp=queryStamp.substring(0,10);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            })
                    //年月日时分秒 的显示与否，不设置则默认全部显示
                    .setType(new boolean[]{true, true, true, true, false, false})
                    .setLabel("年", "月", "日", "", "", "")
                    .isCenterLabel(false)
                    .setDividerColor(Color.DKGRAY)
                    .setContentSize(14)
                    .setDate(selectedDate)
                    .setRangDate(startDate, endDate)
                    .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                    .setDecorView(null)
                    .build();
    }
    private String getDateAndTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH");
        return format.format(date);
    }
    //获取评论数据
    private void getComment(){
        Observable<HttpResult<CommentBean>> result=ServiceApi.getInstance().getServiceContract().commentList(apitoken,scene_id);
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
}
