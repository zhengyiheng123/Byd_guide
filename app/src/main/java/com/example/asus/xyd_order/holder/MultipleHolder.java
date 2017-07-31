package com.example.asus.xyd_order.holder;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseViewHolder;
import com.example.asus.xyd_order.net.BaseApi;
import com.example.asus.xyd_order.net.result.RestaurantDetailsBean;
import com.example.asus.xyd_order.ui.RadioGroupEx;
import com.example.asus.xyd_order.utils.ToastUtils;

/**
 * Created by Zheng on 2017/2/24.
 */
public class MultipleHolder extends BaseViewHolder<RestaurantDetailsBean.GroupMealBean> {

    private RadioGroupEx radio;
    private ImageView iv_img;
    private TextView tv_peer,tv_name;

    @Override
    public BaseViewHolder onCreateViewHolder(View v) {
        radio = (RadioGroupEx) v.findViewById(R.id.radio);
        iv_img = (ImageView) v.findViewById(R.id.iv_img);
        tv_name = (TextView) v.findViewById(R.id.tv_name);
        tv_peer= (TextView) v.findViewById(R.id.tv_peer);
        return this;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final Context context, RestaurantDetailsBean.GroupMealBean s) {
        Glide.with(context).load(BaseApi.getBaseUrl()+s.getImg_path()).into(iv_img);
        tv_name.setText(s.getMeal_name());
        tv_peer.setText(s.getMeal_price().get(0)+"/起");
//        radio.removeAllViews();
//        for (int i=0;i<s.getMeal_price().size();i++){
//            RadioButton radioButton=new RadioButton(context);
//            RadioGroup.LayoutParams params=new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            params.setMargins(8,8,8,0);
//            radioButton.setLayoutParams(params);
//            radioButton.setGravity(Gravity.CENTER);
//            radioButton.setButtonDrawable(null);
//            radioButton.setBackground(context.getDrawable(R.drawable.radiobutton_bg));
//            radioButton.setText(s.getMeal_price().get(i)+"/人");
//            radioButton.setClickable(true);
//            radioButton.setTextColor(context.getResources().getColor(R.color.radiobutton_text_tuancan));
//            radio.addView(radioButton);
//        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.item_zhongcan_details_multiple;
    }

}
