package com.cardee.owner_bookings;


import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.cardee.domain.bookings.BookingState;
import com.cardee.domain.bookings.entity.Booking;
import com.cardee.domain.bookings.usecase.ObtainBookings;
import com.cardee.mvp.BasePresenter;
import com.cardee.mvp.BaseView;

public interface OwnerBookingListContract {

    interface View extends BaseView {

        void invalidate();

        void displaySortType(ObtainBookings.Sort sort);

        void displayFilterType(BookingState filter);

        void openBooking(Integer bookingId);
    }

    interface Presenter extends BasePresenter {

        void setSort(ObtainBookings.Sort sort);

        void setFilter(BookingState filter);

        void showSort(FragmentActivity activity);

        void showFilter(FragmentActivity activity);

        Booking onItem(int position);

        void onItemClick(Booking item);

        int count();

    }
}
