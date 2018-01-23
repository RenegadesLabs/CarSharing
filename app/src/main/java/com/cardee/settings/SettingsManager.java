package com.cardee.settings;

import android.content.Context;
import android.content.SharedPreferences;

import com.cardee.domain.bookings.BookingState;
import com.cardee.domain.bookings.usecase.ObtainBookings;
import com.cardee.renter_browse_cars.presenter.RenterBrowseCarListContract;

public class SettingsManager {

    private static SettingsManager INSTANCE;
    private static final String SETTING_PREF = "_setting_pref";
    private static final String OWNER_BOOKING_SORT = "_owner_booking_sort";
    private static final String OWNER_BOOKING_FILTER = "_owner_booking_filter";
    private static final String RENTER_OFFERS_SORT = "_renter_booking_filter";

    private SharedPreferences settingPrefs;

    private SettingsManager(Context context) {
        settingPrefs = context.getSharedPreferences(SETTING_PREF, Context.MODE_PRIVATE);
    }

    public static SettingsManager getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new SettingsManager(context);
        }
        return INSTANCE;
    }

    public Settings obtainSettings() {
        return new Settings(this);
    }

    public void saveBookingFilter(BookingState filter) {
        settingPrefs
                .edit()
                .putString(OWNER_BOOKING_FILTER, filter == null ? null : filter.name())
                .apply();
    }

    public void saveBookingSort(ObtainBookings.Sort sort) {
        settingPrefs
                .edit()
                .putString(OWNER_BOOKING_SORT, sort == null ? null : sort.name())
                .apply();
    }

    public void saveOffersSort(RenterBrowseCarListContract.Sort sort) {
        settingPrefs
                .edit()
                .putString(RENTER_OFFERS_SORT, sort == null ? null : sort.name())
                .apply();
    }

    public BookingState getBookingFilter() {
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

    public ObtainBookings.Sort getBookingSort() {
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

    public RenterBrowseCarListContract.Sort getOffersSort() {
        String sortName = settingPrefs.getString(RENTER_OFFERS_SORT, null);
        RenterBrowseCarListContract.Sort sort = null;
        if (sortName != null) {
            try {
                sort = RenterBrowseCarListContract.Sort.valueOf(sortName);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        return sort;
    }
}
