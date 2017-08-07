package com.example.asus.xyd_order.holder;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.activity.SelectCountry;
import com.example.asus.xyd_order.base.BaseViewHolder;
import com.example.asus.xyd_order.net.result.CountryBean;
import com.example.asus.xyd_order.ui.StateButton;

/**
 * Created by Zheng on 2017/8/7.
 */

public class Item_country_holder extends BaseViewHolder<CountryBean.CountriesBean> implements View.OnClickListener {
    Button btn_country;
    private CountryBean.CountriesBean bean;

    @Override
    public BaseViewHolder onCreateViewHolder(View v) {
        btn_country= (Button) v.findViewById(R.id.btn_country);
        return this;
    }

    @Override
    public void onBindViewHolder(Context context, CountryBean.CountriesBean s) {
        bean = s;
        btn_country.setText(s.getRegion_name());
        if (s.getState() == 1){
            btn_country.setBackgroundColor(context.getResources().getColor(R.color.tool_bar_color));
            btn_country.setTextColor(context.getResources().getColor(R.color.white));
        }else if (s.getState() == 0){
            btn_country.setBackgroundColor(context.getResources().getColor(R.color.white));
            btn_country.setTextColor(context.getResources().getColor(R.color.material_grey_700));
        }
        btn_country.setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_country;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_country:
                if (bean.getState() == 1){
                    bean.setState(0);
                }else if (bean.getState() == 0){
                    bean.setState(1);
                }
                SelectCountry.instance.notifyAdapter();
                break;
        }
    }
}
