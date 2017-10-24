package com.cardee;

import android.app.Application;
import android.content.Context;

import com.facebook.FacebookSdk;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CardeeApp extends Application {

    public static Context context;

    public static Retrofit mApi;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        initRetrofit();
    }

    private void initRetrofit() {
        mApi = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static Retrofit getBaseApi() {
        return mApi;
    }
}
