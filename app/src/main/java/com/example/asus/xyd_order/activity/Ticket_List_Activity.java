package com.example.asus.xyd_order.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.base.BaseArrayAdapter;
import com.example.asus.xyd_order.holder.Ticket_List_Holder;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.net.result.QueryData;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/5/5.
 */

public class Ticket_List_Activity extends BaseActivity {

    private ListView lv_ticket_list;
    private List<QueryData.TicketsBean> mList=new ArrayList<>();
    private BaseArrayAdapter adapter;
    private QueryData data;
    private String scene_id;
    private String route_id;

    @Override
    public void myOnclick(View view) {

    }

    @Override
    public void setToolbar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(v -> onBackPressed());
        TextView tv_title= (TextView) findViewById(R.id.tv_title);
        tv_title.setText("车次查询");
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_ticket_list;
    }


    @Override
    public int getData() throws Exception {
        route_id = getIntent().getStringExtra("route_id");
        String start_station=getIntent().getStringExtra("start_station");
        String end_station=getIntent().getStringExtra("end_station");
        String date=getIntent().getStringExtra("date");
        scene_id = getIntent().getStringExtra("scene_id");
        getNetData(route_id,"10","6",date);
        return 0;
    }

    @Override
    public void initView() {
        TextView tv_empty= (TextView) findViewById(R.id.tv_empty);
        lv_ticket_list = (ListView) findViewById(R.id.lv_ticket_list);
        lv_ticket_list.setEmptyView(tv_empty);
        adapter = new BaseArrayAdapter(context, new BaseArrayAdapter.OnCreateViewHolderListener() {
            @Override
            public Object onCreateViewHolder() {
                return new Ticket_List_Holder();
            }
        },mList);
        lv_ticket_list.setAdapter(adapter);

    }

    @Override
    public void setEvent() {
        lv_ticket_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(context,SelectTicketActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("ticket",mList.get(i));
                intent.putExtras(bundle);
                intent.putExtra("group_start",data.getGroup_start()+"");
                intent.putExtra("date",data.getDate());
                intent.putExtra("scene_id",scene_id);
                intent.putExtra("route_id",route_id);
                startActivity(intent);
            }
        });
    }
    /**
     * 请求网络数据
     */
    private void getNetData(String route_id,String start_station,String end_station,String date){
        showDialog();
        Observable<HttpResult<QueryData>> result= ServiceApi.getInstance().getServiceContract().ticket_list(apitoken,route_id,start_station,end_station,date);
        result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<QueryData>() {
                    @Override
                    public void onCompleted() {
dismissDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissDialog();
                    }

                    @Override
                    public void onNext(QueryData queryData) {
                        data = queryData;
                        mList.clear();
                        if (queryData.getTickets()!=null){
                            mList.addAll(queryData.getTickets());
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}
