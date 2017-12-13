package com.cardee.data_source.cache;

import com.cardee.data_source.AvailabilityDataSource;


public class LocalAvailabilityDataSource implements AvailabilityDataSource {

    private static LocalAvailabilityDataSource INSTANCE;

    private LocalAvailabilityDataSource() {

    }

    public static LocalAvailabilityDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LocalAvailabilityDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void saveDailyAvailability(int id, String[] dates, Callback callback) {

    }

    @Override
    public void saveHourlyAvailability(int id, String[] dates, String startTime, String endTime, Callback callback) {

    }

    @Override
    public void saveAvailability(int id, boolean availableDaily, boolean availableHourly, Callback callback) {

    }
}
