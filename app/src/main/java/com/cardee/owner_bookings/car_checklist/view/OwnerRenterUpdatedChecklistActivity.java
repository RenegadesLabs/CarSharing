package com.cardee.owner_bookings.car_checklist.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;

import com.cardee.R;
import com.cardee.owner_bookings.car_checklist.presenter.ChecklistContract;
import com.cardee.owner_bookings.car_checklist.presenter.OwnerRenterUpdatedChecklistPresenter;


public class OwnerRenterUpdatedChecklistActivity extends AppCompatActivity {

    private ChecklistContract.Presenter mPresenter;

    private ChecklistContract.View mView;

    public final static String KEY_BOOKING_ID = "booking_id";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ChecklistView view = (ChecklistView) LayoutInflater
                .from(this).inflate(R.layout.activity_owner_handover_checklist, null);
        mView.setPresenter(mPresenter);
        mView = view;
        int bookingId = getIntent().getIntExtra(KEY_BOOKING_ID, -1);
        mPresenter = new OwnerRenterUpdatedChecklistPresenter(bookingId);
        mPresenter.setView(mView);
        setContentView(view);
        setSupportActionBar(view.getToolbar());
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
