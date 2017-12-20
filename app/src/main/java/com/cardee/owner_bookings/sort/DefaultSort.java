package com.cardee.owner_bookings.sort;


import com.cardee.domain.bookings.entity.Booking;

import java.util.List;

public class DefaultSort implements SortStrategy<Booking> {

    private final boolean asc;

    public DefaultSort(boolean asc) {
        this.asc = asc;
    }

    @Override
    public void apply(List<Booking> list, Callback<Booking> callback) {
        callback.onReady(list);
    }
}
