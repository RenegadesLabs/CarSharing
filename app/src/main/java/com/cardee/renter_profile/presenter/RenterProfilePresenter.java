package com.cardee.renter_profile.presenter;


import android.content.Context;

import com.cardee.R;
import com.cardee.data_source.Error;
import com.cardee.data_source.remote.api.profile.response.entity.RenterProfile;
import com.cardee.data_source.remote.api.profile.response.entity.RenterReview;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.owner.usecase.ChangeImage;
import com.cardee.domain.renter.usecase.GetRenterProfile;
import com.cardee.renter_profile.view.RenterProfileView;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class RenterProfilePresenter {

    private final String TAG = this.getClass().getSimpleName();
    private final GetRenterProfile mGetProfileUseCase;
    private final ChangeImage mChangeImage;
    private RenterProfileView mView;
    private UseCaseExecutor mExecutor;

    public RenterProfilePresenter(RenterProfileView view) {
        mView = view;
        mExecutor = UseCaseExecutor.getInstance();
        mGetProfileUseCase = new GetRenterProfile();
        mChangeImage = new ChangeImage();
    }

    public void getRenterProfile() {
        mView.showProgress(true);

        mExecutor.execute(mGetProfileUseCase, null, new UseCase.Callback<GetRenterProfile.ResponseValues>() {
            @Override
            public void onSuccess(GetRenterProfile.ResponseValues response) {
                RenterProfile profile = response.getRenterProfile();
                if (profile != null) {
                    mView.setProfileImage(profile.getProfilePhoto());

                    mView.setProfileName(profile.getName());

                    float rate = profile.getRating();
                    if (rate == (long) rate) {
                        mView.setProfileRating(String.format(Locale.getDefault(), "%d", (long) rate));
                    } else {
                        mView.setProfileRating(String.format(Locale.getDefault(), "%.1f", rate));
                    }

                    mView.setProfileAge(String.valueOf(profile.getAge()));

                    int drivingExp = profile.getYearsDrivingExp();
                    mView.setDrivingExp(String.valueOf(drivingExp));

                    if (drivingExp == 1) {
                        String year = ((Context) mView).getResources().getString(R.string.year);
                        mView.setDrivingExpYears(year);
                    } else {
                        String yrs = ((Context) mView).getResources().getString(R.string.yrs);
                        mView.setDrivingExpYears(yrs);
                    }

                    mView.setBookings(profile.getCntBookings().toString());

                    String note = profile.getNote();
                    if (note == null || note.isEmpty()) {
                        note = ((Context) mView).getResources().getString(R.string.renter_profile_default_note);
                    }
                    mView.setNote(note);

                    int reviewsCount = profile.getReviewCnt();
                    mView.setReviewsCount(String.valueOf(reviewsCount));

                    List<RenterReview> reviews = profile.getReviews();
                    if (reviews != null && !reviews.isEmpty()) {
                        Iterator<RenterReview> iterator = reviews.iterator();
                        while (iterator.hasNext()) {
                            String text = iterator.next().getReview();
                            if (text == null || text.isEmpty()) {
                                iterator.remove();
                            }
                        }
                        mView.setReviews(reviews);
                    }
                }

                mView.showProgress(false);
            }

            @Override
            public void onError(Error error) {
                mView.showProgress(false);
                mView.showMessage(error.getMessage());
            }
        });
    }

    public void setProfilePicture(File photo) {
        mView.showProgress(true);
        mExecutor.execute(mChangeImage,
                new ChangeImage.RequestValues(null, null, photo, null),
                new UseCase.Callback<ChangeImage.ResponseValues>() {
                    @Override
                    public void onSuccess(ChangeImage.ResponseValues response) {
                        mView.showProgress(false);
                        mView.showMessage(R.string.owner_profile_info_photo_success);
                        mView.onChangeImageSuccess();
                    }

                    @Override
                    public void onError(Error error) {
                        mView.showProgress(false);
                        mView.showMessage(error.getMessage());
                    }
                });
    }
}
