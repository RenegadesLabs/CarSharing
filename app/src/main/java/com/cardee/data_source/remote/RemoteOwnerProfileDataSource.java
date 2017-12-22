package com.cardee.data_source.remote;

import android.util.Log;

import com.cardee.CardeeApp;
import com.cardee.data_source.Error;
import com.cardee.data_source.OwnerProfileDataSource;
import com.cardee.data_source.remote.api.BaseResponse;
import com.cardee.data_source.remote.api.profile.Profile;
import com.cardee.data_source.remote.api.profile.request.ChangeEmailRequest;
import com.cardee.data_source.remote.api.profile.request.ChangeNameRequest;
import com.cardee.data_source.remote.api.profile.request.ChangeNoteRequest;
import com.cardee.data_source.remote.api.profile.request.ChangePhoneRequest;
import com.cardee.data_source.remote.api.profile.request.PassChangeRequest;

import io.reactivex.disposables.Disposable;

public class RemoteOwnerProfileDataSource implements OwnerProfileDataSource {

    private static final String TAG = RemoteOwnerProfileDataSource.class.getSimpleName();
    private static RemoteOwnerProfileDataSource INSTANCE;

    private final Profile mApi;
    private Disposable mDisposable;

    private RemoteOwnerProfileDataSource() {
        mApi = CardeeApp.retrofit.create(Profile.class);
    }

    public static RemoteOwnerProfileDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteOwnerProfileDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void loadOwnerProfile(final ProfileCallback callback) {
//        mApi.loadOwnerProfile().subscribe(ownerProfileResponse -> {
//            if (ownerProfileResponse.isSuccessful()) {
//                callback.onSuccess(ownerProfileResponse.getOwnerProfile());
//                return;
//            }
//            handleErrorResponse(callback, ownerProfileResponse);
//        }, throwable -> {
//            Log.e(TAG, throwable.getMessage());
//            callback.onError(new Error(Error.Type.LOST_CONNECTION, throwable.getMessage()));
//        });
        mDisposable = mApi.loadOwnerProfile()
                .filter(BaseResponse::isSuccessful)
                .doAfterSuccess(ownerProfileResponse -> {
                    if (!ownerProfileResponse.isSuccessful()) {
                        handleErrorResponse(callback, ownerProfileResponse);
                    }
                })
                .subscribe(ownerProfileResponse -> callback.onSuccess(ownerProfileResponse.getOwnerProfile()),
                        throwable -> callback.onError(new Error(Error.Type.LOST_CONNECTION, throwable.getMessage())));
    }

    @Override
    public void changeNote(ChangeNoteRequest noteRequest, final NoResponseCallback callback) {
        mApi.updateOwnerNote(noteRequest).subscribe(noDataResponse -> {
            if (noDataResponse.isSuccessful()) {
                callback.onSuccess();
                return;
            }
            handleErrorResponse(callback, noDataResponse);
        }, throwable -> {
            Log.e(TAG, throwable.getMessage());
            callback.onError(new Error(Error.Type.LOST_CONNECTION, throwable.getMessage()));
        });
    }

    @Override
    public void changePassword(PassChangeRequest request, final NoResponseCallback callback) {
        mApi.changePassword(request).subscribe(noDataResponse -> {
            if (noDataResponse.isSuccessful()) {
                callback.onSuccess();
                return;
            }
            handleErrorResponse(callback, noDataResponse);
        }, throwable -> {
            Log.e(TAG, throwable.getMessage());
            if (throwable.getMessage().equals(Error.Message.WRONG_CREDENTIALS)) {
                callback.onError(new Error(Error.Type.WRONG_AUTHENTICATION, Error.Message.WRONG_AUTHENTICATION));
            } else {
                callback.onError(new Error(Error.Type.LOST_CONNECTION, throwable.getMessage()));
            }
        });
    }

    @Override
    public void changeName(ChangeNameRequest request, final NoResponseCallback callback) {
        mApi.changeName(request).subscribe(noDataResponse -> {
            if (noDataResponse.isSuccessful()) {
                callback.onSuccess();
                return;
            }
            handleErrorResponse(callback, noDataResponse);
        }, throwable -> {
            Log.e(TAG, throwable.getMessage());
            callback.onError(new Error(Error.Type.LOST_CONNECTION, throwable.getMessage()));
        });
    }

    @Override
    public void changeEmail(ChangeEmailRequest request, final NoResponseCallback callback) {
        mApi.changeEmail(request).subscribe(noDataResponse -> {
            if (noDataResponse.isSuccessful()) {
                callback.onSuccess();
                return;
            }
            handleErrorResponse(callback, noDataResponse);
        }, throwable -> {
            Log.e(TAG, throwable.getMessage());
            callback.onError(new Error(Error.Type.LOST_CONNECTION, throwable.getMessage()));
        });
    }

    @Override
    public void changePhone(ChangePhoneRequest request, final NoResponseCallback callback) {
        mApi.changePhone(request).subscribe(noDataResponse -> {
            if (noDataResponse.isSuccessful()) {
                callback.onSuccess();
                return;
            }
            handleErrorResponse(callback, noDataResponse);
        }, throwable -> {
            Log.e(TAG, throwable.getMessage());
            callback.onError(new Error(Error.Type.LOST_CONNECTION, throwable.getMessage()));
        });
    }

    @Override
    public void unSubscribe() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }


    private void handleErrorResponse(BaseCallback callback, BaseResponse response) {
        if (response.getResponseCode() == BaseResponse.ERROR_CODE_INTERNAL_SERVER_ERROR) {
            callback.onError(new Error(Error.Type.SERVER, "Server error"));
        } else if (response.getResponseCode() == BaseResponse.ERROR_CODE_UNAUTHORIZED) {
            callback.onError(new Error(Error.Type.AUTHORIZATION, "Unauthorized"));
        } else {
            callback.onError(new Error(Error.Type.OTHER, "Undefined error"));
            throw new IllegalArgumentException("Unsupported response code: " + response.getResponseCode());
        }
    }
}
