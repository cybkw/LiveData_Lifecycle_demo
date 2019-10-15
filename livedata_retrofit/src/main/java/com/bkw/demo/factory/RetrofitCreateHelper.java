package com.bkw.demo.factory;


import com.bkw.demo.BuildConfig;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求retrofit
 *
 * @author bkw
 */

public class RetrofitCreateHelper {

    private static final int TIMEOUT_READ = 20;
    private static final int TIMEOUT_CONNECTION = 20;
    private static final HttpLoggingInterceptor INTERCEPTOR = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);

    private static final X509TrustManager trustManager = new X509TrustManager() {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    };

    private static SSLSocketFactory getSslSocketFactory() {
        SSLContext sslContext = null;
        SSLSocketFactory sslSocketFactory = null;
        try {
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{trustManager}, new SecureRandom());
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sslSocketFactory;
    }


    public static <T> T createApi(Class<T> clazz, String url) {

        //OkHttp
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            okHttpClient.addInterceptor(INTERCEPTOR);
        }
        okHttpClient.sslSocketFactory(getSslSocketFactory(), trustManager);
        okHttpClient.connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS);
        okHttpClient.readTimeout(TIMEOUT_READ, TimeUnit.SECONDS);
        okHttpClient.writeTimeout(TIMEOUT_READ, TimeUnit.SECONDS);
        okHttpClient.retryOnConnectionFailure(true);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(okHttpClient.build())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(clazz);
    }


}

