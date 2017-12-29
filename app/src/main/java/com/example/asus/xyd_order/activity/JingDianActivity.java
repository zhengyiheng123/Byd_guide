package com.example.asus.xyd_order.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.base.BaseArrayAdapter;
import com.example.asus.xyd_order.holder.AttractionsHolder;
import com.example.asus.xyd_order.holder.ZhongcanHolder;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.net.result.JingdianBean;
import com.example.asus.xyd_order.net.result.RestaurantBean;
import com.example.asus.xyd_order.utils.ActivityFactory;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnItemClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/10/23.
 */

public class JingDianActivity extends BaseActivity {
    @Bind(R.id.iv_back)ImageView ivBack;
    @Bind(R.id.auto_text)AutoCompleteTextView autoText;
    @Bind(R.id.tv_search)TextView tv_search;
    @Bind(R.id.lv_searchresult)ListView mListView;

    @Bind(R.id.tv_empty)TextView mEmpty;
    private BaseArrayAdapter adapter;
    List<JingdianBean.ScenesBean> mList=new ArrayList<>();

    @OnClick({R.id.iv_back,R.id.tv_search})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_search:
                String text=autoText.getText().toString();
                getZhongcanInfos(text);
                break;
        }
    }
    @OnItemClick(R.id.lv_searchresult)
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
        switch (mList.get(i).getSub_cate_id()){
            case 6:
                ActivityFactory.gotoAttractionDetails(context,mList.get(i).getScene_id()+"");
                break;
            case 5:
                ActivityFactory.gotoAttractionsNomal(context,mList.get(i).getScene_id());
                break;
            case 7:
                ActivityFactory.gotoAttractionTicket(context,mList.get(i).getScene_id());
                break;
        }
    }
    @Override
    public void myOnclick(View view) {
    }

    @Override
    public void setToolbar() {

    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_searchcanting;
    }

    @Override
    public int getData() throws Exception {
        return 0;
    }
    public void getZhongcanInfos(String key_word){
        Observable<HttpResult<JingdianBean>> result= ServiceApi.getInstance().getServiceContract().findJingdian(apitoken,key_word);
        result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JingdianBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(JingdianBean bean) {
                        mList.clear();
                        if (bean.getScenes().size()>0){
                            mList.addAll(bean.getScenes());
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }
    @Override
    public void initView() {
        mListView.setEmptyView(mEmpty);
        adapter = new BaseArrayAdapter(context, new BaseArrayAdapter.OnCreateViewHolderListener() {
            @Override
            public Object onCreateViewHolder() {
                return new AttractionsHolder();
            }
        },mList);
        mListView.setAdapter(adapter);
    }

    @Override
    public void setEvent() {
    }
}
