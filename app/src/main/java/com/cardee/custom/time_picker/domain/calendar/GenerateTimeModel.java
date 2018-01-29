package com.cardee.custom.time_picker.domain.calendar;

import com.cardee.custom.time_picker.domain.UseCase;
import com.cardee.custom.time_picker.model.Error;
import com.cardee.custom.time_picker.model.Day;

import java.util.List;


public class GenerateTimeModel
        implements UseCase<GenerateTimeModel.RequestValues, GenerateTimeModel.ResponseValues> {

    private final GenerateDaysDelegate delegate;

    public GenerateTimeModel() {
        delegate = new GenerateDaysDelegate();
    }

    @Override
    public void execute(RequestValues request, Callback<ResponseValues> callback) {
        if (request == null) {
            callback.onError(new Error("Request instance: null", Error.Type.BAD_REQUEST));
            return;
        }
        List<Day> days;
        days = delegate.onGenerateFromCurrent(request.getRange());
        if (days == null || days.isEmpty()) {
            callback.onError(new Error("Calendar is not available", Error.Type.UNAVAILABLE));
            return;
        }
        callback.onSuccess(new ResponseValues(days));
    }

    public static class RequestValues implements UseCase.RequestValues {

        public static final int DAY_COUNT = 90;

        private final int range;

        public RequestValues() {
            this(DAY_COUNT);
        }

        public RequestValues(int range) {
            this.range = range;
        }

        public int getRange() {
            return range;
        }
    }

    public static class ResponseValues implements UseCase.ResponseValues {

        private final List<Day> days;

        public ResponseValues(List<Day> days) {
            this.days = days;
        }

        public List<Day> getDays() {
            return days;
        }
    }
}
