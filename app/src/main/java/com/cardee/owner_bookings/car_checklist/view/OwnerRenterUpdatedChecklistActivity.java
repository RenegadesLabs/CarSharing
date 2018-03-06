package com.cardee.owner_bookings.car_checklist.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;

import com.cardee.R;
import com.cardee.owner_bookings.car_checklist.presenter.OwnerChecklistContract;
import com.cardee.owner_bookings.car_checklist.presenter.OwnerRenterUpdatedChecklistPresenter;


public class OwnerRenterUpdatedChecklistActivity extends AppCompatActivity implements OwnerRenterUpdatedChecklistPresenter.View {

    private OwnerChecklistContract.Presenter mPresenter;

    private OwnerChecklistContract.View mView;

    public final static String KEY_BOOKING_ID = "booking_id";
    public int bookingId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ChecklistView view = (ChecklistView) LayoutInflater
                .from(this).inflate(R.layout.activity_owner_handover_checklist, null);
        mView = view;
        bookingId = getIntent().getIntExtra(KEY_BOOKING_ID, -1);
        mPresenter = new OwnerRenterUpdatedChecklistPresenter(bookingId);
        mView.setPresenter(mPresenter);
        mPresenter.setView(mView);
        mPresenter.setViewRenterUpdatedCallbacks(this);
        setContentView(view);
        Toolbar toolbar = view.getToolbar();
        toolbar.setNavigationIcon(null);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
        mView.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.init();
    }

    @Override
    public void onConfirmed() {
        finish();
    }

    @Override
    public void onCancelled() {
        Intent intent = new Intent(this, ChecklistActivity.class);
        intent.putExtra(ChecklistActivity.KEY_BOOKING_ID, bookingId);
        intent.putExtra(ChecklistActivity.KEY_IS_RENTER, false);
        startActivity(intent);
        finish();
    }
}
