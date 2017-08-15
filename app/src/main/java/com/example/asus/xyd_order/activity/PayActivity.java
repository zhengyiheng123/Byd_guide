package com.example.asus.xyd_order.activity;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.net.BaseApi;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.net.result.MyOrderBean;
import com.example.asus.xyd_order.net.result.PayParam;
import com.example.asus.xyd_order.net.result.PayResult;
import com.example.asus.xyd_order.ui.CircleImageView;

import java.util.Map;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/8/4.
 */

public class PayActivity extends BaseActivity {
    private static final int SDK_PAY_FLAG = 1;
    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.rl_zhifubao)
    RelativeLayout rl_zhifubao;

    @Bind(R.id.iv_head)
    CircleImageView iv_head;

    @Bind(R.id.tv_price)
    TextView tv_price;

    @Bind(R.id.tv_name)
    TextView tv_name;

    @Bind(R.id.btn_pay)
    Button btn_pay;
    @Bind(R.id.cb_check)
    CheckBox cb_check;
    private MyOrderBean.OrdersBean bean;
    private PayParam payParam1;
    Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case SDK_PAY_FLAG:
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(PayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        });
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(PayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();

                    }
                    break;
            }
        }
    };
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            PayTask payTask=new PayTask(PayActivity.this);
            Map<String, String> result=payTask.payV2(payParam1.getOrder_string(),true);
            Message msg = new Message();
            msg.what = SDK_PAY_FLAG;
            msg.obj = result;
            mHandler.sendMessage(msg);
        }
    };

    @Override
    public void myOnclick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                break;
            case R.id.rl_zhifubao:
                if (cb_check.isChecked()){
                    cb_check.setChecked(false);
                }else {
                    cb_check.setChecked(true);
                }
                break;
            case R.id.btn_pay:
                if (!cb_check.isChecked()){
                    toastShow("请选择支付方式");
                    return;
                }
                Thread payThread = new Thread(runnable);
                payThread.start();
                break;
        }
    }

    @Override
    public void setToolbar() {
        tv_title.setText("支付订单");
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_pay_activity;
    }

    @Override
    public int getData() throws Exception {
        bean = (MyOrderBean.OrdersBean) getIntent().getSerializableExtra("bean");
        bean.toString();
        getNetData();
        return 0;
    }

    @Override
    public void initView() {

    }

    @Override
    public void setEvent() {
        iv_back.setOnClickListener(this);
        rl_zhifubao.setOnClickListener(this);
        btn_pay.setOnClickListener(this);
    }
    /**
     * 请求网络参数
     */
    private void getNetData(){
        //支付方式 2|支付宝 3|微信 4|银联
        Observable<HttpResult<PayParam>> result= ServiceApi.getInstance().getServiceContract().payParam(apitoken,bean.getOrd_type()+"",bean.getOrd_id()+"","2");
        result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PayParam>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                            toastShow(e.getMessage());
                    }

                    @Override
                    public void onNext(PayParam payParam) {
                        payParam1 = payParam;
                        Glide.with(context).load(BaseApi.getBaseUrl()+bean.getLogo_path()).into(iv_head);
                        tv_name.setText(bean.getMer_name()+bean.getRoute_name());
                        tv_price.setText(payParam.getPrice());
                        btn_pay.setText("支付"+payParam.getPrice());
                    }
                });
    }
}
