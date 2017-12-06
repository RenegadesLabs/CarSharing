package com.cardee.custom.calendar.domain.criteria;

import com.cardee.custom.calendar.model.Day;

public interface SelectionCriteria {

    enum Type {
        RANGE, MULTISELECT
    }

    Type getType();

    boolean contains(Day day);

    Day applyTo(Day day);
}
