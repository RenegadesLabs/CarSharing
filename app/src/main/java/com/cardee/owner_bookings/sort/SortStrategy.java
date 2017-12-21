package com.cardee.owner_bookings.sort;


import java.util.List;

public interface SortStrategy<T> {

    void apply(List<T> list, Callback<T> callback);

    interface Callback<T> {
        void onReady(List<T> list);
    }

}
