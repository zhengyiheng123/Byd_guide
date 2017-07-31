package com.example.asus.xyd_order.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.adapter.ExpandableListviewAdapter;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.base.BaseArrayAdapter;
import com.example.asus.xyd_order.holder.Multiple_Tuan_Holder;
import com.example.asus.xyd_order.net.BaseApi;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.net.result.TuancanBean;
import com.example.asus.xyd_order.ui.MyExpandListView;
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
 * Created by Zheng on 2017/2/27.
 */
public class MultipleDetailsActivity extends BaseActivity {

    private MyListView mylistview;

    List<String> groupList=new ArrayList<>();
    List<String> childList=new ArrayList<>();

    List<TuancanBean.PriceListBean> tuanList=new ArrayList<>();
    private FlyBanner flybanner;
    private List<String> imgList=new ArrayList<>();
    private TextView btn_order;
    private BaseArrayAdapter adapter;
    private String mer_id;
    private String address;
    private String res_name;
    private TextView tv_name,tv_address,tv_price;
    private TuancanBean bean;

    private TuancanBean.PriceListBean selectedBean=null;
    private TextView tv_title;

    @Override
    public void myOnclick(View view) {
        switch (view.getId()){
            case R.id.btn_order:
                for (int i=0;i<bean.getPrice_list().size();i++){
                    if (bean.getPrice_list().get(i).isChecked()){
                        selectedBean=bean.getPrice_list().get(i);
                    }
                }
                if (selectedBean!=null){
                    ActivityFactory.gotoOrder(context,"1",res_name,bean.getMeal_name(),selectedBean,bean.getImg_path(),bean.getMer_id()+"",null);
                }else {
                    toastShow("请选择团餐条目");
                }
                break;
}
    }

    @Override
    public void setToolbar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(v -> {onBackPressed();});
        tv_title = (TextView) findViewById(R.id.tv_title);
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_multiple_details;
    }


    @Override
    public int getData() throws Exception {
        mer_id = getIntent().getStringExtra("mer_id");
//        "mer_id",m_id);
//        intent.putExtra("address",address);
//        intent.putExtra("res_name",res_
        address = getIntent().getStringExtra("address");
        res_name = getIntent().getStringExtra("res_name");
        getNetData();
        for (int i=0;i<5;i++){
            groupList.add("1");
            childList.add("1");
        }
        return 0;
    }

    @Override
    public void initView() {
        tv_name= (TextView) findViewById(R.id.tv_name);
        tv_address= (TextView) findViewById(R.id.tv_address);
        tv_price= (TextView) findViewById(R.id.tv_price);
        flybanner = (FlyBanner) findViewById(R.id.flybanner);
        btn_order = (TextView) findViewById(R.id.btn_order);
        mylistview = (MyListView) findViewById(R.id.lv_multiple);
        adapter = new BaseArrayAdapter(context, new BaseArrayAdapter.OnCreateViewHolderListener() {
            @Override
            public Object onCreateViewHolder() {
                return new Multiple_Tuan_Holder();
            }
        },tuanList);
        mylistview.setAdapter(adapter);

    }

    @Override
    public void setEvent() {
        btn_order.setOnClickListener(this);
        mylistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                for (int i =0;i<tuanList.size();i++){
                    tuanList.get(i).setChecked(false);
                }
                if (tuanList.get(position).isChecked()){
                    tuanList.get(position).setChecked(false);
                }else {
                    tuanList.get(position).setChecked(true);
                }
                adapter.notifyDataSetChanged();
                tv_price.setText("价格："+tuanList.get(position).getMeal_price()+"");
            }
        });
    }

    /**
     * 获取网络数据
     */
    public void getNetData(){
        Observable<HttpResult<TuancanBean>> result= ServiceApi.getInstance().getServiceContract().tuancanDetails(mer_id,apitoken);
        result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TuancanBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(TuancanBean tuancanBean) {
                        bean = tuancanBean;
                        tv_title.setText(bean.getMeal_name());
                        for (int i =0;i<tuancanBean.getPrice_list().size();i++){
                            tuancanBean.getPrice_list().get(i).setMeal_name(tuancanBean.getMeal_name());
                        }

                        imgList.add(BaseApi.getBaseUrl()+tuancanBean.getImg_path());
                        flybanner.setImagesUrl(imgList);

                        tv_address.setText(address);
                        tv_name.setText(res_name);

                        tuanList.addAll(tuancanBean.getPrice_list());
                        adapter.notifyDataSetChanged();

                    }
                });
    }
}
