package com.cardee;

import android.app.Application;
import android.content.Context;

public class CardeeApp extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
}
