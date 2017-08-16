package com.example.asus.xyd_order.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.base.BaseArrayAdapter;
import com.example.asus.xyd_order.holder.RoutesHolder;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.net.result.RoutDetailsBean;
import com.example.asus.xyd_order.net.result.RouteBean;
import com.example.asus.xyd_order.utils.ActivityFactory;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/3/20.
 */
public class SelectRoutes extends BaseActivity {

    private TextView tv_title;
    private ImageView iv_back;
    private ListView lv_routes;
    private List<RouteBean.RoutesBean> mList=new ArrayList<>();
    private String mer_id;
    private BaseArrayAdapter adapter;

    @Override
    public void myOnclick(View view) {

    }

    @Override
    public void setToolbar() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(v -> {onBackPressed();});
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("选择路线");
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_routes;
    }

    String s="";
    @Override
    public int getData() throws Exception {
        mer_id = getIntent().getStringExtra("mer_id");
        getNetData(mer_id);
        return 0;
    }

    @Override
    public void initView() {
        TextView tv_empty= (TextView) findViewById(R.id.tv_empty);
        lv_routes = (ListView) findViewById(R.id.lv_routes);
        lv_routes.setEmptyView(tv_empty);
        adapter = new BaseArrayAdapter(context, new BaseArrayAdapter.OnCreateViewHolderListener() {
            @Override
            public Object onCreateViewHolder() {
                return new RoutesHolder();
            }
        },mList);
        lv_routes.setAdapter(adapter);
    }

    @Override
    public void setEvent() {
        lv_routes.setOnItemClickListener((parent, view, position, id) -> {
            ActivityFactory.gotoOrderNomal(context,mList.get(position).getRoute_id()+"");
        });
    }

    /**
     * 网络数据
     */
    private void getNetData(String mer_id){
        Observable<HttpResult<RouteBean>> result= ServiceApi.getInstance().getServiceContract().routeList(apitoken,mer_id);
        result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RouteBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(RouteBean routeBean) {
                        if (routeBean.getRoutes()!=null && routeBean.getRoutes().size()>0){
                            mList.clear();
                            mList.addAll(routeBean.getRoutes());
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }
}
