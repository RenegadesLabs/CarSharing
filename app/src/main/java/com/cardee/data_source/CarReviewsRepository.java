package com.cardee.data_source;


import com.cardee.data_source.CarReviewsDataSource;
import com.cardee.data_source.Error;
import com.cardee.data_source.remote.RemoteCarReviewsDataSource;
import com.cardee.data_source.remote.api.reviews.response.entity.CarReviews;

public class CarReviewsRepository implements CarReviewsDataSource {
    private final RemoteCarReviewsDataSource mRemoteDataSource;

    public CarReviewsRepository() {
        mRemoteDataSource = RemoteCarReviewsDataSource.getInstance();
    }

    @Override
    public void loadCarReviews(int id, final Callback callback) {
        mRemoteDataSource.loadCarReviews(id, new Callback() {
            @Override
            public void onSuccess(CarReviews carReviews) {
                callback.onSuccess(carReviews);
            }

            @Override
            public void onError(Error error) {
                callback.onError(error);
            }
        });
    }
}
