package com.cardee;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatDelegate;

import com.cardee.data_source.remote.api.NoDataResponse;
import com.cardee.data_source.remote.api.util.ResponseDeserializer;
import com.cardee.data_source.remote.client.HttpClientProvider;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CardeeApp extends Application {

    public static Context context;
    public static Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        context = this;
        MultiDex.install(this);
        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
//                .baseUrl("http://192.168.88.113:5550/api/dev/")
                .client(HttpClientProvider.newInstance().provide(this))
                .addConverterFactory(buildGsonConverter())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                .build();
//        changeLocale();

//        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
//            @Override
//            public void uncaughtException(Thread thread, Throwable throwable) {
//                FirebaseCrash.report(throwable);
//            }
//        });
    }

    private static GsonConverterFactory buildGsonConverter() {
        GsonBuilder gsonBuilder = new GsonBuilder();

        // Adding custom deserializer
        gsonBuilder.registerTypeAdapter(NoDataResponse.class, new ResponseDeserializer());
        Gson myGson = gsonBuilder.create();

        return GsonConverterFactory.create(myGson);
    }

    public static GoogleApiClient initLoginGoogleApi(FragmentActivity activity, GoogleApiClient.OnConnectionFailedListener listener) {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope(Scopes.DRIVE_APPFOLDER))
                .requestServerAuthCode(activity.getString(R.string.google_client_id))
                .build();

        return new GoogleApiClient.Builder(activity)
                .enableAutoManage(activity, listener)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

//    private void changeLocale(){
//        Configuration configuration = new Configuration();
//        configuration.setLocale(new Locale("ms-MY"));
//        getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
//    }
}
