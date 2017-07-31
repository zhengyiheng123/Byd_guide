package com.example.asus.xyd_order.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.net.BaseApi;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.net.result.IsCollectBean;
import com.example.asus.xyd_order.net.result.JingDianDetails;
import com.example.asus.xyd_order.utils.ActivityFactory;
import com.recker.flybanner.FlyBanner;

import java.util.ArrayList;
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

    @Bind(R.id.ratingbar)
    RatingBar ratingbar;
    private ImageView iv_img;

    @Override
    public void myOnclick(View view) {
        switch (view.getId()){
            case R.id.tv_query:
                ActivityFactory.gotoTickList(context);
                break;
            case R.id.iv_img:
                collect();
                break;
        }
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
        scene_id = getIntent().getStringExtra("scene_id");
        getNetData();
        return 0;
    }

    @Override
    public void initView() {

    }

    @Override
    public void setEvent() {
        tv_query.setOnClickListener(this);
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
                            tv_order.setVisibility(View.VISIBLE);
                        }else {
                            tv_order.setVisibility(View.GONE);
                        }
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
