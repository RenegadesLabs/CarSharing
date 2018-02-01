package com.cardee.custom.time_picker.view.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.cardee.custom.time_picker.model.Day;
import com.cardee.custom.time_picker.view.config.BodyConfig;

import java.util.ArrayList;
import java.util.List;

public class DayAdapter extends RecyclerView.Adapter<DayHolder> {

    private final DayHolderDelegate delegate;
    private List<Day> dayList;

    public DayAdapter(BodyConfig config) {
        delegate = new DayHolderDelegate(config);
        dayList = new ArrayList<>();
    }

    @Override
    public DayHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return delegate.onCreateViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(DayHolder holder, int position) {
        holder.onBind(dayList.get(position));
    }

    @Override
    public int getItemCount() {
        return dayList.size();
    }

    public void addMonths(List<Day> days) {
        int startIndex = dayList.size();
        dayList.addAll(days);
        notifyItemRangeInserted(startIndex, days.size());
    }
}
