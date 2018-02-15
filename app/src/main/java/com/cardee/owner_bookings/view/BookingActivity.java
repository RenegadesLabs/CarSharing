package com.cardee.owner_bookings.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;

import com.cardee.R;
import com.cardee.owner_bookings.ChecklistActivity;
import com.cardee.owner_bookings.OwnerBookingContract;
import com.cardee.owner_bookings.car_checklist.service.PendingChecklistStorage;
import com.cardee.owner_bookings.car_checklist.view.OwnerRenterUpdatedChecklistActivity;
import com.cardee.owner_bookings.presenter.OwnerBookingPresenter;

public class BookingActivity extends AppCompatActivity {

    public static final String ACTION_CHECKLIST = "action_cardee_checklist_changed_by_owner";
    public static final String IS_RENTER = "flag_cardee_is_renter";

    OwnerBookingContract.Presenter presenter;
    OwnerBookingContract.View view;
    private ChecklistReceiver checklistReceiver;
    private PendingChecklistStorage pendingChecklists;
    private int bookingId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getIntent().getExtras();
        bookingId = args.getInt(OwnerBookingContract.BOOKING_ID);
        presenter = new OwnerBookingPresenter(bookingId, args.getBoolean(IS_RENTER, false));
        Toolbar toolbar;
        BookingView bookingView = (BookingView) LayoutInflater
                .from(this)
                .inflate(R.layout.view_booking, null);
        setContentView(bookingView);
        view = bookingView;
        toolbar = bookingView.getToolbar();
        view.setPresenter(presenter);
        presenter.setView(view);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pendingChecklists = new PendingChecklistStorage();
        if (pendingChecklists.containsChecklist(this, bookingId)) {
            openCheckListActivity();
        }
    }

    private void openCheckListActivity() {
        Intent intent = new Intent(this, OwnerRenterUpdatedChecklistActivity.class);
        intent.putExtra(OwnerRenterUpdatedChecklistActivity.KEY_BOOKING_ID, bookingId);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        checklistReceiver = new ChecklistReceiver();
        registerReceiver(checklistReceiver, new IntentFilter(ACTION_CHECKLIST));
        presenter.init();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(checklistReceiver);
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

    public class ChecklistReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (ACTION_CHECKLIST.equals(intent.getAction())) {
                int alertBookingId = intent.getIntExtra(OwnerBookingContract.BOOKING_ID, 0);
//                if (alertBookingId != bookingId) {
//                    return;
//                }
                openCheckListActivity();
            }
        }
    }
}
