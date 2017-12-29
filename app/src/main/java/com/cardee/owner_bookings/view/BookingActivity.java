package com.cardee.owner_bookings.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;

import com.cardee.R;
import com.cardee.domain.bookings.BookingState;
import com.cardee.owner_bookings.OwnerBookingContract;
import com.cardee.owner_bookings.presenter.OwnerBookingPresenter;

public class BookingActivity extends AppCompatActivity {

    OwnerBookingContract.Presenter presenter;
    OwnerBookingContract.View view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getIntent().getExtras();
        int bookingId = args.getInt(OwnerBookingContract.BOOKING_ID);
        BookingState state = (BookingState) args.getSerializable(OwnerBookingContract.BOOKING_STATE);
        presenter = new OwnerBookingPresenter(bookingId);
        BookingView bookingView = (BookingView) LayoutInflater
                .from(this).inflate(R.layout.view_booking, null);
        setContentView(bookingView);
        view = bookingView;
        view.setPresenter(presenter);
        presenter.setView(view);
        Toolbar toolbar = bookingView.getToolbar();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.init();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
        view.onDestroy();
    }
}