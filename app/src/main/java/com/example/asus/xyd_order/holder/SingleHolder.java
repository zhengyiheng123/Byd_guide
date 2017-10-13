package com.example.asus.xyd_order.holder;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.activity.SingleChooseActivity;
import com.example.asus.xyd_order.base.BaseViewHolder;
import com.example.asus.xyd_order.net.BaseApi;
import com.example.asus.xyd_order.net.result.RestaurantDetailsBean;

/**
 * Created by Zheng on 2017/2/24.
 */
public class SingleHolder extends BaseViewHolder<RestaurantDetailsBean.SingleMealBean> implements View.OnClickListener{

    private ImageView iv_jia,iv_jian,iv_img;
    private TextView tv_count,tv_name,tv_peer,tv_price;
    private String num;
    private RestaurantDetailsBean.SingleMealBean bean;

    @Override
    public BaseViewHolder onCreateViewHolder(View v) {
        iv_jian = (ImageView) v.findViewById(R.id.iv_jian);
        iv_jia = (ImageView) v.findViewById(R.id.iv_jia);
        tv_count = (TextView) v.findViewById(R.id.tv_count);
        iv_img= (ImageView) v.findViewById(R.id.iv_img);
        tv_name= (TextView) v.findViewById(R.id.tv_name);
        tv_price= (TextView) v.findViewById(R.id.tv_price);
        tv_peer= (TextView) v.findViewById(R.id.tv_peer);
        iv_jia.setOnClickListener(this);
        iv_jian.setOnClickListener(this);
        return this;
    }

    @Override
    public void onBindViewHolder(Context context, RestaurantDetailsBean.SingleMealBean s) {
        bean = s;
            tv_name.setText(s.getMeal_name());
            tv_peer.setText(s.getMeal_weight()+"/克重");
            tv_price.setText(s.getMeal_price()+"元");
        Glide.with(context).load(BaseApi.getBaseUrl()+s.getImg_path()).into(iv_img);

    }

    @Override
    public int getLayoutId() {
        return R.layout.item_zhongcandetails_multiple2;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_jian:
                num = tv_count.getText().toString().trim();
                if (TextUtils.isEmpty(num)){
                    return;
                }else {
                    int count=Integer.valueOf(num);
                    if (count>0){
                        count=count-1;
                        tv_count.setText(count+"");
                        bean.setNums(count);
                    }else {
                        return;
                    }
                }
                SingleChooseActivity.instance.countPrice();
                break;
            case R.id.iv_jia:
                num = tv_count.getText().toString().trim();
                if (TextUtils.isEmpty(num)){
                    tv_count.setText("1");
                }else {
                    int count=Integer.valueOf(num);
                    count=count+1;
                    tv_count.setText(count+"");
                    bean.setNums(count);
                }
                SingleChooseActivity.instance.countPrice();
                break;
        }
    }
}
