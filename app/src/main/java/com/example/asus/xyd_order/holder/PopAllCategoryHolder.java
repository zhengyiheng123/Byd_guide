package com.example.asus.xyd_order.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseViewHolder;
import com.example.asus.xyd_order.net.result.CategoryBean;

/**
 * Created by Zheng on 2017/2/23.
 */
public class PopAllCategoryHolder extends BaseViewHolder<CategoryBean.SubCategoriesBean> {

    private TextView tv_name;

    @Override
    public BaseViewHolder onCreateViewHolder(View v) {
        tv_name = (TextView) v.findViewById(R.id.tv_name);
        return this;
    }

    @Override
    public void onBindViewHolder(Context context, CategoryBean.SubCategoriesBean s) {
        tv_name.setText(s.getCate_name());
        if (s.getState() == 1){
            tv_name.setTextColor(context.getResources().getColor(R.color.tool_bar_color));
        }else if (s.getState() == 0){
            tv_name.setTextColor(context.getResources().getColor(R.color.material_grey_700));
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_pop_allcategory;
    }
}
