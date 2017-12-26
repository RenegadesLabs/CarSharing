package com.cardee.owner_bookings.presenter;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

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

    public OwnerBookingListPresenter(OwnerBookingListContract.View view, Settings settings) {
        this.view = view;
        this.settings = settings;
        sort = settings.getSort();
        filter = settings.getFilter();
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
                .RequestValues(ObtainBookings.Strategy.OWNER, filter, sort);
        executor.execute(obtainBookings,
                request, new UseCase.Callback<ObtainBookings.ResponseValues>() {
                    @Override
                    public void onSuccess(ObtainBookings.ResponseValues response) {
                        bookings = response.getBookings();
                        if (view != null) {
                            view.showProgress(false);
                            view.displayFilterType(filter);
                            view.displaySortType(sort);
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
    public void showSort(FragmentActivity activity) {
        SortBookingDialog sortDialog = SortBookingDialog.getInstance(settings.getSort());
        sortDialog.show(activity.getSupportFragmentManager(), sortDialog.getTag());
        sortDialog.setSortSelectListener(this);
    }

    @Override
    public void showFilter(FragmentActivity activity) {
        FilterBookingDialog filterDialog = FilterBookingDialog.getInstance(settings.getFilter());
        filterDialog.show(activity.getSupportFragmentManager(), filterDialog.getTag());
        filterDialog.setFilterSelectListener(this);
    }

    @Override
    public Booking onItem(int position) {
        return bookings.get(position);
    }

    @Override
    public int count() {
        return bookings.size();
    }

    @Override
    public void onSortSelected(ObtainBookings.Sort sort) {
        this.sort = sort;
        settings.setSort(sort);
        obtainBookings(true);
    }

    @Override
    public void onFilterSelected(BookingState filter) {
        this.filter = filter;
        settings.setFilter(filter);
        obtainBookings(true);
    }
}
