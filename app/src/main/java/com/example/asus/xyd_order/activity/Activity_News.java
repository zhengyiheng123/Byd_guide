package com.example.asus.xyd_order.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.base.BaseArrayAdapter;
import com.example.asus.xyd_order.base.BaseViewHolder;
import com.example.asus.xyd_order.entity.MyorderEntity;
import com.example.asus.xyd_order.holder.Category_Home_Holder;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.net.result.NoticeBean;
import com.example.asus.xyd_order.refresh.widget.swipetorefresh.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/3/13.
 */
public class Activity_News extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,RefreshLayout.OnLoadListener {

    private ListView lv_my_news;
    //数据集合
    private List<NoticeBean.NoticesBean> mList = new ArrayList<>();
    private BaseArrayAdapter<MyorderEntity, BaseViewHolder> arrayAdapter;
    private RefreshLayout refresh;

    //正在刷新
    private boolean isRefresh;
    //页码
    private int page = 0;

    @Override
    public void myOnclick(View view) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        onRefresh();
    }

    @Override
    public void setToolbar() {
        ImageView iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(v -> {
            onBackPressed();
        });
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("我的预定最新通知");
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_mynews;
    }


    @Override
    public int getData() throws Exception {
        return 0;
    }

    @Override
    public void initView() {
        //初始化ListvIew
        initListView();
    }

    private void initListView() {
        refresh = (RefreshLayout) findViewById(R.id.refresh);
        lv_my_news = (ListView) findViewById(R.id.lv_my_news);
        arrayAdapter = new BaseArrayAdapter(context, () -> {
            return new Category_Home_Holder(context);
        }, mList);
        refresh.setAdapter(arrayAdapter, lv_my_news);
    }

    @Override
    public void setEvent() {
        refresh.setOnLoadListener(this);
        refresh.setOnRefreshListener(this);
    }

    /**
     * 请求网络数据
     */
    private void getNetData(int page) {
        showDialog();
        Observable<HttpResult<NoticeBean>> result = ServiceApi.getInstance().getServiceContract().notice(apitoken, "1", page + "");
        result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<NoticeBean>() {
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
                    public void onNext(NoticeBean noticeBean) {
                        if (noticeBean != null) {
                            if (noticeBean.getNotices() == null || noticeBean.getNotices().size() == 0) {
                                refresh.onLoadFinish();
                            }
                            if (isRefresh) {
                                mList.clear();
                                mList.addAll(noticeBean.getNotices());
                            } else {
                                mList.addAll(noticeBean.getNotices());
                            }
                            arrayAdapter.notifyDataSetChanged();
                            refresh.setLoading(true);
                            refresh.setRefreshing(false);
                        }
                    }
                });
    }
    @Override
    public void onRefresh() {
        isRefresh=true;
        page=0;
        getNetData(page);
    }

    @Override
    public void onLoad() {
        isRefresh=false;
        page=page+1;
        getNetData(page);
    }
}
