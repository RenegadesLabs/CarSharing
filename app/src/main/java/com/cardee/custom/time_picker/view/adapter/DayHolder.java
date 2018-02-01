package com.cardee.custom.time_picker.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.cardee.R;
import com.cardee.custom.time_picker.model.Day;

class DayHolder extends RecyclerView.ViewHolder {

    private TextView titleView;
    private RecyclerView bodyView;
    private HourAdapter adapter;

    public DayHolder(View itemView) {
        super(itemView);
        titleView = itemView.findViewById(R.id.month_title);
        bodyView = itemView.findViewById(R.id.month_body);
        adapter = (HourAdapter) bodyView.getAdapter();
    }

    public void onBind(Day model) {
        titleView.setText(model.getDateTitle());
        adapter.setDays(model.getHours());
    }
}
