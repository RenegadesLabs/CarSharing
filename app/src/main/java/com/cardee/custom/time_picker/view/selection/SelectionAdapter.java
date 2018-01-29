package com.cardee.custom.time_picker.view.selection;


import com.cardee.custom.time_picker.model.Hour;

import java.util.Date;
import java.util.List;

public abstract class SelectionAdapter<T> {

    private SelectionManager manager;

    public abstract Date onNext(int position);

    protected abstract int getMode();

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
}
