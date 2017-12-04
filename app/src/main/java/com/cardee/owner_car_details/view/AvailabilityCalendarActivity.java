package com.cardee.owner_car_details.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.cardee.R;
import com.cardee.custom.calendar.view.CalendarView;
import com.cardee.owner_car_details.view.adapter.AvailabilityCalendarAdapter;
import com.cardee.owner_car_details.view.listener.AvailabilityCalendarListener;

public class AvailabilityCalendarActivity extends AppCompatActivity
        implements View.OnClickListener, AvailabilityCalendarListener {

    private TextView titleView;
    private View actionView;
    private CalendarView calendarView;
    private TextView buttonSave;
    private AvailabilityCalendarAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_availability_calendar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        titleView = toolbar.findViewById(R.id.toolbar_title);
        actionView = toolbar.findViewById(R.id.toolbar_action);
        if (getSupportActionBar() == null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        buttonSave = (TextView) findViewById(R.id.btn_availability_save);
        buttonSave.setOnClickListener(this);
        actionView.setOnClickListener(this);
        calendarView = (CalendarView) findViewById(R.id.availability_calendar);
        adapter = new AvailabilityCalendarAdapter();
        calendarView.setSelectionAdapter(adapter);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onSelectedDatesChange(String[] dates) {

    }
}
