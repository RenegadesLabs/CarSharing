package com.cardee.custom.calendar.view.selection;

import android.support.annotation.NonNull;

import com.cardee.custom.calendar.view.CalendarView;

import java.util.Date;
import java.util.List;

public abstract class RangeSelectionAdapter<T> extends SelectionAdapter<T> {

    private T start;
    private T end;

    public final void setRange(@NonNull T start, @NonNull T end) {
        this.start = start;
        this.end = end;
        notifyDataSetChanged();
    }

    @Override
    public final Date onNext(int position) {
        if (position == 0) {
            return onNext(start);
        } else if (position == 1) {
            return onNext(end);
        } else {
            throw new IllegalArgumentException("Range position: " + position);
        }
    }

    protected abstract Date onNext(T item);

    @Override
    protected final int getMode() {
        return CalendarView.MODE_RANGE;
    }

    @Override
    public final int getItemCount() {
        return start == null || end == null ? 0 : 2;
    }
}
