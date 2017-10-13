package com.example.asus.xyd_order.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.base.BaseArrayAdapter;
import com.example.asus.xyd_order.dialog.ConfirmDialog;
import com.example.asus.xyd_order.holder.Help_News_Holder;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.net.result.MutualBean;
import com.example.asus.xyd_order.utils.ActivityFactory;
import com.example.asus.xyd_order.utils.SharedPreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/3/13.
 */
public class Activity_Help_News extends BaseActivity
{
    @Bind(R.id.lv_help_news)
    ListView lv_help_news;

    private List<MutualBean.MutualMessagesBean> mList=new ArrayList<>();
    @Bind(R.id.tv_submit)
    TextView tv_submit;
    private BaseArrayAdapter adapter;
    @Bind(R.id.tv_title)
    TextView tv_title;

    @Override
    public void myOnclick(View view) {
        switch (view.getId()){
            case R.id.tv_submit:
                int state= (int) SharedPreferenceUtils.getParam(context,LoginActivity.CONFIRM_STATE,0);
                if (state == 2){
                    ActivityFactory.gotoAddHelpNews(context);
                }else {
                    ConfirmDialog dialog=new ConfirmDialog(Activity_Help_News.this);
                }
                break;
        }
    }

    @Override
    public void setToolbar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(v -> {onBackPressed();});
        tv_submit.setVisibility(View.VISIBLE);
        tv_submit.setText("发表");
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_help_news;
    }


    @Override
    public int getData() throws Exception {
        getNetData();
        return 0;
    }

    @Override
    public void initView() {
        initListView();
    }

    private void initListView() {
        tv_title.setText("业内互助消息");
        adapter = new BaseArrayAdapter(context, new BaseArrayAdapter.OnCreateViewHolderListener() {
            @Override
            public Object onCreateViewHolder() {
                return new Help_News_Holder();
            }
        },mList);
        lv_help_news.setAdapter(adapter);
    }

    @Override
    public void setEvent() {
        tv_submit.setOnClickListener(this);
    }

    /**
     * 获取网络信息
     */
    private void getNetData(){
        Observable<HttpResult<MutualBean>> result= ServiceApi.getInstance().getServiceContract().getMutualData(apitoken);
        result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MutualBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        toastShow(e.getMessage());
                    }

                    @Override
                    public void onNext(MutualBean mutualBean) {
                        mList.clear();
                        mList.addAll(mutualBean.getMutual_messages());
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}
