package com.cardee.data_source;


import com.cardee.data_source.cache.LocalAvailabilityDataSource;
import com.cardee.data_source.remote.RemoteAvailabilityDataSource;
import com.cardee.data_source.remote.api.cars.response.CarResponseBody;
import com.cardee.data_source.remote.api.profile.response.entity.CarEntity;

public class AvailabilitySettingsRepository implements AvailabilityDataSource {

    private static AvailabilitySettingsRepository INSTANCE;

    private final AvailabilityDataSource remoteDataSource;
    private final AvailabilityDataSource localDataSource;

    private AvailabilitySettingsRepository() {
        remoteDataSource = RemoteAvailabilityDataSource.getInstance();
        localDataSource = LocalAvailabilityDataSource.getInstance();
    }

    public static AvailabilitySettingsRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AvailabilitySettingsRepository();
        }
        return INSTANCE;
    }

    @Override
    public void saveDailyAvailability(final int id, final String[] dates, final Callback callback) {
        remoteDataSource.saveDailyAvailability(id, dates, new Callback() {
            @Override
            public void onSuccess() {
                OwnerCarRepository.getInstance().refresh(id);
                updateListCache(id, dates, null, null, null);
                callback.onSuccess();
            }

            @Override
            public void onError(Error error) {
                callback.onError(error);
            }
        });
    }

    @Override
    public void saveHourlyAvailability(final int id, final String[] dates, final String startTime, final String endTime,
                                       final Callback callback) {
        remoteDataSource.saveHourlyAvailability(id, dates, startTime, endTime, new Callback() {
            @Override
            public void onSuccess() {
                OwnerCarRepository.getInstance().refresh(id);
                updateListCache(id, null, dates, startTime, endTime);
                callback.onSuccess();
            }

            @Override
            public void onError(Error error) {
                callback.onError(error);
            }
        });
    }

    @Override
    public void saveAvailability(final int id, final boolean availableDaily, final boolean availableHourly,
                                 final Callback callback) {
        remoteDataSource.saveAvailability(id, availableDaily, availableHourly, new Callback() {

            @Override
            public void onSuccess() {
                updateCarCache(id, availableDaily, availableHourly);
                callback.onSuccess();
            }

            @Override
            public void onError(Error error) {
                callback.onError(error);
            }
        });
    }

    private void updateCarCache(int id, boolean availableDaily, boolean availableHourly) {
        CarEntity carItem = OwnerCarsRepository.getInstance().getCachedCar(id);
        if (carItem != null) {
            carItem.setCarAvailableOrderDays(availableDaily);
            carItem.setCarAvailableOrderHours(availableHourly);
        }
        CarResponseBody car = OwnerCarRepository.getInstance().getCachedCar(id);
        if (car != null) {
            car.setCarAvailableOrderDays(availableDaily);
            car.setCarAvailableOrderHours(availableHourly);
        }
    }

    private void updateListCache(int carId, String[] dailyDates, String[] hourlyDates, String startTime, String endTime) {
        CarEntity carFromList = OwnerCarsRepository.getInstance().getCachedCar(carId);
        if (carFromList != null) {
            if (dailyDates != null) {
                carFromList.setCarAvailabilityDailyDates(dailyDates);
                carFromList.setCarAvailabilityDailyCount(dailyDates.length);
            }
            if (hourlyDates != null) {
                carFromList.setCarAvailabilityHourlyDates(hourlyDates);
                carFromList.setCarAvailabilityHourlyCount(hourlyDates.length);
            }
            if (startTime != null) {
                carFromList.setCarAvailabilityTimeBegin(startTime);
                carFromList.setCarAvailabilityTimeEnd(endTime);
            }
        }
    }
}
