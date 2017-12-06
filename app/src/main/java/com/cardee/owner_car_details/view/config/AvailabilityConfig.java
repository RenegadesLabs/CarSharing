package com.cardee.owner_car_details.view.config;

import android.content.Context;
import android.content.SharedPreferences;

public final class AvailabilityConfig {

    private static final String CONFIG_STORAGE_NAME = "_config_storage";
    private static final String SINGLE_DATES_SET = "_single_dates_set";

    private boolean singleDatesSet;
    private SharedPreferences prefs;
    public static AvailabilityConfig INSTANCE;

    private AvailabilityConfig(Context context) {
        prefs = context.getSharedPreferences(CONFIG_STORAGE_NAME, Context.MODE_PRIVATE);
        singleDatesSet = prefs.getBoolean(SINGLE_DATES_SET, false);
    }

    public static AvailabilityConfig getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new AvailabilityConfig(context);
        }
        return INSTANCE;
    }

    public void saveIsSingleDatesSet(boolean singleDatesSet) {
        if (prefs == null) {
            throw new IllegalStateException("Config instance is released");
        }
        this.singleDatesSet = singleDatesSet;
        prefs.edit().putBoolean(SINGLE_DATES_SET, singleDatesSet).apply();
    }

    public boolean isSingleDatesSet() {
        return singleDatesSet;
    }

    public static void relese() {
        INSTANCE.prefs = null;
        INSTANCE = null;
    }
}
