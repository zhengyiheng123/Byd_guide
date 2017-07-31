package com.example.asus.xyd_order.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.net.result.JingDianDetails;

import butterknife.Bind;
import rx.Observable;

/**
 * Created by Zheng on 2017/7/21.
 */

public class Activity_Attractiongs_Details extends BaseActivity {
    @Bind(R.id.tv_comment)
    TextView tv_comment;
    @Bind(R.id.iv_img)
    ImageView iv_img;
    @Bind(R.id.tv_res_name)
    TextView tv_res_name;
    @Bind(R.id.child)
    TextView child;
    @Bind(R.id.tv_address)
    TextView tv_address;
    @Bind(R.id.tv_mobile)
    TextView tv_mobile;
    @Bind(R.id.tv_group_num)
    TextView tv_group_num;
    @Bind(R.id.tv_ord_num)
    TextView tv_ord_num;
    @Bind(R.id.tv_eat_sum)
    TextView tv_eat_sum;
    @Bind(R.id.tv_all_money)
    TextView tv_all_money;
    @Bind(R.id.tv_order_time)
    TextView tv_order_time;
    @Bind(R.id.arrive_time)
    TextView arrive_time;
    @Bind(R.id.tv_paytype)
    TextView tv_paytype;
    @Bind(R.id.tv_leave_message)
    TextView tv_leave_message;
    @Bind(R.id.tv_username)
    TextView tv_username;
    @Bind(R.id.tv_order_phone)
    TextView tv_order_phone;
    @Bind(R.id.tv_ord_status)
    TextView tv_ord_status;

    @Bind(R.id.tv_cancel)
    TextView tv_cancel;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.mylistview_single)
    ListView mylistview_single;

    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Override
    public void myOnclick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void setToolbar() {

    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_attraction_details;
    }

    @Override
    public int getData() throws Exception {
        return 0;
    }

    @Override
    public void initView() {
        tv_title.setText("详情");
    }

    @Override
    public void setEvent() {
        iv_back.setOnClickListener(this);
    }
    /**
     * 获取网络数据
     */
    private void getNetData(){
//        Observable<HttpResult<JingDianDetails>> result= ServiceApi.getInstance().getServiceContract().jingdianDetails();
    }
}
