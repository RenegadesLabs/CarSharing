package com.cardee.custom.time_picker.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.cardee.R;
import com.cardee.custom.time_picker.model.Day;

class DayHolder extends RecyclerView.ViewHolder {

    private static final String TAG = DayHolder.class.getSimpleName();

    private TextView titleView;
    private RecyclerView bodyView;
    private HourAdapter adapter;
    private String[] titles;

    public DayHolder(View itemView) {
        super(itemView);
        titles = itemView.getResources().getStringArray(R.array.month_titles);
        titleView = itemView.findViewById(R.id.month_title);
        bodyView = itemView.findViewById(R.id.month_body);
        adapter = (HourAdapter) bodyView.getAdapter();
    }

    public void onBind(Day model) {
        String title;
        try {
            title = titles[model.getNumber()] + " " + model.getYear();
        } catch (ArrayIndexOutOfBoundsException ex) {
            Log.e(TAG, ex.getMessage());
            title = "January 1970";
        }
        titleView.setText(title);
        adapter.setDays(model.getHours());
    }
}
