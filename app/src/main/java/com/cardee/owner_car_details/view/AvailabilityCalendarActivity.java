package com.cardee.owner_car_details.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cardee.R;
import com.cardee.custom.calendar.view.CalendarView;
import com.cardee.owner_car_details.AvailabilityContract;
import com.cardee.owner_car_details.presenter.AvailabilityPresenter;
import com.cardee.owner_car_details.view.adapter.AvailabilityCalendarAdapter;
import com.cardee.owner_car_details.view.config.AvailabilityConfig;
import com.cardee.owner_car_details.view.listener.AvailabilityCalendarListener;
import com.cardee.util.StringFormatDelegate;

import java.util.Date;
import java.util.List;

public class AvailabilityCalendarActivity extends AppCompatActivity
        implements View.OnClickListener, AvailabilityCalendarListener,
        AvailabilityContract.View {

    private TextView titleView;
    private View actionView;
    private CalendarView calendarView;
    private TextView buttonSave;
    private AvailabilityCalendarAdapter adapter;
    private View progress;
    private AvailabilityPresenter presenter;
    private StringFormatDelegate stringFormatDelegate;

    private Toast currentToast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_availability_calendar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        titleView = toolbar.findViewById(R.id.toolbar_title);
        actionView = toolbar.findViewById(R.id.toolbar_action);
        if (getSupportActionBar() == null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        buttonSave = findViewById(R.id.btn_availability_save);
        buttonSave.setOnClickListener(this);
        actionView.setOnClickListener(this);
        calendarView = findViewById(R.id.availability_calendar);
        adapter = new AvailabilityCalendarAdapter();
        adapter.setListener(this);
        calendarView.setSelectionAdapter(adapter);
        stringFormatDelegate = new StringFormatDelegate(this);
        progress = findViewById(R.id.progress_layout);
        initState();
    }

    private void initState() {
        Bundle args = getIntent().getExtras();
        if (args.containsKey(AvailabilityContract.CALENDAR_MODE)) {
            AvailabilityContract.Mode mode =
                    (AvailabilityContract.Mode) args.getSerializable(AvailabilityContract.CALENDAR_MODE);
            if (mode == AvailabilityContract.Mode.DAILY) {
                titleView.setText(R.string.availability_calendar_daily);
                calendarView.setIncludeCurrent(false);
            } else if (mode == AvailabilityContract.Mode.HOURLY) {
                titleView.setText(R.string.availability_calendar_hourly);
                calendarView.setIncludeCurrent(true);
            }
        }
        presenter = new AvailabilityPresenter(this, args, AvailabilityConfig.getInstance(this));
        presenter.init();
    }

    @Override
    public void onClick(View view) {
        if (progress.getVisibility() == View.VISIBLE) {
            return;
        }
        switch (view.getId()) {
            case R.id.toolbar_action:
                break;
            case R.id.btn_availability_save:
                presenter.saveData(adapter.getSelected());
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSelectedDatesChange(List<Date> dates) {
        stringFormatDelegate.onDateCountTitleChange(buttonSave, dates == null ? 0 : dates.size());
    }

    @Override
    public void showProgress(boolean show) {
        progress.setVisibility(show ? View.VISIBLE : View.GONE);
        calendarView.setAlpha(show ? .5f : 1f);
    }

    @Override
    public void showMessage(@StringRes int messageId) {
        showMessage(getString(messageId));
    }

    @Override
    public void showMessage(String message) {
        if (currentToast != null) {
            currentToast.cancel();
        }
        currentToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        currentToast.show();
    }

    @Override
    public void onDatesRetrieved(List<Date> dates) {
        adapter.setDates(dates);
        stringFormatDelegate.onDateCountTitleChange(buttonSave, dates == null ? 0 : dates.size());
    }

    @Override
    public void onTimeBoundsRetrieved(String startTime, String endTime) {

    }

    @Override
    public void onSaved() {
        finish();
    }

}
