package com.cardee.owner_profile_info.presenter;

import android.content.Context;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import com.cardee.R;
import com.cardee.data_source.Error;
import com.cardee.data_source.remote.api.profile.request.OwnerNoteRequest;
import com.cardee.data_source.remote.api.profile.response.entity.OwnerProfile;
import com.cardee.data_source.remote.api.profile.response.entity.OwnerReview;
import com.cardee.data_source.util.DialogHelper;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.owner.entity.Car;
import com.cardee.domain.owner.entity.CarReview;
import com.cardee.domain.owner.entity.mapper.OwnerReviewToCarReviewMapper;
import com.cardee.domain.owner.usecase.ChangeImage;
import com.cardee.domain.owner.usecase.ChangeNote;
import com.cardee.domain.owner.usecase.GetOwnerInfo;
import com.cardee.owner_profile_info.view.ProfileInfoView;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import io.reactivex.functions.Consumer;

public class OwnerProfileInfoPresenter implements Consumer<Car> {

    private final String TAG = this.getClass().getSimpleName();

    private final GetOwnerInfo mGetInfoUseCase;
    private final ChangeNote mChangeNote;
    private final ChangeImage mChangeImage;
    private UseCaseExecutor mExecutor;
    private ProfileInfoView mView;

    public OwnerProfileInfoPresenter(ProfileInfoView view) {
        mGetInfoUseCase = new GetOwnerInfo();
        mChangeNote = new ChangeNote();
        mChangeImage = new ChangeImage();
        mExecutor = UseCaseExecutor.getInstance();
        mView = view;
    }

    public void getOwnerInfo() {
        mView.showProgress(true);

        mExecutor.execute(mGetInfoUseCase, null, new UseCase.Callback<GetOwnerInfo.ResponseValues>() {
            @Override
            public void onSuccess(GetOwnerInfo.ResponseValues response) {
                if (mView != null) {
                    OwnerProfile profile = response.getOwnerProfile();
                    if (profile != null) {
                        mView.setProfileImage(profile.getProfilePhotoLink());
                        mView.setProfileName(profile.getName());
                        float rate = profile.getRating();
                        if (rate == (long) rate) {
                            mView.setProfileRating(String.format(Locale.getDefault(), "%d", (long) rate));
                        } else {
                            mView.setProfileRating(String.format(Locale.getDefault(), "%.1f", rate));
                        }

                        String acceptance = profile.getAcceptance();
                        if (acceptance == null) {
                            acceptance = "0";
                        }
                        mView.setAcceptance(acceptance);

                        String responseTime = profile.getResponseTime();
                        if (responseTime == null) {
                            responseTime = "0";
                        }
                        mView.setResponseText(responseTime);

                        String minutes = ((Context) mView).getResources().getString(R.string.owner_profile_info_minutes);
                        if (Integer.valueOf(responseTime) != 1) {
                            minutes = minutes + "s";
                        }
                        mView.setMinutes(minutes);

                        mView.setBookings(profile.getBookingsCount().toString());

                        mView.setNote(profile.getNote());

                        String address = profile.getAddress();
                        if (address != null && !address.isEmpty()) {
                            Geocoder geocoder = new Geocoder((Context) mView, Locale.getDefault());
                            try {
                                List<Address> addresses = geocoder.getFromLocationName(address, 1);
                                String city = addresses.get(0).getLocality();
                                if (city == null) {
                                    city = ((Context) mView).getResources().getString(R.string.owner_profile_info_default_city);
                                }
                                mView.setNoteTitle(city);
                            } catch (IOException e) {
                                Log.d(TAG, e.getMessage());
                            }
                        }


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

                    OwnerReview[] reviews = profile.getReviews();
                    if (reviews != null && reviews.length > 0) {
                        OwnerReviewToCarReviewMapper mapper = new OwnerReviewToCarReviewMapper();
                        List<CarReview> carReviewList = mapper.transform(reviews);
                        Iterator<CarReview> iterator = carReviewList.iterator();
                        while (iterator.hasNext()) {
                            String text = iterator.next().getReviewText();
                            if (text == null || text.isEmpty()) {
                                iterator.remove();
                            }
                        }
                        mView.setCarReviews(carReviewList);
                    }

                    mView.showProgress(false);
                }
            }

            @Override
            public void onError(Error error) {
                mView.showProgress(false);
                mView.showMessage(error.getMessage());
            }
        });
    }

    @Override
    public void accept(Car car) throws Exception {
        mView.openItem(car);
    }

    public void changeNote(final Context context) {
        DialogHelper.getAlertDialog(context, R.layout.dialog_owner_profile_change_note,
                context.getResources().getString(R.string.owner_profile_info_note_change),
                new DialogHelper.OnClickCallback() {
                    @Override
                    public void onPositiveButtonClick(final String newNote, final DialogInterface dialog) {
                        OwnerNoteRequest ownerNoteRequest = new OwnerNoteRequest();
                        ownerNoteRequest.setNote(newNote);
                        mExecutor.execute(mChangeNote, new ChangeNote.RequestValues(ownerNoteRequest), new UseCase.Callback<ChangeNote.ResponseValues>() {
                            @Override
                            public void onSuccess(ChangeNote.ResponseValues response) {
                                dialog.dismiss();
                                mView.setNote(newNote);
                                mView.showMessage(R.string.owner_profile_info_note_change_success);
                            }

                            @Override
                            public void onError(Error error) {
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
