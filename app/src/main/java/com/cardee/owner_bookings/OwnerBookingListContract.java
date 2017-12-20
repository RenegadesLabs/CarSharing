package com.cardee.owner_bookings;


import com.cardee.domain.bookings.entity.Booking;
import com.cardee.mvp.BasePresenter;
import com.cardee.mvp.BaseView;
import com.cardee.owner_bookings.sort.SortStrategy;

import java.util.List;

public interface OwnerBookingListContract {

    interface View extends BaseView {

        void setBookings(List<Booking> bookings);

    }

    interface Presenter extends BasePresenter {

        void setSortStrategy(SortStrategy<Booking> sortStrategy);

    }
}
