package com.example.asus.xyd_order.holder;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.activity.CatchOrdersActivity;
import com.example.asus.xyd_order.activity.LoginActivity;
import com.example.asus.xyd_order.base.BaseViewHolder;
import com.example.asus.xyd_order.dialog.CatchSeccessDialog;
import com.example.asus.xyd_order.dialog.ConfirmDialog;
import com.example.asus.xyd_order.fragment.CatchOrdersFragment;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.net.result.TakingOrderBean;
import com.example.asus.xyd_order.utils.SharedPreferenceUtils;
import com.example.asus.xyd_order.utils.TimeUtils;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/2/22.
 */
public class CatchViewHolder extends BaseViewHolder<TakingOrderBean.DemandsBean> implements View.OnClickListener{

    private final String apitoken;
    private TextView tv_time,tv_ord_num,tv_level,tv_car_type,tv_service_type,tv_country,tv_catch,tv_groupnum;
    private CatchSeccessDialog seccessDialog;
    private Activity activity;
    private TakingOrderBean.DemandsBean bean;

    public CatchViewHolder(Activity activity){
        this.activity=activity;
        apitoken = (String) SharedPreferenceUtils.getParam(activity,"apitoken","");
    }
    @Override
    public BaseViewHolder onCreateViewHolder(View v) {
//        tv_test = (TextView) v.findViewById(R.id.tv_test);
        tv_time= (TextView) v.findViewById(R.id.tv_time);
        tv_ord_num= (TextView) v.findViewById(R.id.tv_ord_num);
        tv_level= (TextView) v.findViewById(R.id.tv_level);
        tv_car_type= (TextView) v.findViewById(R.id.tv_car_type);
        tv_service_type= (TextView) v.findViewById(R.id.tv_service_type);
        tv_country= (TextView) v.findViewById(R.id.tv_country);
        tv_catch= (TextView) v.findViewById(R.id.tv_catch);
        tv_catch.setOnClickListener(this);
        tv_groupnum= (TextView) v.findViewById(R.id.tv_groupnum);
        return this;
    }

    @Override
    public void onBindViewHolder(Context context, TakingOrderBean.DemandsBean s) {
        bean=s;
        tv_time.setText("开始/结束:"+ TimeUtils.stampToDateS(s.getStart_time()+"")+"——"+TimeUtils.stampToDateS(s.getEnd_time()+""));
        tv_ord_num.setText("订单号："+s.getOrd_num());
        tv_groupnum.setText("团号："+s.getGroup_num());
        StringBuffer countrys = new StringBuffer();
        for (int i=0;i<s.getCountries().size();i++){
            countrys=countrys.append(s.getCountries().get(i));
        }
        if (s.getCountries().size()>0){
            tv_country.setText(s.getCountries().get(0)+"...");
        }else {
            tv_country.setText("不限国家");
        }
        int level=s.getLevel_req();
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
        int lvxi=s.getGroup_type();
        switch (lvxi){
            case 1:
                tv_car_type.setText("公务");
                break;
            case 2:
                tv_car_type.setText("散拼");
                break;
            case 3:
                tv_car_type.setText("自由行");
                break;
            case 4:
                tv_car_type.setText("其他");
                break;
        }
        int sittype=s.getService_type();
        //服务内容 1|找导游 2|租车 3|9座司导 4|翻译 5|其他
        switch (sittype){
            case 1:
                tv_service_type.setText("找导游");
                break;
            case 2:
                tv_service_type.setText("租车");
                break;
            case 3:
                tv_service_type.setText("9座司导");
                break;
            case 4:
                tv_service_type.setText("翻译");
                break;
            case 5:
                tv_service_type.setText("其他");
                break;
        }
        
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_test;
    }

    /**
     * 接单
     */
    private void taking(){
        Observable<HttpResult> result= ServiceApi.getInstance().getServiceContract().taking(apitoken,bean.getDmd_id()+"");
        result.map(new ResultFilter())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (seccessDialog == null) {
                            seccessDialog = new CatchSeccessDialog(activity, R.style.MyDialog, R.mipmap.ic_catch_fail);
                            seccessDialog.show();
                        }else {
                            seccessDialog.show();
                        }
                        CatchOrdersFragment.instance.getNetData();
                    }

                    @Override
                    public void onNext(Object o) {
                        if (seccessDialog == null) {
                            seccessDialog = new CatchSeccessDialog(activity, R.style.MyDialog, R.mipmap.ic_catch_successed);
                            seccessDialog.show();
                        }else {
                            seccessDialog.show();
                        }
                        CatchOrdersFragment.instance.getNetData();
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_catch:
                int state= (int) SharedPreferenceUtils.getParam(activity, LoginActivity.CONFIRM_STATE,0);
                if (state == 2){
                    taking();
                }else {
                    ConfirmDialog dialog=new ConfirmDialog(activity);
                }
                break;
        }
    }
}
