package com.cardee.domain.owner.usecase;


import com.cardee.data_source.CarEditDataSource;
import com.cardee.data_source.CarEditRepository;
import com.cardee.data_source.Error;
import com.cardee.domain.UseCase;

public class DeleteImage
        implements UseCase<DeleteImage.RequestValues, DeleteImage.ResponseValues> {

    private final CarEditRepository repository;

    public DeleteImage() {
        repository = CarEditRepository.getInstance();
    }

    @Override
    public void execute(RequestValues values, final Callback<ResponseValues> callback) {
        int id = values.getCarId();
        Integer imageId = values.getImageId();
        if (id == -1 || imageId == null) {
            callback.onError(new Error(Error.Type.INVALID_REQUEST, "Invalid ID: " + id + " or IMAGE_ID: null"));
            return;
        }
        repository.deleteImage(id, imageId, new CarEditDataSource.Callback() {
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
        private final Integer imageId;

        public RequestValues(int carId, Integer imageId) {
            this.carId = carId;
            this.imageId = imageId;
        }

        public int getCarId() {
            return carId;
        }

        public Integer getImageId() {
            return imageId;
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
