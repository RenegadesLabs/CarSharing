package com.cardee.custom.time_picker.domain.criteria;

import com.cardee.custom.time_picker.model.Hour;

public interface SelectionCriteria {

    enum Type {
        RANGE, MULTISELECT
    }

    Type getType();

    boolean contains(Hour hour);

    Hour applyTo(Hour hour);
}
