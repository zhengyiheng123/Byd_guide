package com.example.asus.xyd_order.net;


import android.content.Context;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.app.APP;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xiaotongchao on 2016/8/25.
 * Function :
 */
public class ServiceHttpsApiCertficates {
	private static ServiceHttpsApiCertficates INSTANCE;
	private static HttpService service;


	private ServiceHttpsApiCertficates(Context appCtx) {
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

	public synchronized static ServiceHttpsApiCertficates getInstance(Context context) {
		if(INSTANCE==null){
			INSTANCE = new ServiceHttpsApiCertficates(context);
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

//    public static OkHttpClient getSafeOkHttpClient() {
//        int[] certficates = new int[]{R.raw.media};
//            OkHttpClient okHttpClient = new OkHttpClient();
//            okHttpClient = okHttpClient.newBuilder()
//                    .sslSocketFactory(getSSLSocketFactory(APP.getCtx(), certficates))
//                    .hostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER).build();
//
//            return okHttpClient;
//
//    }
    protected static SSLSocketFactory getSSLSocketFactory(Context context, int[] certificates) {

        if (context == null) {
            throw new NullPointerException("context == null");
        }

        //CertificateFactory用来证书生成
        CertificateFactory certificateFactory;
        try {
            certificateFactory = CertificateFactory.getInstance("X.509");
            //Create a KeyStore containing our trusted CAs
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);

            for (int i = 0; i < certificates.length; i++) {
                //读取本地证书
                InputStream is = context.getResources().openRawResource(certificates[i]);
                keyStore.setCertificateEntry(String.valueOf(i), certificateFactory.generateCertificate(is));

                if (is != null) {
                    is.close();
                }
            }
            //Create a TrustManager that trusts the CAs in our keyStore
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);

            //Create an SSLContext that uses our TrustManager
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
            return sslContext.getSocketFactory();

        } catch (Exception e) {

        }
        return null;
    }
}
