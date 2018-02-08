package com.cardee.custom.calendar.view.selection;


import com.cardee.custom.calendar.model.Day;
import com.cardee.domain.util.ArrayUtil;
import com.cardee.domain.util.ListUtil;
import com.cardee.domain.util.Mapper;

import java.util.Date;
import java.util.List;

public abstract class SelectionAdapter<T> {

    private SelectionManager manager;
    private List<Day> availableDayz;
    private OnAvailableDatesSetListener availableDatesSetListener;

    public abstract Date onNext(int position);

    protected abstract int getMode();

    void setAvailableDatesSetListener(OnAvailableDatesSetListener listener) {
        availableDatesSetListener = listener;
        if (availableDayz != null && !availableDayz.isEmpty()) {
            availableDatesSetListener.onAvailableDatesSet(availableDayz);
        }
    }

    public void setAvailableDates(Date[] availableDates) {
        availableDayz = transformToEntities(availableDates);
        if (availableDatesSetListener != null) {
            availableDatesSetListener.onAvailableDatesSet(availableDayz);
        }
    }

    private List<Day> transformToEntities(Date[] availableDates) {
        return ArrayUtil.asList(availableDates, Day::from);
    }

    public abstract int getItemCount();

    protected abstract void onSelectionChanged(List<Day> dayz);

    void setSelectionManager(SelectionManager manager) {
        this.manager = manager;
    }

    void notifyDataSetChanged() {
        if (manager != null) {
            manager.refresh();
        }
    }

    interface OnAvailableDatesSetListener {
        void onAvailableDatesSet(List<Day> dates);
    }
}
