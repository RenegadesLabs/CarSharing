package com.cardee.owner_car_details.presenter;


import android.content.Context;

import com.cardee.R;
import com.cardee.data_source.Error;
import com.cardee.data_source.remote.api.reviews.response.entity.CarReviews;
import com.cardee.data_source.remote.api.reviews.response.entity.Review;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.owner.usecase.GetCarReviews;

import java.util.List;

import static com.cardee.owner_car_details.view.CarReviewsContract.Presenter;
import static com.cardee.owner_car_details.view.CarReviewsContract.View;

public class CarReviewsPresenter implements Presenter {

    private View mView;
    private final UseCaseExecutor mExecutor;
    private final GetCarReviews mGetCarReviews;
    private int mCarId;
    private String ratingString;
    private String reviewsString;

    public CarReviewsPresenter(View view, int carId, Context context) {
        mView = view;
        mCarId = carId;

        mExecutor = UseCaseExecutor.getInstance();
        mGetCarReviews = new GetCarReviews();

        ratingString = context.getResources().getString(R.string.car_reviews_ratings);
        reviewsString = context.getResources().getString(R.string.car_reviews);
    }


    @Override
    public void onDestroy() {
        mView = null;
    }

    @Override
    public void get() {
        mView.showProgress(true);
        mExecutor.execute(mGetCarReviews, new GetCarReviews.RequestValues(mCarId), new UseCase.Callback<GetCarReviews.ResponseValues>() {
            @Override
            public void onSuccess(GetCarReviews.ResponseValues response) {
                mView.showProgress(false);
                CarReviews reviews = response.getCarReviews();
                if (reviews != null) {
                    setHeader(reviews);
                    List<Review> carReviews = reviews.getReviews();
                    mView.setCarReviews(carReviews);
                }
            }

            @Override
            public void onError(Error error) {
                mView.showProgress(false);
                mView.showMessage(error.getMessage());
            }
        });
    }

    private void setHeader(CarReviews reviews) {

        int conditions = reviews.getCarConditionCleanliness();
        mView.setConditions(conditions);

        int comfort = reviews.getCarComfortPerformance();
        mView.setComfort(comfort);

        int owner = reviews.getCarOwner();
        mView.setOwnerRate(owner);

        int overall = reviews.getOverallRentalExperience();
        mView.setExperience(overall);

        int ratings = reviews.getRatingCnt();
        StringBuilder builder = new StringBuilder(String.valueOf(ratings));
        builder.append(ratingString);
        if (ratings != 1) {
            builder.append("s");
        }
        mView.setRatingsCount(builder.toString());

        int reviewsCnt = reviews.getReviewsCnt();
        builder = new StringBuilder(String.valueOf(reviewsCnt));
        builder.append(reviewsString);
        if (reviewsCnt != 1) {
            builder.append("s");
        }
        mView.setReviewsCount(builder.toString());
    }

    @Override
    public void init() {

    }
}
