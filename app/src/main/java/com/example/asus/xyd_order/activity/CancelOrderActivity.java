package com.example.asus.xyd_order.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.base.BaseArrayAdapter;
import com.example.asus.xyd_order.holder.CancelOrderHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zheng on 2017/2/27.
 */
public class CancelOrderActivity extends BaseActivity {

    private ListView lv_reasons;
    private List<String> mList;
    private TextView tv_submit;

    @Override
    public void myOnclick(View view) {

    }

    @Override
    public void setToolbar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(v -> onBackPressed());
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_cancelorder;
    }


    @Override
    public int getData() throws Exception {
        mList = new ArrayList<>();
        mList.add("1、团队行程改变");
        mList.add("2、旅途中出现意外");
        mList.add("3、客人不愿意");
        mList.add("4、其他");
        return 1;
    }

    @Override
    public void initView() {
        tv_submit = (TextView) findViewById(R.id.tv_submit);
        tv_submit.setVisibility(View.VISIBLE);
        tv_submit.setText("忽略");


        lv_reasons = (ListView) findViewById(R.id.lv_reasons);
        lv_reasons.setAdapter(new BaseArrayAdapter<>(context, new BaseArrayAdapter.OnCreateViewHolderListener() {
            @Override
            public Object onCreateViewHolder() {
                return new CancelOrderHolder();
            }
        },mList));

    }

    @Override
    public void setEvent() {

    }
}
