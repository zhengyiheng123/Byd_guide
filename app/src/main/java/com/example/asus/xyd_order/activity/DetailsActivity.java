package com.example.asus.xyd_order.activity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.net.BaseApi;
import com.example.asus.xyd_order.net.HttpCallBack;
import com.example.asus.xyd_order.net.HttpResponse;
import com.example.asus.xyd_order.net.HttpService;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.ServiceHttpsApi;
import com.example.asus.xyd_order.net.result.FirstResult;
import com.example.asus.xyd_order.utils.ToastUtils;

import java.io.IOException;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Zheng on 2017/2/17.
 */
public class DetailsActivity extends BaseActivity {

    private Button btn_webchat;

    @Override
    public void myOnclick(View view) {
        switch (view.getId()){
            case R.id.btn_webchat:
//                getWebChatParam();
                getWebChat();
                ToastUtils.show(getApplicationContext(),"弹出",0);
                break;
        }
    }

    @Override
    public void setToolbar() {

    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_details;
    }


    @Override
    public int getData() {
        return 1;
    }

    @Override
    public void initView() {
        btn_webchat = (Button) findViewById(R.id.btn_webchat);
    }

    @Override
    public void setEvent() {
        btn_webchat.setOnClickListener(this);
    }
    private void getWebChatParam(){
        ServiceApi.getInstance().getServiceContract().getWebChat("1",1).enqueue(new HttpCallBack<HttpResponse<FirstResult>>(this) {
            @Override
            public void onSuccess(HttpResponse<FirstResult> firstResultHttpResponse) {
                int code=firstResultHttpResponse.getCode();
                ToastUtils.show(getApplicationContext(),code+"",0);
            }

            @Override
            public void onFailure(int code, String message) {
                super.onFailure(code, message);
                ToastUtils.show(getApplicationContext(),message,0);
            }
        });
    }
    private void getWebChat(){
        ServiceApi.getInstance().getServiceContract().getWeixin("wx2421b1c4370ec43b","支付测试","APP支付测试","10000100",
                "1add1a30ac87aa2db72f57a2375d8fec","http://wxpay.wxutil.com/pub_v2/pay/notify.v2.php","1415659990","14.23.150.211","1",
                "APP","0CB01533B8C1EF103065174F50BCA001"
                ).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                try {
                    response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
