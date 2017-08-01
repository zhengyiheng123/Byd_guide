package com.example.asus.xyd_order.activity;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.net.result.PayResult;
import com.example.asus.xyd_order.utils.StringUtils;

import java.util.Map;

import cn.leo.photopicker.activity.DemoActivity;

/**
 * Created by Zheng on 2017/7/31.
 */

public class TestPay extends BaseActivity {

    private TextView pay;
    private static final int SDK_PAY_FLAG = 1;
    private static String ord_info="alipay_sdk=alipay-sdk-php-20161101&app_id=2017073107973503&biz_content=%7B%22body%22%3A%22%E6%88%91%E6%98%AF%E6%B5%8B%E8%AF%95%E6%95%B0%E6%8D%AE%22%2C%22subject%22%3A+%22App%E6%94%AF%E4%BB%98%E6%B5%8B%E8%AF%95%22%2C%22out_trade_no%22%3A+%2220170125test01%22%2C%22timeout_express%22%3A+%2230m%22%2C%22total_amount%22%3A+%220.01%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Fhelpd.firelord.xin%2Falipay_notify&sign_type=RSA2&timestamp=2017-08-01+11%3A08%3A59&version=1.0&sign=WW7qHTkoiJImyKs%2B4n9zMDjuy62ydnLo9mx4nyRE3MDd%2B%2BSbMTa6I2k0FfngfnrG5yRfDKkUBAnQIQ4m7zbSCR8L7I3f6rZwA7WEsortyMXzRNTcuSp7uo%2FufapirUecPE3ANNYM1kW1%2Fd9xXBDBYuq3GPTK7WSB39PwTz24MWU%3D";
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case SDK_PAY_FLAG:
                    PayResult payResult=new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(TestPay.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(TestPay.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            PayTask payTask=new PayTask(TestPay.this);
            Map<String,String> result=payTask.payV2(ord_info,true);
            Log.e("zyh",result.toString());
            Message msg = new Message();
            msg.what = SDK_PAY_FLAG;
            msg.obj = result;
            handler.sendMessage(msg);
        }
    };
    @Override
    public void myOnclick(View view) {
        switch (view.getId()){
            case R.id.pay:
                Thread thread=new Thread(runnable);
                thread.start();
                break;
        }
    }

    @Override
    public void setToolbar() {

    }

    @Override
    protected int getResourceId() {
        return R.layout.testpay;
    }

    @Override
    public int getData() throws Exception {
        return 0;
    }

    @Override
    public void initView() {
        pay = (TextView) findViewById(R.id.pay);
    }

    @Override
    public void setEvent() {
        pay.setOnClickListener(this);
    }
}
