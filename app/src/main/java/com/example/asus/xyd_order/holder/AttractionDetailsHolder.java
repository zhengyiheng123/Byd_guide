package com.example.asus.xyd_order.holder;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.activity.Activity_Order_Nomal;
import com.example.asus.xyd_order.base.BaseViewHolder;
import com.example.asus.xyd_order.net.result.BaseTicketBean;

/**
 * Created by Zheng on 2017/3/2.
 */
public class AttractionDetailsHolder extends BaseViewHolder<BaseTicketBean> implements View.OnClickListener{

    private TextView tv_ticket_info,tv_left,tv_num;
    private ImageView iv_add;
    private ImageView iv_release;

    private BaseTicketBean bean;

    @Override
    public BaseViewHolder onCreateViewHolder(View v) {
        tv_ticket_info = (TextView) v.findViewById(R.id.tv_ticket_info);
        tv_left= (TextView) v.findViewById(R.id.tv_left);
        tv_num= (TextView) v.findViewById(R.id.tv_num);

        iv_add = (ImageView) v.findViewById(R.id.iv_add);
        iv_release = (ImageView) v.findViewById(R.id.iv_release);
        iv_add.setOnClickListener(this);
        iv_release.setOnClickListener(this);
        return this;
    }

    @Override
    public void onBindViewHolder(Context context, BaseTicketBean s) {
        bean=s;
        tv_ticket_info.setText(s.getTicket_type()+":"+s.getPrice());
        tv_left.setText("剩余:"+s.getTicket_nums()+"张");
        if (!TextUtils.isEmpty(s.getNums()+"")){
            tv_num.setText(s.getNums()+"");
        }else {
            tv_num.setText(0);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_attraction_details_price;
    }

    @Override
    public void onClick(View view) {
        int num=0;
        switch (view.getId()){
            case R.id.iv_add:
                num=Integer.valueOf(tv_num.getText().toString());
                num=num+1;
                bean.setTotalPrice(num*Double.valueOf(bean.getPrice()));
                tv_num.setText(num+"");
                bean.setNums(num);
                Activity_Order_Nomal.instance.upDataCounts();
                break;
            case R.id.iv_release:
                num=Integer.valueOf(tv_num.getText().toString());
                if (num>0){
                    num=num-1;
                    bean.setNums(num);
                    tv_num.setText(num+"");
                    bean.setTotalPrice(num*Double.valueOf(bean.getPrice()));
                }else {
                    return;
                }
                Activity_Order_Nomal.instance.upDataCounts();
                break;
        }
    }
}
