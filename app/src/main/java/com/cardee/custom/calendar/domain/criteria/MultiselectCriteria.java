package com.cardee.custom.calendar.domain.criteria;

import com.cardee.custom.calendar.model.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MultiselectCriteria implements SelectionCriteria {

    private final List<Day> days;
    private final Day startDay;
    private final Day endDay;
    private boolean empty;

    /**
     * @param dates - should be sorted for binary search
     */
    MultiselectCriteria(List<Date> dates, boolean includeCurrent) {
        Day currentDay = Day.from(new Date());
        days = new ArrayList<>();
        for (int i = 0; i < dates.size(); i++) {
            Day day = Day.from(dates.get(i));
            int compare = day.compareTo(currentDay);
            if ((includeCurrent && compare == 0) || compare <= 0) {
                continue;
            }
            days.add(day);
        }
        empty = days.size() == 0;
        if (!empty) {
            startDay = days.get(0);
            endDay = days.get(days.size() - 1);
        } else {
            startDay = null;
            endDay = null;
        }
    }

    @Override
    public Type getType() {
        return Type.MULTISELECT;
    }

    @Override
    public boolean contains(Day day) {
        if (empty) {
            return false;
        }
        int startCompare = day.compareTo(startDay);
        int endCompare = day.compareTo(endDay);
        if (startCompare < 0 || endCompare > 0) {
            return false;
        }
        int index = Collections.binarySearch(days, day);
        return index >= 0;
    }

    @Override
    public Day applyTo(Day day) {
        if (contains(day)) {
            day.setSelectionState(SelectionState.SELECTED);
        }
        return day;
    }
}
