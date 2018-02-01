package com.cardee.custom.time_picker.domain.calendar;


import com.cardee.custom.time_picker.domain.UseCase;
import com.cardee.custom.time_picker.domain.criteria.SelectionCriteria;
import com.cardee.custom.time_picker.model.Hour;
import com.cardee.custom.time_picker.model.Error;

import java.util.ArrayList;
import java.util.List;

public class ApplyInitialSelection
        implements UseCase<ApplyInitialSelection.RequestValues, ApplyInitialSelection.ResponseValues> {

    @Override
    public void execute(RequestValues request, Callback<ResponseValues> callback) {
        List<Hour> dayz = request.getDayz();
        SelectionCriteria criteria = request.getCriteria();
        if (dayz == null || criteria == null) {
            callback.onError(new Error("Bad request: list = " + dayz +
                    "; criteria = " + criteria, Error.Type.BAD_REQUEST));
            return;
        }
        List<Hour> selection = new ArrayList<>();
        for (Hour hour : dayz) {
            Hour modified = criteria.applyTo(hour);
            if (modified.isSelected()) {
                selection.add(modified);
            }
        }
        ResponseValues response = new ResponseValues(selection);
        callback.onSuccess(response);
    }

    public static class RequestValues implements UseCase.RequestValues {

        private final List<Hour> dayz;
        private final SelectionCriteria criteria;

        public RequestValues(List<Hour> dayz, SelectionCriteria criteria) {
            this.dayz = dayz;
            this.criteria = criteria;
        }

        public List<Hour> getDayz() {
            return dayz;
        }

        public SelectionCriteria getCriteria() {
            return criteria;
        }
    }

    public static class ResponseValues implements UseCase.ResponseValues {
        private final List<Hour> selection;

        public ResponseValues(List<Hour> selection) {
            this.selection = selection;
        }

        public List<Hour> getSelection() {
            return selection;
        }
    }
}
