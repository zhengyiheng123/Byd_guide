package com.example.asus.xyd_order.holder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.activity.Activity_Sample_Details;
import com.example.asus.xyd_order.activity.History_Activity;
import com.example.asus.xyd_order.base.BaseViewHolder;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.History_Mode;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.utils.SharedPreferenceUtils;
import com.example.asus.xyd_order.utils.TimeUtils;
import com.example.asus.xyd_order.utils.ToastUtils;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/6/26.
 */

public class HistoryHolder extends BaseViewHolder<History_Mode.SamplesBean> implements View.OnClickListener{

    private TextView tv_tuan_order;
    private TextView tv_country;
    private TextView tv_lvxi_type;
    private TextView tv_sit_type;
    private TextView tv_level;
    private TextView tv_time,cancel,republish;
    private Context context;
    private History_Mode.SamplesBean bean;
    private String apitoken;

    @Override
    public BaseViewHolder onCreateViewHolder(View v) {
        tv_tuan_order = (TextView) v.findViewById(R.id.tv_tuan_order);
        tv_country = (TextView) v.findViewById(R.id.tv_country);
        tv_lvxi_type = (TextView) v.findViewById(R.id.tv_lvxi_type);
        tv_sit_type = (TextView) v.findViewById(R.id.tv_sit_type);
        tv_level = (TextView) v.findViewById(R.id.tv_level);
        tv_time = (TextView) v.findViewById(R.id.tv_time);
        cancel= (TextView) v.findViewById(R.id.cancel);
        republish= (TextView) v.findViewById(R.id.republish);
        return this;
    }

    @Override
    public void onBindViewHolder(Context context, History_Mode.SamplesBean o) {
        this.context=context;
        apitoken = (String) SharedPreferenceUtils.getParam(context,"apitoken","");
        bean=o;
        tv_tuan_order.setText("团号："+o.getGroup_num());
        cancel.setOnClickListener(this);
        republish.setOnClickListener(this);
        StringBuffer countrys = new StringBuffer();
        for (int i=0;i<o.getCountries().size();i++){
            countrys=countrys.append(o.getCountries().get(i));
        }
        if (o.getCountries().size()>0){
            tv_country.setText(o.getCountries().get(0)+"...");
        }else {
            tv_country.setText("不限国家");
        }
        int level=o.getLevel_req();
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
        int lvxi=o.getGroup_type();
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
        int sittype=o.getService_type();
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
        tv_time.setText("开始/结束:"+ TimeUtils.stampToDateS(o.getStart_time()+"")+"——"+TimeUtils.stampToDateS(o.getEnd_time()+""));
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_histor;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cancel:
                deletHistory();
                break;
            case R.id.republish:
                Intent intent=new Intent(context, Activity_Sample_Details.class);
                intent.putExtra("ds_id",bean.getDs_id()+"");
                context.startActivity(intent);
                break;
        }
    }

    /**
     * 删除需求订单
     */
    private void deletHistory(){
        Observable<HttpResult> result= ServiceApi.getInstance().getServiceContract().deleteHistory(bean.getDs_id()+"",apitoken);
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
                        ToastUtils.show(context,"删除成功",0);
                        History_Activity.instance.getNetData();
                    }
                });
    }
}
