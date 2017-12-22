package com.cardee.data_source.remote.client;

import android.content.Context;

import com.cardee.data_source.remote.service.AccountManager;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class HttpClientProvider {

    private HttpClientProvider() {
    }

    public static HttpClientProvider newInstance() {
        return new HttpClientProvider();
    }

    public OkHttpClient provide(Context context) {
        AccountManager accountManager = AccountManager.getInstance(context);
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        return new OkHttpClient.Builder()
                .addInterceptor(new AuthHeaderRequestInterceptor(accountManager))
                .addInterceptor(loggingInterceptor)
                .connectTimeout(20, TimeUnit.SECONDS)
                .build();
    }

    private class AuthHeaderRequestInterceptor implements Interceptor {

        private final AccountManager mAccountManager;

        private AuthHeaderRequestInterceptor(AccountManager accountManager) {
            mAccountManager = accountManager;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = mAccountManager.modifyRequestHeaders(chain.request());
            return chain.proceed(request);
        }
    }

    private class AuthHeaderResponseInterceptor implements Interceptor {

        private final AccountManager mAccountManager;

        private AuthHeaderResponseInterceptor(AccountManager accountManager) {
            mAccountManager = accountManager;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Response response = chain.proceed(chain.request());
            mAccountManager.handleResponseHeaders(response);
            return response;
        }
    }
}
