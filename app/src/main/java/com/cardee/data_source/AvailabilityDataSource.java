package com.cardee.data_source;

public interface AvailabilityDataSource {

    void saveDailyAvailability(int id, String[] dates);

    void seveHourlyAvailability(int id, String[] dates, String startTime, String endTime);

    interface Callback {
        void onSuccess();

        void onError(Error error);
    }
}
