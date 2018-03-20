package com.cardee.owner_bookings.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cardee.R;
import com.cardee.domain.bookings.BookingState;
import com.cardee.domain.bookings.usecase.ObtainBookings;
import com.cardee.owner_bookings.OwnerBookingContract;
import com.cardee.owner_bookings.OwnerBookingListContract;
import com.cardee.owner_bookings.presenter.OwnerBookingListPresenter;
import com.cardee.settings.Settings;
import com.cardee.settings.SettingsManager;

public class BookingListFragment extends Fragment
        implements View.OnClickListener, OwnerBookingListContract.View {

    private OwnerBookingListContract.Presenter presenter;
    private BookingListAdapter adapter;
    private RecyclerView bookingList;
    private View progressLayout;
    private Toast currentToast;
    private TextView filterValue;
    private TextView sortValue;

    public static Fragment newInstance() {
        return new BookingListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Settings settings = SettingsManager.getInstance(getActivity()).obtainSettings();
        presenter = new OwnerBookingListPresenter(this, settings);
        adapter = new BookingListAdapter(presenter, getActivity(), false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_owner_booking_list, container, false);
        bookingList = rootView.findViewById(R.id.booking_list);
        bookingList.setAdapter(adapter);
        bookingList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        bookingList.setItemAnimator(new DefaultItemAnimator());
        progressLayout = rootView.findViewById(R.id.progress_layout);
        rootView.findViewById(R.id.btn_open_filter).setOnClickListener(this);
        rootView.findViewById(R.id.btn_open_sort).setOnClickListener(this);
        filterValue = rootView.findViewById(R.id.btn_open_filter_title);
        sortValue = rootView.findViewById(R.id.btn_open_sort_title);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.init();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_open_filter:
                presenter.showFilter(getActivity());
                break;
            case R.id.btn_open_sort:
                presenter.showSort(getActivity());
                break;
        }
    }

    @Override
    public void showProgress(boolean show) {
        progressLayout.setVisibility(show ? View.VISIBLE : View.GONE);
        bookingList.setAlpha(show ? .5f : 1f);
    }

    @Override
    public void showMessage(String message) {
        if (currentToast != null) {
            currentToast.cancel();
        }
        if (getActivity() != null) {
            currentToast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
            currentToast.show();
        }
    }

    @Override
    public void showMessage(int messageId) {
        showMessage(getString(messageId));
    }

    @Override
    public void invalidate() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void displaySortType(ObtainBookings.Sort sort) {
        sortValue.setText(sort == null ? R.string.booking_sort_date : sort.getTitleId());
    }

    @Override
    public void displayFilterType(BookingState filter) {
        filterValue.setText(filter == null ? R.string.booking_state_all : filter.getTitleId());
    }

    @Override
    public void openBooking(Integer bookingId) {
        Intent intent = new Intent(getActivity(), BookingActivity.class);
        intent.putExtra(OwnerBookingContract.BOOKING_ID, bookingId);
        getActivity().startActivity(intent);
    }
}
