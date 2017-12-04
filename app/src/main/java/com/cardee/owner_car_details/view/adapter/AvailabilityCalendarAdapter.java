package com.cardee.owner_car_details.view.adapter;

import android.util.Log;

import com.cardee.custom.calendar.model.Day;
import com.cardee.custom.calendar.view.selection.MultipleSelectionAdapter;
import com.cardee.custom.calendar.view.selection.RangeSelectionAdapter;
import com.cardee.owner_car_details.view.listener.AvailabilityCalendarListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class AvailabilityCalendarAdapter extends MultipleSelectionAdapter<String> {

    private static final String TAG = AvailabilityCalendarAdapter.class.getSimpleName();
    private static final String DATE_PATTERN = "yyyy-MM-dd";

    private List<String> dates;
    private SimpleDateFormat dateFormat;
    private AvailabilityCalendarListener listener;

    public AvailabilityCalendarAdapter() {
        dateFormat = new SimpleDateFormat(DATE_PATTERN, Locale.getDefault());
    }

    @Override
    protected void onSelectionChanged(List<Day> dayz) {
        if (listener != null && dayz != null && !dayz.isEmpty()) {
            String[] dates = new String[dayz.size()];
            for (int i = 0; i < dates.length; i++) {
                dates[i] = dateFormat.format(dayz.get(i).getCalendarTime());
            }
            listener.onSelectedDatesChange(dates);
        }
    }

    @Override
    protected Date onNext(String item) {
        Date date = null;
        try {
            date = dateFormat.parse(item);
        } catch (ParseException e) {
            Log.e(TAG, e.getMessage());
        }
        return date;
    }

    public void setDates(List<String> dates) {
        this.dates = dates;
        setSelection(dates);
    }

    public void setListener(AvailabilityCalendarListener listener) {
        this.listener = listener;
    }
}
