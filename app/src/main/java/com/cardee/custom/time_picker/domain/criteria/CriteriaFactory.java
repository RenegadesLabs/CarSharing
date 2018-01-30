package com.cardee.custom.time_picker.domain.criteria;

import android.support.annotation.NonNull;

import java.util.Date;
import java.util.List;

public class CriteriaFactory {

    public static SelectionCriteria newRangeCriteria(@NonNull List<Date> dates, boolean includeCurrent) {
        return new RangeCriteria(dates.get(0), dates.get(dates.size() - 1), includeCurrent);
    }

    public static SelectionCriteria newMultiselectCriteria(@NonNull List<Date> dates, boolean includeCurrent) {
        return new MultiselectCriteria(dates, includeCurrent);
    }
}
