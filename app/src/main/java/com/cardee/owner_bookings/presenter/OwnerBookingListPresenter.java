package com.cardee.owner_bookings.presenter;

import android.support.v4.app.FragmentActivity;

import com.cardee.custom.modal.FilterBookingDialog;
import com.cardee.custom.modal.SortBookingDialog;
import com.cardee.data_source.Error;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.bookings.BookingState;
import com.cardee.domain.bookings.entity.Booking;
import com.cardee.domain.bookings.usecase.ObtainBookings;
import com.cardee.owner_bookings.OwnerBookingListContract;
import com.cardee.settings.Settings;

import java.util.ArrayList;
import java.util.List;


public class OwnerBookingListPresenter
        implements OwnerBookingListContract.Presenter,
        SortBookingDialog.SortSelectListener,
        FilterBookingDialog.FilterSelectListener {

    private OwnerBookingListContract.View view;
    private final Settings settings;
    private List<Booking> bookings;
    private ObtainBookings.Sort sort;
    private BookingState filter;

    private final ObtainBookings obtainBookings;
    private final UseCaseExecutor executor;
    private boolean initialized = false;

    public OwnerBookingListPresenter(OwnerBookingListContract.View view, Settings settings) {
        this.view = view;
        this.settings = settings;
        sort = settings.getSortBooking();
        filter = settings.getFilterBooking();
        bookings = new ArrayList<>();
        obtainBookings = new ObtainBookings();
        executor = UseCaseExecutor.getInstance();
    }

    @Override
    public void init() {
        obtainBookings(bookings.isEmpty());
        view.displaySortType(sort);
        view.displayFilterType(filter);
    }

    private void obtainBookings(boolean showProgress) {
        if (showProgress) {
            view.showProgress(true);
        }
        ObtainBookings.RequestValues request = new ObtainBookings
                .RequestValues(ObtainBookings.Strategy.OWNER, filter, sort, !initialized);
        executor.execute(obtainBookings,
                request, new UseCase.Callback<ObtainBookings.ResponseValues>() {
                    @Override
                    public void onSuccess(ObtainBookings.ResponseValues response) {
                        if (bookings.isEmpty() || response.isUpdated()) {
                            bookings = response.getBookings();
                            if (view != null) {
                                view.showProgress(false);
                                view.displayFilterType(filter);
                                view.displaySortType(sort);
                                view.invalidate();
                            }
                        }
                        initialized = true;
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
    public void showSort(FragmentActivity activity) {
        SortBookingDialog sortDialog = SortBookingDialog.getInstance(settings.getSortBooking());
        sortDialog.show(activity.getSupportFragmentManager(), sortDialog.getTag());
        sortDialog.setSortSelectListener(this);
    }

    @Override
    public void showFilter(FragmentActivity activity) {
        FilterBookingDialog filterDialog = FilterBookingDialog.getInstance(settings.getFilterBooking());
        filterDialog.show(activity.getSupportFragmentManager(), filterDialog.getTag());
        filterDialog.setFilterSelectListener(this);
    }

    @Override
    public Booking onItem(int position) {
        return bookings.get(position);
    }

    @Override
    public void onItemClick(Booking item) {
        Integer bookingId = item.getBookingId();

        if (bookingId == null) {
            throw new IllegalArgumentException("Invalid bookingId: " + bookingId);
        }
        view.openBooking(bookingId);
    }

    @Override
    public int count() {
        return bookings.size();
    }

    @Override
    public void onSortSelected(ObtainBookings.Sort sort) {
        this.sort = sort;
        settings.setSortBooking(sort);
        initialized = false;
        obtainBookings(true);
    }

    @Override
    public void onFilterSelected(BookingState filter) {
        this.filter = filter;
        settings.setFilterBooking(filter);
        initialized = false;
        obtainBookings(true);
    }
}
