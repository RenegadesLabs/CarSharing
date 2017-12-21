package com.cardee.owner_bookings;


import com.cardee.domain.bookings.BookingState;
import com.cardee.domain.bookings.entity.Booking;
import com.cardee.domain.bookings.usecase.ObtainBookings;
import com.cardee.mvp.BasePresenter;
import com.cardee.mvp.BaseView;

public interface OwnerBookingListContract {

    interface View extends BaseView {

        void invalidate();

    }

    interface Presenter extends BasePresenter {

        void setSort(ObtainBookings.Sort sort);

        void setFilter(BookingState filter);

        Booking onItem(int position);

        int count();

    }
}
