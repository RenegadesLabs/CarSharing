package com.cardee.data_source;


import com.cardee.data_source.remote.api.reviews.response.entity.CarReviews;

public interface CarReviewsDataSource {

    void loadCarReviews(int id, Callback callback);

    interface Callback {
        void onSuccess(CarReviews carReviews);

        void onError(Error error);
    }
}
