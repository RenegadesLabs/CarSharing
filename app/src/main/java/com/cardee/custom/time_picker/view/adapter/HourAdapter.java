package com.cardee.custom.time_picker.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.cardee.custom.time_picker.model.Hour;
import com.cardee.custom.time_picker.view.HourView;
import com.cardee.custom.time_picker.view.config.BodyConfig;
import com.cardee.custom.time_picker.view.listener.OnViewClickListener;

import java.util.ArrayList;
import java.util.List;

public class HourAdapter extends RecyclerView.Adapter<HourAdapter.DayHolder>
        implements View.OnClickListener {

    private List<Hour> dayz;
    private BodyConfig config;
    private OnViewClickListener<HourView> listener;

    public HourAdapter(BodyConfig config) {
        this.config = config;
        listener = config.getDayClickListener();
        dayz = new ArrayList<>();
    }

    @Override
    public DayHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        HourView hourView = new HourView(parent.getContext());
        hourView.setOnClickListener(this);
        return new DayHolder(hourView, config);
    }

    @Override
    public void onBindViewHolder(DayHolder holder, int position) {
        holder.onBind(dayz.get(position));
    }

    @Override
    public int getItemCount() {
        return dayz.size();
    }

    public void setDays(List<Hour> dayz) {
        this.dayz.clear();
        this.dayz.addAll(dayz);
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            HourView hourView = (HourView) view;
            if (hourView.getHour() != null) {
                listener.onViewClick(hourView);
            }
        }
    }

    public static class DayHolder extends RecyclerView.ViewHolder {

        private HourView hourView;

        public DayHolder(View itemView, BodyConfig config) {
            super(itemView);
            hourView = (HourView) itemView;
            hourView.setSelectionColor(config.getSelectionColor());
            hourView.setTodayColor(config.getCurrentDateColor());
            hourView.setIdleTextColor(config.getBodyTextColor());
        }

        public void onBind(Hour model) {
            hourView.bind(model);
            hourView.refresh();
        }
    }
}
