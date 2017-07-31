package com.example.asus.xyd_order.net;


import android.content.Context;

import com.example.asus.xyd_order.app.APP;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xiaotongchao on 2016/8/25.
 * Function :
 */
public class ServiceApi {
	private static ServiceApi INSTANCE = new ServiceApi();
	private static HttpService service;
	private ServiceApi() {
		Context context= APP.getCtx();

			OkHttpClient okHttpClient = new OkHttpClient();
			if(BaseApi.isDebug){
				HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
				logging.setLevel(HttpLoggingInterceptor.Level.BODY);
				okHttpClient = new OkHttpClient.Builder()
//						.cookieJar(new CookiesManager())
						.connectTimeout(10, TimeUnit.SECONDS)//设置超时时间
						.readTimeout(10, TimeUnit.SECONDS)//设置读取超时时间
						.writeTimeout(10, TimeUnit.SECONDS)//设置写入超时时间
//						.addInterceptor(logging)
						.build();
			}

			//调整json里面的一些格式
			Gson gson = new GsonBuilder()
					.setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
					.create();

			Retrofit retrofit = new Retrofit.Builder()
					.baseUrl(BaseApi.getBaseUrl())
//					.baseUrl("http://www.beixincheng.com/")
//					.addConverterFactory(GsonConverterFactory.create(gson))
					.addConverterFactory(GsonConverterFactory.create())
					.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
					.client(okHttpClient)
					.build();
			service = retrofit.create(HttpService.class);

		/*Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(BaseApi.getBaseUrl())
				.addConverterFactory(GsonConverterFactory.create())
				.build();

		service = retrofit.create(HttpService.class);*/

	}

	public synchronized static ServiceApi getInstance() {
		return INSTANCE;
	}
	/**
	 * 获取后台业务服务 API协议实例
	 *
	 * @return
	 */
	public HttpService getServiceContract() {
		return service;
	}


}
