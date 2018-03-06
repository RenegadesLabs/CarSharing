package com.cardee.owner_bookings.view;

import android.app.Activity;
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
import android.widget.Toast;

import com.cardee.R;
import com.cardee.extend_booking.view.ExtendBookingActivity;
import com.cardee.extend_booking.ExtendBookingContract;
import com.cardee.owner_bookings.OwnerBookingContract;
import com.cardee.owner_bookings.car_checklist.service.PendingChecklistStorage;
import com.cardee.owner_bookings.car_checklist.view.OwnerRenterUpdatedChecklistActivity;
import com.cardee.owner_bookings.car_checklist.view.RenterChecklistActivity;
import com.cardee.owner_bookings.presenter.OwnerBookingPresenter;

public class BookingActivity extends AppCompatActivity implements OwnerBookingContract.ParentView {

    public static final String ACTION_CHECKLIST_OWNER = "action_cardee_checklist_changed_by_owner";
    public static final String ACTION_CHECKLIST_RENTER = "action_cardee_checklist_changed_by_renter";
    public static final String IS_RENTER = "flag_cardee_is_renter";
    private static final int EXTEND_BOOKING_REQUEST = 101;

    OwnerBookingContract.Presenter presenter;
    OwnerBookingContract.View view;
    private ChecklistReceiver checklistReceiver;
    private int bookingId;
    private boolean isRenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getIntent().getExtras();
        bookingId = args.getInt(OwnerBookingContract.BOOKING_ID);
        isRenter = args.getBoolean(IS_RENTER, false);
        presenter = new OwnerBookingPresenter(bookingId, isRenter);
        Toolbar toolbar;
        BookingView bookingView = (BookingView) LayoutInflater
                .from(this)
                .inflate(R.layout.view_booking, null);
        setContentView(bookingView);
        view = bookingView;
        toolbar = bookingView.getToolbar();
        view.setPresenter(presenter);
        presenter.setView(view);
        presenter.setParentView(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (PendingChecklistStorage.containsChecklist(this, bookingId)) {
            PendingChecklistStorage.remove(this, bookingId);
            if (isRenter) {
                openRenterCheckListActivity();
            } else {
                openOwnerCheckListActivity();
            }
        }
    }

    private void openOwnerCheckListActivity() {
        Intent intent = new Intent(this, OwnerRenterUpdatedChecklistActivity.class);
        intent.putExtra(OwnerRenterUpdatedChecklistActivity.KEY_BOOKING_ID, bookingId);
        startActivity(intent);
    }

    private void openRenterCheckListActivity() {
        Intent intent = new Intent(this, RenterChecklistActivity.class);
        intent.putExtra(RenterChecklistActivity.KEY_BOOKING_ID, bookingId);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        checklistReceiver = new ChecklistReceiver();
        if (isRenter) {
            registerReceiver(checklistReceiver, new IntentFilter(ACTION_CHECKLIST_RENTER));
        } else {
            registerReceiver(checklistReceiver, new IntentFilter(ACTION_CHECKLIST_OWNER));
        }
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

    @Override
    public void showRenterCheckList() {
        openRenterCheckListActivity();
    }

    @Override
    public void showExtendBookingDialog() {
        Intent intent = new Intent(this, ExtendBookingActivity.class);
        intent.putExtra(ExtendBookingContract.MODE, ExtendBookingContract.Mode.DAILY);
        intent.putExtra(ExtendBookingContract.ID, bookingId);
        startActivityForResult(intent, EXTEND_BOOKING_REQUEST);
        overridePendingTransition(R.anim.enter_up, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EXTEND_BOOKING_REQUEST && resultCode == Activity.RESULT_OK) {
            Toast.makeText(this, "In process", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public class ChecklistReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int alertBookingId = intent.getIntExtra(OwnerBookingContract.BOOKING_ID, 0);
            if (alertBookingId != bookingId) {
                return;
            }

            PendingChecklistStorage.remove(BookingActivity.this, bookingId);
            if (ACTION_CHECKLIST_OWNER.equals(intent.getAction())) {
                openOwnerCheckListActivity();
            } else if (ACTION_CHECKLIST_RENTER.equals(intent.getAction())) {
                openRenterCheckListActivity();
            }
        }
    }
}
