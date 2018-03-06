package com.cardee.renter_profile.presenter;


import android.content.Context;
import android.content.DialogInterface;

import com.cardee.R;
import com.cardee.data_source.Error;
import com.cardee.data_source.remote.api.profile.request.ChangeNoteRequest;
import com.cardee.data_source.remote.api.profile.response.entity.RenterProfile;
import com.cardee.data_source.remote.api.profile.response.entity.RenterReview;
import com.cardee.data_source.util.DialogHelper;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.owner.usecase.ChangeImage;
import com.cardee.domain.renter.usecase.ChangeNote;
import com.cardee.domain.renter.usecase.GetRenterById;
import com.cardee.domain.renter.usecase.GetRenterProfile;
import com.cardee.renter_profile.view.RenterProfileView;

import java.io.File;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class RenterProfilePresenter {

    private final String TAG = this.getClass().getSimpleName();
    private final GetRenterProfile mGetProfileUseCase;
    private final GetRenterById mGetProfileById;
    private final ChangeImage mChangeImage;
    private final ChangeNote mChangeNote;
    private RenterProfileView mView;
    private UseCaseExecutor mExecutor;

    public RenterProfilePresenter(RenterProfileView view) {
        mView = view;
        mExecutor = UseCaseExecutor.getInstance();
        mGetProfileUseCase = new GetRenterProfile();
        mChangeImage = new ChangeImage();
        mChangeNote = new ChangeNote();
        mGetProfileById = new GetRenterById();
    }

    public void getRenterProfile() {
        mView.showProgress(true);
        mExecutor.execute(mGetProfileUseCase, null, new UseCase.Callback<GetRenterProfile.ResponseValues>() {
            @Override
            public void onSuccess(GetRenterProfile.ResponseValues response) {
                RenterProfile profile = response.getRenterProfile();
                if (profile != null) {
                    setProfile(profile);
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

    public void getRenterById(int profileId) {
        mView.showProgress(true);
        mExecutor.execute(mGetProfileById, new GetRenterById.RequestValues(profileId), new UseCase.Callback<GetRenterById.ResponseValues>() {
            @Override
            public void onSuccess(GetRenterById.ResponseValues response) {
                RenterProfile profile = response.getRenterProfile();
                if (profile != null) {
                    setProfile(profile);
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

    private void setProfile(RenterProfile profile) {
        mView.setProfileImage(profile.getProfilePhoto());

        mView.setProfileName(profile.getName());

        float rate = profile.getRating();
        if (rate == (long) rate) {
            mView.setProfileRating(String.format(Locale.getDefault(), "%d", (long) rate));
        } else {
            mView.setProfileRating(String.format(Locale.getDefault(), "%.1f", rate));
        }

        mView.setProfileAge(String.valueOf(profile.getAge()));

        Integer drivingExp = profile.getYearsDrivingExp();
        if (drivingExp == null) {
            drivingExp = 0;
        }
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
            note = ((Context) mView).getResources().getString(R.string.profile_default_note);
        }
        mView.setNote(note);

        Integer reviewsCount = profile.getReviewCnt();
        if (reviewsCount == null) {
            reviewsCount = 0;
        }
        StringBuilder builder = new StringBuilder(String.valueOf(reviewsCount));
        builder.append(((Context) mView).getResources().getString(R.string.comment));
        if (reviewsCount != 1) {
            builder.append("s");
        }
        mView.setReviewsCount(builder.toString());

        List<RenterReview> reviews = profile.getReviews();
        if (reviews != null && !reviews.isEmpty()) {
            Iterator<RenterReview> iterator = reviews.iterator();
            while (iterator.hasNext()) {
                RenterReview review = iterator.next();
                String text = review.getReview();
                if (text == null || text.isEmpty()) {
                    iterator.remove();
                    continue;
                }
                review.setReview(review.getReview().trim());
            }
            Collections.sort(reviews, (first, second) -> second.getReviewDate().compareTo(first.getReviewDate()));
            mView.setReviews(reviews);
        }
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

    public void changeNote(Context context) {
        DialogHelper.getAlertDialog(context, R.layout.dialog_profile_change_note,
                context.getResources().getString(R.string.profile_info_note_change_title),
                context.getResources().getString(R.string.profile_info_note_change),
                new DialogHelper.OnClickCallback() {
                    @Override
                    public void onPositiveButtonClick(final String newNote, final DialogInterface dialog) {
                        ChangeNoteRequest changeNoteRequest = new ChangeNoteRequest();
                        changeNoteRequest.setNote(newNote);
                        mExecutor.execute(mChangeNote, new ChangeNote.RequestValues(changeNoteRequest), new UseCase.Callback<ChangeNote.ResponseValues>() {
                            @Override
                            public void onSuccess(ChangeNote.ResponseValues response) {
                                dialog.dismiss();
                                mView.setNote(newNote);
                                mView.showMessage(R.string.profile_info_note_change_success);
                            }

                            @Override
                            public void onError(Error error) {
                                dialog.dismiss();
                                mView.showMessage(error.getMessage());
                            }
                        });
                    }

                    @Override
                    public void onNegativeButtonClick(DialogInterface dialog) {
                        dialog.dismiss();
                    }
                }).show();
    }


}
