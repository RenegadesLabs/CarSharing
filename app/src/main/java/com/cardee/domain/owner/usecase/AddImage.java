package com.cardee.domain.owner.usecase;

import android.net.Uri;

import com.cardee.data_source.CarEditDataSource;
import com.cardee.data_source.CarEditRepository;
import com.cardee.data_source.Error;
import com.cardee.domain.UseCase;


public class AddImage implements UseCase<AddImage.RequestValues, AddImage.ResponseValues> {
    private final CarEditRepository repository;


    public AddImage() {
        repository = CarEditRepository.getInstance();
    }

    @Override
    public void execute(RequestValues values, final Callback<ResponseValues> callback) {
        int id = values.getCarId();
        Uri imgUri = values.getImgUri();
        if (id == -1 || imgUri == null) {
            callback.onError(new Error(Error.Type.INVALID_REQUEST,
                    "Invalid id: " + id + " or PATH: " + imgUri));
            return;
        }
        repository.uploadImage(id, imgUri, new CarEditDataSource.ImageCallback() {
            @Override
            public void onSuccess(int imageId) {
                callback.onSuccess(new ResponseValues(imageId));
            }

            @Override
            public void onError(Error error) {
                callback.onError(error);
            }
        });
    }

    public static class RequestValues implements UseCase.RequestValues {

        private final int carId;
        private final Uri imgUri;

        public RequestValues(int carId, Uri imgUri) {
            this.carId = carId;
            this.imgUri = imgUri;
        }

        public Uri getImgUri() {
            return imgUri;
        }

        public int getCarId() {
            return carId;
        }
    }

    public static class ResponseValues implements UseCase.ResponseValues {
        private final Integer imageId;

        public ResponseValues(Integer imageId) {
            this.imageId = imageId;
        }

        public Integer getImageId() {
            return imageId;
        }
    }
}
