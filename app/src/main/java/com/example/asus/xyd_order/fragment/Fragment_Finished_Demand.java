package com.example.asus.xyd_order.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.activity.Activity_Edit_Release;
import com.example.asus.xyd_order.adapter.AllOrderAdapter;
import com.example.asus.xyd_order.base.BaseFragment;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.net.result.MyDemandBean;
import com.example.asus.xyd_order.utils.SharedPreferenceUtils;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/3/13.
 */
public class Fragment_Finished_Demand extends BaseFragment {

    private ListView lv_all;
    private ArrayList<MyDemandBean.DemandsBean> mList;
    private AllOrderAdapter adapter;

    /**
     * 获取网络数据
     */
    private void getNetData(){
        String apitoken= (String) SharedPreferenceUtils.getParam(context,"apitoken","");
        Observable<HttpResult<MyDemandBean>> result= ServiceApi.getInstance().getServiceContract().getMyDeman("2",apitoken);
        result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MyDemandBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MyDemandBean myDemandBean) {
                        mList.clear();
                        mList.addAll(myDemandBean.getDemands());
                        adapter.notifyDataSetChanged();
                    }
                });
    }
    @Override
    public void myOnclick(View view) {

    }

    @Override
    public void initView(View view) {
        initListView(view);

    }

    @Override
    public void onResume() {
        super.onResume();
        getNetData();
    }

    @Override
    public int getResource() {
        return R.layout.fragment_finished_demand;
    }

    @Override
    public int initData() throws Exception {
        mList = new ArrayList<>();
        return 0;
    }
    private void initListView(View v) {
        lv_all = (ListView) v.findViewById(R.id.lv_finish);
        TextView tv_empty= (TextView) v.findViewById(R.id.tv_empty);
        lv_all.setEmptyView(tv_empty);
        adapter = new AllOrderAdapter(mList,getActivity());
        lv_all.setAdapter(adapter);
    }
    @Override
    public void setEvent() {
        lv_all.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(context, Activity_Edit_Release.class);
                intent.putExtra("dmd_id",mList.get(i).getDmd_id()+"");
                intent.putExtra("ord_status",mList.get(i).getOrd_status()+"");
                context.startActivity(intent);
            }
        });
    }

}
