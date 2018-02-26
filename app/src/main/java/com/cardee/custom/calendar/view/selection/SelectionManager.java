package com.cardee.custom.calendar.view.selection;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.cardee.custom.calendar.domain.UseCase;
import com.cardee.custom.calendar.domain.calendar.ApplyInitialSelection;
import com.cardee.custom.calendar.domain.criteria.CriteriaFactory;
import com.cardee.custom.calendar.domain.criteria.SelectionCriteria;
import com.cardee.custom.calendar.domain.criteria.SelectionState;
import com.cardee.custom.calendar.domain.executor.UseCaseExecutor;
import com.cardee.custom.calendar.model.Day;
import com.cardee.custom.calendar.model.Month;
import com.cardee.custom.calendar.model.Error;
import com.cardee.custom.calendar.view.CalendarView;
import com.cardee.custom.calendar.view.DayView;
import com.cardee.custom.calendar.view.adapter.MonthAdapter;
import com.cardee.custom.calendar.view.listener.OnViewClickListener;
import com.cardee.custom.time_picker.model.Hour;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SelectionManager implements OnViewClickListener<DayView>, SelectionAdapter.OnAvailableDatesSetListener {

    private enum RangeBound {
        START, END
    }

    private int selectionMode = CalendarView.MODE_MULTISELECT;
    private final UseCaseExecutor executor;
    private final ApplyInitialSelection applySelection;
    private final List<Day> selectedDayz;
    private final List<Day> allDayz;
    private final MonthAdapter adapter;

    private SelectionAdapter selectionAdapter;
    private Day rangeStart;
    private Day rangeEnd;
    private RangeBound lastBound = RangeBound.START;
    private boolean includeCurrent = true;

    private Handler handler = new Handler(Looper.getMainLooper());

    public SelectionManager(MonthAdapter adapter) {
        selectedDayz = new ArrayList<>();
        allDayz = new ArrayList<>();
        executor = new UseCaseExecutor();
        applySelection = new ApplyInitialSelection();
        this.adapter = adapter;
    }

    public void addToPeriod(List<Month> months) {
        adapter.addMonths(months);
        boolean needRefresh = allDayz.isEmpty();
        for (Month month : months) {
            List<Day> dayz = month.getDays();
            allDayz.addAll(dayz);
        }
        if (needRefresh) {
            refresh();
        }
    }

    @Override
    public void onViewClick(DayView view) {
        Day day = view.getDay();
        if (!day.isEnabled() || (day.isCurrent() && !includeCurrent)) {
            return;
        }
        if (selectionMode == CalendarView.MODE_MULTISELECT) {
            proceedMultiselectModeSelection(day, view);
        } else if (selectionMode == CalendarView.MODE_RANGE) {
            proceedRangeModeSelection(day, view);
        }
    }

    private void proceedRangeModeSelection(Day day, DayView view) {
        if (selectedDayz.isEmpty()) {
            selectSingleDayRange(day, view);
        } else {
            switch (lastBound) {
                case START:
                    if (day.compareTo(rangeStart) < 0) {
                        rangeStart = day;
                    } else {
                        rangeEnd = day;
                        lastBound = RangeBound.END;
                    }
                    break;
                case END:
                    if (day.compareTo(rangeEnd) > 0) {
                        rangeEnd = day;
                    } else {
                        rangeStart = day;
                        lastBound = RangeBound.START;
                    }
            }
            if (!rangeStart.equals(rangeEnd) && checkWholeRangeAvailable(rangeStart, rangeEnd)) {
                selectRange(rangeStart, rangeEnd);
            } else {
                clearSelection();
                adapter.notifyDataSetChanged();
                selectSingleDayRange(day, view);
            }
        }
    }

    private boolean checkWholeRangeAvailable(Day rangeStart, Day rangeEnd) {
        int startIndex = allDayz.indexOf(rangeStart);
        int endIndex = allDayz.indexOf(rangeEnd);
        for (int i = startIndex + 1; i < endIndex; i++) {
            Day day = allDayz.get(i);
            if (!day.isEnabled()) {
                return false;
            }
        }
        return true;
    }

    private void selectSingleDayRange(Day day, DayView view) {
        addToSelectionWithState(day, SelectionState.RANGE_SINGLE_DAY);
        rangeStart = day;
        rangeEnd = day;
        view.refresh();
        notifyInternalSubscriber();
    }

    private void selectRange(Day start, Day end) {
        int startIndex = allDayz.indexOf(start);
        int endIndex = allDayz.indexOf(end);
        clearSelection();
        start.setSelectionState(SelectionState.RANGE_START_DAY);
        end.setSelectionState(SelectionState.RANGE_END_DAY);
        selectedDayz.add(start);
        for (int i = startIndex + 1; i < endIndex; i++) {
            Day day = allDayz.get(i);
            day.setSelectionState(SelectionState.RANGE_DAY);
            selectedDayz.add(day);
        }
        selectedDayz.add(end);
        notifyOnChanges();
    }

    private void proceedMultiselectModeSelection(Day day, DayView view) {
        if (day.isSelected()) {
            removeFromSelection(day);
        } else {
            addToSelectionWithState(day, SelectionState.SELECTED);
        }
        notifyInternalSubscriber();
        view.refresh();
    }

    private void addToSelectionWithState(Day day, SelectionState state) {
        day.setSelectionState(state);
        selectedDayz.add(day);
    }

    private void removeFromSelection(Day day) {
        day.setSelectionState(null);
        selectedDayz.remove(day);
    }

    private void initRangeBounds() {
        if (!selectedDayz.isEmpty()) {
            rangeStart = selectedDayz.get(0);
            rangeEnd = selectedDayz.get(selectedDayz.size() - 1);
        }
    }

    public void setSelectionAdapter(SelectionAdapter selectionAdapter) {
        this.selectionMode = selectionAdapter.getMode();
        this.selectionAdapter = selectionAdapter;
        this.selectionAdapter.setSelectionManager(this);
        this.selectionAdapter.setAvailableDatesSetListener(this);
        refresh();
    }

    public OnViewClickListener<DayView> getDayClickListener() {
        return this;
    }

    public void reset() {
        clearSelection();
        notifyOnChanges();
    }

    private void clearSelection() {
        if (selectedDayz.isEmpty()) {
            return;
        }
        for (Day day : selectedDayz) {
            day.setSelectionState(null);
        }
        selectedDayz.clear();
    }

    public void refresh() {
        clearSelection();
        if (selectionAdapter == null || allDayz.isEmpty()) {
            return;
        }
        int count = selectionAdapter.getItemCount();
        List<Date> selection = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Date date = selectionAdapter.onNext(i);
            if (date != null) {
                selection.add(date);
            }
        }
        if (selection.isEmpty()) {
            return;
        }
        SelectionCriteria criteria;
        if (selectionMode == CalendarView.MODE_MULTISELECT) {
            criteria = CriteriaFactory.newMultiselectCriteria(selection, includeCurrent);
        } else if (selectionMode == CalendarView.MODE_RANGE) {
            criteria = CriteriaFactory.newRangeCriteria(selection, includeCurrent);
        } else {
            throw new IllegalArgumentException("Selection mode: " + selectionMode);
        }
        applySelectionAsync(allDayz, criteria);
    }

    private void applySelectionAsync(List<Day> allDayz, SelectionCriteria criteria) {
        ApplyInitialSelection.RequestValues request =
                new ApplyInitialSelection.RequestValues(allDayz, criteria);
        executor.execute(applySelection, request, new UseCase.Callback<ApplyInitialSelection.ResponseValues>() {
            @Override
            public void onSuccess(ApplyInitialSelection.ResponseValues response) {
                List<Day> selection = response.getSelection();
                selectedDayz.addAll(selection);
                initRangeBounds();
                notifyOnChanges();
            }

            @Override
            public void onError(Error error) {
                Log.e(getClass().getSimpleName(), error.getMessage());
            }
        });
    }

    private void notifyOnChanges() {
        adapter.notifyDataSetChanged();
        notifyInternalSubscriber();
    }

    private void notifyInternalSubscriber() {
        if (selectionAdapter != null) {
            selectionAdapter.onSelectionChanged(selectedDayz);
        }
    }

    public void setIncludeCurrent(boolean include) {
        includeCurrent = include;
        new Thread(() -> {
            for (Day day : selectedDayz) {
                if (day.isCurrent()) {
                    if (day.isSelected()) {
                        day.setSelectionState(null);
                    }
                    break;
                }
            }
            handler.post(adapter::notifyDataSetChanged);
        }).start();
    }

    public boolean isReady() {
        return !allDayz.isEmpty();
    }

    @Override
    public void onAvailableDatesSet(List<Day> availableDayz) {
        if(availableDayz.isEmpty()){
            return;
        }
        new Thread(() -> {
            for (int i = 0; i < allDayz.size(); i++) {
                Day day = allDayz.get(i);
                if (day.isEnabled() && !availableDayz.contains(day)) {
                    day.setEnabled(false);
                }
            }
            handler.post(adapter::notifyDataSetChanged);
        }).start();
    }
}
