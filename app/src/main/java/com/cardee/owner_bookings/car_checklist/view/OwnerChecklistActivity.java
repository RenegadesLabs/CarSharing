package com.cardee.owner_bookings.car_checklist.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;


import com.cardee.R;
import com.cardee.owner_bookings.car_checklist.ChecklistContract;
import com.cardee.owner_bookings.car_checklist.OwnerChecklistPresenter;
import com.cardee.owner_bookings.car_checklist.strategy.ChecklistByMileageStrategy;
import com.cardee.owner_bookings.car_checklist.strategy.ChecklistStrategy;
import com.cardee.owner_bookings.car_checklist.strategy.RenterUpdatedChecklistStrategy;


public class OwnerChecklistActivity extends AppCompatActivity {

    public final static int HANDOVER_CHECKLIST = 0;
    public final static int HANDOVER_CHECKLIST_MILEAGE = 1;
    public final static int RENTER_UPDATED_CHECKLIST = 2;

    public final static String KEY_BOOKING_ID = "booking_id";

    private ChecklistContract.Presenter mPresenter;

    private ChecklistContract.View mView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ChecklistView view = (ChecklistView) LayoutInflater
                .from(this).inflate(R.layout.activity_owner_handover_checklist, null);
        mPresenter = new OwnerChecklistPresenter(getIntent().getIntExtra(KEY_BOOKING_ID, -1));
        mView = view;
        mView.setPresenter(mPresenter);
        mPresenter.setView(mView);
        setContentView(view);
        Toolbar toolbar = view.getToolbar();
        setSupportActionBar(toolbar);
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
