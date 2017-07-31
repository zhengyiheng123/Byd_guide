package com.example.asus.xyd_order.net;


import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xiaotongchao on 2016/8/25.
 * Function :
 */
public class ServiceHttpsApi {
	private static ServiceHttpsApi INSTANCE;
	private static HttpService service;


	private ServiceHttpsApi(Context appCtx) {
		try {
			//调整json里面的一些格式
			Gson gson = new GsonBuilder()
					.setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
					.create();

			Retrofit retrofit = new Retrofit.Builder()
					.baseUrl(BaseApi.getBaseUrl())
					.addConverterFactory(GsonConverterFactory.create(gson))
					.client(getUnsafeOkHttpClient())
					.build();

			service = retrofit.create(HttpService.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized static ServiceHttpsApi getInstance(Context context) {
		if(INSTANCE==null){
			INSTANCE = new ServiceHttpsApi(context);
		}
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
	public static OkHttpClient getUnsafeOkHttpClient() {

		try {
			// Create a trust manager that does not validate certificate chains
			final TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
				@Override
				public void checkClientTrusted(
						java.security.cert.X509Certificate[] chain,
						String authType) throws CertificateException {
				}

				@Override
				public void checkServerTrusted(
						java.security.cert.X509Certificate[] chain,
						String authType) throws CertificateException {
				}

				@Override
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return new java.security.cert.X509Certificate[0];
				}
			} };

			// Install the all-trusting trust manager
			//trustAllCerts

			final SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(null, trustAllCerts,
					new java.security.SecureRandom());
			// Create an ssl socket factory with our all-trusting manager
			final SSLSocketFactory sslSocketFactory = sslContext
					.getSocketFactory();

			OkHttpClient okHttpClient = new OkHttpClient();
			okHttpClient = okHttpClient.newBuilder()
					.sslSocketFactory(sslSocketFactory)
					.hostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER).build();

			return okHttpClient;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
}
