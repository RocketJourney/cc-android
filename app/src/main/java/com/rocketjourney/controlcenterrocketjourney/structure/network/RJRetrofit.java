package com.rocketjourney.controlcenterrocketjourney.structure.network;

import com.google.gson.GsonBuilder;
import com.rocketjourney.controlcenterrocketjourney.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RJRetrofit {

    private static Retrofit instance;
    private static OkHttpClient okhttpclient = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build();

    public static Retrofit getInstance() {
        if (instance == null) {
            instance = new Retrofit.Builder()
                    .baseUrl(BuildConfig.ApiUrl)
                    .client(okhttpclient)
                    .addConverterFactory(GsonConverterFactory.create(
                            new GsonBuilder().
                                    excludeFieldsWithoutExposeAnnotation()
                                    .serializeNulls()
                                    .create()))
                    .build();
        }
        return instance;
    }

}
