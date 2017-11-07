package com.cardee;

import android.app.Application;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatDelegate;

import com.cardee.data_source.remote.client.HttpClientProvider;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;

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

        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(HttpClientProvider.newInstance().provide(this))
                .addConverterFactory(GsonConverterFactory.create())
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
