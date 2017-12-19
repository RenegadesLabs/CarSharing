package com.cardee.domain.owner.usecase;

import com.cardee.data_source.AvailabilityDataSource;
import com.cardee.data_source.Error;
import com.cardee.domain.UseCase;
import com.cardee.data_source.AvailabilitySettingsRepository;

public class SaveHourlyTiming
        implements UseCase<SaveHourlyTiming.RequestValues, SaveHourlyTiming.ResponseValues> {

    private final AvailabilitySettingsRepository repository;

    public SaveHourlyTiming() {
        repository = AvailabilitySettingsRepository.getInstance();
    }

    @Override
    public void execute(RequestValues request, final Callback<ResponseValues> callback) {
        int id = request.getCarId();
        if (id == -1) {
            callback.onError(new Error(Error.Type.INVALID_REQUEST, "Invalid ID: " + id));
            return;
        }
        repository.saveHourlyAvailability(id, request.getDates(), request.getBeginTime(), request.getEndTime(),
                new AvailabilityDataSource.Callback() {
                    @Override
                    public void onSuccess() {
                        callback.onSuccess(new ResponseValues(true));
                    }

                    @Override
                    public void onError(Error error) {
                        callback.onError(error);
                    }
                });
    }

    public static class RequestValues implements UseCase.RequestValues {
        private final int carId;
        private final String beginTime;
        private final String endTime;
        private final String[] dates;

        public RequestValues(int carId, String beginTime, String endTime, String[] dates) {
            this.carId = carId;
            this.beginTime = beginTime;
            this.endTime = endTime;
            this.dates = dates;
        }

        public int getCarId() {
            return carId;
        }

        public String getBeginTime() {
            return beginTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public String[] getDates() {
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
