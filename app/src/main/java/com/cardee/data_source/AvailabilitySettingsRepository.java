package com.cardee.data_source;


public class AvailabilitySettingsRepository implements AvailabilityDataSource {

    private static AvailabilitySettingsRepository INSTANCE;

    private AvailabilitySettingsRepository() {

    }

    public static AvailabilitySettingsRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AvailabilitySettingsRepository();
        }
        return INSTANCE;
    }

    @Override
    public void saveDailyAvailability(int id, String[] dates, Callback callback) {

    }

    @Override
    public void saveHourlyAvailability(int id, String[] dates, String startTime, String endTime, Callback callback) {

    }
}
