package com.cardee;

import android.app.Application;
import android.content.Context;

import com.cardee.data_source.remote.client.HttpClientProvider;

import retrofit2.Retrofit;

import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CardeeApp extends Application {

    public static Context context;
    public static Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(HttpClientProvider.newInstance().provide(this))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
}
