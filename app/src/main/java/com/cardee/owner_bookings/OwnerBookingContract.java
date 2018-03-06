package com.cardee.owner_bookings;


import com.cardee.domain.bookings.entity.Booking;
import com.cardee.mvp.BasePresenter;
import com.cardee.mvp.BaseView;
import com.cardee.owner_bookings.strategy.PresentationStrategy;

public interface OwnerBookingContract {

    String BOOKING_ID = "_booking_id";
    String BOOKING_STATE = "_booking_state";

    interface ParentView {
        void showRenterCheckList();

        void showExtendBookingDialog();
    }

    interface View extends BaseView {

        void setPresenter(Presenter presenter);

        void bind(Booking booking, boolean isRenter);

        void bind();

        void onDestroy();
    }

    interface Presenter extends BasePresenter, PresentationStrategy.ActionListener {

        void setView(View view);

        void setStrategy(PresentationStrategy strategy);

        void onDestroy();

        void setParentView(ParentView parentView);
    }
}
