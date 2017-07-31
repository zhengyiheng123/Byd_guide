package com.example.asus.xyd_order.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.base.BaseArrayAdapter;
import com.example.asus.xyd_order.holder.CityHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zheng on 2017/5/2.
 */

public class CityActivity extends BaseActivity {

    private ListView lv_city;
    private List<String> mList;

    @Override
    public void myOnclick(View view) {

    }

    @Override
    public void setToolbar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(v -> onBackPressed());
        TextView tv_title= (TextView) findViewById(R.id.tv_title);
        tv_title.setText("城市选择");
    }

    @Override
    protected int getResourceId() {
        return R.layout.axctivity_city;
    }


    @Override
    public int getData() throws Exception {
        mList = new ArrayList<>();
        mList.add("巴黎");
        mList.add("希斯罗");
        mList.add("巴塞罗那");
        mList.add("耶路撒冷");
        mList.add("巴黎");
        mList.add("希斯罗");
        mList.add("巴塞罗那");
        mList.add("耶路撒冷");
        mList.add("巴黎");
        mList.add("希斯罗");
        mList.add("巴塞罗那");
        mList.add("耶路撒冷");
        return 0;
    }

    @Override
    public void initView() {
        lv_city = (ListView) findViewById(R.id.lv_city);
        lv_city.setAdapter(new BaseArrayAdapter<>(context, new BaseArrayAdapter.OnCreateViewHolderListener() {
            @Override
            public Object onCreateViewHolder() {
                return new CityHolder();
            }
        },mList));
    }

    @Override
    public void setEvent() {

    }
}
