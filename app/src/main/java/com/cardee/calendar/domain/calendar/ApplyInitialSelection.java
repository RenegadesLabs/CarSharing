package com.cardee.calendar.domain.calendar;


import com.cardee.calendar.domain.UseCase;
import com.cardee.calendar.domain.criteria.SelectionCriteria;
import com.cardee.calendar.model.Day;
import com.cardee.calendar.model.Error;

import java.util.ArrayList;
import java.util.List;

public class ApplyInitialSelection
        implements UseCase<ApplyInitialSelection.RequestValues, ApplyInitialSelection.ResponseValues> {

    @Override
    public void execute(RequestValues request, Callback<ResponseValues> callback) {
        List<Day> dayz = request.getDayz();
        SelectionCriteria criteria = request.getCriteria();
        if (dayz == null || criteria == null) {
            callback.onError(new Error("Bad request: list = " + dayz +
                    "; criteria = " + criteria, Error.Type.BAD_REQUEST));
            return;
        }
        List<Day> selection = new ArrayList<>();
        for (Day day : dayz) {
            Day modified = criteria.applyTo(day);
            if (modified.isSelected()) {
                selection.add(modified);
            }
        }
        ResponseValues response = new ResponseValues(selection);
        callback.onSuccess(response);
    }

    public static class RequestValues implements UseCase.RequestValues {

        private final List<Day> dayz;
        private final SelectionCriteria criteria;

        public RequestValues(List<Day> dayz, SelectionCriteria criteria) {
            this.dayz = dayz;
            this.criteria = criteria;
        }

        public List<Day> getDayz() {
            return dayz;
        }

        public SelectionCriteria getCriteria() {
            return criteria;
        }
    }

    public static class ResponseValues implements UseCase.ResponseValues {
        private final List<Day> selection;

        public ResponseValues(List<Day> selection) {
            this.selection = selection;
        }

        public List<Day> getSelection() {
            return selection;
        }
    }
}
