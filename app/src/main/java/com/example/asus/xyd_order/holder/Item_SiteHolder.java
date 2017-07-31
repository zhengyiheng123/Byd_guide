package com.example.asus.xyd_order.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.activity.Activity_Order_Nomal;
import com.example.asus.xyd_order.activity.SiteDisplayActivity;
import com.example.asus.xyd_order.base.BaseViewHolder;
import com.example.asus.xyd_order.net.result.BaseTicketRouteBean;

/**
 * Created by Zheng on 2017/7/17.
 */

public class Item_SiteHolder extends BaseViewHolder<BaseTicketRouteBean.TicketsBean> implements View.OnClickListener{

    private TextView tv_age,tv_left,tv_num;
    private ImageView iv_release,iv_add;
    private BaseTicketRouteBean.TicketsBean bean;

    @Override
    public BaseViewHolder onCreateViewHolder(View v) {
        tv_age = (TextView) v.findViewById(R.id.tv_age);
        tv_left= (TextView) v.findViewById(R.id.tv_left);
        iv_release= (ImageView) v.findViewById(R.id.iv_release);
        iv_add= (ImageView) v.findViewById(R.id.iv_add);
        tv_num= (TextView) v.findViewById(R.id.tv_num);
        iv_add.setOnClickListener(this);
        iv_release.setOnClickListener(this);
        return this;
    }

    public Item_SiteHolder(CountTotalCount countTotalCount) {
        this.countTotalCount = countTotalCount;
    }

    @Override
    public void onBindViewHolder(Context context, BaseTicketRouteBean.TicketsBean ticketsBean) {
        bean = ticketsBean;
        tv_age.setText(ticketsBean.getTicket_type()+" "+ticketsBean.getPrice());
        tv_left.setText("余"+ticketsBean.getTicket_nums()+"张");
        tv_num.setText(ticketsBean.getNums()+"");
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_child_attract;
    }

    @Override
    public void onClick(View view) {
        int num=0;
        switch (view.getId()){
            case R.id.iv_add:
                num=Integer.valueOf(tv_num.getText().toString());
                num=num+1;
                tv_num.setText(num+"");
                bean.setNums(num);
                bean.setTotlePrice(Double.valueOf(bean.getPrice())*num);
                countTotalCount.countTotal();
                SiteDisplayActivity.instance.updateTitle();
                break;
            case R.id.iv_release:
                num=Integer.valueOf(tv_num.getText().toString());
                if (num>0){
                    num=num-1;
                    tv_num.setText(num+"");
                    bean.setNums(num);
                }else {
                    return;
                }
                bean.setTotlePrice(Double.valueOf(bean.getPrice())*num);
                countTotalCount.countTotal();
                SiteDisplayActivity.instance.updateTitle();
                break;
        }
    }
    private CountTotalCount countTotalCount;
    public interface CountTotalCount{
        void countTotal();
    }

}
