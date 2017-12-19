package com.cardee.domain.owner.usecase;

import com.cardee.data_source.CarEditDataSource;
import com.cardee.data_source.CarEditRepository;
import com.cardee.data_source.Error;
import com.cardee.domain.UseCase;

public class SaveCarDescription
        implements UseCase<SaveCarDescription.RequestValues, SaveCarDescription.ResponseValues> {

    private final CarEditRepository repository;

    public SaveCarDescription() {
        repository = CarEditRepository.getInstance();
    }

    @Override
    public void execute(RequestValues values, final Callback<ResponseValues> callback) {
        int id = values.getCarId();
        if (id == -1) {
            callback.onError(new Error(Error.Type.INVALID_REQUEST, "Invalid ID: " + id));
            return;
        }
        repository.updateDescription(id, values.getCarDescription(), new CarEditDataSource.Callback() {
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
        private final String carDescription;

        public RequestValues(int carId, String carDescription) {
            this.carId = carId;
            this.carDescription = carDescription;
        }

        public int getCarId() {
            return carId;
        }

        public String getCarDescription() {
            return carDescription;
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
