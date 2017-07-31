package com.example.asus.xyd_order.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.base.BaseArrayAdapter;
import com.example.asus.xyd_order.holder.DrawRecordHolder;
import com.example.asus.xyd_order.utils.ActivityFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zheng on 2017/3/10.
 */
public class Activity_DrawBackRecord extends BaseActivity {

    private ListView lv_draw_record;

    @Override
    public void myOnclick(View view) {

    }

    @Override
    public void setToolbar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(v -> {onBackPressed();});
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_drawback_record;
    }


    @Override
    public int getData() throws Exception {
        return 1;
    }

    @Override
    public void initView() {
        List<String> mList=new ArrayList<>();
        mList.add("");
        mList.add("");
        mList.add("");
        lv_draw_record = (ListView) findViewById(R.id.lv_draw_record);
        lv_draw_record.setAdapter(new BaseArrayAdapter<>(context, ()->{return new DrawRecordHolder();},mList));
    }

    @Override
    public void setEvent() {
        lv_draw_record.setOnItemClickListener((parent, view, position, id) -> {
                switch (position){
                    case 0:
                        ActivityFactory.gotoDrawSuccess(context);
                        break;
                    case 1:
                        ActivityFactory.gotoDrawWaiting(context);
                        break;
                    case 2:
                        ActivityFactory.gotoDrawLoading(context);
                        break;
                }
        });
    }
}
