package com.example.asus.xyd_order.holder;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseViewHolder;
import com.example.asus.xyd_order.fragment.Fragment_All_Demand;
import com.example.asus.xyd_order.fragment.GetedFragment_All;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.net.result.MyDemandBean;
import com.example.asus.xyd_order.utils.SharedPreferenceUtils;
import com.example.asus.xyd_order.utils.TimeUtils;
import com.example.asus.xyd_order.utils.ToastUtils;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/7/6.
 */

public class OrdersGetedHolder extends BaseViewHolder<MyDemandBean.DemandsBean> implements View.OnClickListener{
    TextView tv_cancel_state,tv_tuan_order,tv_country,tv_lvxi_type,tv_sit_type,tv_level,
            tv_time,tv_ordnum,cancel,finished;

    LinearLayout linearLayout4;
    private String token;
    private MyDemandBean.DemandsBean bean;
    private Activity activity;

    private Context context;
    public OrdersGetedHolder(Activity activity){
        this.activity=activity;
    }
    @Override
    public BaseViewHolder onCreateViewHolder(View v) {
        tv_cancel_state= (TextView) v.findViewById(R.id.tv_cancel_state);
        tv_tuan_order= (TextView) v.findViewById(R.id.tv_tuan_order);
        tv_country= (TextView) v.findViewById(R.id.tv_country);
        tv_lvxi_type= (TextView) v.findViewById(R.id.tv_lvxi_type);
        tv_sit_type= (TextView) v.findViewById(R.id.tv_sit_type);
        tv_level= (TextView) v.findViewById(R.id.tv_level);
        tv_time= (TextView) v.findViewById(R.id.tv_time);
        tv_ordnum= (TextView) v.findViewById(R.id.tv_ordnum);
        cancel= (TextView) v.findViewById(R.id.cancel);
        finished= (TextView) v.findViewById(R.id.finished);
        linearLayout4= (LinearLayout) v.findViewById(R.id.linearLayout4);

        cancel.setOnClickListener(this);
        finished.setOnClickListener(this);
        return this;
    }

    @Override
    public void onBindViewHolder(Context context, MyDemandBean.DemandsBean demandsBean) {
        this.context=context;
        token = (String) SharedPreferenceUtils.getParam(context,"apitoken","");
        int ord_status=demandsBean.getOrd_status();
        bean=demandsBean;
        if (ord_status == -1){
            linearLayout4.setVisibility(View.GONE);
            tv_cancel_state.setText("我已取消");
            tv_cancel_state.setVisibility(View.VISIBLE);
        }else if (ord_status == -2){
            linearLayout4.setVisibility(View.GONE);
            tv_cancel_state.setText("发布者取消");
            tv_cancel_state.setVisibility(View.VISIBLE);
        }else if (ord_status == 0){
            linearLayout4.setVisibility(View.VISIBLE);
            tv_cancel_state.setVisibility(View.GONE);
        }else if (ord_status == 1){
            linearLayout4.setVisibility(View.VISIBLE);
            tv_cancel_state.setVisibility(View.GONE);
        }else if (ord_status == 2){
            linearLayout4.setVisibility(View.GONE);
            tv_cancel_state.setVisibility(View.VISIBLE);
            tv_cancel_state.setText("已完成");
        }else if (ord_status == 3){
            linearLayout4.setVisibility(View.GONE);
            tv_cancel_state.setVisibility(View.VISIBLE);
            tv_cancel_state.setText("已完成");
        }
        StringBuffer countrys = new StringBuffer();
        for (int i=0;i<demandsBean.getCountries().size();i++){
            countrys=countrys.append(demandsBean.getCountries().get(i));
        }
        if (demandsBean.getCountries().size()>0){
            tv_country.setText(demandsBean.getCountries().get(0)+"...");
        }else {
            tv_country.setText("不限国家");
        }
        tv_ordnum.setText("订单号:"+demandsBean.getOrd_num());
        int level=demandsBean.getLevel_req();
        switch (level){
            case 1:
                tv_level.setText("一级");
                break;
            case 2:
                tv_level.setText("二级");
                break;
            case 3:
                tv_level.setText("三级");
                break;
            case 4:
                tv_level.setText("四级");
                break;
            case 5:
                tv_level.setText("五级");
                break;
        }
        //团队性质 1|公务 2|散拼 3|自由行 4|其他
        int lvxi=demandsBean.getGroup_type();
        switch (lvxi){
            case 1:
                tv_lvxi_type.setText("公务");
                break;
            case 2:
                tv_lvxi_type.setText("散拼");
                break;
            case 3:
                tv_lvxi_type.setText("自由行");
                break;
            case 4:
                tv_lvxi_type.setText("其他");
                break;
        }
        int sittype=demandsBean.getService_type();
        //服务内容 1|找导游 2|租车 3|9座司导 4|翻译 5|其他
        switch (sittype){
            case 1:
                tv_sit_type.setText("找导游");
                break;
            case 2:
                tv_sit_type.setText("租车");
                break;
            case 3:
                tv_sit_type.setText("9座司导");
                break;
            case 4:
                tv_sit_type.setText("翻译");
                break;
            case 5:
                tv_sit_type.setText("其他");
                break;
        }
        tv_tuan_order.setText("团号："+demandsBean.getGroup_num());
        tv_time.setText("开始/结束："+ TimeUtils.stampToDateS(demandsBean.getStart_time()+"")+"——"+TimeUtils.stampToDateS(demandsBean.getEnd_time()+""));

    }

    @Override
    public int getLayoutId() {
        return R.layout.item_orders_geted;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.finished:
                android.app.AlertDialog.Builder builder1=new android.app.AlertDialog.Builder(context);
                builder1.setMessage("确认完成？");
                builder1.setNegativeButton("取消", (dialog, which) -> {dialog.dismiss();});
                builder1.setPositiveButton("确定",(dialog, which) -> finishedDemand());
                builder1.show();
                break;
            case R.id.cancel:
                AlertDialog.Builder builder=new AlertDialog.Builder(activity);
                builder.setTitle("确定取消吗");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        cancel();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
                break;
        }
    }

/**
 * 接单者取消订单
 */
private void cancel(){
    Observable<HttpResult> result= ServiceApi.getInstance().getServiceContract().getedCancel(token,bean.getDmd_id()+"");
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
                    ToastUtils.show(context,"取消成功",0);
                    activity.finish();
                }
            });
}

    /**
     * 完成订单
     */
    private void finishedDemand() {
        Observable<HttpResult> result=ServiceApi.getInstance().getServiceContract().finishDemand(token,bean.getDmd_id()+"");
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
                        activity.finish();
                    }
                });
    }

}
