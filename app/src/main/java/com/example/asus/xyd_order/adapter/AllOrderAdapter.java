package com.example.asus.xyd_order.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.activity.Activity_Edit_Release;
import com.example.asus.xyd_order.activity.Activity_SaveToSample;
import com.example.asus.xyd_order.activity.Activity_write_Opinion;
import com.example.asus.xyd_order.base.BaseArrayAdapter;
import com.example.asus.xyd_order.dialog.CardTypeDialog;
import com.example.asus.xyd_order.dialog.CommonDialog;
import com.example.asus.xyd_order.fragment.Fragment_Geted_Demand;
import com.example.asus.xyd_order.holder.MyOrderHolder;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.net.result.MyDemandBean;
import com.example.asus.xyd_order.utils.ActivityFactory;
import com.example.asus.xyd_order.utils.SharedPreferenceUtils;
import com.example.asus.xyd_order.utils.TimeUtils;
import com.example.asus.xyd_order.utils.ToastUtils;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/3/14.
 */
public class AllOrderAdapter extends BaseAdapter implements View.OnClickListener{
    List<MyDemandBean.DemandsBean> mList;
    Activity context;
    private final String token;
    private MyOrderHolder holder;
    private MyDemandBean.DemandsBean demandsBean;



    public AllOrderAdapter(List<MyDemandBean.DemandsBean> mList, Activity context){
        this.mList=mList;
        this.context=context;
        token = (String) SharedPreferenceUtils.getParam(context,"apitoken","");
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        holder = null;
        if (convertView == null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_all_demand,null);
            holder =new MyOrderHolder();
            holder.cancel= (TextView) convertView.findViewById(R.id.cancel);
            holder.store= (TextView) convertView.findViewById(R.id.store);
            holder.store.setTag(position);
            holder.finished= (TextView) convertView.findViewById(R.id.finished);
            holder.finished.setTag(position);
            holder.modify= (TextView) convertView.findViewById(R.id.modify);
            holder.modify.setTag(position);
            holder.republish= (TextView) convertView.findViewById(R.id.republish);
            holder.write= (TextView) convertView.findViewById(R.id.write);
            holder.write.setTag(position);
            holder.tv_country= (TextView) convertView.findViewById(R.id.tv_country);
            holder.tv_tuan_order= (TextView) convertView.findViewById(R.id.tv_tuan_order);
            holder.tv_lvxi_type= (TextView) convertView.findViewById(R.id.tv_lvxi_type);
            holder.tv_sit_type= (TextView) convertView.findViewById(R.id.tv_sit_type);
            holder.tv_level= (TextView) convertView.findViewById(R.id.tv_level);
            holder.tv_time= (TextView) convertView.findViewById(R.id.tv_time);
            holder.tv_dingdan_num= (TextView) convertView.findViewById(R.id.tv_dingdan_num);
            holder.tv_cancel_state= (TextView) convertView.findViewById(R.id.tv_cancel_state);
            holder.cancel.setTag(position);
            convertView.setTag(holder);
        }else {
            holder = (MyOrderHolder) convertView.getTag();
        }
        setGone();
        holder.cancel.setTag(position);
        switchView(holder,mList.get(position).getOrd_status());
        setData(mList.get(position),holder);
        return convertView;
    }

    private void setData(MyDemandBean.DemandsBean demandsBean,MyOrderHolder holder) {
        this.demandsBean =demandsBean;
        StringBuffer countrys = new StringBuffer();
        for (int i=0;i<demandsBean.getCountries().size();i++){
            countrys=countrys.append(demandsBean.getCountries().get(i));
        }
        if (demandsBean.getCountries().size()>0){
            holder.tv_country.setText(demandsBean.getCountries().get(0)+"...");
        }else {
            holder.tv_country.setText("不限国家");
        }
        holder.tv_dingdan_num.setText("订单号："+demandsBean.getOrd_num());
        int level=demandsBean.getLevel_req();
        int ord_status=demandsBean.getOrd_status();
        if (ord_status == -1){
            holder.tv_cancel_state.setText("接受者取消");
            holder.tv_cancel_state.setVisibility(View.VISIBLE);
        }else if (ord_status == -2){
            holder.tv_cancel_state.setText("发布者取消");
            holder.tv_cancel_state.setVisibility(View.VISIBLE);
        }
        switch (level){
            case 0:
                holder.tv_level.setText("无级别");
                break;
            case 1:
                holder.tv_level.setText("一级");
                break;
            case 2:
                holder.tv_level.setText("二级");
                break;
            case 3:
                holder.tv_level.setText("三级");
                break;
            case 4:
                holder.tv_level.setText("四级");
                break;
            case 5:
                holder.tv_level.setText("五级");
                break;
        }
        //团队性质 1|公务 2|散拼 3|自由行 4|其他
        int lvxi=demandsBean.getGroup_type();
        switch (lvxi){
            case 1:
                holder.tv_lvxi_type.setText("公务");
                break;
            case 2:
                holder.tv_lvxi_type.setText("散拼");
                break;
            case 3:
                holder.tv_lvxi_type.setText("自由行");
                break;
            case 4:
                holder.tv_lvxi_type.setText("其他");
                break;
        }
        int sittype=demandsBean.getService_type();
        //服务内容 1|找导游 2|租车 3|9座司导 4|翻译 5|其他
        switch (sittype){
            case 1:
                holder.tv_sit_type.setText("找导游");
                break;
            case 2:
                holder.tv_sit_type.setText("租车");
                break;
            case 3:
                holder.tv_sit_type.setText("9座司导");
                break;
            case 4:
                holder.tv_sit_type.setText("翻译");
                break;
            case 5:
                holder.tv_sit_type.setText("其他");
                break;
        }
        holder.tv_tuan_order.setText("团号："+demandsBean.getGroup_num());
        holder.tv_time.setText("开始/结束："+ TimeUtils.stampToDateS(demandsBean.getStart_time()+"")+"——"+TimeUtils.stampToDateS(demandsBean.getEnd_time()+""));
    }

    private void switchView(MyOrderHolder holder,int ord_status) {
        //ord_status 订单状态 -2|发布者取消 -1|接受者取消 0|待接单 1|已被接单 2|已完成 3|已评价
        if (ord_status == 0){
            //待接单
            holder.cancel.setVisibility(View.VISIBLE);
            holder.modify.setVisibility(View.VISIBLE);
            holder.modify.setOnClickListener(this);
            holder.cancel.setOnClickListener(this);
        }else if (ord_status==1){
            //已接单
            holder.cancel.setVisibility(View.VISIBLE);
            holder.finished.setVisibility(View.GONE);
            holder.finished.setOnClickListener(this);
            holder.cancel.setOnClickListener(this);
        }else if (ord_status==3){
            //已完成
            holder.store.setVisibility(View.VISIBLE);
            holder.store.setOnClickListener(this);
        }else if (ord_status == 2){
            //已完成
            holder.write.setVisibility(View.VISIBLE);
            holder.store.setVisibility(View.VISIBLE);
            holder.store.setOnClickListener(this);
            holder.write.setOnClickListener(this);
        }else if (ord_status == -1){
            holder.tv_cancel_state.setVisibility(View.VISIBLE);
            holder.tv_cancel_state.setText("接受者取消");
        }else if (ord_status == -2){
            holder.tv_cancel_state.setVisibility(View.VISIBLE);
            holder.tv_cancel_state.setText("发布者取消");
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancel:
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setMessage("确认取消吗");
                builder.setNegativeButton("取消", (dialog, which) -> {dialog.dismiss();});
                builder.setPositiveButton("确定",(dialog, which) -> cancel(v));
                builder.show();
                break;
            case R.id.modify:
                Intent intent=new Intent(context, Activity_Edit_Release.class);
                intent.putExtra("dmd_id",mList.get((int)v.getTag()).getDmd_id()+"");
                intent.putExtra("ord_status",mList.get((int)v.getTag()).getOrd_status()+"");
                context.startActivity(intent);
                break;
            case R.id.finished:
                AlertDialog.Builder builder1=new AlertDialog.Builder(context);
                builder1.setMessage("确认完成？");
                builder1.setNegativeButton("取消", (dialog, which) -> {dialog.dismiss();});
                builder1.setPositiveButton("确定",(dialog, which) -> finishedDemand(v));
                builder1.show();
                break;
            case R.id.write:
                Intent intent1=new Intent(context, Activity_write_Opinion.class);
                intent1.putExtra("dmd_id",mList.get((int)v.getTag()).getDmd_id()+"");
                intent1.putExtra("to_user_id",mList.get((int)v.getTag()).getTo_user_id()+"");
                intent1.putExtra("commentType","10");
                context.startActivity(intent1);
                break;
            case R.id.store:
                Intent intent2=new Intent(context, Activity_SaveToSample.class);
                intent2.putExtra("dmd_id",mList.get((int)v.getTag()).getDmd_id()+"");
                context.startActivity(intent2);
                break;
        }
    }

    private void finishedDemand(View view) {
        Observable<HttpResult> result=ServiceApi.getInstance().getServiceContract().finishDemand(token,mList.get((int)view.getTag()).getDmd_id()+"");
        result.map(new ResultFilter())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.show(context,e.getMessage(),0);
                    }

                    @Override
                    public void onNext(Object o) {
                        ToastUtils.show(context,"订单已确认完成",0);
                        context.finish();
                    }
                });
    }

    private void setGone(){
    if (holder !=null){
        holder.tv_cancel_state.setVisibility(View.GONE);
        holder.republish.setVisibility(View.GONE);
        holder.modify.setVisibility(View.GONE);
        holder.finished.setVisibility(View.GONE);
        holder.store.setVisibility(View.GONE);
        holder.cancel.setVisibility(View.GONE);
        holder.write.setVisibility(View.GONE);
    }
}

    /**
     * 取消需求订单
     */
    private void cancel(View view){
//        ToastUtils.show(context,view.getTag()+"",0);
        String dmd_id=mList.get((int)view.getTag()).getDmd_id()+"";
        Observable<HttpResult> result= ServiceApi.getInstance().getServiceContract().cancelDemand(token,dmd_id);
        result.map(new ResultFilter())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.show(context,e.getMessage(),0);
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onNext(Object o) {
                        ToastUtils.show(context,"取消成功",0);
                        Fragment_Geted_Demand.instance.getNetData();
                    }
                });
    }
}
