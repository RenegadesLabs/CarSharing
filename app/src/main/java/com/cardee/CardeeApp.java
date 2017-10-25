package com.cardee;

import android.app.Application;
import android.content.Context;

import com.cardee.data_source.remote.api.client.HttpClientProvider;

import retrofit2.Retrofit;

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
                .build();
    }
}
