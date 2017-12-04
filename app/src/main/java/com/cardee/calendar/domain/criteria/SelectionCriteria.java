package com.cardee.calendar.domain.criteria;

import com.cardee.calendar.model.Day;

public interface SelectionCriteria {

    enum Type {
        RANGE, MULTISELECT
    }

    Type getType();

    boolean contains(Day day);

    Day applyTo(Day day);
}
