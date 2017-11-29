package com.cardee.owner_profile_info.presenter;

import android.content.Context;

import com.cardee.R;
import com.cardee.data_source.Error;
import com.cardee.data_source.remote.api.profile.response.entity.OwnerProfile;
import com.cardee.data_source.remote.api.reviews.response.entity.CarReviews;
import com.cardee.data_source.remote.api.reviews.response.entity.Review;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.owner.entity.Car;
import com.cardee.domain.owner.usecase.GetCarReviews;
import com.cardee.domain.owner.usecase.GetOwnerInfo;
import com.cardee.owner_profile_info.view.ProfileInfoView;

import java.util.List;
import java.util.Locale;

import io.reactivex.functions.Consumer;

public class OwnerProfileInfoPresenter implements Consumer<Car> {

    private final GetOwnerInfo mGetInfoUseCase;
    private final GetCarReviews mGetCarReviews;
    private UseCaseExecutor mExecutor;
    private ProfileInfoView mView;

    public OwnerProfileInfoPresenter(ProfileInfoView view) {
        mGetInfoUseCase = new GetOwnerInfo();
        mGetCarReviews = new GetCarReviews();
        mExecutor = UseCaseExecutor.getInstance();
        mView = view;
    }

    public void getOwnerInfo() {
//        if (mView != null)
//            mView.showProgress(true);

        mExecutor.execute(mGetInfoUseCase, null, new UseCase.Callback<GetOwnerInfo.ResponseValues>() {
            @Override
            public void onSuccess(GetOwnerInfo.ResponseValues response) {
                if (mView != null) {
                    OwnerProfile profile = response.getOwnerProfile();
                    if (profile != null) {
                        mView.setProfileImage(profile.getProfilePhotoLink());
                        mView.setProfileName(profile.getName());
                        float rate = profile.getRating();
                        mView.setProfileRating(String.format(Locale.getDefault(), "%.1f", rate));

                        String acceptance = profile.getAcceptance();
                        if (acceptance == null) {
                            acceptance = "0%";
                        }
                        mView.setAcceptance(acceptance);

                        String responseTime = profile.getResponseTime();
                        if (responseTime == null) {
                            responseTime = "0";
                        }
                        mView.setResponseText(responseTime);

                        mView.setBookings(profile.getBookingsCount().toString());

                        mView.setNote(profile.getNote());

                        int carsCount = profile.getCarCount();
                        StringBuilder builder = new StringBuilder(String.valueOf(carsCount));
                        builder.append(((Context) mView).getResources().getString(R.string.owner_profile_info_car));
                        if (carsCount != 1) {
                            builder.append("s");
                        }
                        mView.setCarsCount(builder.toString());

                        int reviewsCount = profile.getReviewCount();
                        builder = new StringBuilder(String.valueOf(reviewsCount));
                        builder.append(((Context) mView).getResources().getString(R.string.owner_profile_info_car_review));
                        if (reviewsCount != 1) {
                            builder.append("s");
                        }

                        mView.setReviewsCount(builder.toString());
                    }

                    List<Car> cars = response.getCars();
                    if (cars != null && !cars.isEmpty()) {
                        mView.setCars(cars);
                    }

                    getReviews(cars);

                    mView.showProgress(false);
                }
            }

            @Override
            public void onError(Error error) {
                mView.showProgress(false);
            }
        });
    }

    private void getReviews(List<Car> cars) {
        for (Car car : cars) {
            int id = car.getCarId();
            mExecutor.execute(mGetCarReviews, new GetCarReviews.RequestValues(id),
                    new UseCase.Callback<GetCarReviews.ResponseValues>() {
                        @Override
                        public void onSuccess(GetCarReviews.ResponseValues response) {
                            if (mView != null) {
                                CarReviews carReviews = response.getCarReviews();
                                List<Review> reviews = carReviews.getReviews();
                                mView.setReviews(reviews);
                            }
                        }

                        @Override
                        public void onError(Error error) {
                        }
                    });
        }
    }

    @Override
    public void accept(Car car) throws Exception {
        mView.openItem(car);
    }
}
