package com.cardee.owner_bookings.car_handover.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;


import com.cardee.R;
import com.cardee.owner_bookings.car_handover.HandoverContract;
import com.cardee.owner_bookings.car_handover.OwnerChecklistPresenter;
import com.cardee.owner_bookings.car_handover.strategy.HandoverChecklistByMileageStrategy;
import com.cardee.owner_bookings.car_handover.strategy.HandoverChecklistStrategy;
import com.cardee.owner_bookings.car_handover.strategy.RenterUpdatedChecklistStrategy;


public class OwnerChecklistActivity extends AppCompatActivity {

    public final static int HANDOVER_CHECKLIST = 0;
    public final static int HANDOVER_CHECKLIST_MILEAGE = 1;
    public final static int RENTER_UPDATED_CHECKLIST = 2;

    private HandoverContract.Presenter mPresenter;

    private HandoverContract.View mView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ChecklistView view = (ChecklistView) LayoutInflater
                .from(this).inflate(R.layout.activity_owner_handover_checklist, null);
        mPresenter = new OwnerChecklistPresenter();
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

    private void setStrategy(int type, View view) {
        switch (type) {
            case HANDOVER_CHECKLIST:
                mPresenter.setStrategy(new HandoverChecklistStrategy(view, mPresenter));
                break;
            case HANDOVER_CHECKLIST_MILEAGE:
                mPresenter.setStrategy(new HandoverChecklistByMileageStrategy(view, mPresenter));
                break;
            case RENTER_UPDATED_CHECKLIST:
                mPresenter.setStrategy(new RenterUpdatedChecklistStrategy(view, mPresenter));
                break;
        }
    }
}
