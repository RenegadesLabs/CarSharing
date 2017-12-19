package com.cardee.domain.owner.usecase;

import com.cardee.data_source.Error;
import com.cardee.data_source.OwnerCarDataSource;
import com.cardee.data_source.OwnerCarRepository;
import com.cardee.data_source.remote.api.cars.response.CarResponseBody;
import com.cardee.data_source.remote.api.cars.response.entity.CarDetailsEntity;
import com.cardee.data_source.remote.api.common.entity.ImageEntity;
import com.cardee.domain.UseCase;
import com.cardee.domain.owner.entity.Image;
import com.cardee.domain.util.ArrayUtil;
import com.cardee.domain.util.Mapper;

import java.util.List;


public class GetCarImages
        implements UseCase<GetCarImages.RequestValues, GetCarImages.ResponseValues> {

    private final OwnerCarRepository repository;

    public GetCarImages() {
        repository = OwnerCarRepository.getInstance();
    }

    @Override
    public void execute(GetCarImages.RequestValues values,
                        final UseCase.Callback<GetCarImages.ResponseValues> callback) {
        int carId = values.getCarId();
        if (carId == -1) {
            callback.onError(new Error(Error.Type.INVALID_REQUEST, "Invalid ID: " + carId));
            return;
        }
        repository.obtainCar(carId, new OwnerCarDataSource.Callback() {
            @Override
            public void onSuccess(CarResponseBody carResponse) {
                CarDetailsEntity details = carResponse.getCarDetails();
                if (details == null) {
                    callback.onError(new Error(Error.Type.SERVER, "Empty response"));
                    return;
                }
                ImageEntity[] imageArray = carResponse.getCarDetails().getImages();
                List<Image> images = ArrayUtil.asList(imageArray, new Mapper<ImageEntity, Image>() {
                    @Override
                    public Image map(ImageEntity input) {
                        return new Image(input.getImageId(), input.getLink(), input.getThumbnail(), input.isPrimary());
                    }
                });
                callback.onSuccess(new ResponseValues(images));
            }

            @Override
            public void onError(Error error) {
                callback.onError(error);
            }
        });
    }

    public static class RequestValues implements UseCase.RequestValues {
        private final int carId;

        public RequestValues(int carId) {
            this.carId = carId;
        }

        public int getCarId() {
            return carId;
        }
    }

    public static class ResponseValues implements UseCase.ResponseValues {
        private List<Image> images;

        public ResponseValues(List<Image> images) {
            this.images = images;
        }

        public List<Image> getCarImages() {
            return images;
        }
    }
}
