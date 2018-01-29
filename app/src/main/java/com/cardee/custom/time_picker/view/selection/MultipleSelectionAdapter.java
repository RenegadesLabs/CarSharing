package com.cardee.custom.time_picker.view.selection;

import android.support.annotation.NonNull;

import com.cardee.custom.time_picker.view.TimePicker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class MultipleSelectionAdapter<T> extends SelectionAdapter<T> {

    private List<T> selection;

    public MultipleSelectionAdapter() {
        selection = new ArrayList<>();
    }

    @Override
    public final Date onNext(int position) {
        return onNext(selection.get(position));
    }

    protected abstract Date onNext(T item);

    public final void setSelection(@NonNull List<T> selection) {
        this.selection.addAll(selection);
        notifyDataSetChanged();

    }

    @Override
    protected final int getMode() {
        return TimePicker.MODE_MULTISELECT;
    }

    @Override
    public final int getItemCount() {
        return selection.size();
    }
}
