package com.cardee.owner_bookings.presenter;

import com.cardee.data_source.Error;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.bookings.BookingState;
import com.cardee.domain.bookings.entity.Booking;
import com.cardee.domain.bookings.usecase.ObtainBookings;
import com.cardee.owner_bookings.OwnerBookingListContract;

import java.util.ArrayList;
import java.util.List;


public class OwnerBookingListPresenter implements OwnerBookingListContract.Presenter {

    private OwnerBookingListContract.View view;
    //    private SortStrategy<Booking> sortStrategy;
    private List<Booking> bookings;
    private ObtainBookings.Sort sort;
    private BookingState filter;

    private final ObtainBookings obtainBookings;
    private final UseCaseExecutor executor;

    public OwnerBookingListPresenter(OwnerBookingListContract.View view) {
        this.view = view;
        bookings = new ArrayList<>();
        obtainBookings = new ObtainBookings();
        executor = UseCaseExecutor.getInstance();
    }

    @Override
    public void init() {
        obtainBookings(bookings.isEmpty());
    }

    private void obtainBookings(boolean showProgress) {
        if (showProgress) {
            view.showProgress(true);
        }
        ObtainBookings.RequestValues request = new ObtainBookings
                .RequestValues(ObtainBookings.Strategy.OWNER, filter, sort);
        executor.execute(obtainBookings,
                request, new UseCase.Callback<ObtainBookings.ResponseValues>() {
                    @Override
                    public void onSuccess(ObtainBookings.ResponseValues response) {
                        bookings = response.getBookings();
                        if (view != null) {
                            view.showProgress(false);
                            view.invalidate();
                        }
                    }

                    @Override
                    public void onError(Error error) {
                        if (view != null) {
                            view.showProgress(false);
                            view.showMessage(error.getMessage());
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void setSort(ObtainBookings.Sort sort) {
        this.sort = sort;
        obtainBookings(true);
    }

    @Override
    public void setFilter(BookingState filter) {
        this.filter = filter;
        obtainBookings(true);
    }

    @Override
    public Booking onItem(int position) {
        return bookings.get(position);
    }

    @Override
    public int count() {
        return bookings.size();
    }
}
