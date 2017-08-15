package com.example.asus.xyd_order.net.Filter;

import android.content.Intent;

import com.example.asus.xyd_order.activity.Activity_Register_confirm;
import com.example.asus.xyd_order.activity.LoginActivity;
import com.example.asus.xyd_order.app.APP;
import com.example.asus.xyd_order.net.exception.ApiException;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.net.result.LoginResult;
import com.example.asus.xyd_order.utils.SharedPreferenceUtils;

import rx.functions.Func1;


/**
 * Created by Zheng on 2017/4/25.
 */

public class ResultFilter<T> implements Func1<HttpResult<T>,T> {


    @Override
    public T call(HttpResult<T> tHttpResult) {
        if (tHttpResult.getCode()==1){
            if (tHttpResult.getData()!=null){
                return tHttpResult.getData();
            }else {
                throw new ApiException(tHttpResult.getMessage());
            }
        }else if(tHttpResult.getCode() == -3 ){
            Intent intent=new Intent(APP.getCtx(),LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            APP.getCtx().startActivity(intent);
            SharedPreferenceUtils.clear(APP.getCtx());
            throw new ApiException("用户在另一个客户端登录");
        }else if (tHttpResult.getCode() == 2){
            LoginResult result= (LoginResult) tHttpResult.getData();
            Intent intent=new Intent(APP.getCtx(), Activity_Register_confirm.class);
            SharedPreferenceUtils.setParam(APP.getCtx(),"apitoken",result.getApitoken());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            APP.getCtx().startActivity(intent);
            throw new ApiException(tHttpResult.getMessage());
        }else if (tHttpResult.getCode() == -1){
            Intent intent=new Intent(APP.getCtx(),LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            APP.getCtx().startActivity(intent);
            SharedPreferenceUtils.clear(APP.getCtx());
            throw new ApiException("apitoken不存在或已过期,请重新登陆");
        }else if (tHttpResult.getCode() == -2){
            throw new ApiException("请求失败");
        }
        else {
            throw new ApiException(tHttpResult.getMessage());
        }
    }
}
