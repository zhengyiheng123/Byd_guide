package com.example.asus.xyd_order.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseViewHolder;
import com.example.asus.xyd_order.net.result.EmbassyBean;

/**
 * Created by Zheng on 2017/3/7.
 */
public class ConsulateHolder extends BaseViewHolder<EmbassyBean.EmbassiesBean> {

    private TextView tv_embass_moblie,tv_embass_place,tv_embass_name;

    @Override
    public BaseViewHolder onCreateViewHolder(View v) {
        tv_embass_moblie = (TextView) v.findViewById(R.id.tv_embass_moblie);
        tv_embass_place= (TextView) v.findViewById(R.id.tv_embass_place);
        tv_embass_name= (TextView) v.findViewById(R.id.tv_embass_name);
        return this;
    }

    @Override
    public void onBindViewHolder(Context context, EmbassyBean.EmbassiesBean s) {
        tv_embass_name.setText(s.getEmb_name());
        tv_embass_moblie.setText(s.getEmb_phone());
        tv_embass_place.setText(s.getEmb_address());
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_consulate;
    }
}
