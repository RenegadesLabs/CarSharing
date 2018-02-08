package com.cardee.custom.time_picker.view.selection;


import com.cardee.custom.time_picker.model.Hour;
import com.cardee.domain.util.ArrayUtil;

import java.util.Date;
import java.util.List;

public abstract class SelectionAdapter<T> {

    private SelectionManager manager;
    private List<Hour> availableHourz;
    private OnAvailableDatesSetListener availableDatesSetListener;

    public abstract Date onNext(int position);

    protected abstract int getMode();

    void setAvailableDatesSetListener(OnAvailableDatesSetListener listener) {
        availableDatesSetListener = listener;
        if (availableHourz != null && !availableHourz.isEmpty()) {
            availableDatesSetListener.onAvailableDatesSet(availableHourz);
        }
    }

    public void setAvailableDates(Date[] availableDates) {
        availableHourz = transformToEntities(availableDates);
        if (availableDatesSetListener != null) {
            availableDatesSetListener.onAvailableDatesSet(availableHourz);
        }
    }

    private List<Hour> transformToEntities(Date[] availableDates) {
        return ArrayUtil.asList(availableDates, Hour::from);
    }

    public abstract int getItemCount();

    protected abstract void onSelectionChanged(List<Hour> dayz);

    void setSelectionManager(SelectionManager manager) {
        this.manager = manager;
    }

    void notifyDataSetChanged() {
        if (manager != null) {
            manager.refresh();
        }
    }

    interface OnAvailableDatesSetListener {
        void onAvailableDatesSet(List<Hour> hourz);
    }
}
