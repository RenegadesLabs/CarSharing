package com.cardee.calendar.view.selection;


import com.cardee.calendar.model.Day;

import java.util.Date;
import java.util.List;

public abstract class SelectionAdapter<T> {

    private SelectionManager manager;

    public abstract Date onNext(int position);

    protected abstract int getMode();

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
}
