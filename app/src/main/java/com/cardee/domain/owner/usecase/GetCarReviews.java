package com.cardee.domain.owner.usecase;


import com.cardee.data_source.CarReviewsDataSource;
import com.cardee.data_source.CarReviewsRepository;
import com.cardee.data_source.Error;
import com.cardee.data_source.remote.api.reviews.response.entity.CarReviews;
import com.cardee.domain.UseCase;

public class GetCarReviews implements UseCase<GetCarReviews.RequestValues, GetCarReviews.ResponseValues> {
    private final CarReviewsDataSource mRepository;


    public GetCarReviews() {
        mRepository = new CarReviewsRepository();
    }

    @Override
    public void execute(RequestValues values, final Callback<ResponseValues> callback) {
        mRepository.loadCarReviews(values.getCarId(), new CarReviewsDataSource.Callback() {
            @Override
            public void onSuccess(CarReviews carReviews) {
                callback.onSuccess(new ResponseValues(carReviews));
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
        private final CarReviews carReviews;

        public ResponseValues(CarReviews carReviews) {
            this.carReviews = carReviews;
        }

        public CarReviews getCarReviews() {
            return carReviews;
        }
    }
}
