package com.cardee.calendar.domain.calendar;

import com.cardee.calendar.domain.UseCase;
import com.cardee.calendar.model.Day;
import com.cardee.calendar.model.Month;
import com.cardee.calendar.model.Error;

import java.util.List;


public class GenerateCalendarModel
        implements UseCase<GenerateCalendarModel.RequestValues, GenerateCalendarModel.ResponseValues> {

    private final GenerateCalendarDelegate delegate;

    public GenerateCalendarModel() {
        delegate = new GenerateCalendarDelegate();
    }

    @Override
    public void execute(RequestValues request, Callback<ResponseValues> callback) {
        if (request == null) {
            callback.onError(new Error("Request instance: null", Error.Type.BAD_REQUEST));
            return;
        }
        List<Month> months;
        if (request.isCurrentMonth()) {
            months = delegate.onGenerateFromCurrent(request.getRange());
        } else {
            months = delegate.onGenerateFromNextToDate(request.getRange(), request.getDay());
        }
        if (months == null || months.isEmpty()) {
            callback.onError(new Error("Calendar is not available", Error.Type.UNAVAILABLE));
            return;
        }
        callback.onSuccess(new ResponseValues(months));
    }

    public static class RequestValues implements UseCase.RequestValues {

        public static final int DEFAULT_MONTH_COUNT = 12;

        private final Day dayFromPrevious;
        private final boolean currentMonth;
        private final int range;

        public RequestValues() {
            this(DEFAULT_MONTH_COUNT);
        }

        public RequestValues(int range) {
            this(range, null);
        }

        public RequestValues(int range, Day dayFromPrevious) {
            this.dayFromPrevious = dayFromPrevious;
            this.currentMonth = dayFromPrevious == null;
            this.range = range;
        }

        Day getDay() {
            return dayFromPrevious;
        }

        public boolean isCurrentMonth() {
            return currentMonth;
        }

        public int getRange() {
            return range;
        }
    }

    public static class ResponseValues implements UseCase.ResponseValues {

        private final List<Month> months;

        public ResponseValues(List<Month> months) {
            this.months = months;
        }

        public List<Month> getMonths() {
            return months;
        }
    }
}
