package com.cardee.custom.calendar.view.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.cardee.custom.calendar.model.Month;
import com.cardee.custom.calendar.view.config.BodyConfig;

import java.util.ArrayList;
import java.util.List;

public class MonthAdapter extends RecyclerView.Adapter<MonthHolder> {

    private final MonthViewHolderDelegate delegate;
    private List<Month> monthList;

    public MonthAdapter(BodyConfig config) {
        delegate = new MonthViewHolderDelegate(config);
        monthList = new ArrayList<>();
    }

    @Override
    public MonthHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return delegate.onCreateViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(MonthHolder holder, int position) {
        holder.onBind(monthList.get(position));
    }

    @Override
    public int getItemCount() {
        return monthList.size();
    }

    public void addMonths(List<Month> months) {
        int startIndex = monthList.size();
        monthList.addAll(months);
        notifyItemRangeInserted(startIndex, months.size());
    }
}
