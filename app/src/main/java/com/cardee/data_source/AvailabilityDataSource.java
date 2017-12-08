package com.cardee.data_source;

public interface AvailabilityDataSource {

    void saveDailyAvailability(int id, String[] dates, Callback callback);

    void saveHourlyAvailability(int id, String[] dates, String startTime, String endTime, Callback callback);

    void saveDailyTiming(int id, String pickupTime, String returnTime, Callback callback);

    void saveAvailability(int id, boolean availableDaily, boolean availableHourly, Callback callback);

    interface Callback {
        void onSuccess();

        void onError(Error error);
    }
}
