package com.example.asus.xyd_order.fragment;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseArrayAdapter;
import com.example.asus.xyd_order.base.BaseFragment;
import com.example.asus.xyd_order.holder.MyCommentsHolder;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.CommentRecords;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.ui.MyListView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/3/10.
 */
public class ReceivedCommentsFragment extends BaseFragment {

    private MyListView lv_my_comments;
    private List<CommentRecords.CommentsBean> mlist;
    private BaseArrayAdapter adapter;

    @Override
    public void myOnclick(View view) {

    }

    @Override
    public void initView(View v) {
        lv_my_comments = (MyListView) v.findViewById(R.id.lv_receive_comments);
        TextView tv_empty= (TextView) v.findViewById(R.id.tv_empty);
        lv_my_comments.setEmptyView(tv_empty);
        adapter = new BaseArrayAdapter(context, new BaseArrayAdapter.OnCreateViewHolderListener() {
            @Override
            public Object onCreateViewHolder() {
                return new MyCommentsHolder();
            }
        }, mlist);
        lv_my_comments.setAdapter(adapter);
    }

    @Override
    public int getResource() {
        return R.layout.fragment_receive;
    }

    @Override
    public int initData() throws Exception {
        mlist = new ArrayList<>();
        getNetData();
        return 0;
    }

    @Override
    public void setEvent() {

    }


    /**
     * 请求网络数据
     */
    private void getNetData(){
        Observable<HttpResult<CommentRecords>> result= ServiceApi.getInstance().getServiceContract().commentRecords(apitoken,2);
        result.map(new ResultFilter<>() )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CommentRecords>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CommentRecords commentRecords) {
                        mlist.addAll(commentRecords.getComments());
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}
