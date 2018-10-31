package com.rocketjourney.controlcenterrocketjourney.structure.network;

import com.google.gson.GsonBuilder;
import com.rocketjourney.controlcenterrocketjourney.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RJRetrofit {

    private static Retrofit instance;

    public static Retrofit getInstance() {
        if (instance == null) {
            instance = new Retrofit.Builder()
                    .baseUrl(BuildConfig.ApiUrl)
                    .addConverterFactory(GsonConverterFactory.create(
                            new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()))
                    .build();
        }
        return instance;
    }

}
