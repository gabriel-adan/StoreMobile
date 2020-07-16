package com.gas.storeapp.services;

import android.text.TextUtils;

import com.google.gson.GsonBuilder;
import java.lang.annotation.Annotation;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpService {

    public static String TOKEN = "";
    private static Retrofit.Builder builder;
    private static Retrofit retrofit;
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    static {
        builder = new Retrofit.Builder()
                .baseUrl("http://192.241.139.200/")
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()));
        retrofit = builder.build();
    }

    public static <TService> TService createService(Class<TService> serviceClass) {
        if (!TextUtils.isEmpty(TOKEN)) {
            AuthenticationInterceptor interceptor = new AuthenticationInterceptor(TOKEN);
            if (!httpClient.interceptors().contains(interceptor)) {
                httpClient.addInterceptor(interceptor);
                builder.client(httpClient.build());
                retrofit = builder.build();
            }
        }
        return retrofit.create(serviceClass);
    }

    public static <TResult> TResult ResponseError(ResponseBody body, Class<TResult> result) {
        try {
            return (TResult)retrofit.responseBodyConverter(result, new Annotation[0]).convert(body);
        } catch (Exception e) {
            return null;
        }
    }
}
