package com.cardee.custom.time_picker.domain.criteria;

import com.cardee.custom.time_picker.model.Hour;

import java.util.Calendar;
import java.util.Date;

public class RangeCriteria implements SelectionCriteria {

    private final Hour startHour;
    private final Hour endHour;

    RangeCriteria(Date start, Date end, boolean includeCurrent) {
        Date currentDate = new Date();
        Hour currentHour = Hour.from(currentDate);
        Hour temporaryStartHour = Hour.from(start);
        int compare = temporaryStartHour.compareTo(currentHour);
        if (compare <= 0) {
            if (includeCurrent) {
                startHour = currentHour;
            } else {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(currentDate);
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                startHour = Hour.from(calendar.getTime());
            }
        } else {
            startHour = temporaryStartHour;
        }
        endHour = Hour.from(end);
    }

    @Override
    public Type getType() {
        return Type.RANGE;
    }

    @Override
    public boolean contains(Hour hour) {
        return hour.compareTo(startHour) > -1 && hour.compareTo(endHour) < 1;
    }

    @Override
    public Hour applyTo(Hour hour) {
        int startCompare = hour.compareTo(startHour);
        int endCompare = hour.compareTo(endHour);
        if (startCompare > 0 && endCompare < 0) {
            hour.setSelectionState(SelectionState.RANGE_DAY);
            return hour;
        }
        if (startCompare == 0 && endCompare == 0) {
            hour.setSelectionState(SelectionState.RANGE_SINGLE_DAY);
            return hour;
        }
        if (startCompare == 0) {
            hour.setSelectionState(SelectionState.RANGE_START_DAY);
        } else if (endCompare == 0) {
            hour.setSelectionState(SelectionState.RANGE_END_DAY);
        }
        return hour;
    }

}
