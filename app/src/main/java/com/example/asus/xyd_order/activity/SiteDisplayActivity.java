package com.example.asus.xyd_order.activity;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.adapter.ExpandableAttractListviewAdapter;
import com.example.asus.xyd_order.adapter.MyPagerAdapter;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.base.BaseArrayAdapter;
import com.example.asus.xyd_order.fragment.FragmentSiteFragment1;
import com.example.asus.xyd_order.holder.SiteDisplayHolder;
import com.example.asus.xyd_order.net.BaseApi;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.BaseTicketRouteBean;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.net.result.RoutDetailsBean;
import com.example.asus.xyd_order.net.result.RouteDetails;
import com.example.asus.xyd_order.ui.ChildViewPager;
import com.example.asus.xyd_order.ui.MyExpandListView;
import com.example.asus.xyd_order.ui.MyListView;
import com.example.asus.xyd_order.ui.SmartImageveiw;
import com.example.asus.xyd_order.utils.ActivityFactory;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by Zheng on 2017/3/2.
 */
public class SiteDisplayActivity extends BaseActivity {

    private ImageView iv_back;
    private View v_nine,v_ten;
    //团体票
    List<BaseTicketRouteBean> groupList=new ArrayList<>();
    //单人票
    List<BaseTicketRouteBean> nomalList=new ArrayList<>();

    //数据集合
    List<BaseTicketRouteBean> mList=new ArrayList<>();
//    List<String> childList=new ArrayList<>();
    private RelativeLayout rl_ten,rl_nine;
    private TextView tv_peer;
    private MyExpandListView myListview;
    private ScrollView scrollview;
    private String route_id;
    private SmartImageveiw smart_head;
    private TextView tv_cancel_desc,tv_group,tv_nomal,tv_totalmoney,btn_order_now;
    private RouteDetails bean;


    public static SiteDisplayActivity instance;
    private MyListView lv_order_nomal,lv_order_group;
    private BaseArrayAdapter groupAdapter,nomalAdapter;

    //总金额
    private double totalPrice=0.0f;
    private TextView tv_title;

    @Override
    protected void onResume() {
        super.onResume();
        instance=this;
    }

    @Override
    public void myOnclick(View view) {
        switch (view.getId()){
            case R.id.rl_ten:
                v_ten.setVisibility(View.VISIBLE);
                v_nine.setVisibility(View.GONE);
                lv_order_group.setVisibility(View.VISIBLE);
                lv_order_nomal.setVisibility(View.GONE);
                groupAdapter.notifyDataSetChanged();
                break;
            case R.id.rl_nine:
                v_ten.setVisibility(View.GONE);
                v_nine.setVisibility(View.VISIBLE);
                lv_order_group.setVisibility(View.GONE);
                lv_order_nomal.setVisibility(View.VISIBLE);
                nomalAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_peer:
                break;
            case R.id.btn_order_now:
                List<BaseTicketRouteBean> groupTemp=new ArrayList<>();
                for (int i=0;i<groupList.size();i++){
                    if (groupList.get(i).getAllCount()>0){
                        groupTemp.add(groupList.get(i));
                    }
                }
                List<BaseTicketRouteBean> nomalTemp=new ArrayList<>();
                for (int i=0;i<nomalList.size();i++){
                    if (nomalList.get(i).getAllCount()>0){
                        nomalTemp.add(nomalList.get(i));
                    }
                }
                if (groupTemp.size()== 0 && nomalTemp.size() == 0){
                    toastShow("请选择票");
                    return;
                }
                ActivityFactory.gotoAttractionsOrder(context,groupTemp,nomalTemp,totalPrice+"",bean.getMer_id()+"",bean.getRoute_id()+"");
                break;
        }
    }

    @Override
    public void setToolbar() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(v -> {onBackPressed();});
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("选票");
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_sitedisplay;
    }


    @Override
    public int getData() throws Exception {
        route_id = getIntent().getStringExtra("route_id");
        getNetData();
        return 1;
    }

    //更新标题tou
    public void updateTitle(){
        int groupCount=0;
        double groupPrice=0.0f;
        for ( int i=0;i<groupList.size();i++){
            for (int y=0;y<groupList.get(i).getTickets().size();y++){
                groupCount=groupCount+groupList.get(i).getTickets().get(y).getNums();
                if (groupList.get(i).getTickets().get(y).getTotlePrice()!=null){
                    groupPrice=groupPrice+groupList.get(i).getTickets().get(y).getTotlePrice();
                }
            }
        }
        tv_group.setText(bean.getGroup_start()+"人以上("+groupCount+"张)");

        int nomalCount=0;
        double nomalPrice=0.0f;
        for ( int i=0;i<nomalList.size();i++){
            for (int y=0;y<nomalList.get(i).getTickets().size();y++){
                nomalCount=nomalCount+nomalList.get(i).getTickets().get(y).getNums();
                if (nomalList.get(i).getTickets().get(y).getTotlePrice()!=null){
                    nomalPrice=nomalPrice+nomalList.get(i).getTickets().get(y).getTotlePrice();
                }
            }
        }
        tv_nomal.setText("0-"+(bean.getGroup_start()-1)+"人("+nomalCount+"张)");
        totalPrice=nomalPrice+groupPrice;
        tv_totalmoney.setText("总价："+totalPrice);

    }
    @Override
    public void initView() {
        tv_cancel_desc = (TextView) findViewById(R.id.tv_cancel_desc);
        tv_group= (TextView) findViewById(R.id.tv_group);
        tv_nomal= (TextView) findViewById(R.id.tv_nomal);
        btn_order_now= (TextView) findViewById(R.id.btn_order_now);

        tv_totalmoney= (TextView) findViewById(R.id.tv_totalmoney);
        smart_head = (SmartImageveiw) findViewById(R.id.smart_head);


        scrollview = (ScrollView) findViewById(R.id.scrollview);
        scrollview.scrollTo(0,0);
        lv_order_nomal = (MyListView) findViewById(R.id.lv_order_nomal);
        lv_order_group= (MyListView) findViewById(R.id.lv_order_group);

        groupAdapter = new BaseArrayAdapter(context, new BaseArrayAdapter.OnCreateViewHolderListener() {
            @Override
            public Object onCreateViewHolder() {
                return new SiteDisplayHolder();
            }
        },groupList);
        lv_order_group.setAdapter(groupAdapter);

        nomalAdapter=new BaseArrayAdapter(context, new BaseArrayAdapter.OnCreateViewHolderListener() {
            @Override
            public Object onCreateViewHolder() {
                return new SiteDisplayHolder();
            }
        },nomalList);
        lv_order_nomal.setAdapter(nomalAdapter);

        v_nine = findViewById(R.id.v_nine);
        v_ten=findViewById(R.id.v_ten);
        rl_ten = (RelativeLayout) findViewById(R.id.rl_ten);
        rl_nine= (RelativeLayout) findViewById(R.id.rl_nine);
        tv_peer = (TextView) findViewById(R.id.tv_peer);
    }

    @Override
    public void setEvent() {
        rl_ten.setOnClickListener(this);
        rl_nine.setOnClickListener(this);
        tv_peer.setOnClickListener(this);
        btn_order_now.setOnClickListener(this);
    }

    /**
     * 请求网路数据
     */
    private void getNetData(){
        Observable<HttpResult<RouteDetails>> result= ServiceApi.getInstance().getServiceContract().routeDetails(route_id,apitoken);
        result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RouteDetails>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(RouteDetails routeDetails) {
                        smart_head.setRatio(1.7f);
                        bean = routeDetails;
                        tv_cancel_desc.setText(routeDetails.getIntroduction());
                        tv_group.setText(routeDetails.getGroup_start()+"人以上(0张)");
                        tv_nomal.setText("0-"+(routeDetails.getGroup_start()-1)+"人(0张)");
                        Glide.with(context).load(BaseApi.getBaseUrl()+routeDetails.getImg_path()).into(smart_head);


                        groupList.clear();
                        nomalList.clear();
                        groupList.addAll(routeDetails.getGroup_ticket());
                        nomalList.addAll(routeDetails.getNormal_ticket());

                        groupAdapter.notifyDataSetChanged();
                    }
                });
    }

}
