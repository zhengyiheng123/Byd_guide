package com.example.asus.xyd_order.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.adapter.ExpandableListviewAdapter;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.base.BaseArrayAdapter;
import com.example.asus.xyd_order.holder.Multiple_Tuan_Holder;
import com.example.asus.xyd_order.net.BaseApi;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.net.result.RestaurantDetailsBean;
import com.example.asus.xyd_order.net.result.TuancanBean;
import com.example.asus.xyd_order.ui.MyExpandListView;
import com.example.asus.xyd_order.ui.MyListView;
import com.example.asus.xyd_order.ui.SmartImageveiw;
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


    List<TuancanBean.PriceListBean> tuanList=new ArrayList<>();
    private SmartImageveiw flybanner;
    private List<String> imgList=new ArrayList<>();
    private TextView btn_order;
    private BaseArrayAdapter adapter;
    private TextView tv_name,tv_address,tv_price,tv_details;

    private TuancanBean.PriceListBean selectedBean=null;
    private TextView tv_title;
    private RestaurantDetailsBean.GroupMealBean groupMeal;
    private String res_name;
    private String res_id;

    @Override
    public void myOnclick(View view) {
        switch (view.getId()){
            case R.id.btn_order:
                selectedBean=new TuancanBean.PriceListBean();
                selectedBean.setMeal_name(groupMeal.getMeal_name());
                selectedBean.setMeal_detail(groupMeal.getMeal_detail());
                selectedBean.setMeal_price(groupMeal.getMeal_price());
                selectedBean.setMp_id(groupMeal.getMeal_id());
                selectedBean.setImg_path(groupMeal.getImg_path());
                ActivityFactory.gotoOrder(context,"1",res_name,selectedBean,res_id);
                break;
}
    }

    @Override
    public void setToolbar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(v -> {onBackPressed();});
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(groupMeal.getMeal_name());
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_multiple_details;
    }


    @Override
    public int getData() throws Exception {
        //餐厅名称
        res_name = getIntent().getStringExtra("res_name");
        //餐厅id
        res_id = getIntent().getStringExtra("res_id");
        //团餐id
        groupMeal = (RestaurantDetailsBean.GroupMealBean) getIntent().getExtras().getSerializable("groupMeal");
        return 0;
    }

    @Override
    public void initView() {
        tv_name= (TextView) findViewById(R.id.tv_name);
        tv_name.setText(groupMeal.getMeal_name());
        tv_address= (TextView) findViewById(R.id.tv_address);
        tv_price= (TextView) findViewById(R.id.tv_price);
        tv_price.setText(groupMeal.getMeal_price());
        tv_details= (TextView) findViewById(R.id.tv_details);
        tv_details.setText(groupMeal.getMeal_detail());
        flybanner = (SmartImageveiw) findViewById(R.id.flybanner);
        flybanner.setRatio(2.0f);
        Glide.with(context).load(BaseApi.getBaseUrl()+groupMeal.getImg_path()).into(flybanner);
        btn_order = (TextView) findViewById(R.id.btn_order);
        mylistview = (MyListView) findViewById(R.id.lv_multiple);
        adapter = new BaseArrayAdapter(context, new BaseArrayAdapter.OnCreateViewHolderListener() {
            @Override
            public Object onCreateViewHolder() {
                return new Multiple_Tuan_Holder();
            }
        },tuanList);
        mylistview.setAdapter(adapter);
        imgList.add(BaseApi.getBaseUrl()+groupMeal.getImg_path());
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
//    public void getNetData(){
//        Observable<HttpResult<TuancanBean>> result= ServiceApi.getInstance().getServiceContract().tuancanDetails(mer_id,apitoken);
//        result.map(new ResultFilter<>())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<TuancanBean>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(TuancanBean tuancanBean) {
//                        bean = tuancanBean;
//                        tv_title.setText(bean.getMeal_name());
//                        for (int i =0;i<tuancanBean.getPrice_list().size();i++){
//                            tuancanBean.getPrice_list().get(i).setMeal_name(tuancanBean.getMeal_name());
//                        }
//
//                        imgList.add(BaseApi.getBaseUrl()+tuancanBean.getImg_path());
//                        flybanner.setImagesUrl(imgList);
//                        tv_name.setText(res_name);
//
//                        tuanList.addAll(tuancanBean.getPrice_list());
//                        adapter.notifyDataSetChanged();
//
//                    }
//                });
//    }
}
