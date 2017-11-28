package com.cardee.owner_profile_info.presenter;

import android.content.Context;

import com.cardee.R;
import com.cardee.data_source.Error;
import com.cardee.data_source.remote.api.profile.response.entity.OwnerProfile;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.owner.usecase.GetOwnerInfo;
import com.cardee.owner_profile_info.view.ProfileInfoView;

public class OwnerProfileInfoPresenter {

    private final GetOwnerInfo mGetInfoUseCase;
    private UseCaseExecutor mExecutor;
    private ProfileInfoView mView;

    public OwnerProfileInfoPresenter(ProfileInfoView view) {
        mGetInfoUseCase = new GetOwnerInfo();
        mExecutor = UseCaseExecutor.getInstance();
        mView = view;
    }

    public void getOwnerInfo() {
//        if (mView != null)
//            mView.showProgress(true);

        mExecutor.execute(mGetInfoUseCase, null, new UseCase.Callback<GetOwnerInfo.ResponseValues>() {
            @Override
            public void onSuccess(GetOwnerInfo.ResponseValues response) {
                OwnerProfile profile = response.getOwnerProfile();
                if (profile != null) {
                    mView.setProfileImage(profile.getProfilePhotoLink());
                    mView.setProfileName(profile.getName());
                    float rate = profile.getRating();
                    mView.setProfileRating(String.format("%.1f", rate));

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

                mView.showProgress(false);
            }

            @Override
            public void onError(Error error) {
                mView.showProgress(false);
            }
        });
    }

}
