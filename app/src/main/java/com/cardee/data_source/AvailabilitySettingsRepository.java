package com.cardee.data_source;


import com.cardee.data_source.cache.LocalAvailabilityDataSource;
import com.cardee.data_source.remote.RemoteAvailabilityDataSource;

public class AvailabilitySettingsRepository implements AvailabilityDataSource {

    private static AvailabilitySettingsRepository INSTANCE;

    private final AvailabilityDataSource remoteDataSource;
    private final AvailabilityDataSource localDataSource;
    private final OwnerCarRepository carRepository;
    private final OwnerCarsRepository carsRepository;

    private AvailabilitySettingsRepository() {
        remoteDataSource = RemoteAvailabilityDataSource.getInstance();
        localDataSource = LocalAvailabilityDataSource.getInstance();
        carRepository = OwnerCarRepository.getInstance();
        carsRepository = OwnerCarsRepository.getInstance();
    }

    public static AvailabilitySettingsRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AvailabilitySettingsRepository();
        }
        return INSTANCE;
    }

    @Override
    public void saveDailyAvailability(final int id, String[] dates, final Callback callback) {
        remoteDataSource.saveDailyAvailability(id, dates, new Callback() {
            @Override
            public void onSuccess() {
                OwnerCarRepository.getInstance().refresh(id);
                OwnerCarsRepository.getInstance().refreshCars();
                callback.onSuccess();
            }

            @Override
            public void onError(Error error) {
                callback.onError(error);
            }
        });
    }

    @Override
    public void saveHourlyAvailability(final int id, String[] dates, String startTime, String endTime,
                                       final Callback callback) {
        remoteDataSource.saveHourlyAvailability(id, dates, startTime, endTime, new Callback() {
            @Override
            public void onSuccess() {
                OwnerCarRepository.getInstance().refresh(id);
                OwnerCarsRepository.getInstance().refreshCars();
                callback.onSuccess();
            }

            @Override
            public void onError(Error error) {
                callback.onError(error);
            }
        });
    }

    @Override
    public void saveDailyTiming(int id, String pickupTime, String returnTime, Callback callback) {

    }

    @Override
    public void saveAvailability(int id, boolean availableDaily, boolean availableHourly, final Callback callback) {
        remoteDataSource.saveAvailability(id, availableDaily, availableHourly, new Callback() {

            @Override
            public void onSuccess() {
                //TODO: handle cache update
                callback.onSuccess();
            }

            @Override
            public void onError(Error error) {
                callback.onError(error);
            }
        });
    }
}
