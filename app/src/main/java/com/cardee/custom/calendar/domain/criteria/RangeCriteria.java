package com.cardee.custom.calendar.domain.criteria;

import com.cardee.custom.calendar.model.Day;

import java.util.Date;

public class RangeCriteria implements SelectionCriteria {

    private final Day startDay;
    private final Day endDay;

    RangeCriteria(Date startDate, Date endDate) {
        this.startDay = Day.from(startDate);
        this.endDay = Day.from(endDate);
    }

    @Override
    public Type getType() {
        return Type.RANGE;
    }

    @Override
    public boolean contains(Day day) {
        return day.compareTo(startDay) > -1 && day.compareTo(endDay) < 1;
    }

    @Override
    public Day applyTo(Day day) {
        int startCompare = day.compareTo(startDay);
        int endCompare = day.compareTo(endDay);
        if (startCompare > 0 && endCompare < 0) {
            day.setSelectionState(SelectionState.RANGE_DAY);
            return day;
        }
        if (startCompare == 0 && endCompare == 0) {
            day.setSelectionState(SelectionState.RANGE_SINGLE_DAY);
            return day;
        }
        if (startCompare == 0) {
            day.setSelectionState(SelectionState.RANGE_START_DAY);
        } else if (endCompare == 0) {
            day.setSelectionState(SelectionState.RANGE_END_DAY);
        }
        return day;
    }

}
