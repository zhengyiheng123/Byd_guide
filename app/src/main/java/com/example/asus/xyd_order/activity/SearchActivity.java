package com.example.asus.xyd_order.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.adapter.RegisterAdapter;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.base.BaseFragment;
import com.example.asus.xyd_order.fragment.AttractionsFragment;
import com.example.asus.xyd_order.fragment.RestaurantFragment;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.net.result.SerchResult;
import com.example.asus.xyd_order.ui.ChildViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/8/8.
 */

public class SearchActivity extends BaseActivity {
    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.auto_text)
    EditText auto_text;
    @Bind(R.id.tv_search)
    TextView tv_search;
    @Bind(R.id.tv_restaurant)
    TextView tv_restaurant;
    @Bind(R.id.line_restaurant)
    View line_restaurant;

    @Bind(R.id.tv_attractions)
    TextView tv_attractions;

    @Bind(R.id.line_attractions)
    View line_attractions;

    @Bind(R.id.rl_restaurant)
    RelativeLayout rl_restaurant;
    @Bind(R.id.rl_attractions)
    RelativeLayout rl_attractions;
    @Bind(R.id.register_viewpager)
    ChildViewPager register_viewpager;
    private List<BaseFragment> fragmentList;
    private RegisterAdapter adapter;
    private List<SerchResult.RestaurantsBean> restaurantsList;
    private List<SerchResult.ScenesBean> seceeList;

    @Override
    public void myOnclick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_search:
                if (!TextUtils.isEmpty(auto_text.getText().toString())){
                    search(auto_text.getText().toString());
                }else {
                    toastShow("请输入商户名称");
                }
                break;
            case R.id.rl_attractions:
                tv_restaurant.setTextColor(getResources().getColor(R.color.material_grey_800));
                line_restaurant.setBackgroundColor(getResources().getColor(R.color.white));
                tv_attractions.setTextColor(getResources().getColor(R.color.tool_bar_color));
                line_attractions.setBackgroundColor(getResources().getColor(R.color.tool_bar_color));
                register_viewpager.setCurrentItem(1,false);
                break;
            case R.id.rl_restaurant:
                tv_restaurant.setTextColor(getResources().getColor(R.color.tool_bar_color));
                line_restaurant.setBackgroundColor(getResources().getColor(R.color.tool_bar_color));
                tv_attractions.setTextColor(getResources().getColor(R.color.material_grey_800));
                line_attractions.setBackgroundColor(getResources().getColor(R.color.white));
                register_viewpager.setCurrentItem(0,false);
                break;
        }
    }

    @Override
    public void setToolbar() {
        iv_back.setOnClickListener(this);
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_search;
    }

    @Override
    public int getData() throws Exception {
        return 0;
    }

    @Override
    public void initView() {
    }

    @Override
    public void setEvent() {
        tv_search.setOnClickListener(this);
        rl_attractions.setOnClickListener(this);
        rl_restaurant.setOnClickListener(this);
    }
    /**获取网络数据
     *
     */
    private void search(String mer_name){
        showDialog();
        Observable<HttpResult<SerchResult>> result= ServiceApi.getInstance().getServiceContract().seach(apitoken,mer_name);
        result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SerchResult>() {
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
                    public void onNext(SerchResult serchResult) {
                        restaurantsList = serchResult.getRestaurants();
                        seceeList = serchResult.getScenes();
                        fragmentList = new ArrayList<>();
                        fragmentList.add(new RestaurantFragment(restaurantsList));
                        fragmentList.add(new AttractionsFragment(seceeList));
                        adapter = new RegisterAdapter(getSupportFragmentManager(),fragmentList);
                        register_viewpager.setAdapter(adapter);
                    }
                });
    }
}
