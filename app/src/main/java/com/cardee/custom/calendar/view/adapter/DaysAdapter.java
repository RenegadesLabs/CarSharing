package com.cardee.custom.calendar.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.cardee.custom.calendar.model.Day;
import com.cardee.custom.calendar.view.DayView;
import com.cardee.custom.calendar.view.config.BodyConfig;
import com.cardee.custom.calendar.view.listener.OnViewClickListener;

import java.util.ArrayList;
import java.util.List;

public class DaysAdapter extends RecyclerView.Adapter<DaysAdapter.DayHolder>
        implements View.OnClickListener {

    private List<Day> dayz;
    private BodyConfig config;
    private OnViewClickListener<DayView> listener;

    public DaysAdapter(BodyConfig config) {
        this.config = config;
        listener = config.getDayClickListener();
        dayz = new ArrayList<>();
    }

    @Override
    public DayHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        DayView dayView = new DayView(parent.getContext());
        dayView.setOnClickListener(this);
        return new DayHolder(dayView, config);
    }

    @Override
    public void onBindViewHolder(DayHolder holder, int position) {
        holder.onBind(dayz.get(position));
    }

    @Override
    public int getItemCount() {
        return dayz.size();
    }

    public void setDays(List<Day> dayz) {
        this.dayz.clear();
        Day day = dayz.get(0);
        int dayOfWeed = day.getDayOfWeed();
        if (dayOfWeed == 7) {
            dayOfWeed = 1;
        }
        for (int i = 1; i < dayOfWeed; i++) {
            this.dayz.add(Day.empty());
        }
        this.dayz.addAll(dayz);
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            DayView dayView = (DayView) view;
            if (dayView.getDay() != null) {
                listener.onViewClick(dayView);
            }
        }
    }

    public static class DayHolder extends RecyclerView.ViewHolder {

        private DayView dayView;

        public DayHolder(View itemView, BodyConfig config) {
            super(itemView);
            dayView = (DayView) itemView;
            dayView.setSelectionColor(config.getSelectionColor());
            dayView.setTodayColor(config.getCurrentDateColor());
            dayView.setIdleTextColor(config.getBodyTextColor());
        }

        public void onBind(Day model) {
            dayView.bind(model);
            dayView.refresh();
        }
    }
}
