package com.cardee.domain.owner.usecase;

import android.net.Uri;

import com.cardee.data_source.Error;
import com.cardee.data_source.NewCarDataSource;
import com.cardee.data_source.NewCarRepository;
import com.cardee.domain.UseCase;
import com.cardee.domain.owner.entity.CarData;
import com.cardee.domain.owner.entity.mapper.NewCarDataMapper;

public class SaveCarImage implements UseCase<SaveCarImage.RequestValues, SaveCarImage.ResponseValues> {

    private final NewCarRepository repository;
    private final NewCarDataMapper mapper;


    public SaveCarImage() {
        repository = NewCarRepository.getInstance();
        mapper = new NewCarDataMapper();
    }


    @Override
    public void execute(RequestValues values, final Callback callback) {
        final Uri uri = values.getImgUri();
        repository.saveCarImage(uri, false, new NewCarDataSource.Callback() {
            @Override
            public void onSuccess(Integer newCarId) {
                callback.onSuccess(null);
            }

            @Override
            public void onError(Error error) {
                callback.onError(error);
            }
        });
    }

    public static class RequestValues implements UseCase.RequestValues {
        private final Uri imgUri;

        public RequestValues(Uri imgUri) {
            this.imgUri = imgUri;
        }

        public Uri getImgUri() {
            return imgUri;
        }
    }

    public static class ResponseValues implements UseCase.ResponseValues {
        private final CarData carData;

        public ResponseValues(CarData carData) {
            this.carData = carData;
        }

        public CarData getCarData() {
            return carData;
        }
    }
}
