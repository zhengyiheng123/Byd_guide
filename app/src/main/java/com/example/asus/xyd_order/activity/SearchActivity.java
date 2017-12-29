package com.example.asus.xyd_order.activity;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.adapter.RegisterAdapter;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.base.BaseArrayAdapter;
import com.example.asus.xyd_order.base.BaseFragment;
import com.example.asus.xyd_order.db.DBAdapter;
import com.example.asus.xyd_order.fragment.AttractionsFragment;
import com.example.asus.xyd_order.fragment.RestaurantFragment;
import com.example.asus.xyd_order.holder.CityHistoryHolder;
import com.example.asus.xyd_order.holder.CityHolder;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.CityListBean;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.net.result.RegionsBean;
import com.example.asus.xyd_order.net.result.SerchResult;
import com.example.asus.xyd_order.ui.ChildViewPager;
import com.example.asus.xyd_order.ui.FlowLayout;
import com.example.asus.xyd_order.ui.SelectPopWindow;
import com.example.asus.xyd_order.utils.Myutils;
import com.example.asus.xyd_order.utils.ToastUtils;

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
    AutoCompleteTextView auto_text;
    @Bind(R.id.tv_search)
    TextView tv_search;
    @Bind(R.id.flow_history)
    FlowLayout flow_history;
    @Bind(R.id.iv_delete)
    ImageView iv_delete;
    @Bind(R.id.tv_insert)
    TextView tv_insert;
    @Bind(R.id.tv_query)
    TextView tv_query;

    List<RegionsBean> cityListHistory=new ArrayList<>();
    private List<RegionsBean> cityList;
    private DBAdapter db;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (db!=null){
            db.close();
        }
    }

    @Override
    public void myOnclick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_search:
                if (!TextUtils.isEmpty(auto_text.getText().toString())){

                }else {
                }
                break;
            case R.id.iv_delete:
                cityList.clear();
                flow_history.removeAllViews();
                addView();
                db.deleteAll();
                break;
            case R.id.tv_insert:
                ToastUtils.showShort(context,db.insert(2,"俄罗斯")+"");
                break;
            case R.id.tv_query:
                List<RegionsBean> mList=db.getAllContacts();
                ToastUtils.show(context,"名字："+mList.get(0).getRegion_name(),0);
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
        db = new DBAdapter(this);
        db.open();
        initListPopwindow();
        for (int i=0;i<10;i++){
//            cityListHistory.add(new RegionsBean(1,"法国苏黎世",0));
        }
        addView();
    }

    @Override
    public void setEvent() {
        tv_search.setOnClickListener(this);
        iv_delete.setOnClickListener(this);
        tv_insert.setOnClickListener(this);
        tv_query.setOnClickListener(this);
    }
    //初始化下拉选择框
    private void initListPopwindow() {
        cityList = new ArrayList<>();
        for (int i=0;i<10;i++){
            if (i == 0){
//                cityList.add(new RegionsBean(1,"离我最近",2));
                continue;
            }
//            cityList.add(new RegionsBean(1,"法国",2));
        }

        String[] citys=new String[]{"法国","日本","俄罗斯","法兰西","发布拉诺","俄国","尼日利亚"};
        ArrayAdapter adapter=new ArrayAdapter(context,R.layout.simpe_text,citys);
        auto_text.setAdapter(adapter);
    }

    //循环添加子控件
    private void addView(){
        for (int i=0;i<cityList.size();i++){
            ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(Myutils.Dp2Px(context,8),Myutils.Dp2Px(context,8),Myutils.Dp2Px(context,8),Myutils.Dp2Px(context,8));
            TextView textView=new TextView(context);
            textView.setPadding(Myutils.Dp2Px(context,12),Myutils.Dp2Px(context,4),Myutils.Dp2Px(context,12),Myutils.Dp2Px(context,4));
            textView.setTextColor(getResources().getColor(R.color.white));
            textView.setText(cityList.get(i).getRegion_name());
            textView.setGravity(Gravity.CENTER);
            textView.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_next));
            flow_history.addView(textView,lp);
        }
    }
}
