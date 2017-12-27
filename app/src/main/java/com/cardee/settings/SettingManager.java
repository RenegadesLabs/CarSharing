package com.cardee.settings;

import android.content.Context;
import android.content.SharedPreferences;

import com.cardee.domain.bookings.BookingState;
import com.cardee.domain.bookings.usecase.ObtainBookings;

public class SettingManager {

    private static SettingManager INSTANCE;
    private static final String SETTING_PREF = "_setting_pref";
    private static final String OWNER_BOOKING_SORT = "_owner_booking_sort";
    private static final String OWNER_BOOKING_FILTER = "_owner_booking_filter";

    private SharedPreferences settingPrefs;

    private SettingManager(Context context) {
        settingPrefs = context.getSharedPreferences(SETTING_PREF, Context.MODE_PRIVATE);
    }

    public static SettingManager getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new SettingManager(context);
        }
        return INSTANCE;
    }

    public Settings obtainSettings() {
        return new Settings(this);
    }

    public void saveFilter(BookingState filter) {
        settingPrefs
                .edit()
                .putString(OWNER_BOOKING_FILTER, filter == null ? null : filter.name())
                .apply();
    }

    public void saveSort(ObtainBookings.Sort sort) {
        settingPrefs
                .edit()
                .putString(OWNER_BOOKING_SORT, sort == null ? null : sort.name())
                .apply();
    }

    public BookingState getFilter() {
        String filterName = settingPrefs.getString(OWNER_BOOKING_FILTER, null);
        BookingState filter = null;
        if (filterName != null) {
            try {
                filter = BookingState.valueOf(filterName);
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            }
        }
        return filter;
    }

    public ObtainBookings.Sort getSort() {
        String sortName = settingPrefs.getString(OWNER_BOOKING_SORT, null);
        ObtainBookings.Sort sort = null;
        if (sortName != null) {
            try {
                sort = ObtainBookings.Sort.valueOf(sortName);
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            }
        }
        return sort;
    }
}
