package com.cardee.data_source.remote;


import android.net.Uri;
import android.util.Log;

import com.cardee.CardeeApp;
import com.cardee.data_source.BookingDataSource;
import com.cardee.data_source.Error;
import com.cardee.data_source.remote.api.BaseResponse;
import com.cardee.data_source.remote.api.booking.Bookings;
import com.cardee.data_source.remote.api.booking.Upload;
import com.cardee.data_source.remote.api.booking.request.ReviewAsOwner;
import com.cardee.data_source.remote.api.booking.request.ReviewAsRenter;
import com.cardee.data_source.remote.api.booking.response.BookingResponse;
import com.cardee.data_source.remote.api.booking.response.CostBreakdownResponse;
import com.cardee.data_source.remote.api.booking.response.UploadBookingImageResponse;
import com.cardee.data_source.remote.api.booking.response.entity.ChecklistEntity;
import com.cardee.data_source.remote.api.booking.response.entity.CostRequest;
import com.cardee.data_source.remote.api.booking.response.entity.UploadBookingImageResponseBody;
import com.cardee.util.ImageProcessor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

public class RemoteBookingDataSource implements BookingDataSource {

    private static final String TAG = RemoteBookingDataSource.class.getSimpleName();
    private static RemoteBookingDataSource INSTANCE;

    private final Bookings api;
    private final Upload uploadApi;
    private final ImageProcessor imageProcessor;

    private RemoteBookingDataSource() {
        api = CardeeApp.retrofit.create(Bookings.class);
        uploadApi = CardeeApp.retrofit.create(Upload.class);
        imageProcessor = new ImageProcessor();
    }

    public static RemoteBookingDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteBookingDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void obtainOwnerBookings(String filter, String sort, boolean forceUpdate, BookingsCallback bookingsCallback) {
        try {
            Response<BookingResponse> response = api.getOwnerBookings(filter, sort).execute();
            if (response.isSuccessful() && response.body() != null) {
                bookingsCallback.onSuccess(response.body().getBookings(), true);
                return;
            }
            handleErrorResponse(response.body(), bookingsCallback);
        } catch (IOException ex) {
            bookingsCallback.onError(new Error(Error.Type.LOST_CONNECTION, "Internet connection lost"));
        }
    }

    @Override
    public void obtainRenterBookings(String filter, String sort, BookingsCallback bookingsCallback) {

    }

    @Override
    public void obtainBookingById(int id, BookingCallback callback) {

        api.getBookingById(id).subscribe(response -> {
            if (response.isSuccessful()) {
                callback.onSuccess(response.getBooking());
                return;
            }
            handleErrorResponse(response, callback);
        }, throwable -> {
            Log.e(TAG, throwable.getMessage());
            callback.onError(new Error(Error.Type.LOST_CONNECTION, Error.Message.CONNECTION_LOST));
        });
    }

    @Override
    public void obtainBookingRentalTerms(int id, RentalTermsCallback callback) {
        api.getBookingRentalTerms(id).subscribe(response -> {
            if (response.isSuccessful()) {
                callback.onSuccess(response.getRentalTerms());
                return;
            }
            handleErrorResponse(response, callback);
        }, throwable -> {
            Log.e(TAG, throwable.getMessage());
            callback.onError(new Error(Error.Type.LOST_CONNECTION, throwable.getMessage()));
        });
    }

    @Override
    public void sendReviewAsOwner(int bookingId, byte rate, String review, SimpleCallback callback) {
        ReviewAsOwner reviewAsOwner = new ReviewAsOwner();
        reviewAsOwner.setRating((int) rate);
        reviewAsOwner.setReviewFromOwner(review);

        api.sendReviewAsOwner(bookingId, reviewAsOwner).subscribe(noDataResponse -> {
            if (noDataResponse.isSuccessful()) {
                callback.onSuccess();
                return;
            }
            handleErrorResponse(noDataResponse, callback);
        }, throwable -> {
            Log.e(TAG, throwable.getMessage());
            callback.onError(new Error(Error.Type.LOST_CONNECTION, throwable.getMessage()));
        });
    }

    @Override
    public void sendReviewAsRenter(int bookingId, byte condition, byte comfort, byte owner,
                                   byte overall, String review, SimpleCallback callback) {
        ReviewAsRenter reviewAsRenter = new ReviewAsRenter();
        reviewAsRenter.setCarConditionCleanliness((int) condition);
        reviewAsRenter.setCarComfortPerformance((int) comfort);
        reviewAsRenter.setCarOwner((int) owner);
        reviewAsRenter.setOverallRentalExperience((int) overall);
        reviewAsRenter.setReviewFromRenter(review);

        api.sendReviewAsRenter(bookingId, reviewAsRenter).subscribe(noDataResponse -> {
            if (noDataResponse.isSuccessful()) {
                callback.onSuccess();
                return;
            }
            handleErrorResponse(noDataResponse, callback);
        }, throwable -> {
            Log.e(TAG, throwable.getMessage());
            callback.onError(new Error(Error.Type.LOST_CONNECTION, throwable.getMessage()));
        });
    }

    @Override
    public void getChecklist(int bookingId, ChecklistCallback callback) {
        api.getChecklist(bookingId).subscribe(checklistResponse -> {
            if (checklistResponse.isSuccessful()) {
                callback.onSuccess(checklistResponse.getChecklist());
                return;
            }
            handleErrorResponse(checklistResponse, callback);
        }, throwable -> {
            Log.e(TAG, throwable.getMessage());
            callback.onError(new Error(Error.Type.LOST_CONNECTION, throwable.getMessage()));
        });
    }

    @Override
    public void saveChecklist(int bookingId, String remarks, float tank, int masterMileage, Integer[] imageIds, SimpleCallback callback) {

        ChecklistEntity checklist = new ChecklistEntity();
        checklist.setRemarks(remarks);
        checklist.setTank(tank);
        checklist.setMileage(masterMileage);
        checklist.setImageIds(imageIds);

        api.saveChecklist(bookingId, checklist).subscribe(noDataResponse -> {
            if (noDataResponse.isSuccessful()) {
                callback.onSuccess();
                return;
            }
            handleErrorResponse(noDataResponse, callback);
        }, throwable -> {
            Log.e(TAG, throwable.getMessage());
            callback.onError(new Error(Error.Type.LOST_CONNECTION, throwable.getMessage()));
        });
    }


    @Override
    public void uploadImage(Integer bookingId, Uri uri, ImageCallback callback) {
        if (uri == null || bookingId == null) {
            callback.onError(new Error(Error.Type.INVALID_REQUEST, "Invalid ID: " + bookingId + " or URI: " + uri));
            return;
        }
        String[] split = uri.getPath().split("/");
        File imageFile = new File(CardeeApp.context.getCacheDir(), split[split.length - 1]);
        try {
            InputStream in = CardeeApp.context.getContentResolver().openInputStream(uri);
            boolean resized = imageProcessor.resize(in, imageFile);
            if (!resized) {
                throw new Exception("Failed to resize image");
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            callback.onError(new Error(Error.Type.INTERNAL, e.getMessage()));
            return;
        }
        if (imageFile.exists()) {
            MultipartBody.Part part = MultipartBody.Part.createFormData("photo",
                    imageFile.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), imageFile));
            try {
                Response<UploadBookingImageResponse> response = uploadApi.uploadImage(bookingId, part).execute();
                imageFile.deleteOnExit();
                if (response.isSuccessful() && response.body() != null) {
                    UploadBookingImageResponseBody imageResponse = response.body().getBody();
                    if (imageResponse != null && imageResponse.getClass() != null) {
                        callback.onSuccess(imageResponse.getImageId());
                        return;
                    }
                }
                if (response.code() == 400) {
                    callback.onError(new Error(Error.Type.INVALID_REQUEST, "File already exist. Please choose another photo."));
                    return;
                }
                handleErrorResponse(response.body(), callback);
            } catch (IOException e) {
                callback.onError(new Error(Error.Type.LOST_CONNECTION, e.getMessage()));
            }
        } else {
            callback.onError(new Error(Error.Type.INVALID_REQUEST, "Invalid File path: " + imageFile.getAbsolutePath()));
        }
    }

    @Override
    public Disposable getCostBreakdown(CostRequest request, CostCallback callback) {
        return api.getCostBreakdown(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableMaybeObserver<CostBreakdownResponse>() {
                    @Override
                    public void onSuccess(CostBreakdownResponse response) {
                        if (response.isSuccessful()) {
                            callback.onSuccess(response.getCostBreakdown());
                            return;
                        }
                        handleErrorResponse(response, callback);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new Error(Error.Type.LOST_CONNECTION, Error.Message.CONNECTION_LOST));
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    public void changeBookingState(int bookingId, String state, SimpleCallback callback) {
        api.changeBookingState(bookingId, state).subscribe(response -> {
            if (response.isSuccessful()) {
                callback.onSuccess();
                return;
            }
            handleErrorResponse(response, callback);
        }, throwable -> callback.onError(new Error(Error.Type.LOST_CONNECTION, throwable.getMessage())));
    }

    private void handleErrorResponse(BaseResponse response, BaseCallback bookingsCallback) {
        if (response == null) {
            bookingsCallback.onError(new Error(Error.Type.OTHER, "null"));
            return;
        }
        if (response.getResponseCode() == BaseResponse.ERROR_CODE_INTERNAL_SERVER_ERROR) {
            bookingsCallback.onError(new Error(Error.Type.SERVER, "Server error"));
        } else if (response.getResponseCode() == BaseResponse.ERROR_CODE_UNAUTHORIZED) {
            bookingsCallback.onError(new Error(Error.Type.AUTHORIZATION, "Unauthorized"));
        } else {
            bookingsCallback.onError(new Error(Error.Type.OTHER, "Undefined error"));
            throw new IllegalArgumentException("Unsupported response code: " + response.getResponseCode());
        }
    }
}
