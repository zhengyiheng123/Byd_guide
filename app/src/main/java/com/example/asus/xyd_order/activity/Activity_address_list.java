package com.example.asus.xyd_order.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.base.BaseArrayAdapter;
import com.example.asus.xyd_order.holder.Address_Holder;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.AddressBean;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.utils.ActivityFactory;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/3/3.
 */
public class Activity_address_list extends BaseActivity {
    public static Activity_address_list instance;
    private ListView lv_address;
    private List<AddressBean.AddressesBean> mlist = new ArrayList<>();;
    private TextView add_new_address;
    private BaseArrayAdapter adapter;
    private TextView tv_empty;
    private String choose;

    @Override
    protected void onResume() {
        super.onResume();
        instance=this;
        getNetData();
    }
    public BaseArrayAdapter getAdatper(){
        return adapter;
    }
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    public void myOnclick(View view) {
        switch (view.getId()){
            case R.id.add_new_address:
                ActivityFactory.gotoAddAddress(context);
                break;
        }
    }

    @Override
    public void setToolbar() {
        ImageView iv_back = (ImageView) findViewById(R.id.iv_back);
        TextView tv_title= (TextView) findViewById(R.id.tv_title);
        tv_title.setText("收货地址");
        iv_back.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_manager_address;
    }


    @Override
    public int getData() throws Exception {
        choose = getIntent().getStringExtra("choose");
        return 0;
    }

    @Override
    public void initView() {
        add_new_address = (TextView) findViewById(R.id.add_new_address);
        tv_empty = (TextView) findViewById(R.id.tv_empty);
        initListView();
    }

    private void initListView() {
        lv_address = (ListView) findViewById(R.id.lv_address);
        lv_address.setEmptyView(tv_empty);
        adapter = new BaseArrayAdapter(context, new BaseArrayAdapter.OnCreateViewHolderListener() {
            @Override
            public Object onCreateViewHolder() {
                return new Address_Holder();
            }
        },mlist);
        lv_address.setAdapter(adapter);
    }

    @Override
    public void setEvent() {
        add_new_address.setOnClickListener(this);
        lv_address.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (choose !=null){
                    Intent intent=new Intent();
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("address",mlist.get(i));
                    intent.putExtras(bundle);
                    setResult(1,intent);
                    finish();
                }
            }
        });
    }


    /**
     * 获取网络数据
     */
    public void getNetData(){
        Observable<HttpResult<AddressBean>> result= ServiceApi.getInstance().getServiceContract().addressData(apitoken);
        result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AddressBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        toastShow(e.getMessage());
                    }

                    @Override
                    public void onNext(AddressBean addressBean) {
                            mlist.clear();
                            mlist.addAll(addressBean.getAddresses());
                            adapter.notifyDataSetChanged();
                    }
                });
    }


}
