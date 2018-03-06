package com.cardee.custom.time_picker.view.selection;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;

import com.cardee.custom.ChangeStrategy;
import com.cardee.custom.time_picker.domain.UseCase;
import com.cardee.custom.time_picker.domain.calendar.ApplyInitialSelection;
import com.cardee.custom.time_picker.domain.criteria.CriteriaFactory;
import com.cardee.custom.time_picker.domain.criteria.SelectionCriteria;
import com.cardee.custom.time_picker.domain.criteria.SelectionState;
import com.cardee.custom.time_picker.domain.executor.UseCaseExecutor;
import com.cardee.custom.time_picker.model.Hour;
import com.cardee.custom.time_picker.model.Error;
import com.cardee.custom.time_picker.model.Day;
import com.cardee.custom.time_picker.view.HourView;
import com.cardee.custom.time_picker.view.TimePicker;
import com.cardee.custom.time_picker.view.adapter.DayAdapter;
import com.cardee.custom.time_picker.view.listener.OnViewClickListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SelectionManager implements
        OnViewClickListener<HourView>, SelectionAdapter.OnAvailableDatesSetListener {

    private enum RangeBound {
        START, END
    }

    private int selectionMode = TimePicker.MODE_MULTISELECT; //default mode is MULTISELECT
    private final UseCaseExecutor executor;
    private final ApplyInitialSelection applySelection;
    private final List<Hour> selectedDayz;
    private final List<Hour> allDayz;
    private final DayAdapter adapter;
    private ChangeStrategy changeStrategy = ChangeStrategy.ANY;
    private Date fixedDate;
    private TimePicker.OnMessageListener messageListener;

    private SelectionAdapter selectionAdapter;
    private Hour rangeStart;
    private Hour rangeEnd;
    private RangeBound lastBound = RangeBound.START;
    private boolean includeCurrent = true;

    private Handler handler = new Handler(Looper.getMainLooper());

    public SelectionManager(DayAdapter adapter) {
        selectedDayz = new ArrayList<>();
        allDayz = new ArrayList<>();
        executor = new UseCaseExecutor();
        applySelection = new ApplyInitialSelection();
        this.adapter = adapter;
    }

    public void setMessageListener(TimePicker.OnMessageListener messageListener) {
        this.messageListener = messageListener;
    }

    public void setChangeStrategy(ChangeStrategy strategy, @Nullable Date fixedDate){
        this.changeStrategy = strategy;
        this.fixedDate = fixedDate;
    }

    public void addToPeriod(List<Day> days) {
        adapter.addMonths(days);
        boolean needRefresh = allDayz.isEmpty();
        for (Day day : days) {
            List<Hour> dayz = day.getHours();
            allDayz.addAll(dayz);
        }
        if (needRefresh) {
            refresh();
        }
    }

    @Override
    public void onViewClick(HourView view) {
        Hour hour = view.getHour();
        if (!hour.isEnabled()) {
            return;
        }
        if (selectionMode == TimePicker.MODE_MULTISELECT) {
            proceedMultiselectModeSelection(hour, view);
        } else if (selectionMode == TimePicker.MODE_RANGE) {
            if (ChangeStrategy.EXTENSION_ONLY.equals(changeStrategy) && fixedDate != null) {
                if (hour.compareTo(fixedDate) < 0) {
                    messageListener.onMessage("You can only extend current period");
                    return;
                }
                lastBound = RangeBound.START;
            }
            proceedRangeModeSelection(hour, view);
        }
    }

    private void proceedRangeModeSelection(Hour hour, HourView view) {
        if (selectedDayz.isEmpty()) {
            selectSingleDayRange(hour, view);
        } else {
            switch (lastBound) {
                case START:
                    if (hour.compareTo(rangeStart) < 0) {
                        rangeStart = hour;
                    } else {
                        rangeEnd = hour;
                        lastBound = RangeBound.END;
                    }
                    break;
                case END:
                    if (hour.compareTo(rangeEnd) > 0) {
                        rangeEnd = hour;
                    } else {
                        rangeStart = hour;
                        lastBound = RangeBound.START;
                    }
            }
            if (!rangeStart.equals(rangeEnd) && checkWholeRangeAvailable(rangeStart, rangeEnd)) {
                selectRange(rangeStart, rangeEnd);
            } else {
                if(ChangeStrategy.EXTENSION_ONLY.equals(changeStrategy)){
                    messageListener.onMessage("Cannot extend range. Car is not available at this period of time");
                    return;
                }
                clearSelection();
                adapter.notifyDataSetChanged();
                selectSingleDayRange(hour, view);
            }
        }
    }

    private boolean checkWholeRangeAvailable(Hour rangeStart, Hour rangeEnd) {
        int startIndex = allDayz.indexOf(rangeStart);
        int endIndex = allDayz.indexOf(rangeEnd);
        for (int i = startIndex + 1; i < endIndex; i++) {
            Hour hour = allDayz.get(i);
            if (!hour.isEnabled()) {
                return false;
            }
        }
        return true;
    }

    private void selectSingleDayRange(Hour hour, HourView view) {
        addToSelectionWithState(hour, SelectionState.RANGE_SINGLE_DAY);
        rangeStart = hour;
        rangeEnd = hour;
        view.refresh();
        notifyInternalSubscriber();
    }

    private void selectRange(Hour start, Hour end) {
        int startIndex = allDayz.indexOf(start);
        int endIndex = allDayz.indexOf(end);
        clearSelection();
        start.setSelectionState(SelectionState.RANGE_START_DAY);
        end.setSelectionState(SelectionState.RANGE_END_DAY);
        selectedDayz.add(start);
        for (int i = startIndex + 1; i < endIndex; i++) {
            Hour hour = allDayz.get(i);
            hour.setSelectionState(SelectionState.RANGE_DAY);
            selectedDayz.add(hour);
        }
        selectedDayz.add(end);
        notifyOnChanges();
    }

    private void proceedMultiselectModeSelection(Hour hour, HourView view) {
        if (hour.isSelected()) {
            removeFromSelection(hour);
        } else {
            addToSelectionWithState(hour, SelectionState.SELECTED);
        }
        notifyInternalSubscriber();
        view.refresh();
    }

    private void addToSelectionWithState(Hour hour, SelectionState state) {
        hour.setSelectionState(state);
        selectedDayz.add(hour);
    }

    private void removeFromSelection(Hour hour) {
        hour.setSelectionState(null);
        selectedDayz.remove(hour);
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

    public OnViewClickListener<HourView> getDayClickListener() {
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
        for (Hour hour : selectedDayz) {
            hour.setSelectionState(null);
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
        if (selectionMode == TimePicker.MODE_MULTISELECT) {
            criteria = CriteriaFactory.newMultiselectCriteria(selection, includeCurrent);
        } else if (selectionMode == TimePicker.MODE_RANGE) {
            criteria = CriteriaFactory.newRangeCriteria(selection, includeCurrent);
        } else {
            throw new IllegalArgumentException("Selection mode: " + selectionMode);
        }
        applySelectionAsync(allDayz, criteria);
    }

    private void applySelectionAsync(List<Hour> allDayz, SelectionCriteria criteria) {
        ApplyInitialSelection.RequestValues request =
                new ApplyInitialSelection.RequestValues(allDayz, criteria);
        executor.execute(applySelection, request, new UseCase.Callback<ApplyInitialSelection.ResponseValues>() {
            @Override
            public void onSuccess(ApplyInitialSelection.ResponseValues response) {
                List<Hour> selection = response.getSelection();
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
            for (Hour hour : selectedDayz) {
                if (hour.isSelected()) {
                    hour.setSelectionState(null);
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
    public void onAvailableDatesSet(List<Hour> availableHourz) {
        if(availableHourz.isEmpty()) {
            return;
        }
        new Thread(() -> {
            Hour lastHour = availableHourz.get(availableHourz.size() - 1);
            for (int i = 0; i < allDayz.size(); i++) {
                Hour hour = allDayz.get(i);
                if (hour.isEnabled() && !availableHourz.contains(hour)) {
                    hour.setEnabled(false);
                }
            }
            handler.post(adapter::notifyDataSetChanged);
        }).start();
    }
}
