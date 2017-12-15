package com.cardee.domain.owner.usecase;

import com.cardee.domain.UseCase;

import java.util.Date;
import java.util.List;

public class SaveHourlyDates implements UseCase<SaveHourlyDates.RequestValues, SaveHourlyDates.ResponseValues> {


    @Override
    public void execute(RequestValues values, Callback<ResponseValues> callback) {

    }

    public static class RequestValues implements UseCase.RequestValues {
        private final int id;
        private final String startTime;
        private final String endTime;
        private final List<Date> dates;

        public RequestValues(int id, String startTime, String endTime, List<Date> dates) {
            this.id = id;
            this.startTime = startTime;
            this.endTime = endTime;
            this.dates = dates;
        }

        public int getId() {
            return id;
        }

        public String getStartTime() {
            return startTime;
        }

        public String getEndTime() {
            return endTime;
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
