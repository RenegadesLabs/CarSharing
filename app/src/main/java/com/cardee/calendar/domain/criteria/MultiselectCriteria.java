package com.cardee.calendar.domain.criteria;

import com.cardee.calendar.model.Day;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MultiselectCriteria implements SelectionCriteria {

    private final Day[] days;
    private final Day startDay;
    private final Day endDay;
    private boolean empty;

    /**
     *
     * @param dates - should be sorted for binary search
     */
    MultiselectCriteria(List<Date> dates) {
        days = new Day[dates.size()];
        for(int i = 0; i < days.length; i++){
            days[i] = Day.from(dates.get(i));
        }
        empty = days.length == 0;
        if (!empty) {
            startDay = days[0];
            endDay = days[days.length - 1];
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
        int index = Arrays.binarySearch(days, day);
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
