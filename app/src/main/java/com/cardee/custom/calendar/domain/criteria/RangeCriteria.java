package com.cardee.custom.calendar.domain.criteria;

import com.cardee.custom.calendar.model.Day;

import java.util.Calendar;
import java.util.Date;

public class RangeCriteria implements SelectionCriteria {

    private final Day startDay;
    private final Day endDay;

    RangeCriteria(Date start, Date end, boolean includeCurrent) {
        Date currentDate = new Date();
        Day currentDay = Day.from(currentDate);
        Day temporaryStartDay = Day.from(start);
        int compare = temporaryStartDay.compareTo(currentDay);
        if (compare <= 0) {
            if (includeCurrent) {
                startDay = currentDay;
            } else {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(currentDate);
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                startDay = Day.from(calendar.getTime());
            }
        } else {
            startDay = temporaryStartDay;
        }
        endDay = Day.from(end);
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
