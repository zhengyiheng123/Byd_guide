package com.example.asus.xyd_order.adapter;

import android.content.Context;
import android.media.Rating;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.net.BaseApi;
import com.example.asus.xyd_order.net.result.Collections;
import com.example.asus.xyd_order.ui.SmartImageveiw;

import java.util.List;

/**
 * Created by Zheng on 2017/7/20.
 */

public class MyCollectMultipleAdapter extends BaseAdapter {
    private List<Collections.CollectionsBean> mList;
    private int typeCount;
    //餐厅
    private static int RESTAURANT=0;
    //景点
    private static int SCENE=1;

    Context context;
    public MyCollectMultipleAdapter(Context context, List<Collections.CollectionsBean> mList, int typeCount) {
        this.mList=mList;
        this.typeCount=typeCount;
        this.context=context;
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public int getViewTypeCount() {
        return typeCount;
    }

    @Override
    public int getItemViewType(int position) {
        if (!TextUtils.isEmpty(mList.get(position).getRes_id()+"")){
            return RESTAURANT;
        }else if (!TextUtils.isEmpty(mList.get(position).getScene_id()+"")){
            return SCENE;
        }else {
            return 0;
        }
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v=null;
        int type=getItemViewType(i);
        if(type == RESTAURANT){
            v=restaurant(i,view);
        }else if (type == SCENE){
            v=scene(i,view);
        }
        return v;
    }


    private View restaurant(int i, View view) {
        HolderRestaurant restaurantHolder=null;
        if (view==null){
            restaurantHolder=new HolderRestaurant();
            view= LayoutInflater.from(context).inflate(R.layout.item_zhongcan,null);
            restaurantHolder.iv_img= (SmartImageveiw) view.findViewById(R.id.iv_img);
            restaurantHolder.iv_img.setRatio(1.5f);
            restaurantHolder.rb_stars= (RatingBar) view.findViewById(R.id.rb_stars);
            restaurantHolder.tv_distance= (TextView) view.findViewById(R.id.tv_distance);
            restaurantHolder.tv_distance.setVisibility(View.GONE);
            restaurantHolder.tv_range= (TextView) view.findViewById(R.id.tv_range);
            restaurantHolder.tv_peer= (TextView) view.findViewById(R.id.tv_peer);
            restaurantHolder.tv_name= (TextView) view.findViewById(R.id.tv_name);
            view.setTag(restaurantHolder);
        }else {
            restaurantHolder= (HolderRestaurant) view.getTag();
        }
        Glide.with(context).load(BaseApi.getBaseUrl()+mList.get(i).getLogo_path()).into(restaurantHolder.iv_img);
        if (!TextUtils.isEmpty(mList.get(i).getRank())){
            restaurantHolder.rb_stars.setRating(Integer.valueOf(mList.get(i).getRank()));
        }
        restaurantHolder.tv_name.setText(mList.get(i).getRes_name());
        restaurantHolder.tv_peer.setText("人均消费："+mList.get(i).getAvg_cost()+"人");
        if (!TextUtils.isEmpty(mList.get(i).getMeal_type())){
            int meal_type= Integer.valueOf(mList.get(i).getMeal_type());
            switch (meal_type){
                case 1:
                    restaurantHolder.tv_range.setText("营业范围：团餐");
                    break;
                case 2:
                    restaurantHolder.tv_range.setText("营业范围：单点");
                    break;
                case 3:
                    restaurantHolder.tv_range.setText("营业范围：团餐+单点");
                    break;
            }
        }
        return view;
    }
    private View scene(int i, View view) {
        HolderSCENE sceneHolder=null;
        if (view==null){
            sceneHolder=new HolderSCENE();
            view= LayoutInflater.from(context).inflate(R.layout.item_attractions,null);
            sceneHolder.iv_img= (SmartImageveiw) view.findViewById(R.id.iv_img);
            sceneHolder.iv_img.setRatio(1.5f);
            sceneHolder.tv_range= (TextView) view.findViewById(R.id.tv_range);
            sceneHolder.tv_peer= (TextView) view.findViewById(R.id.tv_peer);
            sceneHolder.tv_name= (TextView) view.findViewById(R.id.tv_name);
            sceneHolder.tv_distance_of_you= (TextView) view.findViewById(R.id.tv_distance_of_you);
            sceneHolder.tv_distance_of_you.setVisibility(View.GONE);
            view.setTag(sceneHolder);
        }else {
            sceneHolder= (HolderSCENE) view.getTag();
        }
        Glide.with(context).load(BaseApi.getBaseUrl()+mList.get(i).getLogo_path()).into(sceneHolder.iv_img);
        sceneHolder.tv_name.setText(mList.get(i).getScene_name());
        sceneHolder.tv_peer.setText("门票："+mList.get(i).getAvg_cost()+"/人起");
        return view;
    }
    class HolderRestaurant{
        SmartImageveiw iv_img;
        RatingBar rb_stars;
        TextView tv_range,tv_peer,tv_name,tv_distance;
    }

    class HolderSCENE{
        SmartImageveiw iv_img;
        TextView tv_name,tv_range,tv_peer,tv_distance_of_you;
    }
}
