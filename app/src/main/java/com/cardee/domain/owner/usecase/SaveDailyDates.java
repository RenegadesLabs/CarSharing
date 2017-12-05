package com.cardee.domain.owner.usecase;


import com.cardee.domain.UseCase;

import java.util.Date;
import java.util.List;

public class SaveDailyDates implements UseCase<SaveDailyDates.RequestValues, SaveDailyDates.ResponseValues> {


    @Override
    public void execute(RequestValues values, Callback<ResponseValues> callback) {

    }

    public static class RequestValues implements UseCase.RequestValues {
        private final int id;
        private final List<Date> dates;

        public RequestValues(int id, List<Date> dates) {
            this.id = id;
            this.dates = dates;
        }

        public int getId() {
            return id;
        }

        public List<Date> getDates() {
            return dates;
        }
    }

    public static class ResponseValues implements UseCase.ResponseValues {
        private final boolean successful;

        public ResponseValues(boolean successful) {
            this.successful = successful;
        }

        public boolean isSuccessful() {
            return successful;
        }
    }
}
