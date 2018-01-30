package com.cardee.custom.time_picker.domain.criteria;

import com.cardee.custom.time_picker.model.Hour;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MultiselectCriteria implements SelectionCriteria {

    private final List<Hour> hours;
    private final Hour startHour;
    private final Hour endHour;
    private boolean empty;

    /**
     * @param dates - should be sorted for binary search
     */
    MultiselectCriteria(List<Date> dates, boolean includeCurrent) {
        Hour currentHour = Hour.from(new Date());
        Collections.sort(dates);
        hours = new ArrayList<>();
        for (int i = 0; i < dates.size(); i++) {
            Hour hour = Hour.from(dates.get(i));
            int compare = hour.compareTo(currentHour);
            if (compare < 0 || (!includeCurrent && compare == 0)) {
                continue;
            }
            hours.add(hour);
        }
        empty = hours.size() == 0;
        if (!empty) {
            startHour = hours.get(0);
            endHour = hours.get(hours.size() - 1);
        } else {
            startHour = null;
            endHour = null;
        }
    }

    @Override
    public Type getType() {
        return Type.MULTISELECT;
    }

    @Override
    public boolean contains(Hour hour) {
        if (empty) {
            return false;
        }
        int startCompare = hour.compareTo(startHour);
        int endCompare = hour.compareTo(endHour);
        if (startCompare < 0 || endCompare > 0) {
            return false;
        }
        int index = Collections.binarySearch(hours, hour);
        return index >= 0;
    }

    @Override
    public Hour applyTo(Hour hour) {
        if (contains(hour)) {
            hour.setSelectionState(SelectionState.SELECTED);
        }
        return hour;
    }
}
