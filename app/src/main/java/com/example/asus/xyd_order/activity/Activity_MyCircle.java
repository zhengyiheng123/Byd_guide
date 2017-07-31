package com.example.asus.xyd_order.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.base.BaseArrayAdapter;
import com.example.asus.xyd_order.holder.MyCircleHolder;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.Guide_Circle_Bean;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.utils.ActivityFactory;
import com.example.asus.xyd_order.utils.SharedPreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/3/10.
 */
public class Activity_MyCircle extends BaseActivity {

    private ListView lv_mycircle;
    private List<Guide_Circle_Bean.UsersBean> mList = new ArrayList<>();;
    private BaseArrayAdapter adapter;
    private List<Guide_Circle_Bean.UsersBean> usersBeen;

    @Override
    public void myOnclick(View view) {

    }

    @Override
    public void setToolbar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(v -> {onBackPressed();});
        TextView tv_title= (TextView) findViewById(R.id.tv_title);
        tv_title.setText("导游圈");
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_mycircle;
    }


    @Override
    public int getData() throws Exception {
        getNetdata();
        return 0;
    }

    @Override
    public void initView() {
        lv_mycircle = (ListView) findViewById(R.id.lv_mycircle);
        TextView tv_empty= (TextView) findViewById(R.id.tv_empty);
        lv_mycircle.setEmptyView(tv_empty);
        adapter = new BaseArrayAdapter(context, new BaseArrayAdapter.OnCreateViewHolderListener() {
            @Override
            public Object onCreateViewHolder() {
                return new MyCircleHolder();
            }
        },mList);
        lv_mycircle.setAdapter(adapter);
    }

    @Override
    public void setEvent() {
        lv_mycircle.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent=new Intent(Activity_MyCircle.this,Activity_myguide_details.class);
            intent.putExtra("user_id",usersBeen.get(position).getUser_id()+"");
            startActivity(intent);
        });
    }
    /**
     * 获取网络数据
     */
    private void getNetdata(){
        showDialog();
        String token= (String) SharedPreferenceUtils.getParam(context,"apitoken","");
        Observable<HttpResult<Guide_Circle_Bean>> result= ServiceApi.getInstance().getServiceContract().guideCicle(token);
        result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Guide_Circle_Bean>() {
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
                    public void onNext(Guide_Circle_Bean bean) {
                        usersBeen = bean.getUsers();
                        mList.addAll(usersBeen);
                        adapter.notifyDataSetChanged();
                    }
                });

    }
}
