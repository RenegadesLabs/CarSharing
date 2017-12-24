package com.cardee.settings;

import com.cardee.domain.bookings.BookingState;
import com.cardee.domain.bookings.usecase.ObtainBookings;

public class Settings {

    private BookingState filter;
    private ObtainBookings.Sort sort;

    private final SettingManager manager;

    Settings(SettingManager manager) {
        this.manager = manager;
    }

    public BookingState getFilter() {
        return filter;
    }

    public ObtainBookings.Sort getSort() {
        return sort;
    }

    public void setFilter(BookingState filter) {
        this.filter = filter;
        manager.saveFilter(filter);
    }

    public void setSort(ObtainBookings.Sort sort) {
        this.sort = sort;
        manager.saveSort(sort);
    }
}
